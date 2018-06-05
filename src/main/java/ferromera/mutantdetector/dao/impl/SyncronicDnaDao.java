package ferromera.mutantdetector.dao.impl;


import ferromera.mutantdetector.dao.DnaDao;
import ferromera.mutantdetector.util.Gzip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SyncronicDnaDao implements DnaDao{
    
    
    @Value("${compress.threshold:7}")
    private int compressThreshold;
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    
    @Override
    public void saveDna(String[] dna, boolean isMutant) {
        Boolean compressed = false;
        StringBuilder bld = new StringBuilder();
        for(String row :dna)
            bld.append(row);
        String dnaString = bld.toString();
        byte[] dnaData;
        if(compressThreshold!= 0 && dna.length >= compressThreshold) {
            try {
                dnaData = Gzip.compress(dnaString);
                compressed = true;
            } catch (IOException e) {
                dnaData = dnaString.getBytes();
            }
        }else
            dnaData = dnaString.getBytes();
    
    
    
        jdbcTemplate.update("INSERT INTO dna (compressed,dnaData,isMutant) VALUES ( ? , ? , ?)",compressed,dnaData,isMutant);
        if(isMutant)
            jdbcTemplate.queryForObject("SELECT nextval('mutants')",Integer.class);
        else
            jdbcTemplate.queryForObject("SELECT nextval('notMutants')",Integer.class);
    
    }
}
