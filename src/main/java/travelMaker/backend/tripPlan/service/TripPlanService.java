package travelMaker.backend.tripPlan.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelMaker.backend.tripPlan.dto.response.MakerDto;
import travelMaker.backend.tripPlan.model.TripPlan;
import travelMaker.backend.tripPlan.repository.TripPlanRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripPlanService {

    private final TripPlanRepository tripPlanRepository;

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
}
