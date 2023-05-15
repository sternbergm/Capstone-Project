package htd.project.data;

import htd.project.models.ContractorCohortModule;

import java.util.List;

public interface ContractorCohortModuleRepository {

    List<ContractorCohortModule> findAll();
	ContractorCohortModule findByContractor(int contractorId);
    ContractorCohortModule findByCohort(int cohortId);
    ContractorCohortModule findByModule(int moduleId);
	ContractorCohortModule create(ContractorCohortModule ccm);
	boolean update(ContractorCohortModule ccm);
	boolean deleteByIds(int contractorId, int cohortId, int moduleId);

}
