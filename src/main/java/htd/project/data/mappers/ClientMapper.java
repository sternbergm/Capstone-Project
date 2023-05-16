package htd.project.data.mappers;

import htd.project.models.Client;
import htd.project.models.Module;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientMapper implements RowMapper<Client> {
    @Override
    public Client mapRow(ResultSet resultSet, int i) throws SQLException {

            Client result = new Client();
            result.setClientId(resultSet.getInt("client_id"));
            result.setClientName(resultSet.getString("`name`"));
            result.setAddress(resultSet.getString("address"));
            result.setCompanySize(resultSet.getInt("company_size"));
            result.setEmail(resultSet.getString("email"));
            return result;
    }
}
