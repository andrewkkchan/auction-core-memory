package co.cambridgetechnology.auction.core.memory.controller;

import co.cambridgetechnology.auction.core.memory.entity.AuctionItem;
import co.cambridgetechnology.auction.core.memory.repository.AuctionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST controller which provides only GET endpoints for {@link co.cambridgetechnology.auction.core.memory.entity.AuctionItem}.
 */
@RestController
@RequestMapping("/auction")
public class AuctionItemController {
    private final AuctionRepository auctionRepository;

    public AuctionItemController(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }


    /**
     * Get all {@link co.cambridgetechnology.auction.core.memory.entity.AuctionItem} from ledger.
     * @return all {@link co.cambridgetechnology.auction.core.memory.entity.AuctionItem}
     */
    @GetMapping(value = "/",
            produces = {"application/json"},
            consumes = {"application/json"})
    @ResponseBody
    public Iterable<AuctionItem> getAll() {
        return auctionRepository.findAll();
    }

    /**
     * Get one {@link AuctionItem} which matches the item key
     * @param itemKey auction item key
     * @return {@link AuctionItem} matching the item key
     */
    @GetMapping(value = "/{itemKey}",
            produces = {"application/json"},
            consumes = {"application/json"})
    @ResponseBody
    public AuctionItem get(@PathVariable String itemKey) {
        Optional<AuctionItem> byId = auctionRepository.findByItemKey(itemKey);
        return byId.orElse(null);
    }
}
