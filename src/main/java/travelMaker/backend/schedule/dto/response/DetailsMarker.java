package travelMaker.backend.schedule.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class DetailsMarker {

    @Schema(description = "목적지 Y 좌표", example = "37.123456")
    private Double destinationY;

    @Schema(description = "목적지 X 좌표", example = "126.789012")
    private Double destinationX;

}
