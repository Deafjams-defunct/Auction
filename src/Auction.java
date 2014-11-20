package com.emma.auction;

import java.util.PriorityQueue;
import java.util.Date;

public class Auction {
    
    private Item auctionedItem;
    private PriorityQueue<Bid> bids;
    
    public Auction (Item inAuctionedItem) {
        
        this.auctionedItem = inAuctionedItem;
        
        this.bids = new PriorityQueue<Bid>();
        
    }
    
    public boolean placeBid (Bid inBid) {
        
        inBid.timeSubmitted(new Date());
        
        if (this.bids.size() == 0 || inBid.amount() > this.topBid().amount()) {
            
            this.bids.add(inBid);
            return true;
            
        }
        
        inBid.invalidate(); //Since this bid doesn't qualify for the top bid, and was submitted after the current top bid, it's invalid. We're storing it for statistical and usage analysis reasons.
        
        this.bids.add(inBid); 
        return false;
        
    }
    
    public Item item () {
        
        return this.auctionedItem;
        
    }
    
    public Bid topBid () {
        
        return this.bids.peek();
        
    }
    
    public int totalNumberOfBids () {
        
        return this.bids.size();
        
    }
    
}