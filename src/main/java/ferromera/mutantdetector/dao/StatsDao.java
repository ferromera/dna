package ferromera.mutantdetector.dao;


import ferromera.mutantdetector.model.Count;
import ferromera.mutantdetector.model.Stat;
import ferromera.mutantdetector.model.StatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatsDao {

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    
    public Long getMutantCount() {
        Count count = jdbcTemplate.queryForObject("SELECT last_value,is_called FROM mutants",new CountMapper());
        return count.isCalled() ? count.getLastValue() : 0L ;
    }
    
    
    public Long getNotMutantCount() {
        Count count = jdbcTemplate.queryForObject("SELECT last_value,is_called FROM notMutants",new CountMapper());
        return count.isCalled() ? count.getLastValue()  : 0L ;
    }
}
