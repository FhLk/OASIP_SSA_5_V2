package oasip.Controller;

import oasip.DTO.MatchDTO;
import oasip.Service.MatchService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkAuthen")
@CrossOrigin(origins = "http://localhost:3000/",allowedHeaders = "*",allowCredentials = "true",
        methods = {RequestMethod.DELETE,RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class CheckAuthenController {
    @Autowired private MatchService service;
    @Autowired private ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<MatchDTO> matching(@RequestBody MatchDTO match) throws Exception{
        return ResponseEntity.ok(service.Matching(match));
    }
}