package oasip.Service;

import oasip.DTO.UserDTO;
import oasip.DTO.UserDetailDTO;
import oasip.Entity.EventUser;
import oasip.Repository.UserRepository;
import oasip.Utils.ListMapper;
import oasip.exeption.BookingException;
import oasip.exeption.UserException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;

    public List<UserDTO> getUsers(int page, int padeSize, String sort) {
        List<EventUser> userlist = repository.findAll(PageRequest.of(page, padeSize, Sort.by(Sort.Direction.ASC, sort))).getContent();
        return listMapper.mapList(userlist, UserDTO.class, modelMapper);
    }

    public UserDetailDTO getUserDetail(Integer id) {
        EventUser user = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User" + id + "does not exits."));
        return modelMapper.map(user, UserDetailDTO.class);
    }

    public EventUser NewUser(@Valid UserDTO newUser) throws UserException {
        newUser.setName(newUser.getName().trim());
        newUser.setEmail(newUser.getEmail().trim());
        newUser.setRole(newUser.getRole().trim().toLowerCase());
        EventUser user = modelMapper.map(newUser, EventUser.class);
        List<EventUser> duplicateName = repository.findByName(user.getName());
        List<EventUser> duplicateEmail = repository.findByEmail(user.getEmail());
        List<String> errors = new ArrayList<>();
        if (!duplicateName.isEmpty() && !duplicateEmail.isEmpty()) {
            errors.add("This Username and Email are already use!!!");
            throw new UserException(errors.toString());
        }
        if (!duplicateName.isEmpty()) {
            errors.add("This Username is already use!!!");

            throw new UserException(errors.toString());
        }
        if (!duplicateEmail.isEmpty()) {
            errors.add("This Email is already use!!!");
            throw new UserException(errors.toString());
        }
        return repository.saveAndFlush(user);
    }

    public EventUser UpdateUser(Integer id,@Valid UserDetailDTO updateUser) throws BookingException{
        UserDetailDTO oldUser = getUserDetail(id);
        List<String> errors=new ArrayList<>();
        if (!oldUser.getName().equals(updateUser.getName())){
            errors.add("Username is already use");
            if (!oldUser.getEmail().equals(updateUser.getEmail())){
                errors.add("Email is already use");
            }
            throw new BookingException(errors.toString());
        }
//        updateUser.setName(updateUser.getName().trim());
//        updateUser.setEmail(updateUser.getEmail().trim());
//        updateUser.setRole(updateUser.getRole().trim());
        EventUser users = repository.findById(id).map(b->mapUser(modelMapper.map(b,UserDetailDTO.class),updateUser))
                .orElseGet(()->{
                    updateUser.setId(id);
                    return modelMapper.map(updateUser,EventUser.class);
                });
        return repository.saveAndFlush(users);
    }
    
    private EventUser mapUser(UserDetailDTO oldUser,UserDetailDTO newUser){
        oldUser.setName(newUser.getName().trim());
        oldUser.setEmail(newUser.getEmail().trim());
        oldUser.setRole(newUser.getRole().trim());
        return modelMapper.map(oldUser,EventUser.class);
    }

    public void DeleteUser(Integer id){
        repository.deleteById(id);
    }

}

