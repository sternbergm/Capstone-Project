package htd.project.domains;

import htd.project.data.ContractorCohortModuleRepository;
import htd.project.data.ObjectRepository;
import htd.project.models.Client;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
@Service
public class ClientService {
    private ObjectRepository<Client> repository;

    public ClientService(ObjectRepository<Client> repository) {
        this.repository = repository;
    }

    public List<Client> findAll() {
        return repository.readAll();
    }

    public Client findById(int clientId) {
        return repository.readById(clientId);
    }
    public Result<Client> create(Client client){
        Result<Client> result = validate(client);
        if(!result.isSuccessful()) return  result;
        validateDuplicates(client, result);
        if(!result.isSuccessful()) return  result;

        client = repository.create(client);
        if(client == null) {
            result.addMessage("Database Error when creating client");
            return result;
        }
        result.setPayload(client);
        return result;
    }
    public Result<Client> update(Client client){
        Result<Client> result = validate(client);
        if(!result.isSuccessful()) return  result;

        validateContains(client.getClientId(), result);
        if(!result.isSuccessful()) return  result;

        if(!repository.update(client)) {
            result.addMessage("Database Error when updating client");
            return result;
        }

        result.setPayload(client);
        return result;
    }
    public Result<Void> delete(int clientId){
        Result<Void> result = validateDelete(clientId);
        if(!result.isSuccessful()) return  result;

        if(!repository.delete(clientId)) {
            result.addMessage("Database Error when deleting client");
        }
        return result;
    }
    private Result<Client> validate(Client client){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Client>> errors = validator.validate(client);

        Result<Client> result = new Result<>();
        if(!errors.isEmpty()) {

            for (ConstraintViolation<Client> violation :
                    errors) {
                result.addMessage(violation.getMessage());
            }

            return result;
        }
        return result;
    }

    private void validateDuplicates(Client client, Result<Client> result) {
        List<Client> clients = findAll();

        if(clients.stream().anyMatch(m -> m.equals(client))) {
            result.addMessage("Client cannot be duplicate, must have unique ");
        }
    }
    private Result<Void> validateDelete(int clientId ){

        Result<Void> result = new Result<>();
        validateContains(clientId, result);
        if(!result.isSuccessful()) return result;
        return result;
    }

    private void validateContains(int clientId, Result result) {
        List<Client> modules = findAll();
        if(modules.stream().noneMatch(m -> m.getClientId() == clientId)) {
            result.addMessage("Client Id not present in clients");
        }
    }

}
