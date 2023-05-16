package htd.project.data;

import htd.project.models.Instructor;
import htd.project.models.Module;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InstructorJdbcTemplateRepository implements ObjectRepository<Instructor> {
    private JdbcTemplate jdbcTemplate;

    public InstructorJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Instructor> readAll() {
        return null;
    }

    @Override
    public Instructor readById(int id) {
        return null;
    }

    @Override
    public Instructor create(Instructor instructor) {
        return null;
    }

    @Override
    public boolean update(Instructor instructor) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
