package co.cambridgetechnology.auction.core.memory.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import co.cambridgetechnology.auction.core.memory.consumer.Processor;
import co.cambridgetechnology.auction.core.memory.entity.TransactionResult;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Centralized place to inject beans
 */
@Profile("kafka")
@Configuration
public class LedgerConfig {
    /**
     * @return a single thread executor for {@link Processor} to run on
     */
    @Bean
    public ExecutorService executorService() {
        return Executors.newSingleThreadExecutor();
    }

    /**
     * @return {@link Logger} for standardized logging
     */
    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger("com.industrieit.dragon.clientledger.core.memory");
    }

    /**
     * @return {@link ObjectMapper} for JSON serialization, as Ledger is JSON-based.
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }


    @Bean
    public ProducerFactory<String, TransactionResult> producerFactoryForResult() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }


    @Bean
    public KafkaTemplate<String, TransactionResult> kafkaTemplateForResult() {
        return new KafkaTemplate<>(producerFactoryForResult());
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ConsumerConfig.CLIENT_ID_CONFIG, "consumer_core_memory");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_core_memory");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConcurrency(1);
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public Consumer<String, String> consumer() {
        return consumerFactory().createConsumer();
    }
}
