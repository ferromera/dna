package ferromera.mutantdetector.model;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatMapper  implements RowMapper<Stat> {
    @Override
    public Stat mapRow(ResultSet resultSet, int i) throws SQLException {
        Stat stat = new Stat();
        stat.setId(resultSet.getInt("id"));
        stat.setCount(resultSet.getString("count"));
        return stat;
    }
}
