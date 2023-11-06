package travelMaker.backend.tripPlan.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelMaker.backend.tripPlan.dto.response.MakerDto;
import travelMaker.backend.tripPlan.model.TripPlan;

import java.util.ArrayList;
import java.util.List;
import travelMaker.backend.tripPlan.dto.response.SearchRegionDto;
import travelMaker.backend.tripPlan.repository.TripPlanRepository;


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
    
    public SearchRegionDto searchTripPlans(String region, String destinationX, String destinationY) {
        Double x = Double.valueOf(destinationX);
        Double y = Double.valueOf(destinationY);
        return tripPlanRepository.findTripPlansByRegionAndCoordinates(region, x, y);
    }
    
}
