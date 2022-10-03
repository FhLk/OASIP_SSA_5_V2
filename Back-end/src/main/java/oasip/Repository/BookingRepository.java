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

    EventBooking findByIdAndBookingEmail(Integer id,String email);
    List<EventBooking> findByBookingEmail(String email,Pageable page);
    List<EventBooking> findByBookingEmailAndCategoryOrderByStartTimeDesc(Pageable page,String email,EventCategory category);
    List<EventBooking> findByBookingEmailAndStartTimeLessThanOrderByStartTimeDesc(Pageable page,String email,LocalDateTime localDateTime);
    List<EventBooking> findByBookingEmailAndStartTimeBetweenOrderByStartTimeAsc(Pageable page,String email,LocalDateTime startdate, LocalDateTime enddate);
}
