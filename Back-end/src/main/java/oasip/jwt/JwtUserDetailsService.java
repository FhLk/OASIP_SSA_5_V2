package oasip.jwt;


import java.util.ArrayList;
import java.util.List;


import oasip.DTO.UserDTOwithPassword;
import oasip.Entity.EventUser;
import oasip.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<EventUser> user = repository.findByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with mail: " + username);
        }
        return new User(user.get(0).getEmail(),user.get(0).getPassword(),new ArrayList<>());
//        if ("javainuse".equals(username)) {
//            return new User("javainuse", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
//                    new ArrayList<>());
//        } else {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
    }


}