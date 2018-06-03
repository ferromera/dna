package ferromera.mutantdetector.configuration;

import ferromera.mutantdetector.async.DummyProducer;
import ferromera.mutantdetector.async.KafkaProducer;
import ferromera.mutantdetector.dao.DnaDao;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    
    @Value("${kafka.bootstrap-servers:}")
    private String bootstrapServers;
    
    @Value("${kafka.max.block.ms:30000}")
    private Long kafkaMaxBlockMs;
    
    @Value("${kafka.request.timeout.ms:30000}")
    private Integer kafkaRequestTimeoutMs;
    
    @Bean
    @ConditionalOnProperty(value="persist.asyncronally",havingValue = "true")
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        // list of host:port pairs used for establishing the initial connections to the Kakfa cluster
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, kafkaMaxBlockMs);
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, kafkaRequestTimeoutMs);
        
        return props;
    }
    
    @Bean
    @ConditionalOnProperty(value="persist.asyncronally",havingValue = "true")
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }
    
    @Bean
    @ConditionalOnProperty(value="persist.asyncronally",havingValue = "true")
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
    
    
    @Bean
    @ConditionalOnProperty(value="persist.asyncronally",havingValue = "true")
    public KafkaProducer kafkaProducer() {
        return new KafkaProducer();
    }
    
    @Bean
    @ConditionalOnProperty(value = "persist.asyncronally",havingValue = "false")
    public DummyProducer dummyProducer() {
        return new DummyProducer();
    }
    
    
    @Bean
    @ConditionalOnProperty(value = "persist.asyncronally",havingValue = "true")
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "json");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
//        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.ferromera.mutantdetector.model");
    
    
        return props;
    }
    
    @Bean
    @ConditionalOnProperty(value = "persist.asyncronally" ,havingValue = "true")
    public ConsumerFactory<String, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(), new JsonDeserializer<>(Object.class));
    }
    
    @Bean
    @ConditionalOnProperty(value = "persist.asyncronally",havingValue = "true")
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }
   
}