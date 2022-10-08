package oasip.Repository;

import oasip.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    List<User> findByName(String name);
//    List<EventUser> findByEmail(String email);

    User findByEmail(String email);

}
