package oasip.Repository;

import oasip.Entity.Event;
import oasip.Entity.EventCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Event,Integer> {
    List<Event> findAllByCategoryOrderByStartTimeDesc(Pageable page, EventCategory category);
    List<Event> findAllByStartTimeLessThanOrderByStartTimeDesc(Pageable page, LocalDateTime localDateTime);
    List<Event> findAllByStartTimeBetweenOrderByStartTimeAsc(Pageable page, LocalDateTime startdate, LocalDateTime enddate);

    Event findByIdAndBookingEmail(Integer id, String email);
    List<Event> findByBookingEmail(String email, Pageable page);
    List<Event> findByBookingEmailAndCategoryOrderByStartTimeDesc(Pageable page, String email, EventCategory category);
    List<Event> findByBookingEmailAndStartTimeLessThanOrderByStartTimeDesc(Pageable page, String email, LocalDateTime localDateTime);
    List<Event> findByBookingEmailAndStartTimeBetweenOrderByStartTimeAsc(Pageable page, String email, LocalDateTime startdate, LocalDateTime enddate);

    List<Event> findByCategory(EventCategory eventCategory);
    List<Event> findByCategory_Id(Integer id);

    List<Event> findAllById(Integer id);

}
