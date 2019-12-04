package co.cambridgetechnology.auction.core.memory.service.impl;

import co.cambridgetechnology.auction.core.memory.entity.AuctionItem;
import co.cambridgetechnology.auction.core.memory.exception.InvalidBusinessRuleException;
import co.cambridgetechnology.auction.core.memory.model.request.impl.AcceptRequest;
import co.cambridgetechnology.auction.core.memory.model.request.impl.BidRequest;
import co.cambridgetechnology.auction.core.memory.model.request.impl.OfferRequest;
import co.cambridgetechnology.auction.core.memory.repository.AuctionRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.never;

public class AuctionServiceImplTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Mock
    private AuctionRepository auctionRepository;

    @InjectMocks
    private AuctionServiceImpl auctionService;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOffer() {
        Mockito.when(auctionRepository.existsByItemKey(nullable(String.class))).thenReturn(false);
        auctionService.offer(new OfferRequest());
        Mockito.verify(auctionRepository).save(nullable(AuctionItem.class));
    }

    @Test
    public void testOffer_itemExist() {
        thrown.expect(InvalidBusinessRuleException.class);
        thrown.expectMessage("Auction of the item already existed");
        Mockito.when(auctionRepository.existsByItemKey(nullable(String.class))).thenReturn(true);
        auctionService.offer(new OfferRequest());
        Mockito.verify(auctionRepository, never()).save(nullable(AuctionItem.class));
    }

    @Test
    public void testBid() {
        Mockito.when(auctionRepository.existsByItemKey(nullable(String.class))).thenReturn(true);
        Mockito.when(auctionRepository.findByItemKey(nullable(String.class))).thenReturn(Optional.of(new AuctionItem("vase123", "andrew")));
        auctionService.bid(new BidRequest("vase123", "tom", BigDecimal.TEN));
        Mockito.verify(auctionRepository).save(nullable(AuctionItem.class));
    }

    @Test
    public void testBid_itemNotExist() {
        thrown.expect(InvalidBusinessRuleException.class);
        thrown.expectMessage("Auction of the item does not exist-- your bid is not allowed");
        Mockito.when(auctionRepository.existsByItemKey(nullable(String.class))).thenReturn(false);
        auctionService.bid(new BidRequest("vase123", "tom", BigDecimal.TEN));
        Mockito.verify(auctionRepository, never()).save(nullable(AuctionItem.class));
    }

    @Test
    public void testBid_itemNull() {
        thrown.expect(InvalidBusinessRuleException.class);
        thrown.expectMessage("Auction of the item does not exist-- your bid is not allowed");
        Mockito.when(auctionRepository.existsByItemKey(nullable(String.class))).thenReturn(true);
        Mockito.when(auctionRepository.findByItemKey(nullable(String.class))).thenReturn(Optional.empty());
        auctionService.bid(new BidRequest("vase123", "tom", BigDecimal.TEN));
        Mockito.verify(auctionRepository, never()).save(nullable(AuctionItem.class));
    }

    @Test
    public void testBid_itemClosed() {
        thrown.expect(InvalidBusinessRuleException.class);
        thrown.expectMessage("The auction is closed as another bid has been accepted -- your bid is not allowed");
        Mockito.when(auctionRepository.existsByItemKey(nullable(String.class))).thenReturn(true);
        AuctionItem auctionItem = new AuctionItem("vase123", "andrew");
        auctionItem.setAccepted(true);
        Mockito.when(auctionRepository.findByItemKey(nullable(String.class))).thenReturn(Optional.of(auctionItem));
        auctionService.bid(new BidRequest("vase123", "tom", BigDecimal.TEN));
        Mockito.verify(auctionRepository, never()).save(nullable(AuctionItem.class));
    }

    @Test
    public void testBid_lowerBid() {
        thrown.expect(InvalidBusinessRuleException.class);
        thrown.expectMessage("Your bid is lower or equal to the highest bid-- your bid is not allowed");
        Mockito.when(auctionRepository.existsByItemKey(nullable(String.class))).thenReturn(true);
        AuctionItem auctionItem = new AuctionItem("vase123", "andrew");
        auctionItem.setAccepted(false);
        auctionItem.setHighestBid(BigDecimal.valueOf(100));
        Mockito.when(auctionRepository.findByItemKey(nullable(String.class))).thenReturn(Optional.of(auctionItem));
        auctionService.bid(new BidRequest("vase123", "tom", BigDecimal.TEN));
        Mockito.verify(auctionRepository, never()).save(nullable(AuctionItem.class));
    }

    @Test
    public void testAccept() {
        Mockito.when(auctionRepository.existsByItemKey(nullable(String.class))).thenReturn(true);
        AuctionItem auctionItem = new AuctionItem("vase123", "andrew");
        auctionItem.setHighestBid(BigDecimal.TEN);
        Mockito.when(auctionRepository.findByItemKey(nullable(String.class))).thenReturn(Optional.of(auctionItem));
        auctionService.accept(new AcceptRequest());
        Mockito.verify(auctionRepository).save(nullable(AuctionItem.class));
    }

    @Test
    public void testAcceptWithoutBid() {
        thrown.expect(InvalidBusinessRuleException.class);
        thrown.expectMessage("The auction has no bid -- your acceptance is not allowed");
        Mockito.when(auctionRepository.existsByItemKey(nullable(String.class))).thenReturn(true);
        AuctionItem auctionItem = new AuctionItem("vase123", "andrew");
        auctionItem.setHighestBid(BigDecimal.ZERO);
        Mockito.when(auctionRepository.findByItemKey(nullable(String.class))).thenReturn(Optional.of(auctionItem));
        auctionService.accept(new AcceptRequest());
        Mockito.verify(auctionRepository, never()).save(nullable(AuctionItem.class));
    }

    @Test
    public void testAcceptWithoutItem() {
        thrown.expect(InvalidBusinessRuleException.class);
        thrown.expectMessage("Auction of the item does not exist-- your acceptance is not allowed");
        Mockito.when(auctionRepository.existsByItemKey(nullable(String.class))).thenReturn(false);
        AuctionItem auctionItem = new AuctionItem("vase123", "andrew");
        auctionItem.setHighestBid(BigDecimal.ZERO);
        Mockito.when(auctionRepository.findByItemKey(nullable(String.class))).thenReturn(Optional.of(auctionItem));
        auctionService.accept(new AcceptRequest());
        Mockito.verify(auctionRepository, never()).save(nullable(AuctionItem.class));
    }

    @Test
    public void testAcceptWithNullItem() {
        thrown.expect(InvalidBusinessRuleException.class);
        thrown.expectMessage("Auction of the item does not exist-- your acceptance is not allowed");
        Mockito.when(auctionRepository.existsByItemKey(nullable(String.class))).thenReturn(true);
        Mockito.when(auctionRepository.findByItemKey(nullable(String.class))).thenReturn(Optional.empty());
        auctionService.accept(new AcceptRequest());
        Mockito.verify(auctionRepository, never()).save(nullable(AuctionItem.class));
    }

    @Test
    public void testAcceptAlreadyAccepted() {
        thrown.expect(InvalidBusinessRuleException.class);
        thrown.expectMessage("The auction has been accepted -- your acceptance is not allowed");
        Mockito.when(auctionRepository.existsByItemKey(nullable(String.class))).thenReturn(true);
        AuctionItem auctionItem = new AuctionItem("vase123", "andrew");
        auctionItem.setHighestBid(BigDecimal.TEN);
        auctionItem.setAccepted(true);
        Mockito.when(auctionRepository.findByItemKey(nullable(String.class))).thenReturn(Optional.of(auctionItem));
        auctionService.accept(new AcceptRequest());
        Mockito.verify(auctionRepository, never()).save(nullable(AuctionItem.class));
    }


}
