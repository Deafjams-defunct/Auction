/*
Emma Foster - November 2014
Auction
A representation of an Auction using Emma's Fun Time Auction Rules (see the readme)
For Shawn at StreamingEdge.
*/
package com.emma.auction;

import java.util.Set; //To store our participants, we only need one copy of each one, a set does just that.
import java.util.HashSet; //The actual implementation of Set that we used, not very important.
import java.util.PriorityQueue; //How we store the top bids. Fancy!
import java.util.Arrays; //Needed to convert our PriorityQueue into an accurately sorted array.
import java.util.Date; //So we can store what time each bid is placed.

public class Auction {
    
    private Item auctionedItem; //The actual Item we're auctioning off.
    private PriorityQueue<Bid> bids; //All of the Bids that have been placed during this auction.
    private Set<Person> participants; //A single reference to each Person that has placed a bid during this auction.
    
    //We need an Item to Auction off, so just require that our Auction starts with one.
    public Auction (Item inAuctionedItem) {
        
        this.auctionedItem = inAuctionedItem;
        
        this.bids = new PriorityQueue<Bid>();
        
        this.participants = new HashSet<Person>();
        
        this.placeBid(new Bid(new Person("Owner"), 0)); //We want to place an initial 'seed' bid, so the owner will be declared the winner if no one bids on this item.
        
    }
    
    //Method to process a Bid submitted by a Person and determine if it's the current top bid.
    public boolean placeBid (Bid inBid) {
        
        this.participants.add(inBid.bidder()); //Toss the person who submitted this Bid into our participants. This won't give duplicates since we're using a set.
        
        inBid.timeSubmitted(new Date()); //Set the time this Bid was submitted. By setting the time in the Auction, we gain some integrity to our data, knowing that the Bid class didn't back date the Bid in some way. (Of course, this is a fictional plus for us, as we wrote the Bid class too and wouldn't attempt something actively harmful to our project).
        
        if (this.bids.size() == 0 || inBid.amount() > this.topBid().amount()) { //If our bid is higher than the current top bid, or we have no bids entered, we have a valid bid.
            
            this.bids.add(inBid); //add our bid to the queue of Bids, it'll be on top.
            return true; //this is a valid bid, so let the caller know.
            
        }
        
        inBid.invalidate(); //Since this bid doesn't qualify for the top bid, and was submitted after the current top bid, it's invalid. We're storing it for statistical and usage analysis reasons.
        
        this.bids.add(inBid); //Add our bid to the queue of bids, it won't be on top.
        return false; //this isn't a valid bid, so let the caller know.
        
    }
    
    //Getters for our basic properties
    public Item item () { return this.auctionedItem; }
    public Bid topBid () { return this.bids.peek(); }
    public int totalNumberOfBids () { return this.bids.size(); }
    
    //Note that this gives an Array of participants, but each element is guaranteed to be unique as we're generating this array out of a Set.
    public Person[] participants () {
        
        return this.participants.toArray(new Person[this.participants.size()]); //This generates a Person[] rather than an Object[] from our Set<Person>.
        
    }
    
    //We're trying to give a faithful representation of the bids here, with the highest bids first.
    //So, we'll sort the bids so that our user doesn't have to deal with out of order bids.
    public Bid[] bids () {
        
        Bid[] bidsArray = this.bids.toArray(new Bid[this.bids.size()]); //Again making a Bid[] rather than an Object[] from our Queue.
        
        Arrays.sort(bidsArray); //toArray doesn't give us a guarantee of order in a Priority Queue, so we'll just sort it to give our user a presentation of all the bids that is faithful to our data representation.
        
        return bidsArray;
        
    }
    
    
    
}