package co.cambridgetechnology.auction.core.memory.controller;

import co.cambridgetechnology.auction.core.memory.entity.BidEntry;
import co.cambridgetechnology.auction.core.memory.repository.BidEntryRepository;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller which provides only GET endpoints for {@link co.cambridgetechnology.auction.core.memory.entity.BidEntry}.
 */
@RestController
@RequestMapping("/bid")
public class BidEntryController {
    private final BidEntryRepository bidEntryRepository;

    public BidEntryController(BidEntryRepository bidEntryRepository) {
        this.bidEntryRepository = bidEntryRepository;
    }

    /**
     * Get all {@link co.cambridgetechnology.auction.core.memory.entity.BidEntry} from ledger.
     *
     * @return all {@link co.cambridgetechnology.auction.core.memory.entity.BidEntry}
     */
    @GetMapping(value = "/",
            produces = {"application/json"},
            consumes = {"application/json"})
    @ResponseBody
    public Iterable<BidEntry> getAll() {
        return bidEntryRepository.findAll();
    }

    /**
     * Get all {@link co.cambridgetechnology.auction.core.memory.entity.BidEntry} from ledger
     * @param itemKey with the filter by item key of the item being auctioned
     * @return all {@link co.cambridgetechnology.auction.core.memory.entity.BidEntry}
     */
    @GetMapping(value = "/item/{itemKey}",
            produces = {"application/json"},
            consumes = {"application/json"})
    @ResponseBody
    public Iterable<BidEntry> getAllByItemKey(@PathVariable String itemKey) {
        return bidEntryRepository.findAllByItemKey(itemKey);
    }

    /**
     * Get all {@link co.cambridgetechnology.auction.core.memory.entity.BidEntry} from ledger
     * @param bidder with the filter by item key of the item being auctioned
     * @return all {@link co.cambridgetechnology.auction.core.memory.entity.BidEntry}
     */
    @GetMapping(value = "/bidder/{bidder}",
            produces = {"application/json"},
            consumes = {"application/json"})
    @ResponseBody
    public Iterable<BidEntry> getAllByBidder(@PathVariable String bidder) {
        return bidEntryRepository.findAllByBidder(bidder);
    }
}
