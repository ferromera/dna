package ferromera.mutantdetector.async;



import com.fasterxml.jackson.databind.ObjectMapper;
import ferromera.mutantdetector.dao.DnaDao;
import ferromera.mutantdetector.model.DnaMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "persist.asyncronally",havingValue ="true")
public class KafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
    private DnaDao dnaDao;
    
    public KafkaConsumer(@Autowired DnaDao dao) {
        this.dnaDao = dao;
    }
    
    @KafkaListener(topics = "${kafka.topic}")
    public void receivePaymentIntentionForInsert(ConsumerRecord<String, DnaMessage> consumerRecord, Acknowledgment acknowledgment) {
        LOGGER.info("Received consumer record: {}", consumerRecord);
        DnaMessage message = new ObjectMapper().convertValue(consumerRecord.value(), DnaMessage.class) ;
	    dnaDao.persist(message.getDna(),message.isMutant());
        acknowledgment.acknowledge();
    }
    

}
