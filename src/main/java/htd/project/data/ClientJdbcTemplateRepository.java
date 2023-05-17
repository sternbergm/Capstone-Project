package htd.project.data;

import htd.project.data.mappers.ClientMapper;
import htd.project.data.mappers.ModuleMapper;
import htd.project.models.Client;
import htd.project.models.Module;
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
public class ClientJdbcTemplateRepository implements ObjectRepository<Client>{
    private JdbcTemplate jdbcTemplate;

    public ClientJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Client> readAll() {
        final String sql = "select " +
                "client_id, " +
                "`name`, " +
                "address, " +
                "company_size, " +
                "email " +
                "from clients;";
        try {
            return jdbcTemplate.query(sql, new ClientMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }


    @Override
    public  Client readById(int id) {
        final String sql = "select " +
                "client_id, " +
                "`name`, " +
                "address, " +
                "company_size, " +
                "email " +
                "from clients " +
                "where client_id = ?;";
        try {
            return jdbcTemplate.query(sql, new ClientMapper(), id).stream().findFirst().orElse(null);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Client create(Client client) {
        final String sql = "insert into clients (`name`,address,company_size,email) " +
                "values (?,?,?,?);";

        int rowsAffected = 0;
        KeyHolder keys = new GeneratedKeyHolder();

        try {
            rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, client.getClientName());
                ps.setString(2, client.getAddress());
                ps.setInt(3, client.getCompanySize());
                ps.setString(4, client.getEmail());
                return ps;
            }, keys);
        } catch (DataAccessException e) {
            return null;
        }

        if(rowsAffected<=0) return null;

        client.setClientId(keys.getKey().intValue());
        return client;

    }

    @Override
    public boolean update(Client client) {

        final String sql = "update clients set " +
                "`name`= ?, " +
                "address = ?, " +
                "company_size = ?, " +
                "email = ? " +
                "where client_id = ?;";
        int rowsUpdated = 0;
        try {
            rowsUpdated = jdbcTemplate.update(sql,client.getClientName(),client.getAddress(),client.getCompanySize(),client.getEmail(),client.getClientId());
        } catch (DataAccessException e) {
            return false;
        }

        return rowsUpdated>0;
    }
    @Override
    public boolean delete(int id) {
        final String sql = "delete from clients " +
                "where client_id = ?;";

        int rowsDeleted = 0;

        try {
            rowsDeleted = jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            return false;
        }

        return rowsDeleted>0;
    }
}


