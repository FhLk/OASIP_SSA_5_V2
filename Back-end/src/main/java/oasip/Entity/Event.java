package oasip.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "eventbooking")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id", nullable = false)
    private Integer id;

    @Column(name = "booking_name", nullable = false, length = 150)
    private String bookingName;

    @Column(name = "booking_email", nullable = false, length = 150)
    private String bookingEmail;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "booking_duration", nullable = false)
    private Integer bookingDuration;

    @Column(name = "event_note", length = 550)
    private String eventNote;

    public LocalDateTime getStartTime() {return startTime;}
    public void setStartTime(String startTime) {
        this.startTime = LocalDateTime.parse(startTime);
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category", nullable = false)
    private EventCategory category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;




}