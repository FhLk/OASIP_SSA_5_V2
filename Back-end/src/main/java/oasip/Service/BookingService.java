package oasip.Service;

import oasip.DTO.BookingDTO;
import oasip.Entity.EventBooking;
import oasip.Entity.EventCategory;
import oasip.Repository.BookingRepository;
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
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository repository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private ListMapper listMapper;
    public List<BookingDTO> getBookings(int page, int pageSize, String sort){
        List<EventBooking> bookingList = repository.findAll(PageRequest.of(page,pageSize, Sort.by(Sort.Direction.DESC,sort))).getContent();
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<EventBooking> bookingListSt = repository.findByBookingEmail(userEmail,PageRequest.of(page,pageSize,Sort.by(Sort.Direction.DESC,sort)));
        if(userRoles.contains(EnumRole.STUDENT.name())){
            return listMapper.mapList(bookingListSt, BookingDTO.class, modelMapper);
        }
        return listMapper.mapList(bookingList, BookingDTO.class, modelMapper);
    }

    public BookingDTO getBookingId(Integer bookingId){
        EventBooking booking = repository.findById(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Booking id "+bookingId+" does not exits."));
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<String> errors = new ArrayList<>();
            if (userRoles.contains(EnumRole.STUDENT.name())) {
                if (!userEmail.equals(booking.getBookingEmail())){
                    try {
                        errors.add("This email is not booking this event");
                        throw new ForbiddenEx(errors.toString());
                    }catch (ForbiddenEx forbiddenEx){
                        throw new RuntimeException(forbiddenEx);
                    }
                }
                return modelMapper.map(repository.findByIdAndBookingEmail(bookingId, userEmail), BookingDTO.class);
            }
        return modelMapper.map(booking, BookingDTO.class);
    }


    public List<BookingDTO> getBookingCategory(
            int page,
            int pageSize,
            EventCategory category){
        List<EventBooking> bookingList = repository.findAllByCategoryOrderByStartTimeDesc(PageRequest.of(page,pageSize),category);
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<EventBooking> bookingListSt = repository.findByBookingEmailAndCategoryOrderByStartTimeDesc(PageRequest.of(page,pageSize),userEmail,category);
        if(userRoles.contains(EnumRole.STUDENT.name())){
            return listMapper.mapList(bookingListSt, BookingDTO.class, modelMapper);
        }
        return listMapper.mapList(bookingList, BookingDTO.class,modelMapper);
    }

    public List<BookingDTO> getBookingSortPast(int page, int pageSize, LocalDateTime localDateTime){
        List<EventBooking> bookingList = repository.findAllByStartTimeLessThanOrderByStartTimeDesc(PageRequest.of(page,pageSize),localDateTime);
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<EventBooking> bookingListSt = repository.findByBookingEmailAndStartTimeLessThanOrderByStartTimeDesc(PageRequest.of(page,pageSize),userEmail,localDateTime);
        if(userRoles.contains(EnumRole.STUDENT.name())){
            return listMapper.mapList(bookingListSt, BookingDTO.class, modelMapper);
        }
        return listMapper.mapList(bookingList, BookingDTO.class, modelMapper);
    }

    public List<BookingDTO> getBookingWithSpecify(
            int page,
            int pageSize,
            String startdate,
            String enddate){
        List<EventBooking> bookingList = repository.findAllByStartTimeBetweenOrderByStartTimeAsc(
                PageRequest.of(page,pageSize),
                LocalDateTime.parse(startdate),
                LocalDateTime.parse(enddate));
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<EventBooking> bookingListSt = repository.findByBookingEmailAndStartTimeBetweenOrderByStartTimeAsc(PageRequest.of(page,pageSize),userEmail,LocalDateTime.parse(startdate),
                LocalDateTime.parse(enddate));
        if(userRoles.contains(EnumRole.STUDENT.name())){
            return listMapper.mapList(bookingListSt, BookingDTO.class, modelMapper);
        }
        return listMapper.mapList(bookingList, BookingDTO.class,modelMapper);
    }

    public EventBooking CreateBooking(BookingDTO newBooking){
        newBooking.setBookingName(newBooking.getBookingName().trim());
        newBooking.setBookingEmail(newBooking.getBookingEmail().trim());
        newBooking.setEventNote(newBooking.getEventNote().trim());
        EventBooking booking = modelMapper.map(newBooking,EventBooking.class);
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
        return repository.saveAndFlush(booking);
    }
    public EventBooking UpdateBooking(Integer bookingId,BookingDTO updateBooking) throws BookingException {
        BookingDTO oldBooking = getBookingId(bookingId);
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
        EventBooking booking = repository.findById(bookingId).map(b->mapBooking(modelMapper.map(b,BookingDTO.class),updateBooking))
                .orElseGet(()->{
                    updateBooking.setId(bookingId);
                    return modelMapper.map(updateBooking,EventBooking.class);
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
        return repository.saveAndFlush(booking);
    }

    private EventBooking mapBooking(BookingDTO oldBooking,BookingDTO newBooking){
        oldBooking=newBooking;
        return modelMapper.map(oldBooking,EventBooking.class);
    }

    public void DeleteBooking(Integer BookingId){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<String> errors=new ArrayList<>();
        if (userRoles.contains(EnumRole.STUDENT.name())) {
            if (!userEmail.equals(getBookingId(BookingId).getBookingEmail())){
                try {
                    errors.add("This email is not booking this event");
                    throw new ForbiddenEx(errors.toString());
                }catch (ForbiddenEx forbiddenEx){
                    throw new RuntimeException(forbiddenEx);
                }
            }
        }
        repository.deleteById(BookingId);
    }
}
