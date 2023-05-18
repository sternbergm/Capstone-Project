package htd.project.controllers;

import htd.project.domains.ClientService;
import htd.project.domains.Result;
import htd.project.models.Client;
import htd.project.models.Module;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<List<Client>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<Client> findById(@PathVariable int clientId) {
        return new ResponseEntity<>(service.findById(clientId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody @Valid Client client, BindingResult errors){
        if(errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        Result<Client> result = service.create(client);
        if(!result.isSuccessful()) {
            return ErrorResponse.build(result);
        } else {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<Object> update(@PathVariable int clientId, @RequestBody @Valid Client client, BindingResult errors) {
        if(errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        if(clientId!=client.getClientId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Client> result = service.update(client);
        if(!result.isSuccessful()) {
            return ErrorResponse.build(result);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<Object> delete(@PathVariable int clientId) {

        Result<Void> result = service.delete(clientId);
        if(!result.isSuccessful()) {
            return ErrorResponse.build(result);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

}
