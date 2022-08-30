package oasip.exeption;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShowException{
    private Integer statusCode;
    private String status;
    private Map<String,String> message;
}
