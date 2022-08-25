package oasip.Entity;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class EventUser {
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
    private String role = "student";

    @Column(name = "created_on", nullable = false,insertable = false)
    private LocalDateTime createdOn;

    @Column(name = "update_on", nullable = false,insertable = false,updatable = false)
    private LocalDateTime updateOn;

    public Integer getId(){return  id;}

    public void setId(Integer id){this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public LocalDateTime getCreatedOn() {
        return createdOn;
    }
    public LocalDateTime getUpdateOn() {
        return updateOn;
    }

    public void setUpdateOn(String updateOn) {
        this.updateOn = LocalDateTime.parse(updateOn);
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = LocalDateTime.parse(createdOn);
    }


}