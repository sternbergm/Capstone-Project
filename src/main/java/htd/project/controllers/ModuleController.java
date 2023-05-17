package htd.project.controllers;

import htd.project.domains.ModuleService;
import htd.project.domains.Result;
import htd.project.models.Module;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/module")
public class ModuleController {
    private ModuleService service;

    public ModuleController(ModuleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Module>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{moduleId}")
    public ResponseEntity<Module> findById(@PathVariable int moduleId) {
        return new ResponseEntity<>(service.findById(moduleId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody @Valid Module module, BindingResult errors){
        if(errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        Result<Module> result = service.create(module);
        if(!result.isSuccessful()) {
            return ErrorResponse.build(result);
        } else {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
    }

    @PutMapping("/{moduleId}")
    public ResponseEntity<Object> update(@PathVariable int moduleId, @RequestBody @Valid Module module, BindingResult errors) {
        if(errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        if(moduleId!=module.getModuleId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Module> result = service.update(module);
        if(!result.isSuccessful()) {
            return ErrorResponse.build(result);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{moduleId}")
    public ResponseEntity<Object> delete(@PathVariable int moduleId) {

        Result<Void> result = service.delete(moduleId);
        if(!result.isSuccessful()) {
            return ErrorResponse.build(result);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

}
