package oasip.Repository;

import oasip.Entity.Event;
import oasip.Entity.EventCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Event,Integer> {
    List<Event> findAllByCategoryOrderByStartTimeDesc(Pageable page, EventCategory category);
    List<Event> findAllByStartTimeLessThanOrderByStartTimeDesc(Pageable page, LocalDateTime localDateTime);
    List<Event> findAllByStartTimeBetweenOrderByStartTimeAsc(Pageable page, LocalDateTime starter, LocalDateTime end);

    Event findByIdAndBookingEmail(Integer id, String email);
    List<Event> findByBookingEmail(String email, Pageable page);
    List<Event> findByBookingEmailAndCategoryOrderByStartTimeDesc(Pageable page, String email, EventCategory category);
    List<Event> findByBookingEmailAndStartTimeLessThanOrderByStartTimeDesc(Pageable page, String email, LocalDateTime localDateTime);
    List<Event> findByBookingEmailAndStartTimeBetweenOrderByStartTimeAsc(Pageable page, String email, LocalDateTime starter, LocalDateTime end);
    List<Event> findByCategoryAndStartTimeLessThanOrderByStartTimeDesc(EventCategory eventCategory,Pageable page, LocalDateTime localDateTime);
    List<Event> findByCategoryAndCategoryOrderByStartTimeDesc(EventCategory eventCategory, Pageable page, EventCategory category);

    List<Event> findByCategoryAndStartTimeBetweenOrderByStartTimeAsc(EventCategory eventCategory,Pageable page, LocalDateTime starter, LocalDateTime end);


    List<Event> findByCategory(EventCategory eventCategory);
    List<Event> findAllById(Integer id);

    List<Event> findEventByCategory_Id(Integer id);
}
