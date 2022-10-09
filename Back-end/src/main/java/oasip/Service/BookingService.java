package oasip.Service;

import lombok.extern.slf4j.Slf4j;
import oasip.DTO.BookingDTO;
import oasip.Entity.*;
import oasip.Repository.BookingRepository;
import oasip.Repository.CategoryOwnerRepository;
import oasip.Repository.CategoryRepository;
import oasip.Repository.UserRepository;
import oasip.Utils.EnumRole;
import oasip.Utils.ListMapper;
import oasip.exeption.BookingException;
import oasip.exeption.ForbiddenEx;
import oasip.exeption.UserException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service @Slf4j
public class BookingService {

    @Autowired
    private BookingRepository repository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private ListMapper listMapper;
    @Autowired private CategoryOwnerRepository categoryOwnerRepository;
    @Autowired private CategoryRepository categoryRepository;

    @Autowired private UserService userService;
    @Autowired private UserRepository userRepository;
    public List<BookingDTO> getBookings(int page, int pageSize, String sort){
        List<Event> bookingList = repository.findAll(PageRequest.of(page,pageSize, Sort.by(Sort.Direction.DESC,sort))).getContent();
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<Event> bookingListSt = repository.findByBookingEmail(userEmail,PageRequest.of(page,pageSize,Sort.by(Sort.Direction.DESC,sort)));
        if(userRoles.contains(EnumRole.STUDENT.name())){
            return listMapper.mapList(bookingListSt, BookingDTO.class, modelMapper);
        }
        if(userRoles.contains(EnumRole.LECTURER.name())) {
            User user = userRepository.findByEmail(userEmail);
            List<CategoryOwner> categoryOwners = categoryOwnerRepository.findByUserIdLecturer(user);
            List<Event> events = new ArrayList<>();
            categoryOwners.forEach(e -> {
                List<Event> eventList = repository.findByCategory(e.getEventcategory());
                events.addAll(eventList);
            });
            return listMapper.mapList(events, BookingDTO.class, modelMapper);
        }
        return listMapper.mapList(bookingList, BookingDTO.class, modelMapper);
    }


    public List<BookingDTO> getAllBooking(){
        List<Event> bookingList =repository.findAll();
        return listMapper.mapList(bookingList, BookingDTO.class, modelMapper);
    }

