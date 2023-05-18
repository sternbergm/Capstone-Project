package htd.project.data;

import htd.project.data.mappers.ErrorMapper;
import htd.project.models.Error;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class GlobalExceptionJdbcTemplateRepository implements GlobalExceptionRepository{

    JdbcTemplate jdbcTemplate;

    public GlobalExceptionJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean add(Error error) {
        final String sql = "insert into globalExceptions values (?,?)";

        int rowsAffected = 0;

        try {
            rowsAffected = jdbcTemplate.update(sql, error.getTimestamp(), error.getStackTrace());
        } catch (DataAccessException e) {
            return false;
        }

        return rowsAffected >0;
    }

    @Override
    public List<Error> getAll() {
        final String sql = "select `timestamp`, stackTrace from globalExceptions";

        return jdbcTemplate.query(sql, new ErrorMapper());
    }
}
