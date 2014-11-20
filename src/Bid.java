package com.emma.auction;

import java.lang.Comparable; //For comparing in tests
import java.util.Comparator; //For comparing in PriorityQueue
import java.util.Date;

public class Bid implements Comparator<Bid>, Comparable<Bid> {
    
    private Person bidder;
    private int amount;
    private Date timeSubmitted;
    private boolean valid;
    
    public Bid (Person inBidder, int inAmount) {
        
        this.bidder = inBidder;
        this.amount = inAmount >= 0 ? inAmount : 0; //We only want positive dollar values.
        this.timeSubmitted = null;
        this.valid = true;
        
    }
    
    //Fetch person who placed bid
    public Person bidder () {
        
        return this.bidder;
        
    }
    
    //Fetch bid amount
    public int amount () {
        
        return this.amount;
        
    }
    
    //Fetch time this Bid was submitted to an Auction
    public Date timeSubmitted () {
        
        if (this.timeSubmitted == null) return new Date(0);
        
        return this.timeSubmitted;
        
    }
    
    //Set the time this Bid was submitted to an Auction
    public void timeSubmitted (Date inTimeSubmitted) {
        
        this.timeSubmitted = inTimeSubmitted;
        
    }
    
    //Fetch if this Bid is valid
    public boolean validity () {
        
        return this.valid;
        
    }
    
    //Validate a Bid, note that Bids are valid by default, 
    //so this shouldn't be needed in most cases.
    public void validate () {
        
        this.valid = true;
        
    }
    
    //Invalidate a Bid. This is used when a person submits a Bid
    //that is less than or equal to the current Bid.
    //We still want to gather the information that this person submitted a Bid
    //for statistical or usage analysis later down the line.
    public void invalidate () {
        
        this.valid = false;
        
    }
    
    //For pretty printing
    @Override
    public String toString () {
        
        return "<Bid: " + this.bidder.toString() + ", Amount: " + amount + ">";
        
    }
    
    //Comparator interface, for comparing bids.
    @Override
    public int compare (Bid firstBid, Bid secondBid) {
        
        return secondBid.amount() - firstBid.amount(); //We really only care which bid is larger when comparing.
        
    }
    
    @Override
    public boolean equals (Object inObject) {
        
        if (inObject == this) return true; //if inObject is referencing the same thing as this, equality is trivial. inObject is exactly the same as this.
        if (!(inObject instanceof Bid)) return false; //If inObject isn't a Bid, it can't possibly be equal to this.
        
        Bid inBid = (Bid) inObject; //This is a safe cast as we just checked if inObject is a Bid. It is, as we've gotten this far.
        
        //Now we just want to compare these two bids in exactly how we did in compare. If we get 0, they're equal.
        if (this.compare(this, inBid) == 0) return true;
        else return false;
        
    }
    
    //Note that since we're not using a hash-based data structure (we're using a PriorityQueue)
    //we only have to override equals(), and not hashCode(), even though
    //it's common to see these two functions overwritten at the same time.
    
    //Comparable interface, for comparing during testing.
    @Override
    public int compareTo (Bid inBid) {
        
        return this.compare(this, inBid);
        
    }
    
}