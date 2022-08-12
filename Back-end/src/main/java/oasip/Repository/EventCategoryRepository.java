package oasip.Repository;

import oasip.Entity.Eventcategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCategoryRepository extends JpaRepository<Eventcategory,Integer> {
}
