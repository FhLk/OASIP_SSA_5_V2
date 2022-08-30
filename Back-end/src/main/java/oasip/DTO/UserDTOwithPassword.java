package oasip.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import oasip.Utils.EnumRole;
import oasip.exeption.EnumValidator;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTOwithPassword {
    private Integer id;

    @NotNull(message = "Name shouldn't be null")
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(max = 100,message = "Your Name have length more than 100 character")
    private String name;

    @Email(message = "Email doesn't follow format")
    @NotNull(message = "Email shouldn't be null")
    @NotEmpty(message = "Email shouldn't be empty")
    @Size(max = 50,message = "Your Email have length more than 50 character")
    private String email;

    @NotNull(message = "Role shouldn't be null")
    @EnumValidator(enumClass = EnumRole.class)
    private String role;

    @NotNull
    @NotBlank(message = "Email shouldn't be blank")
    @NotEmpty(message = "Email shouldn't be empty")
    @Size(min = 8,max = 14,message = "Your password doesn't in length")
    private String password;

    public void setRole(String role) {
        this.role =role.toLowerCase();
        if (role.trim() == ""){
            this.role = "student";
        }
    }

    public void setEmail(String email){
        this.email = email.trim();
    }
}
