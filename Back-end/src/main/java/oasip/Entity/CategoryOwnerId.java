package oasip.Entity;

import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class CategoryOwnerId implements Serializable {
    private static final long serialVersionUID = 5492661979618626711L;
    @Column(name = "eventcategory_category_id", nullable = false)
    private Integer eventCategoryOwner;
    @Column(name = "user_id_lecturer", nullable = false)
    private Integer userIdLecturer;

    public Integer getUserIdLecturer() {
        return userIdLecturer;
    }

    public void setUserIdLecturer(Integer userIdLecturer) {
        this.userIdLecturer = userIdLecturer;
    }

    public Integer getEventCategoryId() {
        return eventCategoryOwner;
    }

    public void setEventCategoryId(Integer eventCategoryOwnerId) {
        this.eventCategoryOwner = eventCategoryOwnerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userIdLecturer, eventCategoryOwner);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CategoryOwnerId entity = (CategoryOwnerId) o;
        return Objects.equals(this.userIdLecturer, entity.userIdLecturer) &&
                Objects.equals(this.eventCategoryOwner, entity.eventCategoryOwner);
    }
}