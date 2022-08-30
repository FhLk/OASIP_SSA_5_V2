package oasip.Controller;

import oasip.DTO.MatchDTO;
import oasip.Entity.EventUser;
import oasip.Service.MatchService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:3000/",allowedHeaders = "*",allowCredentials = "true",
        methods = {RequestMethod.DELETE,RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class MatchController {
    @Autowired private MatchService service;
    @Autowired private ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<MatchDTO> matching(@RequestBody MatchDTO match) throws Exception{
        return ResponseEntity.ok(service.Matching(match));
    }
}
