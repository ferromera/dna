package ferromera.mutantdetector.dao;

import ferromera.mutantdetector.dto.DNAChainDTO;
import ferromera.mutantdetector.util.Gzip;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Arrays;

@Repository
public class DnaDao {

    @Value("${compress.threshold:7}")
    private int compressThreshold;

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    public void saveDna(String[] dna,boolean isMutant){
        Boolean compressed = false;
        StringBuffer dnaConcat = new StringBuffer();
        Arrays.asList(dna).stream().forEachOrdered(str -> dnaConcat.append(str));
        byte[] dnaData;
        if(dna.length >= compressThreshold) {
            try {
                dnaData = Gzip.compress(dnaConcat.toString());
                compressed = true;
            } catch (IOException e) {
                dnaData = dnaConcat.toString().getBytes();
            }
        }else
            dnaData = dnaConcat.toString().getBytes();
        
        jdbcTemplate.update("INSERT INTO dna (compressed,dnaData,isMutant) VALUES ( ? , ? , ?)",compressed,dnaData,isMutant);
        if(isMutant)
            jdbcTemplate.queryForObject("SELECT nextval('mutants')",Integer.class);
        else
            jdbcTemplate.queryForObject("SELECT nextval('notMutants')",Integer.class);
   
    }
    
}
