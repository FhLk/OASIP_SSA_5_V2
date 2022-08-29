package oasip.exeption;

import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ShowException handleValidationException(MethodArgumentNotValidException ex) {
        ShowException errors=new ShowException();
        Map<String, String> errorsMap = new HashMap<>();
        errors.setStatusCode(400);
        errors.setStatus("BAD REQUEST");
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorsMap.put(fieldName,errorMessage);
        });
        errors.setMessage(errorsMap);
        return errors;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BookingException.class)
    public ShowException handleBusinessException(BookingException ex) {
        ShowException errors=new ShowException();
        errors.setStatusCode(500);
        errors.setStatus("INTERNAL SERVER ERROR");
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Message", ex.getMessage());
        errors.setMessage(errorMap);
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserException.class)
    public ShowException handleBusinessException(UserException ex) {
        ShowException errors=new ShowException();
        errors.setStatusCode(400);
        errors.setStatus("BAD REQUEST");
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Message", ex.getMessage());
        errors.setMessage(errorMap);
        return errors;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(OkException.class)
    public ShowException handleBusinessException(OkException ok) {
        ShowException errors=new ShowException();
        errors.setStatusCode(200);
        errors.setStatus("OK");
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Message", ok.getMessage());
        errors.setMessage(errorMap);
        return errors;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedEx.class)
    public ShowException handleBusinessException(UnauthorizedEx un) {
        ShowException errors=new ShowException();
        errors.setStatusCode(401);
        errors.setStatus("UNAUTHORIZED");
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Message", un.getMessage());
        errors.setMessage(errorMap);
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotfoundEx.class)
    public ShowException handleBusinessException(NotfoundEx nf) {
        ShowException errors=new ShowException();
        errors.setStatusCode(404);
        errors.setStatus("NOT FOUND");
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Message", nf.getMessage());
        errors.setMessage(errorMap);
        return errors;
    }
}