package htd.project.data;

import htd.project.models.Client;
import htd.project.models.Contractor;
import htd.project.models.Instructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class InstructorJdbcTemplateRepositoryTest {

    @Autowired
    ObjectRepository<Instructor> repository;

    @Autowired
    KnownGoodState knownGoodState;

    Instructor instructor1;
    Instructor instructor2;
    List<Instructor> instructors;
    @BeforeEach
    void setUp() {

            knownGoodState.set();
            instructor1 = new Instructor(1, "James", "Bond", 30, "espionage",BigDecimal.valueOf(3000000,2));
            instructor2 = new Instructor(2, "Indiana", "Jones", 20, "History", BigDecimal.valueOf(3500000,2));
            instructors = new ArrayList<>();
            instructors.add(instructor1);
            instructors.add(instructor2);
        }


    @Test
    void readAll() {
        List<Instructor> result = repository.readAll();
        assertEquals(instructors, result);
    }

    @Test
    void readById() {

            Instructor result = repository.readById(1);

            assertEquals(instructor1, result);

            result = repository.readById(100);

            assertNull(result);
    }

    @Test
    void create() {
        Instructor test = new Instructor(3, "James1", "Bond1", 20, "espionage11",BigDecimal.valueOf(3000000,2));

        Instructor actual = repository.create(test);

        assertEquals(test, actual);
        assertEquals(3, repository.readAll().size());
    }

    @Test
    void update() {
        Instructor test = new Instructor(2, "James18", "Bond18", 10, "espionage101",BigDecimal.valueOf(300000,2));

        assertTrue(repository.update(test));
        assertEquals(test, repository.readById(2));
        assertEquals(2, repository.readAll().size());

        test = new Instructor(500, "James18", "Bond18", 10, "espionage101",BigDecimal.valueOf(300000,2));
        assertFalse(repository.update(test));
    }

    @Test
    void delete() {
        Instructor test = new Instructor(3, "James18", "Bond18", 10, "espionage101", BigDecimal.valueOf(3000000,2));
        Instructor actual = repository.create(test);

        assertTrue(repository.delete(3));
        assertEquals(2, repository.readAll().size());

        assertFalse(repository.delete(500));
        assertEquals(2, repository.readAll().size());
    }
    }