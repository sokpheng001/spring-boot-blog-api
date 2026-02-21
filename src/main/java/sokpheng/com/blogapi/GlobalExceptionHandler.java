package sokpheng.com.blogapi;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sokpheng.com.blogapi.exception.ExistingException;
import sokpheng.com.blogapi.exception.SokphengNotFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleInvalidEnum(HttpMessageNotReadableException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("error","BAD Data send to API");
        String fullMessage = ex.getMessage();

        if (fullMessage != null) {
            String readableMessage = fullMessage.substring(fullMessage.lastIndexOf(":") + 1).trim();
            error.put("message", readableMessage);
        } else {
            error.put("message", "Invalid request body. Please check your input.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    @ExceptionHandler(SokphengNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNtoFoundEntity(SokphengNotFoundException ex) {
        // Check if the cause is an invalid Enum value
//        if (ex.getCause() instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body("Invalid Status! Please use only: DRAFT or PUBLISHED.");
//        }
        return ex.getMessage();
    }
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNtoFoundEntity(BadCredentialsException ex) {
        // Check if the cause is an invalid Enum value
//        if (ex.getCause() instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body("Invalid Status! Please use only: DRAFT or PUBLISHED.");
//        }
        return ex.getMessage();
    }
    @ExceptionHandler(ExistingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEntityExisting(ExistingException ex) {
        // Check if the cause is an invalid Enum value
//        if (ex.getCause() instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body("Invalid Status! Please use only: DRAFT or PUBLISHED.");
//        }
        return ex.getMessage();
    }

}