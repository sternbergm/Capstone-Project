package htd.project.controllers;

import htd.project.domains.CohortService;
import htd.project.domains.Result;
import htd.project.models.Cohort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cohort")
public class CohortController {

    private CohortService service;

    public CohortController(CohortService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Cohort>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{cohortId}")
    public ResponseEntity<Cohort> findById(@PathVariable int cohortId) {
        return new ResponseEntity<>(service.findById(cohortId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody @Valid Cohort cohort, BindingResult errors){
        if(errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        Result<Cohort> result = service.create(cohort);
        if(result.isSuccessful()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        } else {
            return ErrorResponse.build(result);
        }
    }

    @PutMapping("/{cohortId}")
    public ResponseEntity<Object> update(@PathVariable int cohortId, @RequestBody @Valid Cohort cohort, BindingResult errors) {
        if(errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        if(cohort.getCohortId() != cohortId) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Cohort> result = service.update(cohort);
        if(result.isSuccessful()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ErrorResponse.build(result);
        }
    }

    @DeleteMapping("/{cohortId}")
    public ResponseEntity<Object> delete(@PathVariable int cohortId) {

        Result<Void> result = service.delete(cohortId);
        if(result.isSuccessful()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ErrorResponse.build(result);
        }
    }

}
