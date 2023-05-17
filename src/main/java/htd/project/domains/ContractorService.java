package htd.project.domains;

import htd.project.data.ContractorCohortModuleRepository;
import htd.project.data.ObjectRepository;
import htd.project.models.Contractor;
import htd.project.models.Module;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

public class ContractorService {

    private ObjectRepository<Contractor> repository;

    private ContractorCohortModuleRepository CCMRepository;

    public ContractorService(ObjectRepository<Contractor> repository, ContractorCohortModuleRepository CCMRepository) {
        this.repository = repository;
        this.CCMRepository = CCMRepository;
    }

    public List<Contractor> findAll(){
        return repository.readAll();
    }
    public Contractor findById(int contractorId){
        return repository.readById(contractorId);
    }
    public Result<Contractor> create(Contractor contractor){
        Result<Contractor> result = validate(contractor);
        if(!result.isSuccessful()) return  result;
        validateDuplicates(contractor, result);
        if(!result.isSuccessful()) return  result;

        contractor = repository.create(contractor);
        if(contractor == null) {
            result.addMessage("Database Error when creating contractor");
            return result;
        }

        result.setPayload(contractor);
        return result;
    }
    public Result<Contractor> update(Contractor contractor){
        Result<Contractor> result = validate(contractor);
        if(!result.isSuccessful()) return  result;

        validateContains(contractor.getContractorId(), result);
        if(!result.isSuccessful()) return  result;

        if(!repository.update(contractor)) {
            result.addMessage("Database Error when updating contractor");
            return result;
        }

        result.setPayload(contractor);
        return result;
    }
    public Result<Void> delete(int contractorId){
        Result<Void> result = validateDelete(contractorId);
        if(!result.isSuccessful()) return  result;

        if(!repository.delete(contractorId)) {
            result.addMessage("Database Error when deleting contractor");
        }

        return result;
    }
    private Result<Contractor> validate(Contractor contractor){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Contractor>> errors = validator.validate(contractor);

        Result<Contractor> result = new Result<>();
        if(!errors.isEmpty()) {

            for (ConstraintViolation<Contractor> violation :
                    errors) {
                result.addMessage(violation.getMessage());
            }

            return result;
        }
        return result;
    }

    private void validateDuplicates(Contractor contractor, Result<Contractor> result) {
        List<Contractor> contractors = findAll();

        if(contractors.stream().anyMatch(m -> m.equals(contractor))) {
            result.addMessage("contractor cannot be duplicate, must have unique ");
        }
    }
    private Result<Void> validateDelete(int contractorId){

        Result<Void> result = new Result<>();
        validateContains(contractorId, result);
        if(!result.isSuccessful()) return result;

        if(CCMRepository.readByContractor(contractorId).size()>0) {
            result.addMessage("contractor is currently in use in a cohort, you must delete this relation first");
        }

        return result;
    }

    private void validateContains(int moduleId, Result result) {
        List<Contractor> contractors = findAll();
        if(contractors.stream().noneMatch(m -> m.getContractorId() == moduleId)) {
            result.addMessage("Module Id not present in modules");
        }
    }

}


