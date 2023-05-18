package htd.project.controllers;

import htd.project.data.GlobalExceptionRepository;
import htd.project.models.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    GlobalExceptionRepository repository;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> HandleException(Exception exception) {
        repository.add(new Error(LocalDateTime.now(), Arrays.toString(exception.getStackTrace())));
        System.out.println(Arrays.toString(exception.getStackTrace()));
        return new ResponseEntity<>(new ErrorResponse("We encountered an issue with your request, we won't show you what happened"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> HandleException(MethodArgumentTypeMismatchException exception) {
        repository.add(new Error(LocalDateTime.now(), Arrays.toString(exception.getStackTrace())));
        System.out.println(Arrays.toString(exception.getStackTrace()));
        return new ResponseEntity<>(new ErrorResponse("Incorrect input type."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
