package htd.project.data.mappers;

import htd.project.models.Instructor;
import htd.project.models.Module;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InstructorMapper implements RowMapper<Instructor> {

    @Override
    public Instructor mapRow(ResultSet resultSet, int i) throws SQLException {
        Instructor result = new Instructor();
        result.setInstructorId(resultSet.getInt("instructor_id"));
        result.setFirstName(resultSet.getString("first_name"));
        result.setLastname(resultSet.getString("last_name"));
        result.setYearsOfExperience(resultSet.getInt("years_of_experience"));
        result.setExpertise(resultSet.getString("expertise"));
        result.setSalary(resultSet.getBigDecimal("salary"));
        return result;
    }
}
