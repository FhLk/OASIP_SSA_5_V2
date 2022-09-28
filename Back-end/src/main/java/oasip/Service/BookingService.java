package oasip.Service;

import oasip.DTO.BookingDTO;
import oasip.DTO.UserDTO;
import oasip.Entity.EventBooking;
import oasip.Entity.EventCategory;
import oasip.Entity.EventUser;
import oasip.Repository.BookingRepository;
import oasip.Utils.ListMapper;
import oasip.exeption.BookingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository repository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private ListMapper listMapper;

    public List<BookingDTO> getBookings(int page, int pageSize, String sort){
        List<EventBooking> bookingList = repository.findAll(PageRequest.of(page,pageSize, Sort.by(Sort.Direction.DESC,sort))).getContent();
        return listMapper.mapList(bookingList, BookingDTO.class, modelMapper);
    }

    public List<BookingDTO> getAllBooking(){
        List<EventBooking> bookingList =repository.findAll();
        return listMapper.mapList(bookingList, BookingDTO.class, modelMapper);
    }

    public BookingDTO getBookingId(Integer bookingId){
        EventBooking booking = repository.findById(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Booking id "+bookingId+" does not exits."));
        return modelMapper.map(booking, BookingDTO.class);
    }

    public List<BookingDTO> getBookingCategory(
            int page,
            int pageSize,
            EventCategory category){
        List<EventBooking> bookingList = repository.findAllByCategoryOrderByStartTimeDesc(PageRequest.of(page,pageSize),category);
        return listMapper.mapList(bookingList, BookingDTO.class,modelMapper);
    }

    public List<BookingDTO> getBookingSortPast(int page, int pageSize, LocalDateTime localDateTime){
        List<EventBooking> bookingList = repository.findAllByStartTimeLessThanOrderByStartTimeDesc(PageRequest.of(page,pageSize),localDateTime);
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
        return listMapper.mapList(bookingList, BookingDTO.class,modelMapper);
    }

    public EventBooking CreateBooking(BookingDTO newBooking){
        newBooking.setBookingName(newBooking.getBookingName().trim());
        newBooking.setBookingEmail(newBooking.getBookingEmail().trim());
        newBooking.setEventNote(newBooking.getEventNote().trim());
        EventBooking booking = modelMapper.map(newBooking,EventBooking.class);
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
        return repository.saveAndFlush(booking);
    }

    private EventBooking mapBooking(BookingDTO oldBooking,BookingDTO newBooking){
        oldBooking=newBooking;
        return modelMapper.map(oldBooking,EventBooking.class);
    }

    public void DeleteBooking(Integer BookingId){
        repository.deleteById(BookingId);
    }
}