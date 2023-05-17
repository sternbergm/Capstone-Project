package htd.project.domains;

import htd.project.data.ContractorCohortModuleRepository;
import htd.project.models.ContractorCohortModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ContractorCohortModuleServiceTest {

    @Autowired
    ContractorCohortModuleService service;

    @MockBean
    ContractorCohortModuleRepository CCMRepository;

    ContractorCohortModule ccm;

    @BeforeEach
    void setUp() {
        ccm = new ContractorCohortModule(1, 1, 1, BigDecimal.TEN);
    }

    @Test
    void create() {
        when(CCMRepository.create(ccm)).thenReturn(ccm);

        Result<ContractorCohortModule> result = service.create(ccm);

        assertTrue(result.isSuccessful());
        assertEquals(ccm, result.getPayload());

        //trigger invalid object validation
        ccm.setContractorId(-1);
        result = service.create(ccm);

        assertFalse(result.isSuccessful());
        ccm.setContractorId(1);

        //trigger duplicate object validation
        when(CCMRepository.readAll()).thenReturn(List.of(ccm));
        result = service.create(ccm);

        assertFalse(result.isSuccessful());
        when(CCMRepository.readAll()).thenReturn(new ArrayList<>());

        //trigger database error validation
        when(CCMRepository.create(ccm)).thenReturn(null);
        result = service.create(ccm);

        assertFalse(result.isSuccessful());
    }

    @Test
    void update() {
        when(CCMRepository.update(ccm)).thenReturn(true);
        when(CCMRepository.readAll()).thenReturn(List.of(ccm));

        Result<ContractorCohortModule> result = service.update(ccm);

        assertTrue(result.isSuccessful());
        assertEquals(ccm, result.getPayload());

        //Trigger not found validation
        when(CCMRepository.readAll()).thenReturn(new ArrayList<>());

        result = service.update(ccm);

        assertFalse(result.isSuccessful());
        when(CCMRepository.readAll()).thenReturn(List.of(ccm));

        //trigger database validation
        when(CCMRepository.update(ccm)).thenReturn(false);
        result = service.update(ccm);

        assertFalse(result.isSuccessful());
    }

    @Test
    void delete() {
        when(CCMRepository.deleteByIds(ccm.getContractorId(), ccm.getCohortId(), ccm.getModuleId())).thenReturn(true);
        when(CCMRepository.readAll()).thenReturn(List.of(ccm));

        Result<Void> result = service.delete(ccm.getContractorId(), ccm.getCohortId(), ccm.getModuleId());

        assertTrue(result.isSuccessful());

        //Trigger not found validation
        when(CCMRepository.readAll()).thenReturn(new ArrayList<>());

        result = service.delete(ccm.getContractorId(), ccm.getCohortId(), ccm.getModuleId());

        assertFalse(result.isSuccessful());
        when(CCMRepository.readAll()).thenReturn(List.of(ccm));

        //trigger database validation
        when(CCMRepository.deleteByIds(ccm.getContractorId(), ccm.getCohortId(), ccm.getModuleId())).thenReturn(false);
        result = service.delete(ccm.getContractorId(), ccm.getCohortId(), ccm.getModuleId());

        assertFalse(result.isSuccessful());
    }
}