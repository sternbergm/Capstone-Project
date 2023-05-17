package htd.project.data;

import htd.project.models.Client;
import htd.project.models.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ClientJdbcTemplateRepositoryTest {


    @Autowired
    ObjectRepository<Client> repository;

    @Autowired
    KnownGoodState knownGoodState;

    Client client1;
    Client client2;

    List<Client> clients;

    @BeforeEach
    void setUp() {
        knownGoodState.set();
        client1 = new Client(1, "Main bank", "One Main st", 1000, "info@mainbank.com");
        client2 = new Client(2, "Realtors CO", "200 cherry road", 150, "realtors@mail.com");
        clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client2);
    }

    @Test
    void readAll() {
        List<Client> result = repository.readAll();
        assertEquals(clients, result);
    }

    @Test
    void readById() {
        Client result = repository.readById(1);

        assertEquals(client1, result);

        result = repository.readById(100);

        assertNull(result);
    }

    @Test
    void create() {
        Client test = new Client(3,"RealCO", "777 cherry road", 190, "realtors@mail.com");

       Client actual = repository.create(test);

        assertEquals(test, actual);
        assertEquals(3, repository.readAll().size());

    }

    @Test
    void update() {
        Client test = new Client(2, "Realtors CO", "200 cherry road", 150, "realtors@mail.com");

        assertTrue(repository.update(test));
        assertEquals(test, repository.readById(2));
        assertEquals(2, repository.readAll().size());

        test = new Client(2,"client changed","Address change",135,"own@gmail.com");
        assertTrue(repository.update(test));
    }

    @Test
    void delete() {

       // Client test = new Client(3, "Realtors CO", "200 cherry road", 150, "realtors@mail.com");
      //  Client test new Client

       // assertTrue(repository.update(test));

    }
}