package oasip.Repository;

import oasip.Entity.Eventbooking;
import oasip.Entity.Eventcategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventEventBookingRepository extends JpaRepository<Eventbooking,Integer> {
    List<Eventbooking> findAllByCategoryOrderByStartTimeDesc(Pageable page, Eventcategory category);
    List<Eventbooking> findAllByStartTimeLessThanOrderByStartTimeDesc(Pageable page, LocalDateTime localDateTime);
    List<Eventbooking> findAllByStartTimeBetweenOrderByStartTimeAsc(Pageable page,LocalDateTime startdate,LocalDateTime enddate);
}
