package net.jobcompare.backend.exceptions;

import org.springframework.http.*;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// '@RestControllerAdvice' annotation to consolidate a lot of the try-catch 'Exception' handling in the controller files
// '@RestControllerAdvice' constitutes of '@ContollerAdvice' & '@ResponseBody' annotations
@RestControllerAdvice
public class GlobalExceptionHandler {
    // '@ExceptionHandle'
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException e){
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(e.getMessage());
    }
}
