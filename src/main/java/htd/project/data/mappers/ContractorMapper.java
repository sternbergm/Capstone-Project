package htd.project.data.mappers;

import htd.project.models.Contractor;
import htd.project.models.Module;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContractorMapper implements RowMapper<Contractor> {

    @Override
    public Contractor mapRow(ResultSet resultSet, int i) throws SQLException {
        Contractor result = new Contractor();
        result.setContractorId(resultSet.getInt("contractor_id"));
        result.setFirstName(resultSet.getString("first_name"));
        result.setLastName(resultSet.getString("last_name"));
        result.setDateOfBirth(resultSet.getDate("date_of_birth"));
        result.setAddress(resultSet.getString("address"));
        result.setEmail(resultSet.getString("email"));
        result.setSalary(resultSet.getBigDecimal("salary"));
        result.setHired(result.isHired());
        return result;
    }
}
