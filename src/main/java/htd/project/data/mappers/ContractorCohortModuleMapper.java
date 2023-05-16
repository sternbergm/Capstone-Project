package htd.project.data.mappers;

import htd.project.models.ContractorCohortModule;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContractorCohortModuleMapper implements RowMapper<ContractorCohortModule> {
    @Override
    public ContractorCohortModule mapRow(ResultSet resultSet, int i) throws SQLException {
        return new ContractorCohortModule(resultSet.getInt("contractor_id"),
                resultSet.getInt("cohort_id"),
                resultSet.getInt("module_id"),
                resultSet.getBigDecimal("grade"));
    }
}
