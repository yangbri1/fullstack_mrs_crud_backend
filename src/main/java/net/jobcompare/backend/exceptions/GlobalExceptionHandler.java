package net.jobcompare.backend.exceptions;

import org.springframework.http.*;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


// '@RestControllerAdvice' annotation to consolidate a lot of the try-catch 'Exception' handling in the controller files
// '@RestControllerAdvice' constitutes of '@ContollerAdvice' & '@ResponseBody' annotations
/* Aside: Spring boot will ...
** 1. Scans the proj (compoent scanning)
** 2. Finds this 'GlobalExceptionHandler.java' class
** 3. Wraps all the controllers w/ it
** 4. Intercepts any exception thrown */
@RestControllerAdvice
// below scopes to specific controllers ... good for distiction in big apps
// @RestControllerAdvice(basePackage = "net.jobcompare.backend.controllers")
public class GlobalExceptionHandler {

    // 'Exception' to handle when the resource is NOT FOUND (extended from RuntimeException)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException e){
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(e.getMessage());
    }

    // method to handle 
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequest(BadRequestException e){
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(e.getMessage());
    }

    // 'Exception' to handle general internal server-side errors (http status codes 5xx)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception e){
        return ResponseEntity
            // this would yield Http status code in the 500 range
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Oh dear something is amissed: " + e.getMessage());

    }
}
