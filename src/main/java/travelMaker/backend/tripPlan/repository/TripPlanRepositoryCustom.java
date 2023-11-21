package travelMaker.backend.tripPlan.repository;

import travelMaker.backend.tripPlan.dto.request.SearchRequest;
import travelMaker.backend.tripPlan.dto.response.SearchRegionDto;
import travelMaker.backend.tripPlan.model.TripPlan;

import java.util.List;

public interface TripPlanRepositoryCustom {
    SearchRegionDto findTripPlansByRegionAndCoordinates(String region, Double destinationX, Double destinationY);
    SearchRegionDto searchTripPlan(SearchRequest searchRequest, Double destinationX, Double destinationY);
    List<TripPlan> findTripPlansByConditions(SearchRequest searchRequest);

}
