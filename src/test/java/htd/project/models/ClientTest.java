package htd.project.models;

import htd.project.models.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    Client client;
    Validator validator;
    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validatorShouldPass() {
        Client client =new Client(1, "Main bank", "One Main st", 1000, "info@mainbank.com");
        Set<ConstraintViolation<Client>> errors = validator.validate(client);

        assertEquals(0, errors.size());
    }
}