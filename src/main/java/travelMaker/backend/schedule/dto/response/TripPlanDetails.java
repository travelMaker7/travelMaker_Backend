package travelMaker.backend.schedule.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;


@Getter
@Builder
@AllArgsConstructor
@ToString
public class TripPlanDetails {

    private Long tripPlanId;

    private String destinationName;

    private boolean overWish;

    private Integer joinCnt;

    private Integer wishCnt;

    private boolean wishJoin;

    private String address;

    private LocalTime arriveTime;

    private LocalTime leaveTime;
}
