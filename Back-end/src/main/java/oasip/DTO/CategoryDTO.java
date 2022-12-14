package oasip.DTO;

import lombok.*;
import javax.validation.constraints.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Integer id;
    @NotBlank(message = "Category name shouldn't null/empty")
    @NotEmpty(message = "Category name shouldn't empty")
    @NotNull(message = "Category name shouldn't null")
    @Size(max = 100,message = "Category Name have length more than 100 character")
    private String categoryName;

    @Size(max = 500,message = "Category description have length more than 500 character")
    private String description;

    @Max(value = 480,message = "Duration have more than 480 minute")
    @Min(value = 1,message = "Duration have less 1 minute")
    @NotNull(message = "Duration shouldn't be null")
    private Integer duration;

}
