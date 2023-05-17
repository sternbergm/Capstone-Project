package htd.project.domains;

import htd.project.data.ContractorCohortModuleRepository;
import htd.project.data.ObjectRepository;
import htd.project.models.Cohort;
import htd.project.models.ContractorCohortModule;
import htd.project.models.Module;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class CohortService {

    private ObjectRepository<Cohort> repository;
    private ContractorCohortModuleRepository CCMRepository;

    public CohortService(ObjectRepository<Cohort> repository, ContractorCohortModuleRepository CCMRepository) {
        this.repository = repository;
        this.CCMRepository = CCMRepository;
    }

    public List<Cohort> findAll() {
        return repository.readAll();
    }
    public Cohort findById(int cohortId) {
        return repository.readById(cohortId);
    }
    public Result<Cohort> create(Cohort cohort) {
        Result<Cohort> result = validate(cohort);
        if(!result.isSuccessful()) return result;

        validateDuplicates(cohort, result);
        if(!result.isSuccessful()) return result;

        cohort = repository.create(cohort);
        if(cohort == null) {
            result.addMessage("Database Error when creating cohort");
            return result;
        }

        result.setPayload(cohort);
        return result;
    }



    public Result<Cohort> update(Cohort cohort) {
        Result<Cohort> result = validate(cohort);
        if(!result.isSuccessful()) return result;

        validateContains(cohort.getCohortId(), result);
        if(!result.isSuccessful()) return result;

        if(!repository.update(cohort)) {
            result.addMessage("Database Error when updating cohort");
            return result;
        }

        result.setPayload(cohort);
        return result;
    }
    public Result<Void> delete(int cohortId) {
        Result<Void> result = validateDelete(cohortId);
        if(!result.isSuccessful()) return result;

        if(!repository.delete(cohortId)) {
            result.addMessage("Database Error when deleting cohort");
        }

        return result;
    }
    private Result<Cohort> validate(Cohort cohort) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Cohort>> errors = validator.validate(cohort);

        Result<Cohort> result = new Result<>();
        if(!errors.isEmpty()) {

            for (ConstraintViolation<Cohort> violation :
                    errors) {
                result.addMessage(violation.getMessage());
            }
        }

        return result;
    }

    private void validateDuplicates(Cohort cohort, Result<Cohort> result) {
        List<Cohort> cohorts = findAll();
        if(cohorts.stream().anyMatch(c -> c.equals(cohort))){
            result.addMessage("Cannot add duplicate cohort");
        }
    }
    private Result<Void> validateDelete(int cohortId) {
        Result result = new Result();

        validateContains(cohortId, result);
        if(!result.isSuccessful()) return result;

        if(CCMRepository.readByCohort(cohortId).size()>0) {
            result.addMessage("There are contractors and modules assigned to this cohort, it cannot be deleted");
        }

        return result;
    }

    private void validateContains(int cohortId, Result result) {
        List<Cohort> cohorts = findAll();
        if(cohorts.stream().noneMatch(c -> c.getCohortId() == cohortId)){
            result.addMessage("Cohort Id not present in cohorts");
        }
    }

}
