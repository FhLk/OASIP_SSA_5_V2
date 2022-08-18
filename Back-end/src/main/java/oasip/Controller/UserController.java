package oasip.Controller;

import oasip.DTO.UserDTO;
import oasip.DTO.UserDetailDTO;
import oasip.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
