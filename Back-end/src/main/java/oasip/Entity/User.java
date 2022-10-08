package oasip.Entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Lob
    @Column(name = "role", nullable = false)
    private String role = "STUDENT";

    @Column(name = "created_on", nullable = false,insertable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name = "update_on", nullable = false,insertable = false,updatable = false)
    @CreationTimestamp
    @LastModifiedBy
    private LocalDateTime updateOn;

    @Column(name = "password",nullable = false,length = 100,updatable = false)
    private String password;

    public void setUpdateOn(String updateOn) {
        this.updateOn = LocalDateTime.parse(updateOn);
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = LocalDateTime.parse(createdOn);
    }
}