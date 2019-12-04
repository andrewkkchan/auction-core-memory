package co.cambridgetechnology.auction.core.memory.consumer.impl;

import co.cambridgetechnology.auction.core.memory.entity.TransactionEvent;
import co.cambridgetechnology.auction.core.memory.entity.Type;
import co.cambridgetechnology.auction.core.memory.consumer.Processor;
import co.cambridgetechnology.auction.core.memory.consumer.Producer;
import org.springframework.stereotype.Component;

@Component
public class BackUpProcessor implements Processor {
    private final Producer producer;


    public BackUpProcessor(Producer producer) {
        this.producer = producer;
    }

    @Override
    public void process(TransactionEvent transactionEvent) {
        producer.produceSuccess(transactionEvent.getId(), null,
                transactionEvent.getKafkaPartition(), transactionEvent.getKafkaOffset());

    }

    @Override
    public String getType() {
        return Type.BACK_UP.toString();
    }
}
