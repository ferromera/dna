package ferromera.mutantdetector.dao.impl;


import ferromera.mutantdetector.dao.StatsDao;
import ferromera.mutantdetector.dao.mapper.CountMapper;
import ferromera.mutantdetector.model.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StatsDaoImpl implements StatsDao{

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
