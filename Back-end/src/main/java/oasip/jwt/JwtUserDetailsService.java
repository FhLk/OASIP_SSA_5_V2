package oasip.jwt;


import java.util.ArrayList;
import java.util.List;


import oasip.DTO.UserDTOwithPassword;
import oasip.Entity.EventUser;
import oasip.Repository.UserRepository;
import oasip.exeption.NotfoundEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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
        if (user == null) {
            try{
                throw new NotfoundEx("User not found with mail: " + username);
            } catch (NotfoundEx ex){
                throw new RuntimeException(ex);
            }
        }
        return new User(user.get(0).getEmail(),user.get(0).getPassword(),new ArrayList<>());
    }


}