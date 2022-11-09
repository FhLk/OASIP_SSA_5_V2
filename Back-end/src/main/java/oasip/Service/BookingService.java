package oasip.Service;

import lombok.extern.slf4j.Slf4j;
import oasip.DTO.BookingDTO;
import oasip.DTO.CategoryDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Slf4j
public class BookingService {

    @Autowired
    private BookingRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;
    @Autowired
    private CategoryOwnerRepository categoryOwnerRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender emailSender;

    public List<BookingDTO> getBookings(int page, int pageSize, String sort) {
        List<Event> bookingList = repository.findAll(PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, sort))).getContent();
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<Event> bookingListSt = repository.findByBookingEmail(userEmail, PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, sort)));
        if (userRoles.contains(EnumRole.STUDENT.name())) {
            return listMapper.mapList(bookingListSt, BookingDTO.class, modelMapper);
        }
        if (userRoles.contains(EnumRole.LECTURER.name())) {
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


    public List<BookingDTO> getAllBooking() {
        List<Event> bookingList = repository.findAll();
        return listMapper.mapList(bookingList, BookingDTO.class, modelMapper);
    }

    public BookingDTO getBookingId(Integer bookingId) {
        Event booking = repository.findById(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking id " + bookingId + " does not exits."));
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<String> errors = new ArrayList<>();
        if (userRoles.contains(EnumRole.STUDENT.name())) {
            if (!userEmail.equals(booking.getBookingEmail())) {
                try {
                    errors.add("This email is not booking this event");
                    throw new ForbiddenEx(errors.toString());
                } catch (ForbiddenEx forbiddenEx) {
                    throw new RuntimeException(forbiddenEx);
                }
            }
        }
        if (userRoles.contains(EnumRole.LECTURER.name())) {
            User user = userRepository.findByEmail(userEmail);
            List<CategoryOwner> categoryOwners = categoryOwnerRepository.findByUserIdLecturer(user);
            List<Event> events = new ArrayList<>();
            categoryOwners.forEach(e -> {
                List<Event> eventList = repository.findByCategory(e.getEventcategory());
                events.addAll(eventList);
            });
            List<Event> event = new ArrayList<>();
            events.forEach(f -> {
                if (f.getId().equals(bookingId)) {
                    List<Event> eventSpec = repository.findAllById(f.getId());
                    event.addAll(eventSpec);
                }
            });
            if (event.isEmpty()) {
                try {
                    errors.add("You are not Lecturer of this event");
                    throw new ForbiddenEx(errors.toString());
                } catch (ForbiddenEx forbiddenEx) {
                    throw new RuntimeException(forbiddenEx);
                }
            }
            return modelMapper.map(event.get(0), BookingDTO.class);
        }
        return modelMapper.map(booking, BookingDTO.class);
    }


    public List<BookingDTO> getBookingCategory(
            int page,
            int pageSize,
            EventCategory category) {
        List<Event> bookingList = repository.findAllByCategoryOrderByStartTimeDesc(PageRequest.of(page, pageSize), category);
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<Event> bookingListSt = repository.findByBookingEmailAndCategoryOrderByStartTimeDesc(PageRequest.of(page, pageSize), userEmail, category);
        List<String> errors = new ArrayList<>();
        if (userRoles.contains(EnumRole.STUDENT.name())) {
            return listMapper.mapList(bookingListSt, BookingDTO.class, modelMapper);
        }
        if (userRoles.contains(EnumRole.LECTURER.name())) {
            User user = userRepository.findByEmail(userEmail);
            List<CategoryOwner> categoryOwners = categoryOwnerRepository.findByUserIdLecturer(user);
            List<Event> events = new ArrayList<>();
            categoryOwners.forEach(e -> {
                List<Event> eventList = repository
                        .findByCategoryAndCategoryOrderByStartTimeDesc(
                                e.getEventcategory(),
                                PageRequest.of(page, pageSize),
                                category);
                events.addAll(eventList);

            });
            if (events.isEmpty()) {
                List<Integer> getIdCategory=new ArrayList<>();
                categoryOwners.forEach(e->{
                    getIdCategory.add(e.getId().getEventCategoryId());
                });
                if(!getIdCategory.contains(category.getId())){
                    try {
                        errors.add("You are not Lecturer of this event");
                        throw new ForbiddenEx(errors.toString());
                    } catch (ForbiddenEx forbiddenEx) {
                        throw new RuntimeException(forbiddenEx);
                    }
                }
            }
            return listMapper.mapList(events, BookingDTO.class, modelMapper);
        }
        return listMapper.mapList(bookingList, BookingDTO.class, modelMapper);
    }

    public List<BookingDTO> getBookingSortPast(int page, int pageSize, LocalDateTime localDateTime) {
        List<Event> bookingList = repository.findAllByStartTimeLessThanOrderByStartTimeDesc(PageRequest.of(page, pageSize), localDateTime);
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<Event> bookingListSt = repository.findByBookingEmailAndStartTimeLessThanOrderByStartTimeDesc(PageRequest.of(page, pageSize), userEmail, localDateTime);
        if (userRoles.contains(EnumRole.STUDENT.name())) {
            return listMapper.mapList(bookingListSt, BookingDTO.class, modelMapper);
        }
        if (userRoles.contains(EnumRole.LECTURER.name())) {
            User user = userRepository.findByEmail(userEmail);
            List<CategoryOwner> categoryOwners = categoryOwnerRepository.findByUserIdLecturer(user);
            List<Event> events = new ArrayList<>();
            categoryOwners.forEach(e -> {
                List<Event> eventList = repository
                        .findByCategoryAndStartTimeLessThanOrderByStartTimeDesc(
                                e.getEventcategory(),
                                PageRequest.of(page, pageSize),
                                localDateTime);
                events.addAll(eventList);
            });
            return listMapper.mapList(events, BookingDTO.class, modelMapper);
        }
        return listMapper.mapList(bookingList, BookingDTO.class, modelMapper);
    }

    public List<BookingDTO> getBookingWithSpecify(
            int page,
            int pageSize,
            String starter,
            String end) {
        List<Event> bookingList = repository.findAllByStartTimeBetweenOrderByStartTimeAsc(
                PageRequest.of(page, pageSize),
                LocalDateTime.parse(starter),
                LocalDateTime.parse(end));
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<Event> bookingListSt = repository.findByBookingEmailAndStartTimeBetweenOrderByStartTimeAsc(PageRequest.of(page, pageSize), userEmail, LocalDateTime.parse(starter),
                LocalDateTime.parse(end));
        if (userRoles.contains(EnumRole.STUDENT.name())) {
            return listMapper.mapList(bookingListSt, BookingDTO.class, modelMapper);
        }
        if (userRoles.contains(EnumRole.LECTURER.name())) {
            User user = userRepository.findByEmail(userEmail);
            List<CategoryOwner> categoryOwners = categoryOwnerRepository.findByUserIdLecturer(user);
            List<Event> events = new ArrayList<>();
            categoryOwners.forEach(e -> {
                List<Event> eventList = repository
                        .findByCategoryAndStartTimeBetweenOrderByStartTimeAsc(
                                e.getEventcategory(),
                                PageRequest.of(page, pageSize),
                                LocalDateTime.parse(starter),
                                LocalDateTime.parse(end));
                events.addAll(eventList);
            });
            return listMapper.mapList(events, BookingDTO.class, modelMapper);
        }
        return listMapper.mapList(bookingList, BookingDTO.class, modelMapper);
    }

    public Event CreateBooking(BookingDTO newBooking) throws UserException {
        newBooking.setBookingName(newBooking.getBookingName().trim());
        newBooking.setBookingEmail(newBooking.getBookingEmail().trim());
        newBooking.setEventNote(newBooking.getEventNote().trim());
        Event booking = modelMapper.map(newBooking, Event.class);
        List<Event> events = repository.findEventByCategory_Id(booking.getCategory().getId());
        checkOverlap(LocalDateTime.parse(newBooking.getStartTime()), newBooking.getBookingDuration(), events);
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<String> errors = new ArrayList<>();
        if (userRoles.contains(EnumRole.STUDENT.name())) {
            if (!userEmail.equals(newBooking.getBookingEmail().trim())) {
                try {
                    errors.add("Booking email must be same as the student's email");
                    throw new UserException(errors.toString());
                } catch (UserException userException) {
                    throw new RuntimeException(userException);
                }
            }
        }
        if (userRoles.contains(EnumRole.LECTURER.name())) {
            try {
                errors.add("Access Denied");
                throw new ForbiddenEx(errors.toString());
            } catch (ForbiddenEx forbiddenEx) {
                throw new RuntimeException(forbiddenEx);
            }
        }
        return  repository.saveAndFlush(booking);
    }

    public void sendConfirmEmail(BookingDTO newBooking) throws MessagingException, UnsupportedEncodingException {
        String subject = "[OASIP] " + newBooking.getCategory().getCategoryName() + " @ " + newBooking.getStartTime();
        String senderName = "OASIP ADMIN";
        String mailContent = "<p>Reply-to: noreply@intproj21.sit.kmutt.ac.th </p>";

        mailContent += "<p>BookingName: " + newBooking.getBookingName() + "</p>";

        mailContent += "<p>Event Category: " + newBooking.getCategory().getCategoryName() + "</p>";

        mailContent += "<p>When: " + newBooking.getStartTime() + "</p>";

        mailContent += "<p>Event Note: " + newBooking.getEventNote() + "</p>";

        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("noreply@intproj21.sit.kmutt.ac.th", senderName);
        helper.setTo(newBooking.getBookingEmail());
        helper.setSubject(subject);
        helper.setText(mailContent,true);
        emailSender.send(message);

    }

    public Event UpdateBooking(Integer bookingId, BookingDTO updateBooking) throws BookingException {
        BookingDTO oldBooking = (BookingDTO) getBookingId(bookingId);
        List<String> errors = new ArrayList<>();
        if (!oldBooking.getBookingName().equals(updateBooking.getBookingName())) {
            errors.add("The bookingName can't change");
            if (!oldBooking.getBookingEmail().equals(updateBooking.getBookingEmail())) {
                errors.add("The bookingEmail can't change");
            }
            throw new BookingException(errors.toString());
        }
        updateBooking.setBookingName(updateBooking.getBookingName().trim());
        updateBooking.setBookingEmail(updateBooking.getBookingEmail().trim());
        updateBooking.setEventNote(updateBooking.getEventNote().trim());
        Event booking = repository.findById(bookingId).map(b -> mapBooking(modelMapper.map(b, BookingDTO.class), updateBooking))
                .orElseGet(() -> {
                    updateBooking.setId(bookingId);
                    return modelMapper.map(updateBooking, Event.class);
                });
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (userRoles.contains(EnumRole.STUDENT.name())) {
            if (!userEmail.equals(updateBooking.getBookingEmail())) {
                try {
                    errors.add("This email is not booking this event");
                    throw new ForbiddenEx(errors.toString());
                } catch (ForbiddenEx forbiddenEx) {
                    throw new RuntimeException(forbiddenEx);
                }
            }
        }
        if (userRoles.contains(EnumRole.LECTURER.name())) {
            try {
                errors.add("Access Denied");
                throw new ForbiddenEx(errors.toString());
            } catch (ForbiddenEx forbiddenEx) {
                throw new RuntimeException(forbiddenEx);
            }
        }
        return repository.saveAndFlush(booking);
    }

    private Event mapBooking(BookingDTO oldBooking, BookingDTO newBooking) {
        oldBooking = newBooking;
        return modelMapper.map(oldBooking, Event.class);
    }

    private void checkOverlap(LocalDateTime dateTime, Integer duration, List<Event> eventList) throws UserException {
        LocalDateTime newStartTime = dateTime;
        LocalDateTime newEndTime = newStartTime.plusMinutes(duration);
        List<String> errors = new ArrayList<>();
        for (Event event : eventList){
            LocalDateTime startTime = event.getStartTime();
            LocalDateTime endTime = event.getStartTime().plusMinutes(event.getBookingDuration());
            if (    newStartTime.isEqual(startTime) ||
                    newStartTime.isBefore(startTime) && newEndTime.isAfter(startTime) ||
                    newStartTime.isBefore(endTime) && newEndTime.isAfter(endTime) ||
                    newStartTime.isBefore(startTime) && newEndTime.isAfter(endTime) ||
                    newStartTime.isAfter(startTime) && newEndTime.isBefore(endTime)
            ){
                errors.add("Time is overlap");
                throw new UserException(errors.toString());
            }
        }
    }

    public void DeleteBooking(Integer BookingId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<String> errors = new ArrayList<>();
        if (userRoles.contains(EnumRole.STUDENT.name())) {
            if (!userEmail.equals(getBookingId(BookingId).getBookingEmail())) {
                try {
                    errors.add("This email is not booking this event");
                    throw new ForbiddenEx(errors.toString());
                } catch (ForbiddenEx forbiddenEx) {
                    throw new RuntimeException(forbiddenEx);
                }
            }
        }
        if (userRoles.contains(EnumRole.LECTURER.name())) {
            try {
                errors.add("Access Denied");
                throw new ForbiddenEx(errors.toString());
            } catch (ForbiddenEx forbiddenEx) {
                throw new RuntimeException(forbiddenEx);
            }
        }
        repository.deleteById(BookingId);
    }
}