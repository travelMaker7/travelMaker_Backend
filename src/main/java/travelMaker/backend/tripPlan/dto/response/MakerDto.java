package travelMaker.backend.tripPlan.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MakerDto {

    private List<Maker> makers;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Maker{

        private String destinationName;
        private String address;
        private Double destinationX;
        private Double destinationY;
    }
}
