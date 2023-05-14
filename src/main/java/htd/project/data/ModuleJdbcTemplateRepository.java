package htd.project.data;

import htd.project.data.mappers.ModuleMapper;
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
public class ModuleJdbcTemplateRepository implements ObjectRepository<Module> {

    private JdbcTemplate jdbcTemplate;

    public ModuleJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Module> readAll() {
        final String sql = "select " +
                "module_id, " +
                "topic, " +
                "start_date, " +
                "end_date, " +
                "exercise_amount, " +
                "lesson_amount " +
                "from modules;";

        try {
            return jdbcTemplate.query(sql, new ModuleMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Module readById(int id) {
        final String sql = "select " +
                "module_id, " +
                "topic, " +
                "start_date, " +
                "end_date, " +
                "exercise_amount, " +
                "lesson_amount " +
                "from modules " +
                "where module_id = ?;";

        try {
            return jdbcTemplate.query(sql, new ModuleMapper(), id).stream().findFirst().orElse(null);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Module create(Module module) {
        final String sql = "insert into modules (topic, start_date, end_date, exercise_amount, lesson_amount) " +
                "values (?,?,?,?,?);";

        int rowsAffected = 0;
        KeyHolder keys = new GeneratedKeyHolder();

        try {
            rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, module.getTopic());
                ps.setDate(2, Date.valueOf(module.getStartDate()));
                ps.setDate(3, Date.valueOf(module.getEndDate()));
                ps.setInt(4, module.getExerciseAmount());
                ps.setInt(5, module.getLessonAmount());
                return ps;
            }, keys);
        } catch (DataAccessException e) {
            return null;
        }

        if(rowsAffected<=0) return null;

        module.setModuleId(keys.getKey().intValue());
        return module;

    }

    @Override
    public boolean update(Module module) {
        final String sql = "update modules set " +
                "topic = ?, " +
                "start_date = ?, " +
                "end_date = ?, " +
                "exercise_amount = ?, " +
                "lesson_amount = ? " +
                "where module_id = ?;";

        int rowsUpdated = 0;

        try {
            rowsUpdated = jdbcTemplate.update(sql, module.getTopic(),Date.valueOf(module.getStartDate()), Date.valueOf(module.getEndDate()), module.getExerciseAmount(), module.getLessonAmount(), module.getModuleId());
        } catch (DataAccessException e) {
            return false;
        }

        return rowsUpdated>0;
    }

    @Override
    public boolean delete(int id) {
        final String sql = "delete from modules " +
                "where module_id = ?;";

        int rowsDeleted = 0;

        try {
            rowsDeleted = jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            return false;
        }

        return rowsDeleted>0;
    }
}
