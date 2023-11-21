package travelMaker.backend.tripPlan.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Getter
@Setter
public class SearchRequest {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetFinishDate;
    private String ageRange;
    private String gender;
    private Integer minPerson;
    private Integer maxPerson;
    private String region;

    public SearchRequest(LocalDate targetStartDate, LocalDate targetFinishDate, String ageRange, String gender, Integer minPerson, Integer maxPerson, String region) {
        this.targetStartDate = targetStartDate;
        this.targetFinishDate = targetFinishDate;
        this.ageRange = ageRange;
        this.gender = gender;
        this.minPerson = minPerson;
        this.maxPerson = maxPerson;
        this.region = region;
    }
}