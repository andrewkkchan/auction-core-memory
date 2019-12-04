package co.cambridgetechnology.auction.core.memory.controller;

import co.cambridgetechnology.auction.core.memory.entity.AuctionItem;
import co.cambridgetechnology.auction.core.memory.repository.AuctionRepository;
import com.google.common.collect.Iterables;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.nullable;

public class AuctionItemControllerTest {
    @Mock
    private AuctionRepository auctionRepository;
    @InjectMocks
    private AuctionItemController auctionItemController;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAll(){
        Mockito.when(auctionRepository.findAll()).thenReturn(new ArrayList<>());
        Iterable<AuctionItem> all = auctionItemController.getAll();
        Assert.assertNotNull(all);
        Assert.assertEquals(0, Iterables.size(all));
    }

    @Test
    public void testGet(){
        Mockito.when(auctionRepository.findByItemKey(nullable(String.class))).thenReturn(Optional.of(new AuctionItem("vase123","andrew")));
        AuctionItem auctionItem = auctionItemController.get("vase123");
        Assert.assertNotNull(auctionItem);
    }
}
