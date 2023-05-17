package htd.project.domains;

import htd.project.data.ContractorCohortModuleRepository;
import htd.project.data.ObjectRepository;
import htd.project.models.Module;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class ModuleService {
    private ObjectRepository<Module> repository;

    private ContractorCohortModuleRepository CCMRepository;

    public ModuleService(ObjectRepository<Module> repository, ContractorCohortModuleRepository CCMRepository) {
        this.repository = repository;
        this.CCMRepository = CCMRepository;
    }
    public List<Module> findAll(){
        return repository.readAll();
    }
    public Module findById(int moduleId){
        return repository.readById(moduleId);
    }
    public Result<Module> create(Module module){
        Result<Module> result = validate(module);
        if(!result.isSuccessful()) return  result;
        validateDuplicates(module, result);
        if(!result.isSuccessful()) return  result;

        module = repository.create(module);
        if(module == null) {
            result.addMessage("Database Error when creating module");
            return result;
        }

        result.setPayload(module);
        return result;
    }
    public Result<Module> update(Module module){
        Result<Module> result = validate(module);
        if(!result.isSuccessful()) return  result;

        validateContains(module.getModuleId(), result);
        if(!result.isSuccessful()) return  result;

        if(!repository.update(module)) {
            result.addMessage("Database Error when updating module");
            return result;
        }

        result.setPayload(module);
        return result;
    }
    public Result<Void> delete(int moduleId){
        Result<Void> result = validateDelete(moduleId);
        if(!result.isSuccessful()) return  result;

        if(!repository.delete(moduleId)) {
            result.addMessage("Database Error when deleting module");
        }

        return result;
    }
    private Result<Module> validate(Module module){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Module>> errors = validator.validate(module);

        Result<Module> result = new Result<>();
        if(!errors.isEmpty()) {

            for (ConstraintViolation<Module> violation :
                    errors) {
                result.addMessage(violation.getMessage());
            }
        }

        return result;
    }

    private void validateDuplicates(Module module, Result<Module> result) {
        List<Module> modules = findAll();

        if(modules.stream().anyMatch(m -> m.equals(module))) {
            result.addMessage("Module cannot be duplicate, must have unique topic and start/end dates");
        }
    }
    private Result<Void> validateDelete(int moduleId){

        Result<Void> result = new Result<>();
        validateContains(moduleId, result);
        if(!result.isSuccessful()) return result;

        if(CCMRepository.readByModule(moduleId).size()>0) {
            result.addMessage("Module is currently in use in a cohort, you must delete this relation first");
        }

        return result;
    }

    private void validateContains(int moduleId, Result result) {
        List<Module> modules = findAll();
        if(modules.stream().noneMatch(m -> m.getModuleId() == moduleId)) {
            result.addMessage("Module Id not present in modules");
        }
    }

}
