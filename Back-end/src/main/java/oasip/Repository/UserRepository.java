package oasip.Repository;

import oasip.Entity.EventUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<EventUser,String> {
}
