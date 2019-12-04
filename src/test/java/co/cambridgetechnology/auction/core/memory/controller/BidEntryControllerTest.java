package co.cambridgetechnology.auction.core.memory.controller;

import co.cambridgetechnology.auction.core.memory.entity.AuctionItem;
import co.cambridgetechnology.auction.core.memory.entity.BidEntry;
import co.cambridgetechnology.auction.core.memory.repository.BidEntryRepository;
import com.google.common.collect.Iterables;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.nullable;

public class BidEntryControllerTest {
    @Mock
    private BidEntryRepository bidEntryRepository;
    @InjectMocks
    private BidEntryController bidEntryController;
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAll(){
        Mockito.when(bidEntryRepository.findAll()).thenReturn(new ArrayList<>());
        Iterable<BidEntry> all = bidEntryController.getAll();
        Assert.assertNotNull(all);
        Assert.assertEquals(0, Iterables.size(all));
    }

    @Test
    public void testGetAllByItemKey(){
        Mockito.when(bidEntryRepository.findAllByItemKey(nullable(String.class))).thenReturn(new ArrayList<>());
        Iterable<BidEntry> all = bidEntryController.getAllByItemKey("vase123");
        Assert.assertNotNull(all);
        Assert.assertEquals(0, Iterables.size(all));
    }

    @Test
    public void testGetAllByBidder(){
        Mockito.when(bidEntryRepository.findAllByBidder(nullable(String.class))).thenReturn(new ArrayList<>());
        Iterable<BidEntry> all = bidEntryController.getAllByBidder("thomas");
        Assert.assertNotNull(all);
        Assert.assertEquals(0, Iterables.size(all));
    }

}
