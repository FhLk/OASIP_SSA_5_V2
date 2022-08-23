package oasip.Repository;

import oasip.Entity.EventBooking;
import oasip.Entity.EventCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<EventBooking,Integer> {
    List<EventBooking> findAllByCategoryOrderByStartTimeDesc(Pageable page, EventCategory category);
    List<EventBooking> findAllByStartTimeLessThanOrderByStartTimeDesc(Pageable page, LocalDateTime localDateTime);
    List<EventBooking> findAllByStartTimeBetweenOrderByStartTimeAsc(Pageable page, LocalDateTime startdate, LocalDateTime enddate);
}
