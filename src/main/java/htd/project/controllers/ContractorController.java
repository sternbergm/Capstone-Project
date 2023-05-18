package htd.project.controllers;

import htd.project.domains.ClientService;
import htd.project.domains.ContractorService;
import htd.project.domains.Result;
import htd.project.models.Client;
import htd.project.models.Contractor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contractor")
public class ContractorController {
    private ContractorService service;

    public ContractorController(ContractorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Contractor>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{contractorId}")
    public ResponseEntity<Contractor> findById(@PathVariable int contractorId) {
        return new ResponseEntity<>(service.findById(contractorId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody @Valid Contractor contractor, BindingResult errors){
        if(errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        Result<Contractor> result = service.create(contractor);
        if(!result.isSuccessful()) {
            return ErrorResponse.build(result);
        } else {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
    }

    @PutMapping("/{contractorId}")
    public ResponseEntity<Object> update(@PathVariable int contractorId, @RequestBody @Valid Contractor contractor, BindingResult errors) {
        if(errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        if(contractorId!=contractor.getContractorId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Contractor> result = service.update(contractor);
        if(!result.isSuccessful()) {
            return ErrorResponse.build(result);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{contractorId}")
    public ResponseEntity<Object> delete(@PathVariable int contractorId) {

        Result<Void> result = service.delete(contractorId);
        if(!result.isSuccessful()) {
            return ErrorResponse.build(result);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

}

