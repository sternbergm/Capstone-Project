package htd.project.data;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class GlobalExceptionJdbcTemplateRepository implements GlobalExceptionRepository{

    JdbcTemplate jdbcTemplate;

    public GlobalExceptionJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean add(LocalDateTime timestamp, String stackTrace) {
        final String sql = "insert into globalExceptions values (?,?)";

        int rowsAffected = 0;

        try {
            rowsAffected = jdbcTemplate.update(sql, timestamp, stackTrace);
        } catch (DataAccessException e) {
            return false;
        }

        return rowsAffected >0;
    }
}
