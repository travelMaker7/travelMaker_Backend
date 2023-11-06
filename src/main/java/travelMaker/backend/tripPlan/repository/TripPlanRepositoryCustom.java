package travelMaker.backend.tripPlan.repository;

import travelMaker.backend.tripPlan.dto.response.SearchRegionDto;

public interface TripPlanRepositoryCustom {

    SearchRegionDto findTripPlansByRegionAndCoordinates(String region, Double destinationX, Double destinationY);
}
