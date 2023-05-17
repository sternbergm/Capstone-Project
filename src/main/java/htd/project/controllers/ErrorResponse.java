package htd.project.controllers;



import htd.project.domains.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ErrorResponse {

    private final LocalDateTime timestamp = LocalDateTime.now();

    private final String message;
    public ErrorResponse(String s) {
        message = s;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public static <T> ResponseEntity<Object> build(Result<T> result) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(result.getMessages(), status);
    }
}
