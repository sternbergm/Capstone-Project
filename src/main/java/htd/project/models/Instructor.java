package htd.project.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Instructor {
    @PositiveOrZero
    private int instructorId;
    @NotBlank(message = "firstName is Required")
    @NotNull(message = "firstName cannot be Null")
    @Size(max = 50, message = "firstName cannot be greater than 50 characters")
    private String firstName;
    @NotBlank(message = "lastName is Required")
    @NotNull(message = "lastName cannot be Null")
    @Size(max = 50, message = "lastName cannot be greater than 50 characters")
    private String lastName;

   @PositiveOrZero
    private int yearsOfExperience;
    @NotBlank(message = "expertise is Required")
    @NotNull(message = "expertise cannot be Null")
    @Size(max = 50, message = "expertise cannot be greater than 50 characters")

    private String expertise;

    @NotNull(message = "salary cannot be Null")
    private BigDecimal salary;

}
