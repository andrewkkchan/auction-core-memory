package co.cambridgetechnology.auction.core.memory.consumer.impl;

import co.cambridgetechnology.auction.core.memory.consumer.Producer;
import co.cambridgetechnology.auction.core.memory.entity.AuctionItem;
import co.cambridgetechnology.auction.core.memory.entity.TransactionEvent;
import co.cambridgetechnology.auction.core.memory.entity.Type;
import com.fasterxml.jackson.databind.ObjectMapper;
import co.cambridgetechnology.auction.core.memory.exception.InvalidBusinessRuleException;
import co.cambridgetechnology.auction.core.memory.model.request.impl.OfferRequest;
import co.cambridgetechnology.auction.core.memory.service.AuctionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.nullable;

public class OfferProcessorTest {
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private AuctionService auctionService;
    @Mock
    private Producer producer;
    @InjectMocks
    private OfferProcessor offerProcessor;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess() throws IOException {
        Mockito.when(objectMapper.readValue(nullable(String.class), ArgumentMatchers.eq(OfferRequest.class))).thenReturn(new OfferRequest("vase123","andrew"));
        Mockito.when(auctionService.offer(nullable(OfferRequest.class))).thenReturn(new AuctionItem("vase123","andrew"));
        TransactionEvent transactionEvent = new TransactionEvent();
        transactionEvent.setId("abcd1234");
        transactionEvent.setRequest("{}");
        transactionEvent.setType(Type.OFFER.toString());
        offerProcessor.process(transactionEvent);
        Mockito.verify(producer).produceSuccess(nullable(String.class), nullable(Object.class), nullable(Integer.class), nullable(long.class));
        Mockito.verify(producer, Mockito.never()).produceError(nullable(String.class), nullable(InvalidBusinessRuleException.class), nullable(Integer.class), nullable(long.class));
    }

    @Test
    public void testProcess_cannotRead() throws IOException {
        Mockito.when(objectMapper.readValue(nullable(String.class), ArgumentMatchers.eq(OfferRequest.class))).thenThrow(new IOException());
        Mockito.when(auctionService.offer(nullable(OfferRequest.class))).thenReturn(new AuctionItem("vase123","andrew"));
        TransactionEvent transactionEvent = new TransactionEvent();
        transactionEvent.setId("abcd1234");
        transactionEvent.setRequest("{}");
        transactionEvent.setType(Type.OFFER.toString());
        offerProcessor.process(transactionEvent);
        Mockito.verify(producer, Mockito.never()).produceSuccess(nullable(String.class), nullable(Object.class), nullable(Integer.class), nullable(long.class));
        Mockito.verify(producer).produceError(nullable(String.class), nullable(InvalidBusinessRuleException.class), nullable(Integer.class), nullable(long.class));
    }

    @Test
    public void testProcess_serviceFail() throws IOException {
        Mockito.when(objectMapper.readValue(nullable(String.class), ArgumentMatchers.eq(OfferRequest.class))).thenReturn(new OfferRequest("vase123","andrew"));
        Mockito.when(auctionService.offer(nullable(OfferRequest.class))).thenThrow(new InvalidBusinessRuleException("service fail"));
        TransactionEvent transactionEvent = new TransactionEvent();
        transactionEvent.setId("abcd1234");
        transactionEvent.setRequest("{}");
        transactionEvent.setType(Type.OFFER.toString());
        offerProcessor.process(transactionEvent);
        Mockito.verify(producer, Mockito.never()).produceSuccess(nullable(String.class), nullable(Object.class), nullable(Integer.class), nullable(long.class));
        Mockito.verify(producer).produceError(nullable(String.class), nullable(InvalidBusinessRuleException.class), nullable(Integer.class), nullable(long.class));
    }
    @Test
    public void testGetType() {
        Assert.assertEquals(Type.OFFER.toString(), offerProcessor.getType());
    }
}
