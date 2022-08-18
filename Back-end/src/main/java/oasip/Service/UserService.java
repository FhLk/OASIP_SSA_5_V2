package oasip.Service;

import oasip.DTO.UserDTO;
import oasip.DTO.UserDetailDTO;
import oasip.Entity.EventUser;
import oasip.Repository.UserRepository;
import oasip.Utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {
    @Autowired private UserRepository repository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private ListMapper listMapper;

    public List<UserDTO> getUsers(int page, int padeSize,String sort){
     List<EventUser> userlist = repository.findAll(PageRequest.of(page,padeSize, Sort.by(Sort.Direction.ASC,sort))).getContent();
     return listMapper.mapList(userlist,UserDTO.class,modelMapper);
    }

    public UserDetailDTO getUserDetail(String userName){
        EventUser user = repository.findById(userName).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User"+userName+"does not exits."));
        return modelMapper.map(user,UserDetailDTO.class);
    }
}

