package htd.project.data;

import htd.project.models.Client;
import htd.project.models.Contractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ContractorJdbcTemplateRepositoryTest {

    @Autowired
    ObjectRepository<Contractor> repository;

    @Autowired
    KnownGoodState knownGoodState;

    Contractor contractor1;
    Contractor contractor2;
    List<Contractor> contractors;

    @BeforeEach
    void setUp() {
        knownGoodState.set();
        contractor1 = new Contractor(1, "John", "Doe",LocalDate.of(1990,01,01), "123 way st", "john@doe.com", BigDecimal.valueOf(3000000,2), true);
        contractor2 = new Contractor(2, "Jane", "Doe", LocalDate.of(1990,10,10), "456 main ave", "Jane@doe.com", BigDecimal.valueOf(4000000,2), true);
        contractors = new ArrayList<>();
        contractors.add(contractor1);
        contractors.add(contractor2);
    }

    @Test
    void readAll() {
        List<Contractor> result = repository.readAll();
        assertEquals(contractors, result);
    }

    @Test
    void readById() {
        Contractor result = repository.readById(1);

        assertEquals(contractor1, result);

        result = repository.readById(100);

        assertNull(result);

    }

    @Test
    void create() {

        Contractor test = new Contractor(3, "Jane2", "Doe", LocalDate.of(1990,10,10), "456main ave", "Jane@doe.com", new BigDecimal(40000.00), true);

        Contractor actual = repository.create(test);

        assertEquals(test, actual);
        assertEquals(3, repository.readAll().size());

    }

    @Test
    void update() {
        Contractor test = new Contractor(2, "Jane2", "Doe", LocalDate.of(1990,10,10), "456main ave", "Jane@doe.com", BigDecimal.valueOf(4000000,2), true);

        assertTrue(repository.update(test));
        assertEquals(test, repository.readById(2));
        assertEquals(2, repository.readAll().size());

        test = new Contractor(500, "Jane2", "Doe", LocalDate.of(1990,10,10), "456main ave", "Jane@doe.com", BigDecimal.valueOf(4000000,2), true);

        assertFalse(repository.update(test));
    }

    @Test
    void delete() {

        Contractor test = new Contractor(3, "Jane2", "Doe", LocalDate.of(1990,10,10), "456main ave", "Jane@doe.com", BigDecimal.valueOf(4000000,2), true);
        Contractor actual = repository.create(test);

        assertTrue(repository.delete(3));
        assertEquals(2, repository.readAll().size());

        assertFalse(repository.delete(500));
        assertEquals(2, repository.readAll().size());
    }
}