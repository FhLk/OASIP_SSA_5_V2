package oasip.Service;

import oasip.DTO.UserDTO;
import oasip.DTO.UserDTOwithPassword;
import oasip.DTO.UserDetailDTO;
import oasip.Entity.User;
import oasip.Repository.UserRepository;
import oasip.Utils.ListMapper;
import oasip.exeption.BookingException;
import oasip.exeption.UserException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService  {
    @Autowired
    private UserRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;

    public List<UserDTO> getUsers(int page, int padeSize, String sort) {
        List<User> userlist = repository.findAll(PageRequest.of(page, padeSize, Sort.by(Sort.Direction.ASC, sort))).getContent();
        return listMapper.mapList(userlist, UserDTO.class, modelMapper);
    }

    public List<UserDTO> getAllUser(){
        List<User> userList =repository.findAll();
        return listMapper.mapList(userList, UserDTO.class, modelMapper);
    }

    public UserDetailDTO getUserDetail(Integer id) {
        User user = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User" + id + "does not exits."));
        return modelMapper.map(user, UserDetailDTO.class);
    }

    public UserDTO getTestUser(Integer id) {
        User user = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User" + id + "does not exits."));
        return modelMapper.map(user, UserDTO.class);
    }

    public User NewUser(@Valid UserDTOwithPassword newUser) throws UserException {
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(8,32,1,65536,10);
        newUser.setName(newUser.getName().trim());
        newUser.setEmail(newUser.getEmail().trim());
        newUser.setRole(newUser.getRole().trim());
        newUser.setPassword(encoder.encode(newUser.getPassword()).trim());
        User user = modelMapper.map(newUser, User.class);
        List<User> duplicateName = repository.findByName(user.getName());
        User duplicateEmail = repository.findByEmail(user.getEmail());
        List<String> errors = new ArrayList<>();
        if (!duplicateName.isEmpty() && duplicateEmail != null) {
            errors.add("This Username and Email are already use!!!");
            throw new UserException(errors.toString());
        }
        if (!duplicateName.isEmpty()) {
            errors.add("This Username is already use!!!");

            throw new UserException(errors.toString());
        }
        if (duplicateEmail != null) {
            errors.add("This Email is already use!!!");
            throw new UserException(errors.toString());
        }
        return repository.saveAndFlush(user);
    }

    public User UpdateUser(Integer id, @Valid UserDTO updateUser) throws BookingException {
        List<User> oldUser = repository.findAll();
        List<String> errors = new ArrayList<>();
        for (User user : oldUser) {
            if (user.getId() != id) {
                if (user.getName().equals(updateUser.getName())) {
                    errors.add("Username is already use");
                    if (user.getEmail().equals(updateUser.getEmail())) {
                        errors.add("Email is already use");
                    }
                    throw new BookingException(errors.toString());
                }
            }
        }
        User users = repository.findById(id).map(b -> mapUser(modelMapper.map(b, UserDTO.class), updateUser))
                .orElseGet(() -> {
                    updateUser.setId(id);
                    return modelMapper.map(updateUser, User.class);
                });
        return repository.saveAndFlush(users);
    }

    private User mapUser(UserDTO oldUser, UserDTO newUser) {
        oldUser.setName(newUser.getName().trim());
        oldUser.setEmail(newUser.getEmail().trim());
        oldUser.setRole(newUser.getRole().trim());
        return modelMapper.map(oldUser, User.class);
    }

    public void DeleteUser(Integer id) {
        repository.deleteById(id);
    }


}

