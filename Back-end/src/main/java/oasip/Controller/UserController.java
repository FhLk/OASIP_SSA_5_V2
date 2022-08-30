package oasip.Controller;

import oasip.DTO.UserDTO;
import oasip.DTO.UserDTOwithPassword;
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
@CrossOrigin(origins = "http://localhost:3000/",allowedHeaders = "*",allowCredentials = "true",
         methods = {RequestMethod.DELETE,RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
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

    @GetMapping("/check")
    public ResponseEntity<List<UserDTO>> getAllUser(){
        return ResponseEntity.ok(service.getAllUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailDTO> getUser(@PathVariable Integer id){
        return ResponseEntity.ok(service.getUserDetail(id));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDTO> AddUser(@Valid @RequestBody UserDTOwithPassword newUser) throws UserException {
        EventUser eventUser = service.NewUser(newUser);
        return new ResponseEntity<>(modelMapper.map(eventUser,UserDTO.class),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Integer id ,@Valid @RequestBody UserDTO updateUser) throws BookingException{
        service.UpdateUser(id,updateUser);
        return new ResponseEntity<>(updateUser,HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id){
        service.DeleteUser(id);
    }


}
