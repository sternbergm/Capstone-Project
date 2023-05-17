package htd.project.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @PositiveOrZero
    private int clientId;
    @NotBlank(message = "clientName is required")
    @NotNull(message = "Name cannot be Null")
    @Size(max = 50, message = "clientName cannot be greater than 50 characters")
    private String clientName;
    @NotBlank(message = "Address is Required")
    @NotNull(message = "Address cannot be Null")
    @Size(max = 50, message = "Address cannot be greater than 50 characters")
    private String address;

    @PositiveOrZero
    private int companySize;
    @NotBlank(message = "email is Required")
    @NotNull(message = "email cannot be Null")
    @Size(max = 50, message = "email cannot be greater than 50 characters")
    private String email;

}
