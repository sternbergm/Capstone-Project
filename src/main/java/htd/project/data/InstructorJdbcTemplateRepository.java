package htd.project.data;

import htd.project.data.mappers.ContractorMapper;
import htd.project.data.mappers.InstructorMapper;
import htd.project.data.mappers.ModuleMapper;
import htd.project.models.Instructor;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class InstructorJdbcTemplateRepository implements ObjectRepository<Instructor> {
    private JdbcTemplate jdbcTemplate;

    public InstructorJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Instructor> readAll() {

        final String sql = "select " +
                "instructor_id, " +
                "first_name, " +
                "last_name, " +
                "years_of_experience, " +
                "expertise, " +
                "salary " +
                "from instructors;";
        try {
            return jdbcTemplate.query(sql, new InstructorMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }


    @Override
    public Instructor readById(int id) {

        final String sql = "select " +
                "instructor_id, "+
                "first_name, " +
                "last_name, " +
                "years_of_experience, " +
                "expertise, " +
                "salary " +
                "from instructors " +
                "where instructor_id = ?;";
        try {
            return jdbcTemplate.query(sql, new InstructorMapper(), id).stream().findFirst().orElse(null);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Instructor create(Instructor instructor) {

        final String sql = "insert into instructors (first_name,last_name,years_of_experience,expertise,salary) " +
                "values (?,?,?,?,?);";
        int rowsAffected = 0;
        KeyHolder keys = new GeneratedKeyHolder();
        try {
            rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, instructor.getFirstName());
                ps.setString(2, instructor.getLastname());
                ps.setInt(3, instructor.getYearsOfExperience());
                ps.setString(4, instructor.getExpertise());
                ps.setBigDecimal(5, instructor.getSalary());
                return ps;
            }, keys);
        } catch (DataAccessException e) {
            return null;
        }
        if(rowsAffected<=0) return null;
        instructor.setInstructorId(keys.getKey().intValue());
        return instructor;
    }

    @Override
    public boolean update(Instructor instructor) {
        final String sql = "update instructors set " +
                "first_name = ?, " +
                "last_name = ?, " +
                "years_of_experience = ?, " +
                "expertise = ?, " +
                "salary = ? " +
                "where instructor_id = ?;";
        int rowsUpdated = 0;
        try {
            rowsUpdated = jdbcTemplate.update(sql, instructor.getFirstName(),instructor.getLastname(),instructor.getYearsOfExperience(),instructor.getExpertise(),instructor.getSalary(),instructor.getInstructorId());
        } catch (DataAccessException e) {
            return false;
        }

        return rowsUpdated>0;
    }

    @Override
    public boolean delete(int id) {
        final String sql = "delete from instructors " +
                "where instructor_id = ?;";

        int rowsDeleted = 0;

        try {
            rowsDeleted = jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            return false;
        }

        return rowsDeleted>0;
    }
    }
