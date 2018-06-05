package ferromera.mutantdetector.dao.impl;

import ferromera.mutantdetector.async.KafkaProducer;
import ferromera.mutantdetector.dao.DnaDao;
import ferromera.mutantdetector.model.DnaMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;


@Component
@ConditionalOnProperty(value = "persist.asyncronally",havingValue = "true")
@Primary
public class AsyncronicDnaDao implements DnaDao{
  
    @Autowired
    KafkaProducer kafkaProducer;
    
    
    @Value("${kafka.topic}")
    private String kafkaTopic;
    
    public void saveDna(String[] dna,boolean isMutant){
        kafkaProducer.send(kafkaTopic,new DnaMessage(dna,isMutant));
    }
    
}
