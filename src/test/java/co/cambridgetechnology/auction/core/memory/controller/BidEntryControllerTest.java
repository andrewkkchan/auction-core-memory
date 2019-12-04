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

}
