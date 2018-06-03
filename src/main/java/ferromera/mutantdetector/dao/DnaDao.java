package ferromera.mutantdetector.dao;

import ferromera.mutantdetector.async.KafkaProducer;
import ferromera.mutantdetector.model.DnaMessage;
import ferromera.mutantdetector.util.Gzip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Arrays;

@Component
public class DnaDao {
    
    @Value("${compress.threshold:7}")
    private int compressThreshold;
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Autowired
    KafkaProducer kafkaProducer;
    
    @Value("${persist.asyncronally:true}")
    private boolean persitAsyncronally;
    @Value("${Nkafka.topic:dna}")
    private String kafkaTopic;
    
    public void saveDna(String[] dna,boolean isMutant){
        
        if(persitAsyncronally)
            sendMessage(dna,isMutant);
        else
            persist(dna,isMutant);
        
    }
    
    private void sendMessage(String[] dna, boolean isMutant) {
        kafkaProducer.send(kafkaTopic,new DnaMessage(dna,isMutant));
    }
    
    public void persist(String[] dna, boolean isMutant) {
        Boolean compressed = false;
        String dnaString = Arrays.toString(dna);
        byte[] dnaData;
        if(dna.length >= compressThreshold) {
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
    
    
    public void setKafkaProducer(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }
}
