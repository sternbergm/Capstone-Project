package htd.project.domains;

import htd.project.data.ContractorCohortModuleRepository;
import htd.project.data.ObjectRepository;
import htd.project.models.Client;
import htd.project.models.ContractorCohortModule;
import htd.project.models.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ClientServiceTest {
    @Autowired
    ClientService service;

    @MockBean
    ObjectRepository<Client> clientRepository;

    Client client;

    @BeforeEach
    void setUp() {
        client = new Client(1, "Main bank", "One Main st", 1000, "info@mainbank.com");
    }
    @Test
    void create() {
        when(clientRepository.create(client)).thenReturn(client);

        Result<Client> result = service.create(client);

        assertTrue(result.isSuccessful());

        assertEquals(client, result.getPayload());

        client.setClientName(null);
        result = service.create(client);
        assertFalse(result.isSuccessful());
        assertNull(result.getPayload());
        client.setAddress(null);
        result = service.create(client);
        assertFalse(result.isSuccessful());
        assertNull(result.getPayload());
        client.setEmail(null);
        result = service.create(client);
        assertFalse(result.isSuccessful());
        assertNull(result.getPayload());

        when(clientRepository.readAll()).thenReturn(List.of(client));
        result = service.create(client);
        assertFalse(result.isSuccessful());
    }

    @Test
    void update() {
        when(clientRepository.update(client)).thenReturn(true);
        when(clientRepository.readAll()).thenReturn(List.of(client));
        Result<Client> result = service.update(client);

        assertTrue(result.isSuccessful());

        assertEquals(client, result.getPayload());

        when(clientRepository.update(client)).thenReturn(false);

        result = service.update(client);

        assertFalse(result.isSuccessful());

        when(clientRepository.readAll()).thenReturn(new ArrayList<>());

        result = service.update(client);

        assertFalse(result.isSuccessful());
    }

    @Test
    void delete() {

        when(clientRepository.delete(1)).thenReturn(true);
        when(clientRepository.readAll()).thenReturn(List.of(client));

        Result<Void> result = service.delete(1);

        assertTrue(result.isSuccessful());

        result = service.delete(500);

        assertFalse(result.isSuccessful());

        when(clientRepository.delete(1)).thenReturn(false);

        result = service.delete(1);

        assertFalse(result.isSuccessful());

    }
}