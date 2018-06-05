package ferromera.mutantdetector.dao.impl;


import ferromera.mutantdetector.dao.DnaDao;
import ferromera.mutantdetector.service.impl.MutantDetectorServiceImpl;
import ferromera.mutantdetector.util.Gzip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Component
public class SyncronicDnaDao implements DnaDao{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SyncronicDnaDao.class);
    
    @Value("${compress.threshold:7}")
    private int compressThreshold;
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    
    @Override
    public void saveDna(String[] dna, boolean isMutant) {
        LOGGER.info("Persisting dna: {}", Arrays.toString(dna) );
    
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
    
        LOGGER.info("Dna: {} persisted successfully", Arrays.toString(dna) );
    
    
    }
}
