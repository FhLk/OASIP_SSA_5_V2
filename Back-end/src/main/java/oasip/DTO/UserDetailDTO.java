package oasip.DTO;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDetailDTO {
    private String name;
    private String email;
    private String role;
    private LocalDateTime createdOn;

    public String getCreatedOn(){
        return this.createdOn.toString();
    }
    private LocalDateTime updateOn;
}
