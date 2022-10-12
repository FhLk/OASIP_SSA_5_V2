package oasip.Service;

import lombok.extern.slf4j.Slf4j;
import oasip.Entity.User;
import oasip.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service @Slf4j
public class TokenService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =  repository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Email not found" + email);
//            try{
//                throw new NotfoundEx("User not found with mail: " + email);
//            } catch (NotfoundEx ex){
//                throw new RuntimeException(ex);
//            }
//            log.error("Email Not Found");
//            throw new UsernameNotFoundException("Email Not Found");
//            try{
//                throw new NotfoundEx("Email does not exist");
//            } catch (NotfoundEx ex){
//                throw new RuntimeException(ex);
//            }
        }
//        else {
//            log.info("Email is Founding: {}", email);
//        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }
}
