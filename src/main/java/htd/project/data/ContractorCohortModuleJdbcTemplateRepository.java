package htd.project.data;

import htd.project.data.mappers.ContractorCohortModuleMapper;
import htd.project.models.ContractorCohortModule;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ContractorCohortModuleJdbcTemplateRepository implements ContractorCohortModuleRepository{

    private JdbcTemplate jdbcTemplate;

    public ContractorCohortModuleJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ContractorCohortModule> readAll() {
        final String sql = "select " +
                "contractor_id, " +
                "cohort_id, " +
                "module_id, " +
                "grade " +
                "from contractor_cohort_module;";

        try {
            return jdbcTemplate.query(sql, new ContractorCohortModuleMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<ContractorCohortModule> readByContractor(int contractorId) {
        final String sql = "select contractor_id, cohort_id, module_id, grade from contractor_cohort_module where contractor_id = ?;";

        try {
            return jdbcTemplate.query(sql, new ContractorCohortModuleMapper(), contractorId);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<ContractorCohortModule> readByCohort(int cohortId) {
        final String sql = "select contractor_id, cohort_id, module_id, grade from contractor_cohort_module where cohort_id = ?;";

        try {
            return jdbcTemplate.query(sql, new ContractorCohortModuleMapper(), cohortId);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<ContractorCohortModule> readByModule(int moduleId) {
        final String sql = "select contractor_id, cohort_id, module_id, grade from contractor_cohort_module where module_id = ?;";

        try {
            return jdbcTemplate.query(sql, new ContractorCohortModuleMapper(), moduleId);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public ContractorCohortModule create(ContractorCohortModule ccm) {
        final String sql = "insert into contractor_cohort_module (contractor_id, cohort_id, module_id, grade) values (?,?,?,?);";


        int rowsAffected = 0;

        try {
            rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ps.setInt(1, ccm.getContractorId());
                ps.setInt(2, ccm.getCohortId());
                ps.setInt(3, ccm.getModuleId());
                ps.setBigDecimal(4, ccm.getGrade());

                return ps;
            });
        } catch (DataAccessException e) {
            return null;
        }

        if (rowsAffected<=0) return null;
        else return ccm;
    }

    @Override
    public boolean update(ContractorCohortModule ccm) {
        final String sql = "update contractor_cohort_module set " +
                "contractor_id = ?, " +
                "cohort_id = ?, " +
                "module_id = ?, " +
                "grade = ? " +
                "where contractor_id = ? and cohort_id = ? and module_id = ?;";

        int rowsUpdated = 0;

        try {
            rowsUpdated = jdbcTemplate.update(sql, ccm.getContractorId(), ccm.getCohortId(), ccm.getModuleId(), ccm.getGrade(), ccm.getContractorId(), ccm.getCohortId(), ccm.getModuleId());
        } catch (DataAccessException e) {
            return false;
        }

        return rowsUpdated>0;
    }

    @Override
    public boolean deleteByIds(int contractorId, int cohortId, int moduleId) {
        final String sql = "delete from contractor_cohort_module " +
                "where contractor_id = ? and cohort_id = ? and module_id = ?;";

        int rowsDeleted = 0;

        try {
            rowsDeleted = jdbcTemplate.update(sql, contractorId, cohortId, moduleId);
        } catch (DataAccessException e) {
            return false;
        }

        return rowsDeleted>0;
    }
}
