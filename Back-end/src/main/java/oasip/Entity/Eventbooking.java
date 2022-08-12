package oasip.Entity;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "eventbooking")
public class Eventbooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id", nullable = false)
    private Integer id;

    @Column(name = "booking_name", nullable = false, length = 150)
    private String bookingName;

    @Column(name = "booking_email", nullable = false, length = 150)
    private String bookingEmail;

    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @Column(name = "booking_duration", nullable = false)
    private Integer bookingDuration;

    @Column(name = "event_note", length = 550)
    private String eventNote;

    @Column(name = "update_date", nullable = false)
    private Instant updateDate;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category", nullable = false)
    private Eventcategory category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookingName() {
        return bookingName;
    }

    public void setBookingName(String bookingName) {
        this.bookingName = bookingName;
    }

    public String getBookingEmail() {
        return bookingEmail;
    }

    public void setBookingEmail(String bookingEmail) {
        this.bookingEmail = bookingEmail;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Integer getBookingDuration() {
        return bookingDuration;
    }

    public void setBookingDuration(Integer bookingDuration) {
        this.bookingDuration = bookingDuration;
    }

    public String getEventNote() {
        return eventNote;
    }

    public void setEventNote(String eventNote) {
        this.eventNote = eventNote;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public Eventcategory getCategory() {
        return category;
    }

    public void setCategory(Eventcategory category) {
        this.category = category;
    }

}