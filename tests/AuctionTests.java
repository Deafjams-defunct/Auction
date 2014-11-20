package com.emma.auction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.Date;

public class AuctionTests {
    
    @Test
    public void generateBid () {
        
        Bid testBid = new Bid(new Person("Felix"), 5);
        
        assertEquals("Bid was placed by Felix", "Felix", testBid.bidder().name());
        assertEquals("Bid was placed at $5.00", 5, testBid.amount());
        
        Date testTimeSubmitted = new Date(); //the time right now
        
        testBid.timeSubmitted(testTimeSubmitted); 
        
        assertEquals("Bid was placed at " + testTimeSubmitted.getTime(), testTimeSubmitted.getTime(), testBid.timeSubmitted().getTime());
        
    }
    
    @Test
    public void generatePerson () {
        
        Person testPerson = new Person("Sarah");
        
        assertEquals("Person's name is Sarah", "Sarah", testPerson.name());
        
    }
    
    @Test
    public void generateItem () {
        
        Item testItem = new Item ("Leather Jacket");
        
        assertEquals("Item is a Leather Jacket", "Leather Jacket", testItem.name());
        
    }
    
    @Test
    public void generateAuction () {
        
        Auction testAuction = new Auction(new Item("Leather Jacket"));
        
        assertEquals("Item being auctioned is a Leather Jacket", "Leather Jacket", testAuction.item().name());
        
    }
    
    @Test
    public void bidFixesNegativeInputValues () {
        
        Bid testBid = new Bid(new Person("Amelia"), -100);
        
        assertEquals("Negative bids should be set to 0.", 0, testBid.amount());
        
    }
    
    @Test
    public void topBidIsActuallyTopBid () {
        
        Auction testAuction = new Auction(new Item("Leather Jacket"));
        
        Bid firstBid = new Bid(new Person("Felix"), 5);
        Bid secondBid = new Bid(new Person("Sarah"), 6);
        Bid thirdBid = new Bid(new Person("Helena"), 3);
        Bid fourthBid = new Bid(new Person("Cosima"), 6);
        
        testAuction.placeBid(firstBid);
        testAuction.placeBid(secondBid);
        testAuction.placeBid(thirdBid);
        testAuction.placeBid(fourthBid);
        
        assertEquals("Top bid is by Sarah", secondBid, testAuction.topBid());
        
    }
    
    @Test
    public void auctionSetsBidSubmissionTime () {
        
        Auction testAuction = new Auction(new Item("Leather Jacket"));
        
        Bid firstBid = new Bid(new Person("Alison"), 5);
        Bid secondBid = new Bid(new Person("Beth"), 6);
        Bid thirdBid = new Bid(new Person("Rachel"), 3);
        Bid fourthBid = new Bid(new Person("Tony"), 6);
        
        testAuction.placeBid(firstBid);
        testAuction.placeBid(secondBid);
        testAuction.placeBid(thirdBid);
        testAuction.placeBid(fourthBid);
        
        //Here we're checking to make sure each bid has a time that's greater than 0. 0 is our 'Not given a time' when placed value.
        //Since each of these bids have been placed, they need to have submission times.
        assertFalse("First bid should have been given a submission time when it was placed.", firstBid.timeSubmitted().getTime() <= 0);
        assertFalse("Second bid should have been given a submission time when it was placed.", secondBid.timeSubmitted().getTime() <= 0);
        assertFalse("Third Bid should have been given a submission time when it was placed.", thirdBid.timeSubmitted().getTime() <= 0);
        assertFalse("Fourth Bid should have been given a submission time when it was placed.", fourthBid.timeSubmitted().getTime() <= 0);
        
    }
    
    @Test
    public void aunctionRecordsAllBids () {
        
        Auction testAuction = new Auction(new Item("Leather Jacket"));
        
        Bid firstBid = new Bid(new Person("Alison"), 5);
        Bid secondBid = new Bid(new Person("Beth"), 6);
        Bid thirdBid = new Bid(new Person("Rachel"), 3);
        Bid fourthBid = new Bid(new Person("Tony"), 6);
        
        testAuction.placeBid(firstBid);
        testAuction.placeBid(secondBid);
        testAuction.placeBid(thirdBid);
        testAuction.placeBid(fourthBid);
        
        //Here we're checking to make sure each bid has a time that's greater than 0. 0 is our 'Not given a time' when placed value.
        //Since each of these bids have been placed, they need to have submission times.
        assertEquals("Auction should have four bids placed.", 4, testAuction.totalNumberOfBids());
        
    }
    
    @Test
    public void togglingValidityOfBid () {
        
        Bid testBid = new Bid(new Person("Alison"), 5);
        
        assertEquals("Bid should be valid by default", true, testBid.validity());
        
        testBid.invalidate();
        
        assertEquals("Bid should be invalid", false, testBid.validity());
        
        testBid.validate();
        
        assertEquals("Bid should be valid", true, testBid.validity());
        
    }
    /*
    @Test
    public void runExampleAuction () {
        
        AuctionRunner testAuctionRunner = new AuctionRunner(new Item("Alison's Minivan"));
        
        testAuctionRunner.runAuction();
        
        
        
    }
    */
    
}