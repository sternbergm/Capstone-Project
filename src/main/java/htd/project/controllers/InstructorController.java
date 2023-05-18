package htd.project.controllers;

import htd.project.domains.ContractorService;
import htd.project.domains.InstructorService;
import htd.project.domains.Result;
import htd.project.models.Contractor;
import htd.project.models.Instructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    private InstructorService service;

    public InstructorController(InstructorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Instructor>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{instructorId}")
    public ResponseEntity<Instructor> findById(@PathVariable int instructorId) {
        return new ResponseEntity<>(service.findById(instructorId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody @Valid Instructor instructor, BindingResult errors){
        if(errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        Result<Instructor> result = service.create(instructor);
        if(!result.isSuccessful()) {
            return ErrorResponse.build(result);
        } else {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
    }

    @PutMapping("/{instructorId}")
    public ResponseEntity<Object> update(@PathVariable int instructorId, @RequestBody @Valid Instructor instructor, BindingResult errors) {
        if(errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        if(instructorId!=instructor.getInstructorId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Instructor> result = service.update(instructor);
        if(!result.isSuccessful()) {
            return ErrorResponse.build(result);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{instructorId}")
    public ResponseEntity<Object> delete(@PathVariable int instructorId) {

        Result<Void> result = service.delete(instructorId);
        if(!result.isSuccessful()) {
            return ErrorResponse.build(result);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

}
