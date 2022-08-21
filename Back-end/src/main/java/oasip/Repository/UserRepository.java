package oasip.Repository;

import oasip.DTO.UserDTO;
import oasip.Entity.EventUser;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<EventUser,String> {
    List<EventUser> findByName(String name);
    List<EventUser> findByEmail(String email);
}
