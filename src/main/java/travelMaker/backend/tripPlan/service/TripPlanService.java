package travelMaker.backend.tripPlan.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelMaker.backend.JoinRequest.model.JoinStatus;
import travelMaker.backend.JoinRequest.repository.JoinRequestRepository;
import travelMaker.backend.common.error.ErrorCode;
import travelMaker.backend.common.error.GlobalException;
import travelMaker.backend.schedule.dto.request.DestinationDetail;
import travelMaker.backend.schedule.model.Schedule;
import travelMaker.backend.schedule.repository.ScheduleRepository;
import travelMaker.backend.tripPlan.dto.request.UpdateTripPlanDto;
import travelMaker.backend.tripPlan.dto.response.MakerDto;
import travelMaker.backend.tripPlan.model.TripPlan;

import java.util.ArrayList;
import java.util.List;
import travelMaker.backend.tripPlan.dto.response.SearchRegionDto;
import travelMaker.backend.tripPlan.repository.TripPlanRepository;
import travelMaker.backend.user.login.LoginUser;
import travelMaker.backend.user.model.User;
import travelMaker.backend.user.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class TripPlanService {
    private final TripPlanRepository tripPlanRepository;
    private final JoinRequestRepository joinRequestRepository;
    private final ScheduleRepository scheduleRepository;
    @Transactional(readOnly = true)
    public MakerDto getAllMaker(String region) {
        List<TripPlan> regionTripPlans = tripPlanRepository.findByRegion(region);
        List<MakerDto.Maker> makers = new ArrayList<>();
        for (TripPlan regionTripPlan : regionTripPlans) {
            MakerDto.Maker maker = MakerDto.Maker.builder()
                    .destinationName(regionTripPlan.getDestinationName())
                    .address(regionTripPlan.getAddress())
                    .destinationX(regionTripPlan.getDestinationX())
                    .destinationY(regionTripPlan.getDestinationY())
                    .build();
            makers.add(maker);

        }
        return MakerDto.builder()
                .makers(makers)
                .build();
    }
    @Transactional(readOnly = true)
    public SearchRegionDto searchTripPlans(String region, String destinationX, String destinationY) {
        Double x = Double.valueOf(destinationX);
        Double y = Double.valueOf(destinationY);
        return tripPlanRepository.findTripPlansByRegionAndCoordinates(region, x, y);
    }
    @Transactional
    public void updateTripPlan(
            Long scheduleId,
            Long tripPlanId,
            UpdateTripPlanDto updateTripPlanDto,
            LoginUser loginUser
    ){
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new GlobalException(ErrorCode.SCHEDULE_NOT_FOUND));
        if(schedule.getUser().getUserId() != loginUser.getUser().getUserId()){
            throw new GlobalException(ErrorCode.SCHEDULE_NOT_OWNED_BY_USER);
        }
        TripPlan tripPlan = tripPlanRepository.findById(tripPlanId).orElseThrow(() -> new GlobalException(ErrorCode.TRIP_PLAN_NOT_FOUND));
        int reservedUserCnt = joinRequestRepository.findByTripPlanIdAndJoinStatus(tripPlanId, JoinStatus.신청수락).size();

        if(reservedUserCnt > 0){
            throw new GlobalException(ErrorCode.TRIP_PLAN_UPDATE_FAIL);
        }
        if(updateTripPlanDto.getDestinationName() != null){
            tripPlan.addDestinationName(updateTripPlanDto.getDestinationName());
        }
        if(updateTripPlanDto.isWishJoin()){
            //동행을 희망하는 경우
            tripPlan.addWishJoin(true);
            if(updateTripPlanDto.getWishCnt() != null){
                tripPlan.addWishCnt(updateTripPlanDto.getWishCnt());
            }
            if(updateTripPlanDto.getArriveTime() != null){
                tripPlan.addArriveTime(updateTripPlanDto.getArriveTime());
            }
            if(updateTripPlanDto.getLeaveTime() != null){
                tripPlan.addLeaveTime(updateTripPlanDto.getLeaveTime());
            }

        }else{
            tripPlan.addWishJoin(false);
            tripPlan.addWishCnt(0);
            tripPlan.addArriveTime(null);
            tripPlan.addLeaveTime(null);
        }
        if(updateTripPlanDto.getAddress() != null){
            tripPlan.addAddress(updateTripPlanDto.getAddress());
        }
        if(updateTripPlanDto.getDestinationX() != null){
            tripPlan.addDestinationX(updateTripPlanDto.getDestinationX());
        }
        if(updateTripPlanDto.getDestinationY() != null){
            tripPlan.addDestinationY(updateTripPlanDto.getDestinationY());
        }
        if(updateTripPlanDto.getRegion() != null){
            tripPlan.addRegion(updateTripPlanDto.getRegion());
        }

    }
    @Transactional
    public void deleteTripPlan(Long scheduleId, Long tripPlanId, LoginUser loginUser) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new GlobalException(ErrorCode.SCHEDULE_NOT_FOUND));
        if(schedule.getUser().getUserId() != loginUser.getUser().getUserId()){
            throw new GlobalException(ErrorCode.SCHEDULE_NOT_OWNED_BY_USER);
        }

        TripPlan tripPlan = tripPlanRepository.findById(tripPlanId).orElseThrow(() -> new GlobalException(ErrorCode.TRIP_PLAN_NOT_FOUND));
        int reservedUserCnt = joinRequestRepository.findByTripPlanIdAndJoinStatus(tripPlanId, JoinStatus.신청수락).size();


        if(reservedUserCnt > 0){
            throw new GlobalException(ErrorCode.TRIP_PLAN_DELETE_FAIL);
        }
        tripPlanRepository.delete(tripPlan);
    }


}
