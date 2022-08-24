package oasip.DTO;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDetailDTO {
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
    private String role;
    private LocalDateTime createdOn;

    public String getCreatedOn(){
        return this.createdOn.toString();
    }
    private LocalDateTime updateOn;
    public String getUpdateOn(){
        return this.updateOn.toString();
    }
}
