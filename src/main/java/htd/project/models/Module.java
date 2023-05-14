package htd.project.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Module {

    private int moduleId;

    @NotNull(message = "Topic cannot be null")
    @NotBlank(message = "Topic cannot be blank")
    @Size(max = 50, message = "Topic cannot be greater than 50 characters")
    private String topic;

    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    private LocalDate endDate;

    @Min(value = 0, message = "amount of exercises must be between 0 and 15")
    @Max(value = 15, message = "amount of exercises must be between 0 and 15")
    private int exerciseAmount;

    @Min(value = 0, message = "amount of lessons must be between 0 and 20")
    @Max(value = 20, message = "amount of exercises must be between 0 and 20")
    private int lessonAmount;

}
