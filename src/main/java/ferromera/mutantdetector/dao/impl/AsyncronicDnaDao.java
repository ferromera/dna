package ferromera.mutantdetector.dao.impl;

import ferromera.mutantdetector.async.KafkaProducer;
import ferromera.mutantdetector.dao.DnaDao;
import ferromera.mutantdetector.model.DnaMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
@ConditionalOnProperty(value = "persist.asyncronally",havingValue = "true")
@Primary
public class AsyncronicDnaDao implements DnaDao{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncronicDnaDao.class);
    
    
    @Autowired
    KafkaProducer kafkaProducer;
    
    
    @Value("${kafka.topic}")
    private String kafkaTopic;
    
    public void saveDna(String[] dna,boolean isMutant){
        LOGGER.info("Sending to kafka dna: {} on topic {}", Arrays.toString(dna),kafkaTopic );
        kafkaProducer.send(kafkaTopic,new DnaMessage(dna,isMutant));
    }
    
}
