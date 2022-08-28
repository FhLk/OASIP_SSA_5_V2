package oasip.Service;

import oasip.DTO.MatchDTO;
import oasip.Entity.EventUser;
import oasip.Repository.UserRepository;
import oasip.Utils.ListMapper;
import oasip.exeption.UserException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {
    @Autowired private UserRepository repository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private ListMapper listMapper;

    public MatchDTO Matching(MatchDTO match)throws UserException,Exception {
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(8,32,1,65536,10);
        List<EventUser> checkPassword = repository.findAll();
        List<EventUser> duplicateEmail = repository.findByEmail(match.getEmail());
        List<EventUser> duplicatePassword = repository.findByPassword(match.getPassword());
        List<String> errors = new ArrayList<>();
        for (EventUser eventUser : checkPassword){
            if (eventUser.getEmail().equals(match.getEmail())){
                if (encoder.matches(match.getPassword(), eventUser.getPassword()) == true){
                    throw new Exception("Password Matched");
                }else {
                    throw new Exception("Password Not Matched");
                }
            }
        }
        if (duplicateEmail.isEmpty()) {
            throw new Exception("A user with the specified email DOES NOT exist");
        }
        return match;
    }
}
