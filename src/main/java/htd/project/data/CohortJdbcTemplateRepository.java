package htd.project.data;

import htd.project.data.mappers.CohortMapper;
import htd.project.data.mappers.ModuleMapper;
import htd.project.models.Cohort;
import htd.project.models.Contractor;
import htd.project.models.Module;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CohortJdbcTemplateRepository implements ObjectRepository<Cohort> {

    private JdbcTemplate jdbcTemplate;

    public CohortJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Cohort> readAll() {
        final String sql = "select " +
                "cohort_id, " +
                "start_date, " +
                "end_date, " +
                "c.client_id, " +
                "c.`name`, " +
                "c.address, " +
                "c.company_size, " +
                "c.email, " +
                "i.instructor_id, " +
                "i.first_name, " +
                "i.last_name, " +
                "i.years_of_experience, " +
                "i.expertise, " +
                "i.salary " +
                "from cohorts " +
                "inner join clients c on c.client_id = cohorts.client_id " +
                "inner join instructors i on i.instructor_id = cohorts.instructor_id;";

        List<Cohort> result = null;
        try {
            result = jdbcTemplate.query(sql, new CohortMapper());
        } catch (DataAccessException e) {
            return null;
        }

        result.forEach(this::addContractors);
        result.forEach(this::addModules);

        return result;
    }



    @Override
    public Cohort readById(int id) {
        final String sql = "select " +
                "cohort_id, " +
                "start_date, " +
                "end_date, " +
                "c.client_id, " +
                "c.`name`, " +
                "c.address, " +
                "c.company_size, " +
                "c.email, " +
                "i.instructor_id, " +
                "i.first_name, " +
                "i.last_name, " +
                "i.years_of_experience, " +
                "i.expertise, " +
                "i.salary " +
                "from cohorts " +
                "inner join clients c on c.client_id = cohorts.client_id " +
                "inner join instructors i on i.instructor_id = cohorts.instructor_id " +
                "where cohort_id = ?;";

        Cohort cohort = null;
        try {
            cohort = jdbcTemplate.query(sql, new CohortMapper(), id).stream().findFirst().orElse(null);
        } catch (DataAccessException e) {
            return null;
        }

        addContractors(cohort);
        addModules(cohort);

        return cohort;
    }

    @Override
    public Cohort create(Cohort cohort) {

        final String sql = "insert into cohorts (start_date, end_date, client_id, instructor_id) values (?,?,?,?);";

        int rowsAffected = 0;

        KeyHolder keys = new GeneratedKeyHolder();

        try {
            rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ps.setDate(1, Date.valueOf(cohort.getStart_date()));
                ps.setDate(2, Date.valueOf(cohort.getEnd_date()));
                ps.setInt(3, cohort.getClient().getClientId);
                ps.setInt(4, cohort.getInstructor().getInstructorId());
                return ps;
            }, keys);
        } catch (DataAccessException e) {
            return null;
        }

        if(rowsAffected<=0) return null;

        cohort.setCohortId(keys.getKey().intValue());
        return cohort;
    }

    @Override
    public boolean update(Cohort cohort) {

        final String sql = "update cohorts set " +
                "start_date = ?, " +
                "end_date = ?, " +
                "client_id = ?, " +
                "instructor_id = ? " +
                "where cohort_id = ?;";

        int rowsUpdated = 0;

        try {
            rowsUpdated = jdbcTemplate.update(sql,
                    Date.valueOf(cohort.getStart_date()),
                    Date.valueOf(cohort.getEnd_date()),
                    cohort.getClient().getClientId(),
                    cohort.getInstructor().getInstructorId(),
                    cohort.getCohortId());
        } catch (DataAccessException e) {
            return false;
        }

        return rowsUpdated>0;
    }

    @Override
    public boolean delete(int id) {
        final String sql = "delete from cohorts " +
                "where cohort_id = ?;";

        int rowsDeleted = 0;

        try {
            rowsDeleted = jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            return false;
        }

        return rowsDeleted>0;
    }


    private void addContractors(Cohort cohort) {
        final String sql = "select " +
                "ccm.cohort_id, " +
                "contractor_id, " +
                "first_name, " +
                "last_name, " +
                "date_of_birth, " +
                "address, " +
                "email, " +
                "salary, " +
                "isHired " +
                "from contractors " +
                "inner join contractor_cohort_module ccm on contractors.contractor_id = ccm.contractor_id " +
                "where ccm.cohort_id = ?;";

        List<Contractor> contractors;

        try {
            contractors = jdbcTemplate.query(sql, new ContractorMapper(), cohort.getCohortId());
        } catch (Exception e) {
            contractors = new ArrayList<>();
        }

        cohort.setContractors(contractors);

    }

    private void addModules(Cohort cohort) {

        final String sql = "select " +
                "ccm.cohort_id, " +
                "module_id, " +
                "topic, " +
                "start_date, " +
                "end_date, " +
                "exercise_amount, " +
                "lesson_amount " +
                "from modules " +
                "inner join contractor_cohort_module ccm on ccm.module_id = modules.module_id " +
                "where ccm.cohort_id = ?;";


        List<Module> modules;

        try {
            modules = jdbcTemplate.query(sql, new ModuleMapper(), cohort.getCohortId());
        } catch (Exception e) {
            modules = new ArrayList<>();
        }

        cohort.setModules(modules);
    }
}
