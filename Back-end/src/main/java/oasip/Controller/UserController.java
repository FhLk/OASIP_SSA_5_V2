package oasip.Controller;

import oasip.DTO.UserDTO;
import oasip.DTO.UserDetailDTO;
import oasip.Entity.EventUser;
import oasip.Service.UserService;
import oasip.exeption.BookingException;
import oasip.exeption.UserException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired private UserService service;
    @Autowired private ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAllUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "name") String sort){
        return ResponseEntity.ok(service.getUsers(page,pageSize,sort));
    }

    @GetMapping("/{Username}")
    public ResponseEntity<UserDetailDTO> getUser(@PathVariable String Username){
        return ResponseEntity.ok(service.getUserDetail(Username));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDTO> AddUser(@Valid @RequestBody UserDTO newUser) throws UserException {
        EventUser eventUser = service.NewUser(newUser);
        return new ResponseEntity<>(modelMapper.map(eventUser,UserDTO.class),HttpStatus.CREATED);
    }

    @DeleteMapping("/{name}")
    public void deleteUser(@PathVariable String name){
        service.DeleteUser(name);
    }


}
