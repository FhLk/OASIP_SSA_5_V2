package oasip.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import oasip.DTO.BookingDTO;
import oasip.Entity.Event;
import oasip.Entity.EventCategory;
import oasip.Service.BookingService;
import oasip.exeption.BookingException;
import oasip.exeption.UserException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:3000/",allowedHeaders = "*",allowCredentials = "true",
         methods = {RequestMethod.DELETE,RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class BookingController {
    @Autowired
    private BookingService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<List<BookingDTO>> getAllBooking(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "startTime") String sort){
        return ResponseEntity.ok(service.getBookings(page,pageSize,sort));
    }

    @GetMapping("/check")
    public ResponseEntity<List<BookingDTO>> getAllBooking(){
        return ResponseEntity.ok(service.getAllBooking());
    }

    @GetMapping("/{BookingId}")
    public ResponseEntity<BookingDTO> getBooking(@Valid @PathVariable Integer BookingId){
        return ResponseEntity.ok(service.getBookingId(BookingId));
    }

    @GetMapping("/sortByCategory")
    public ResponseEntity<List<BookingDTO>> getBookingByCategory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam EventCategory category){
        return ResponseEntity.ok(service.getBookingCategory(page,pageSize,category));
    }

    @GetMapping("/sortByPast")
    public ResponseEntity<List<BookingDTO>> getAllBookingByPast(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int pageSize
    ){
        return ResponseEntity.ok(service.getBookingSortPast(page,pageSize, LocalDateTime.now()));
    }

    @GetMapping("/sortByDay")
    public ResponseEntity<List<BookingDTO>> getBookingBySpecify(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam String date){
        return ResponseEntity.ok(service.getBookingWithSpecify(
                page,
                pageSize,
                date+"T00:00",
                date+"T23:59"
        ));
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Object AddBooking(@Valid @RequestParam("event") String newBooking, @RequestParam(value = "file", required = false)MultipartFile file) throws MessagingException, UnsupportedEncodingException, UserException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        BookingDTO booking = objectMapper.readValue(newBooking, BookingDTO.class);
        Event event =service.CreateBooking(booking,file);
        service.sendConfirmEmail(booking);
        return new ResponseEntity<>(modelMapper.map(event,BookingDTO.class),HttpStatus.CREATED);
    }

    @PutMapping("/{BookingId}")
    public ResponseEntity<BookingDTO> update(@PathVariable Integer BookingId,@Valid @RequestBody BookingDTO updateBooking) throws BookingException{
        service.UpdateBooking(BookingId,updateBooking);
        return new ResponseEntity<>(updateBooking,HttpStatus.OK);
    }

    @DeleteMapping("/{bookingId}")
    public void deleteBooking(@PathVariable Integer bookingId){
        service.DeleteBooking(bookingId);
    }
}
