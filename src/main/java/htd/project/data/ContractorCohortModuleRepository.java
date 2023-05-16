package htd.project.data;

import htd.project.models.ContractorCohortModule;

import java.util.List;

public interface ContractorCohortModuleRepository {

    List<ContractorCohortModule> readAll();
	List<ContractorCohortModule> readByContractor(int contractorId);
	List<ContractorCohortModule> readByCohort(int cohortId);
	List<ContractorCohortModule> readByModule(int moduleId);
	ContractorCohortModule create(ContractorCohortModule ccm);
	boolean update(ContractorCohortModule ccm);
	boolean deleteByIds(int contractorId, int cohortId, int moduleId);

}
