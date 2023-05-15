package htd.project.data.mappers;

import htd.project.models.Cohort;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CohortMapper implements RowMapper<Cohort> {
    @Override
    public Cohort mapRow(ResultSet resultSet, int i) throws SQLException {
        Cohort cohort = new Cohort();
        cohort.setCohortId(resultSet.getInt("cohort_id"));
        cohort.setStart_date(resultSet.getDate("start_date").toLocalDate());
        cohort.setEnd_date(resultSet.getDate("end_date").toLocalDate());


        cohort.setClient(new CLientMapper(resultSet, i));
        cohort.setInstructor(new InstructorMapper(resultSet, i));

        return cohort;
    }
}
