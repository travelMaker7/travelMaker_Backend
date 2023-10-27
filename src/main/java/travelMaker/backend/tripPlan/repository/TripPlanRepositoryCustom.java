package travelMaker.backend.tripPlan.repository;

import travelMaker.backend.tripPlan.dto.response.SearchRegionDto;

public interface TripPlanRepositoryCustom {

    SearchRegionDto searchTripPlan(String region, String destinationName);
}
