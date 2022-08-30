package oasip.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MatchDTO {

    @Email(message = "Email doesn't follow format")
    @NotNull(message = "Email shouldn't be null")
    @NotEmpty(message = "Email shouldn't be empty")
    @Size(max = 50,message = "Your Email have length more than 50 character")
    private String email;

    @NotNull
    @NotBlank(message = "Email shouldn't be blank")
    @NotEmpty(message = "Email shouldn't be empty")
    @Size(min = 8,max = 14,message = "Your password doesn't in length")
    private String password;
}
