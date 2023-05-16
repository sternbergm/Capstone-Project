package htd.project.domains;

import htd.project.data.ObjectRepository;
import htd.project.models.Cohort;
import htd.project.models.ContractorCohortModule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CohortService {

    private ObjectRepository<Cohort> repository;
    private ObjectRepository<ContractorCohortModule> CCMRepository;

    public CohortService(ObjectRepository<Cohort> repository, ObjectRepository<ContractorCohortModule> CCMRepository) {
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
        }

        return result;
    }



    public Result<Cohort> update(Cohort cohort) {
        return null;
    }
    public Result<Void> delete(int cohortId) {
        return null;
    }
    private Result<Cohort> validate(Cohort cohort) {
        return null;
    }

    private void validateDuplicates(Cohort cohort, Result<Cohort> result) {
    }
    private Result<Void> validateDelete(Cohort cohort) {
        return null;
    }

}
