package htd.project.data.mappers;

import htd.project.models.Error;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ErrorMapper implements RowMapper<Error> {
    @Override
    public Error mapRow(ResultSet resultSet, int i) throws SQLException{
        return new Error(resultSet.getTimestamp("timestamp").toLocalDateTime(), resultSet.getString("stackTrace"));
    }
}
