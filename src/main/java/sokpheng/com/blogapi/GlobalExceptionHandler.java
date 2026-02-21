package sokpheng.com.blogapi;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sokpheng.com.blogapi.exception.SokphengNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidEnum(HttpMessageNotReadableException ex) {
        // Check if the cause is an invalid Enum value
//        if (ex.getCause() instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body("Invalid Status! Please use only: DRAFT or PUBLISHED.");
//        }
        return ResponseEntity.badRequest().body("Invalid Status! Please use only: DRAFT or PUBLISHED.");
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
}