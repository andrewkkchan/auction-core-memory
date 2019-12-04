package co.cambridgetechnology.auction.core.memory.consumer.impl;

import co.cambridgetechnology.auction.core.memory.consumer.Producer;
import co.cambridgetechnology.auction.core.memory.entity.AuctionItem;
import co.cambridgetechnology.auction.core.memory.entity.TransactionEvent;
import co.cambridgetechnology.auction.core.memory.entity.Type;
import co.cambridgetechnology.auction.core.memory.exception.InvalidBusinessRuleException;
import co.cambridgetechnology.auction.core.memory.model.request.impl.BidRequest;
import co.cambridgetechnology.auction.core.memory.model.request.impl.OfferRequest;
import co.cambridgetechnology.auction.core.memory.service.AuctionService;
import co.cambridgetechnology.auction.core.memory.service.BidEntryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.io.IOException;
import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.nullable;

public class BidProcessorTest {
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private AuctionService auctionService;
    @Mock
    private BidEntryService bidEntryService;
    @Mock
    private Producer producer;
    @InjectMocks
    private BidProcessor bidProcessor;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess()throws IOException {
        Mockito.when(objectMapper.readValue(nullable(String.class), ArgumentMatchers.eq(BidRequest.class))).thenReturn(new BidRequest("vase123","johnny", BigDecimal.TEN));
        Mockito.when(auctionService.bid(nullable(BidRequest.class))).thenReturn(new AuctionItem("vase123","andrew"));
        TransactionEvent transactionEvent = new TransactionEvent();
        transactionEvent.setId("abcd1234");
        transactionEvent.setRequest("{}");
        transactionEvent.setType(Type.BID.toString());
        bidProcessor.process(transactionEvent);
        Mockito.verify(producer).produceSuccess(nullable(String.class), nullable(Object.class), nullable(Integer.class), nullable(long.class));
        Mockito.verify(producer, Mockito.never()).produceError(nullable(String.class), nullable(InvalidBusinessRuleException.class), nullable(Integer.class), nullable(long.class));
    }

    @Test
    public void testProcess_cannotRead() throws IOException {
        Mockito.when(objectMapper.readValue(nullable(String.class), ArgumentMatchers.eq(BidRequest.class)))
                .thenThrow(new IOException());
        Mockito.when(auctionService.bid(nullable(BidRequest.class))).thenReturn(new AuctionItem("vase123","andrew"));
        TransactionEvent transactionEvent = new TransactionEvent();
        transactionEvent.setId("abcd1234");
        transactionEvent.setRequest("{}");
        transactionEvent.setType(Type.BID.toString());
        bidProcessor.process(transactionEvent);
        Mockito.verify(producer, Mockito.never()).produceSuccess(nullable(String.class), nullable(Object.class), nullable(Integer.class), nullable(long.class));
        Mockito.verify(producer).produceError(nullable(String.class), nullable(InvalidBusinessRuleException.class), nullable(Integer.class), nullable(long.class));
    }

    @Test
    public void testProcess_serviceFail() throws IOException {
        Mockito.when(objectMapper.readValue(nullable(String.class), ArgumentMatchers.eq(BidRequest.class)))
                .thenReturn(new BidRequest("vase123","johnny", BigDecimal.TEN));
        Mockito.when(auctionService.bid(nullable(BidRequest.class))).thenThrow(new InvalidBusinessRuleException("service fail"));
        TransactionEvent transactionEvent = new TransactionEvent();
        transactionEvent.setId("abcd1234");
        transactionEvent.setRequest("{}");
        transactionEvent.setType(Type.BID.toString());
        bidProcessor.process(transactionEvent);
        Mockito.verify(producer, Mockito.never()).produceSuccess(nullable(String.class), nullable(Object.class), nullable(Integer.class), nullable(long.class));
        Mockito.verify(producer).produceError(nullable(String.class), nullable(InvalidBusinessRuleException.class), nullable(Integer.class), nullable(long.class));
    }
    @Test
    public void testGetType() {
        Assert.assertEquals(Type.BID.toString(), bidProcessor.getType());
    }
}
