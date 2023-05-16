package htd.project.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Module module = (Module) o;
        return Objects.equals(topic, module.topic) && Objects.equals(startDate, module.startDate) && Objects.equals(endDate, module.endDate);
    }
}
