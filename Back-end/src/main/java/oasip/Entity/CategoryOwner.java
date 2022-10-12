package oasip.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "eventcategory_owner")
public class CategoryOwner {
    @EmbeddedId
    private CategoryOwnerId id;

    @MapsId("eventCategoryOwner")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "eventcategory_category_id", nullable = false)
    private EventCategory eventCategoryOwner;

    @MapsId("userIdLecturer")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id_lecturer", nullable = false)
    private User userIdLecturer;

    public User getUserIdLecturer() {
        return userIdLecturer;
    }

    public void setUserIdLecturer(User userIdLecturer) {
        this.userIdLecturer = userIdLecturer;
    }

    public EventCategory getEventcategory() {
        return eventCategoryOwner;
    }

    public void setEventcategory(EventCategory eventCategoryOwner) {
        this.eventCategoryOwner = eventCategoryOwner;
    }

    public CategoryOwnerId getId() {
        return id;
    }

    public void setId(CategoryOwnerId id) {
        this.id = id;
    }
}