package co.cambridgetechnology.auction.core.memory.consumer;

import co.cambridgetechnology.auction.core.memory.entity.TransactionEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.IOException;

/**
 * Consumer which runs on single thread, and consume the request event strictly serially
 * No locking of database tables is therefore needed.
 */
public interface Consumer {

    /**
     * consume exactly one {@link TransactionEvent}
     * protect for idempotency, so that {@link TransactionEvent} with same UUID represents same request, and will not be processed
     * @param consumerRecord which can be parsed into exactly one {@link TransactionEvent} with offset and partition meta data
     * @throws IOException on wrong parsing
     */
    void consume(ConsumerRecord<String, String> consumerRecord) throws IOException;

}
