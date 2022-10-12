package oasip.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "eventcategory")
public class EventCategory {
    @Id
    @Column(name = "category_id", nullable = false)
    private Integer id;

    @Column(name = "category_name", nullable = false, length = 100)
    private String categoryName;

    @Column(name = "description", length = 550)
    private String description;

    @Column(name = "duration", nullable = false)
    private Integer duration;


}