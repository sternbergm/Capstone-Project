package htd.project.controllers;

import htd.project.data.GlobalExceptionRepository;
import htd.project.models.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/errors")
public class ErrorController {

    GlobalExceptionRepository repository;

    public ErrorController(GlobalExceptionRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Error>> getAll() {
        return new ResponseEntity<>(repository.getAll(), HttpStatus.OK);
    }

}
