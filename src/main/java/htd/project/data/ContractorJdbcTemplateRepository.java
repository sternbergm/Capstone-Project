package htd.project.data;

import htd.project.models.Contractor;
import htd.project.models.Module;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ContractorJdbcTemplateRepository implements ObjectRepository<Contractor> {
    private JdbcTemplate jdbcTemplate;

    public ContractorJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Contractor> readAll() {
        return null;
    }

    @Override
    public Contractor readById(int id) {
        return null;
    }

    @Override
    public Contractor create(Contractor contractor) {
        return null;
    }

    @Override
    public boolean update(Contractor contractor) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}