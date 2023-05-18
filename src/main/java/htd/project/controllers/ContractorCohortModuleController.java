package htd.project.controllers;

import htd.project.domains.ContractorCohortModuleService;
import htd.project.domains.Result;
import htd.project.models.ContractorCohortModule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grade")
public class ContractorCohortModuleController {

    private ContractorCohortModuleService service;

    public ContractorCohortModuleController(ContractorCohortModuleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ContractorCohortModule>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/cohort/{cohortId}")
    public ResponseEntity<List<ContractorCohortModule>> findByCohort(@PathVariable int cohortId) {
        return new ResponseEntity<>(service.findByCohort(cohortId), HttpStatus.OK);

    }

    @GetMapping("/module/{moduleId}")
    public ResponseEntity<List<ContractorCohortModule>> findByModule(@PathVariable int moduleId) {
        return new ResponseEntity<>(service.findByModule(moduleId), HttpStatus.OK);
    }

    @GetMapping("/contractor/{contractorId}")
    public ResponseEntity<List<ContractorCohortModule>> findByContractor(@PathVariable int contractorId) {
        return new ResponseEntity<>(service.findByContractor(contractorId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody @Valid ContractorCohortModule ccm, BindingResult errors){
        if(errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors(),HttpStatus.BAD_REQUEST);
        }

        Result<ContractorCohortModule> result = service.create(ccm);
        if(result.isSuccessful()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else {
            return ErrorResponse.build(result);
        }
    }


    @PutMapping
    public ResponseEntity<Object> update(@RequestBody @Valid ContractorCohortModule ccm, BindingResult errors) {
        if(errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors(),HttpStatus.BAD_REQUEST);
        }

        Result<ContractorCohortModule> result = service.update(ccm);
        if(result.isSuccessful()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return ErrorResponse.build(result);
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody ContractorCohortModule ccm) {

        Result<Void> result = service.delete(ccm.getContractorId(), ccm.getCohortId(), ccm.getModuleId());
        if(result.isSuccessful()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return ErrorResponse.build(result);
        }
    }


}
