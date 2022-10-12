package oasip.Repository;

import oasip.Entity.Event;
import oasip.Entity.EventCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<EventCategory,Integer> {
//    EventCategory findById(Integer id);

}
