package htd.project.data;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import htd.project.models.Module;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class ModuleJdbcTemplateRepositoryTest {

    @Autowired
    ObjectRepository<Module> repository;

    @Autowired
    KnownGoodState knownGoodState;

    Module module1;
    Module module2;

    List<Module> modules;

    @BeforeEach
    void setUp() {
        knownGoodState.set();
        module1 = new Module(1, "Spring", LocalDate.of(2023, 04, 01), LocalDate.of(2023,04,07), 5, 5);
        module2 = new Module(2, "File IO", LocalDate.of(2023, 04, 8), LocalDate.of(2023, 04, 15), 4, 4);
        modules = new ArrayList<>();
        modules.add(module1);
        modules.add(module2);
    }

    @Test
    void readAll() {
        List<Module> result = repository.readAll();
        assertEquals(modules, result);
    }

    @Test
    void readById() {
        Module result = repository.readById(1);

        assertEquals(module1, result);

        result = repository.readById(100);

        assertNull(result);
    }

    @Test
    void create() {
        Module test = new Module(3, "SpringBoot", LocalDate.of(2023, 05, 8), LocalDate.of(2023, 05, 15), 6, 6);

        Module actual = repository.create(test);

        assertEquals(test, actual);
        assertEquals(3, repository.readAll().size());
    }

    @Test
    void update() {
        Module test = new Module(2, "SpringBoot", LocalDate.of(2023, 05, 8), LocalDate.of(2023, 05, 15), 6, 6);

        assertTrue(repository.update(test));
        assertEquals(test, repository.readById(2));
        assertEquals(2, repository.readAll().size());

        test = new Module(500, "SpringBoot", LocalDate.of(2023, 05, 8), LocalDate.of(2023, 05, 15), 6, 6);


        assertFalse(repository.update(test));
    }

    @Test
    void delete() {

        Module test = new Module(3, "SpringBoot", LocalDate.of(2023, 05, 8), LocalDate.of(2023, 05, 15), 6, 6);

        Module actual = repository.create(test);

        assertTrue(repository.delete(3));
        assertEquals(2, repository.readAll().size());

        assertFalse(repository.delete(500));
        assertEquals(2, repository.readAll().size());
    }
}