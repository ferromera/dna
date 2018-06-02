package ferromera.mutantdetector.dao;

import ferromera.mutantdetector.model.Count;
import ferromera.mutantdetector.model.Stat;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountMapper implements RowMapper<Count> {
    @Override
    public Count mapRow(ResultSet resultSet, int i) throws SQLException {
        Count count = new Count();
        count.setCalled(resultSet.getBoolean("is_called"));
        count.setLastValue(resultSet.getLong("last_value"));
        return count;
    }
}
