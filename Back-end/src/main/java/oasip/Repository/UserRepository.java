package oasip.Repository;

import oasip.DTO.UserDTO;
import oasip.Entity.EventUser;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<EventUser,Integer> {
    List<EventUser> findByName(String name);
    List<EventUser> findByEmail(String email);
    List<EventUser> findByRole(String role);


}
