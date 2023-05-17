package htd.project.models;

import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contractor {
    @PositiveOrZero
    private int contractorId;
    @NotBlank(message = "firstName is Required")
    @NotNull(message = "firstName cannot be Null")
    @Size(max = 50, message = "firstName cannot be greater than 50 characters")
   private String firstName;
    @NotBlank(message = "lastName is Required")
    @NotNull(message = "lastName cannot be Null")
    @Size(max = 50, message = "lastName cannot be greater than 50 characters")

    private String lastName;
    @NotBlank(message = "dateOfBirth is Required")
    private Date dateOfBirth;

    @NotBlank(message = "address is Required")
    @NotNull(message = "address cannot be Null")
    @Size(max = 50, message = "address cannot be greater than 50 characters")
    private String address;

    @NotBlank(message = " email is Required")
    @NotNull(message = " email cannot be Null")
    @Size(max = 50, message = " email cannot be greater than 50 characters")
    private String email;

    @NotBlank(message = " salary is Required")
    @NotNull(message = " salary cannot be Null")
    @Size(max = 50, message = "salary cannot be greater than 50 characters")
    private BigDecimal salary;
    @BooleanFlag
    public boolean isHired;


}
