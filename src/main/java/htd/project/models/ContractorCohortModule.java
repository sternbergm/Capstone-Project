package htd.project.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractorCohortModule {

    @PositiveOrZero(message = "Must be valid Contractor id")
    private int ContractorId;

    @PositiveOrZero(message = "Must be valid Cohort id")
    private int CohortId ;

    @PositiveOrZero(message = "Must be valid Module id")
    private int moduleId;

    @NotNull(message = "grade cannot be null")
    @DecimalMax(value = "100.00", message = "grade cannot exceed 100%")
    @DecimalMin(value = "0.00", message = "grade cannot be below 0%")
    private BigDecimal grade;

}
