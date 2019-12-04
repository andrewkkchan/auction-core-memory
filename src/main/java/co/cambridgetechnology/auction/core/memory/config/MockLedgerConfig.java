package co.cambridgetechnology.auction.core.memory.config;

import co.cambridgetechnology.auction.core.memory.consumer.Processor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.MockConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Profile("local")
@Configuration
public class MockLedgerConfig {
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
    public Consumer<String, String> consumer() {
        return new MockConsumer<>(null);
    }
}
