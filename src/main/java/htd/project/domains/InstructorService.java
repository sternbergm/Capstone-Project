package htd.project.domains;

import htd.project.data.ContractorCohortModuleRepository;
import htd.project.data.ObjectRepository;
import htd.project.models.Instructor;
import htd.project.models.Module;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

public class InstructorService {
    private ObjectRepository<Instructor> repository;

    private ContractorCohortModuleRepository CCMRepository;

    public InstructorService(ObjectRepository<Instructor> repository, ContractorCohortModuleRepository CCMRepository) {
        this.repository = repository;
        this.CCMRepository = CCMRepository;
    }

    public List<Instructor> findAll(){
        return repository.readAll();
    }
    public Instructor findById(int instructorId){
        return repository.readById(instructorId);
    }
    public Result<Instructor> create(Instructor instructor){
        Result<Instructor> result = validate(instructor);
        if(!result.isSuccessful()) return  result;
        validateDuplicates(instructor, result);
        if(!result.isSuccessful()) return  result;

        instructor = repository.create(instructor);
        if(instructor == null) {
            result.addMessage("Database Error when creating instructor");
            return result;
        }

        result.setPayload(instructor);
        return result;
    }
    public Result<Instructor> update(Instructor instructor){
        Result<Instructor> result = validate(instructor);
        if(!result.isSuccessful()) return  result;

        validateContains(instructor.getInstructorId(), result);
        if(!result.isSuccessful()) return  result;

        if(!repository.update(instructor)) {
            result.addMessage("Database Error when updating instructor");
            return result;
        }

        result.setPayload(instructor);
        return result;
    }
    public Result<Void> delete(int instructorId){
        Result<Void> result = validateDelete(instructorId);
        if(!result.isSuccessful()) return  result;

        if(!repository.delete(instructorId)) {
            result.addMessage("Database Error when deleting instructor");
        }

        return result;
    }
    private Result<Instructor> validate(Instructor instructor){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Instructor>> errors = validator.validate(instructor);

        Result<Instructor> result = new Result<>();
        if(!errors.isEmpty()) {

            for (ConstraintViolation<Instructor> violation :
                    errors) {
                result.addMessage(violation.getMessage());
            }

            return result;
        }

//        if(instructor.getStartDate().isAfter(module.getEndDate())) {
//            result.addMessage("Start date cannot be before end date");
//        }

        return result;
    }

    private void validateDuplicates(Instructor instructor, Result<Instructor> result) {
        List<Instructor> instructors = findAll();

        if(instructors.stream().anyMatch(m -> m.equals(instructor))) {
            result.addMessage("Instructor cannot be duplicate, must have unique ");
        }
    }
    private Result<Void> validateDelete(int instructorId){

        Result<Void> result = new Result<>();
        validateContains(instructorId, result);
        if(!result.isSuccessful()) return result;

//        if(CCMRepository.readByInstructor(instructorId).size()>0) {
//            result.addMessage("Module is currently in use in a cohort, you must delete this relation first");
//        }

        return result;
    }

    private void validateContains(int instructorId, Result result) {
        List<Instructor> instructors = findAll();
        if(instructors.stream().noneMatch(m -> m.getInstructorId() == instructorId)) {
            result.addMessage("Instructor Id not present in instructors");
        }
    }

}
