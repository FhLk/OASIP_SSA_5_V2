package oasip.DTO;

import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDetailDTO {
    private String name;
    private String email;
    private String role;
    private LocalDateTime createOn;
    private LocalDateTime updateOn;

    public  String getCreateOn(){return  createOn.toString();}

    public  void setCreateOn(String createOn){this.createOn = LocalDateTime.parse(createOn);}
}
