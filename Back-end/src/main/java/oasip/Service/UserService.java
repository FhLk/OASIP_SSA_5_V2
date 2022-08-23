package oasip.Service;

import oasip.DTO.UserDTO;
import oasip.DTO.UserDetailDTO;
import oasip.Entity.EventUser;
import oasip.Repository.UserRepository;
import oasip.Utils.ListMapper;
import oasip.exeption.UserException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


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

    public UserDetailDTO getUserDetail(String userName) {
        EventUser user = repository.findById(userName).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User" + userName + "does not exits."));
        return modelMapper.map(user, UserDetailDTO.class);
    }

    public EventUser NewUser(@Valid UserDTO newUser) throws UserException {
        newUser.setName(newUser.getName().trim());
        newUser.setEmail(newUser.getEmail().trim());
        EventUser user = modelMapper.map(newUser, EventUser.class);
        List<EventUser> duplicateName = repository.findByName(user.getName());
        List<EventUser> duplicateEmail = repository.findByEmail(user.getEmail());
        List<EventUser> roles = repository.findByRole(user.getRole());
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
//        if(!newUser.getRole().equals(EnumRole.class)){
//            errors.add("Don't have this role!!!");
//            throw new UserException(errors.toString());
//        }
//        if(newUser.getRole() == "") {
//
//            for (EnumRole role : EnumRole.values()) {
//                if () {
//                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This role is already use!!!");
//                }
//            }
//        }
        return repository.saveAndFlush(user);
    }
    public void DeleteUser(String Username){
        repository.deleteById(Username);
    }
}

