package htd.project.domains;

import htd.project.data.ContractorCohortModuleRepository;
import htd.project.data.ObjectRepository;
import htd.project.models.Cohort;
import htd.project.models.ContractorCohortModule;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class ContractorCohortModuleService {
    private ContractorCohortModuleRepository repository;

    public ContractorCohortModuleService(ContractorCohortModuleRepository repository) {
        this.repository = repository;
    }

    public List<ContractorCohortModule> findAll() {
        return repository.readAll();
    }

    public List<ContractorCohortModule> findByCohort(int cohortId) {
        return repository.readByCohort(cohortId);
    }
    public List<ContractorCohortModule> findByContractor(int contractorId) {
        return repository.readByContractor(contractorId);
    }
    public List<ContractorCohortModule> findByModule(int moduleId) {
        return repository.readByModule(moduleId);
    }
    public Result<ContractorCohortModule> create(ContractorCohortModule ccm) {
        Result<ContractorCohortModule> result = validate(ccm);
        if(!result.isSuccessful()) return result;

        validateDuplicates(result, ccm);
        if(!result.isSuccessful()) return result;

        ccm = repository.create(ccm);
        if(ccm == null) {
            result.addMessage("Database Error when creating grade");
            return result;
        }
        result.setPayload(ccm);
        return result;
    }
    public Result<ContractorCohortModule> update(ContractorCohortModule ccm) {
        Result<ContractorCohortModule> result = validate(ccm);
        if(!result.isSuccessful()) return result;

        validateContains(result, ccm);
        if(!result.isSuccessful()) return result;

        if(!repository.update(ccm)) {
            result.addMessage("Database Error when updating grade");
            return result;
        }

        result.setPayload(ccm);
        return result;

    }



    public Result<Void> delete(int contractorId, int cohortId, int moduleId) {
        Result<Void> result = new Result<>();
        validateContains(result, contractorId, cohortId, moduleId);
        if(!result.isSuccessful()) return result;

        if(!repository.deleteByIds(contractorId, cohortId, moduleId)) {
            result.addMessage("Database Error when deleting grade");

        }

        return result;
    }
    private Result<ContractorCohortModule> validate(ContractorCohortModule ccm) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<ContractorCohortModule>> errors = validator.validate(ccm);

        Result<ContractorCohortModule> result = new Result<>();
        if(!errors.isEmpty()) {

            for (ConstraintViolation<ContractorCohortModule> violation :
                    errors) {
                result.addMessage(violation.getMessage());
            }
        }

        return result;
    }

    private void validateDuplicates(Result<ContractorCohortModule> result, ContractorCohortModule ccm) {
        List<ContractorCohortModule> ccmList = findAll();

        if(ccmList.stream().anyMatch(CCM -> CCM.equals(ccm))) {
            result.addMessage("Cannot add duplicate relationship to table");
        }
    }

    private void validateContains(Result<ContractorCohortModule> result, ContractorCohortModule ccm) {
        List<ContractorCohortModule> ccmList = findAll();

        if(ccmList.stream().noneMatch(CCM -> CCM.equals(ccm))) {
            result.addMessage("Set of ids not present in existing list");
        }
    }

    private void validateContains(Result<Void> result, int contractorId, int cohortId, int moduleId) {
        List<ContractorCohortModule> ccmList = findAll();

        if(ccmList.stream().noneMatch(CCM -> CCM.getContractorId()==contractorId&&CCM.getCohortId()==cohortId&&CCM.getModuleId()==moduleId)) {
            result.addMessage("Set of ids not present in existing list");
        }
    }

}
