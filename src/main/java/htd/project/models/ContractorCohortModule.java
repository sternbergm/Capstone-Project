package htd.project.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractorCohortModule {

    private int ContractorId;
    private int CohortId ;
    private int moduleId;

    @NotNull(message = "grade cannot be null")
    private BigDecimal grade;

}