    public List<BookingDTO> getBookingId(Integer bookingId){
        List<Event> booking = Collections.singletonList(repository.findById(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking id " + bookingId + " does not exits.")));
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<Event> stBooking = Collections.singletonList(repository.findByIdAndBookingEmail(bookingId, userEmail));
        List<String> errors = new ArrayList<>();
            if (userRoles.contains(EnumRole.STUDENT.name())) {
                if (!userEmail.equals(booking.get(0).getBookingEmail())){
                    try {
                        errors.add("This email is not booking this event");
                        throw new ForbiddenEx(errors.toString());
                    }catch (ForbiddenEx forbiddenEx){
                        throw new RuntimeException(forbiddenEx);
                    }
                }
                return listMapper.mapList(stBooking, BookingDTO.class,modelMapper);
            }
            if(userRoles.contains(EnumRole.LECTURER.name())) {
                User user = userRepository.findByEmail(userEmail);
                List<CategoryOwner> categoryOwners = categoryOwnerRepository.findByUserIdLecturer(user);
                List<Event> events = new ArrayList<>();
                categoryOwners.forEach(e -> {
                    List<Event> eventList = repository.findByCategory(e.getEventcategory());
                    events.addAll(eventList);
                });
                List<Event> event = new ArrayList<>();
                events.forEach(f ->{
                    if (f.getId().equals(bookingId)){
                        List<Event> eventSpec = repository.findAllById(f.getId());
                        event.addAll(eventSpec);
                    }
                });
                if (event.isEmpty()){
                    try {
                        errors.add("You are not Lecturer of this event");
                        throw new ForbiddenEx(errors.toString());
                    }catch (ForbiddenEx forbiddenEx){
                        throw new RuntimeException(forbiddenEx);
                    }
                }
                return listMapper.mapList(event, BookingDTO.class,modelMapper);
            }
        return listMapper.mapList(booking, BookingDTO.class,modelMapper);
    }


    public List<BookingDTO> getBookingCategory(
            int page,
            int pageSize,
            EventCategory category){
        List<Event> bookingList = repository.findAllByCategoryOrderByStartTimeDesc(PageRequest.of(page,pageSize),category);
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<Event> bookingListSt = repository.findByBookingEmailAndCategoryOrderByStartTimeDesc(PageRequest.of(page,pageSize),userEmail,category);
        if(userRoles.contains(EnumRole.STUDENT.name())){
            return listMapper.mapList(bookingListSt, BookingDTO.class, modelMapper);
        }
        if(userRoles.contains(EnumRole.LECTURER.name())) {
            User user = userRepository.findByEmail(userEmail);
            List<CategoryOwner> categoryOwners = categoryOwnerRepository.findByUserIdLecturer(user);
            List<Event> events = new ArrayList<>();
            categoryOwners.forEach(e -> {
                List<Event> eventList = repository.findByCategory(e.getEventcategory());
                events.addAll(eventList);
            });
            List<Event> event = new ArrayList<>();
            events.forEach(f ->{
                List<Event> eventSort = repository.findAllByCategoryOrderByStartTimeDesc(PageRequest.of(page,pageSize),category);
                event.addAll(eventSort);
            });
            return listMapper.mapList(event, BookingDTO.class, modelMapper);
        }
        return listMapper.mapList(bookingList, BookingDTO.class,modelMapper);
    }

    public List<BookingDTO> getBookingSortPast(int page, int pageSize, LocalDateTime localDateTime){
        List<Event> bookingList = repository.findAllByStartTimeLessThanOrderByStartTimeDesc(PageRequest.of(page,pageSize),localDateTime);
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<Event> bookingListSt = repository.findByBookingEmailAndStartTimeLessThanOrderByStartTimeDesc(PageRequest.of(page,pageSize),userEmail,localDateTime);
        if(userRoles.contains(EnumRole.STUDENT.name())){
            return listMapper.mapList(bookingListSt, BookingDTO.class, modelMapper);
        }
        if(userRoles.contains(EnumRole.LECTURER.name())) {
            User user = userRepository.findByEmail(userEmail);
            List<CategoryOwner> categoryOwners = categoryOwnerRepository.findByUserIdLecturer(user);
            List<Event> events = new ArrayList<>();
            categoryOwners.forEach(e -> {
                List<Event> eventList = repository.findByCategory(e.getEventcategory());
                events.addAll(eventList);
            });
            List<Event> event = new ArrayList<>();
            events.forEach(f ->{
                List<Event> eventSort = repository.findAllByStartTimeLessThanOrderByStartTimeDesc(PageRequest.of(page,pageSize),localDateTime);
                event.addAll(eventSort);
            });
            return listMapper.mapList(event, BookingDTO.class, modelMapper);
        }
        return listMapper.mapList(bookingList, BookingDTO.class, modelMapper);
    }

    public List<BookingDTO> getBookingWithSpecify(
            int page,
            int pageSize,
            String startdate,
            String enddate){
        List<Event> bookingList = repository.findAllByStartTimeBetweenOrderByStartTimeAsc(
                PageRequest.of(page,pageSize),
                LocalDateTime.parse(startdate),
                LocalDateTime.parse(enddate));
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<Event> bookingListSt = repository.findByBookingEmailAndStartTimeBetweenOrderByStartTimeAsc(PageRequest.of(page,pageSize),userEmail,LocalDateTime.parse(startdate),
                LocalDateTime.parse(enddate));
        if(userRoles.contains(EnumRole.STUDENT.name())){
            return listMapper.mapList(bookingListSt, BookingDTO.class, modelMapper);
        }
        if(userRoles.contains(EnumRole.LECTURER.name())) {
            User user = userRepository.findByEmail(userEmail);
            List<CategoryOwner> categoryOwners = categoryOwnerRepository.findByUserIdLecturer(user);
            List<Event> events = new ArrayList<>();
            categoryOwners.forEach(e -> {
                List<Event> eventList = repository.findByCategory(e.getEventcategory());
                events.addAll(eventList);
            });
            List<Event> event = new ArrayList<>();
            events.forEach(f ->{
                List<Event> eventSort = repository.findAllByStartTimeBetweenOrderByStartTimeAsc(PageRequest.of(page,pageSize),LocalDateTime.parse(startdate), LocalDateTime.parse(enddate));
                event.addAll(eventSort);
            });
            return listMapper.mapList(event, BookingDTO.class, modelMapper);
        }
        return listMapper.mapList(bookingList, BookingDTO.class,modelMapper);
    }

    public Event CreateBooking(BookingDTO newBooking){
        newBooking.setBookingName(newBooking.getBookingName().trim());
        newBooking.setBookingEmail(newBooking.getBookingEmail().trim());
        newBooking.setEventNote(newBooking.getEventNote().trim());
        Event booking = modelMapper.map(newBooking, Event.class);
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<String> errors = new ArrayList<>();
        if(userRoles.contains(EnumRole.STUDENT.name())){
            if(!userEmail.equals(newBooking.getBookingEmail().trim())){
                try {
                    errors.add("Booking email must be same as the student's email");
                    throw new UserException(errors.toString());
                }catch (UserException userException){
                    throw new RuntimeException(userException);
                }
            }
        }
        if (userRoles.contains(EnumRole.LECTURER.name())){
            try {
                errors.add("Access Denied");
                throw new ForbiddenEx(errors.toString());
            }catch (ForbiddenEx forbiddenEx){
                throw new RuntimeException(forbiddenEx);
            }
        }
        return repository.saveAndFlush(booking);
    }
    public Event UpdateBooking(Integer bookingId, BookingDTO updateBooking) throws BookingException {
        BookingDTO oldBooking = (BookingDTO) getBookingId(bookingId);
        List<String> errors=new ArrayList<>();
        if(!oldBooking.getBookingName().equals(updateBooking.getBookingName())){
            errors.add("The bookingName can't change");
            if(!oldBooking.getBookingEmail().equals(updateBooking.getBookingEmail())){
                errors.add("The bookingEmail can't change");
            }
            throw new BookingException(errors.toString());
        }
        updateBooking.setBookingName(updateBooking.getBookingName().trim());
        updateBooking.setBookingEmail(updateBooking.getBookingEmail().trim());
        updateBooking.setEventNote(updateBooking.getEventNote().trim());
        Event booking = repository.findById(bookingId).map(b->mapBooking(modelMapper.map(b,BookingDTO.class),updateBooking))
                .orElseGet(()->{
                    updateBooking.setId(bookingId);
                    return modelMapper.map(updateBooking, Event.class);
                });
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (userRoles.contains(EnumRole.STUDENT.name())) {
            if (!userEmail.equals(updateBooking.getBookingEmail())){
                try {
                    errors.add("This email is not booking this event");
                    throw new ForbiddenEx(errors.toString());
                }catch (ForbiddenEx forbiddenEx){
                    throw new RuntimeException(forbiddenEx);
                }
            }
        }
        if (userRoles.contains(EnumRole.LECTURER.name())){
            try {
                errors.add("Access Denied");
                throw new ForbiddenEx(errors.toString());
            }catch (ForbiddenEx forbiddenEx){
                throw new RuntimeException(forbiddenEx);
            }
        }
        return repository.saveAndFlush(booking);
    }

    private Event mapBooking(BookingDTO oldBooking, BookingDTO newBooking){
        oldBooking=newBooking;
        return modelMapper.map(oldBooking, Event.class);
    }

    public void DeleteBooking(Integer BookingId){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<String> errors=new ArrayList<>();
        if (userRoles.contains(EnumRole.STUDENT.name())) {
            if (!userEmail.equals(getBookingId(BookingId).get(0).getBookingEmail())){
                try {
                    errors.add("This email is not booking this event");
                    throw new ForbiddenEx(errors.toString());
                }catch (ForbiddenEx forbiddenEx){
                    throw new RuntimeException(forbiddenEx);
                }
            }
        }
        if (userRoles.contains(EnumRole.LECTURER.name())){
            try {
                errors.add("Access Denied");
                throw new ForbiddenEx(errors.toString());
            }catch (ForbiddenEx forbiddenEx){
                throw new RuntimeException(forbiddenEx);
            }
        }
        repository.deleteById(BookingId);
    }
}