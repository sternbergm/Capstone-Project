package htd.project.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ContractorTest {
    Contractor contractor;
    Validator validator;
    @BeforeEach
    void setUp() {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }

//    @Test
//    void validatorShouldPass() {
//        Contractor contractor = new Contractor();
//        Set<ConstraintViolation<Contractor>> errors = validator.validate(contractor);
//        assertEquals(0, errors.size());
//    }


    }

