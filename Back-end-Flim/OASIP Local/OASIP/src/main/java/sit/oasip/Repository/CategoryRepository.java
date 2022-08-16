package sit.oasip.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.oasip.Entity.EventCategory;

public interface CategoryRepository extends JpaRepository<EventCategory,Integer> {
}
