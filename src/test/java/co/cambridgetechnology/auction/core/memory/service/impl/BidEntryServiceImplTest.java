package co.cambridgetechnology.auction.core.memory.service.impl;

import co.cambridgetechnology.auction.core.memory.entity.AuctionItem;
import co.cambridgetechnology.auction.core.memory.entity.BidEntry;
import co.cambridgetechnology.auction.core.memory.model.request.impl.BidRequest;
import co.cambridgetechnology.auction.core.memory.repository.BidEntryRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.nullable;

public class BidEntryServiceImplTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Mock
    private BidEntryRepository bidEntryRepository;

    @InjectMocks
    private BidEntryServiceImpl bidEntryService;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRecord(){
        bidEntryService.record("12345", new BidRequest("vase123", "andrew", BigDecimal.TEN), 123, 123L);
        Mockito.verify(bidEntryRepository).save(nullable(BidEntry.class));

    }
}
