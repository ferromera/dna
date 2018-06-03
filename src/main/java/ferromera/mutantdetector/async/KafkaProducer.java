package ferromera.mutantdetector.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.function.Consumer;

public class KafkaProducer {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    public void send(String topic, Object object) {
        this.send(topic, object,
                result -> LOGGER.info("Message sent"),
                throwable -> LOGGER.error("Error while sending message")
        );
    }
    
    
    public void send(String topic, Object object, Consumer<SendResult<String, Object>> onSuccess, Consumer<Throwable> onError) {
        // the KafkaTemplate provides asynchronous send methods returning a Future
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, object);
        
        // register a callback with the listener to receive the result of the send asynchronously
        future.addCallback(
                new ListenableFutureCallback<SendResult<String, Object>>() {
                    @Override
                    public void onSuccess(SendResult<String, Object> result) {
                        onSuccess.accept(result);
                    }
                    @Override
                    public void onFailure(Throwable ex) {
                        onError.accept(ex);
                    }
                }
        );
    }
    
}