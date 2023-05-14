package htd.project.data.mappers;

import htd.project.models.Module;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModuleMapper implements RowMapper<Module> {
    @Override
    public Module mapRow(ResultSet resultSet, int i) throws SQLException {
        Module result = new Module();
        result.setModuleId(resultSet.getInt("module_id"));
        result.setTopic(resultSet.getString("topic"));
        result.setStartDate(resultSet.getDate("start_date").toLocalDate());
        result.setEndDate(resultSet.getDate("end_date").toLocalDate());
        result.setExerciseAmount(resultSet.getInt("exercise_amount"));
        result.setLessonAmount(resultSet.getInt("lesson_amount"));

        return result;
    }
}
