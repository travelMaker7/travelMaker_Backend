package travelMaker.backend.tripPlan.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelMaker.backend.tripPlan.dto.response.SearchRegionDto;
import travelMaker.backend.tripPlan.repository.TripPlanRepository;

@Service
@RequiredArgsConstructor
public class TripPlanService {
    private final TripPlanRepository tripPlanRepository;
    public SearchRegionDto searchRegion(String region, String destinationName) {

        return tripPlanRepository.searchTripPlan(region, destinationName);
    }
}
