package htd.project.data;

import htd.project.data.mappers.ContractorMapper;
import htd.project.models.Contractor;
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
public class ContractorJdbcTemplateRepository implements ObjectRepository<Contractor> {
    private JdbcTemplate jdbcTemplate;

    public ContractorJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Contractor> readAll() {
        final String sql = "select " +
                "contractor_id, " +
                "first_name, " +
                "last_name, " +
                "date_of_birth, " +
                "address, " +
                "email, " +
                "salary, " +
                "isHired " +
                "from contractors;";
        try {
            return jdbcTemplate.query(sql, new ContractorMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Contractor readById(int id) {

        final String sql = "select " +
                "contractor_id, " +
                "first_name, " +
                "last_name, " +
                "date_of_birth, " +
                "address, " +
                "email, " +
                "salary, " +
                "isHired " +
                "from contractors " +
                "where contractor_id= ?;";
        try {
            return jdbcTemplate.query(sql, new ContractorMapper(), id).stream().findFirst().orElse(null);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Contractor create(Contractor contractor) {
        final String sql = "insert into Contractors (first_name, last_name, date_of_birth, address, email,salary,isHired) " +
                "values (?,?,?,?,?,?,?);";

        int rowsAffected = 0;
        KeyHolder keys = new GeneratedKeyHolder();

        try {
            rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, contractor.getFirstName());
                ps.setString(2, contractor.getLastName());
                ps.setDate(3, Date.valueOf(contractor.getDateOfBirth()));
                ps.setString(4, contractor.getAddress());
                ps.setString(5, contractor.getEmail());
                ps.setBigDecimal(6, contractor.getSalary());
                ps.setBoolean(7, contractor.isHired());
                return ps;
            }, keys);
        } catch (DataAccessException e) {
            return null;
        }

        if(rowsAffected<=0) return null;

        contractor.setContractorId(keys.getKey().intValue());
        return contractor;
    }


    @Override
    public boolean update(Contractor contractor) {
        final String sql = "update contractors set " +
                "first_name = ?, " +
                "last_name = ?, " +
                "date_of_birth = ?, " +
                "address = ?, " +
                "email = ?, " +
                "salary = ?, " +
                "isHired = ? " +
                "where contractor_id = ?;";

        int rowsUpdated = 0;

        try {
            rowsUpdated = jdbcTemplate.update(sql, contractor.getFirstName(),contractor.getLastName(),Date.valueOf(contractor.getDateOfBirth()),contractor.getAddress(),contractor.getEmail(),contractor.getSalary(),contractor.isHired(),contractor.getContractorId());
        } catch (DataAccessException e) {
            return false;
        }

        return rowsUpdated>0;
    }

    @Override
    public boolean delete(int id) {
        final String sql = "delete from contractors " +
                "where contractor_id = ?;";

        int rowsDeleted = 0;

        try {
            rowsDeleted = jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            return false;
        }

        return rowsDeleted>0;
    }
    }
