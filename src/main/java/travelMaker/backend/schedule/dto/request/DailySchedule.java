package travelMaker.backend.schedule.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class DailySchedule{

    @NotNull(message = "날짜를 입력해 주세요")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "일정 날짜", example = "2024-05-10")
    private LocalDate scheduledDate;

    @Valid
    @NotEmpty(message = "여행할 도시를 최소 한 개 이상 입력해 주세요.")
    private List<DestinationDetail> details;


}
