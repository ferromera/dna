package ferromera.mutantdetector.dao;


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
    
    
    public List<Stat> getAllStats(){
        String sql = "SELECT id , count FROM STAT";
        return jdbcTemplate.query(sql, new StatMapper());
    }



}
