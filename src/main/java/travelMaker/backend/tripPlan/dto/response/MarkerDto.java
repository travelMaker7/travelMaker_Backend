package travelMaker.backend.tripPlan.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarkerDto {

    private List<Marker> makers;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Marker{

        private String destinationName;
        private String address;
        private Double destinationX;
        private Double destinationY;
    }
}
