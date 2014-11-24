/*
Emma Foster - November 2014
Auction Tests
All the tests that seemed important to this project.
For Shawn at StreamingEdge.
*/
package com.emma.auction;

//Just a bunch of junit stuff
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.Date; //and Date for working with Bids.

public class AuctionTests {
    
    //First, really simple tests making each object in the project and testing their basic functionality.
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
        assertTrue("Item qualities should be greater than or equal to 0", testItem.usefulness() >= 0 && testItem.rarity() >= 0 && testItem.quality() >= 0);
        
    }
    
    @Test
    public void generateAuction () {
        
        Auction testAuction = new Auction(new Item("Leather Jacket"));
        
        assertEquals("Item being auctioned is a Leather Jacket", "Leather Jacket", testAuction.item().name());
        
    }
    
    @Test
    public void generateAuctionRunner () {
        
        AuctionRunner testAuctionRunner = new AuctionRunner(new Item("Leather Jacket"), 20);
        
        testAuctionRunner.run();
        
        assertTrue("When an Auction is run, the winner should not be null.", testAuctionRunner.winningBid() != null);
        
    }
    
    @Test
    //Ensuring bids cannot be given negative amounts.
    public void bidFixesNegativeInputValues () {
        
        Bid testBid = new Bid(new Person("Amelia"), -100);
        
        assertEquals("Negative bids should be set to 0.", 0, testBid.amount());
        
    }
    
    @Test
    //Ensuring auctions are correctly calculating top bids.
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
    //Ensuring auctions set the time each Bid was submitted at.
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
    //The greatest bids will be submitted earlier and have the largest amount. Bids submitted later and for less amounts are "lesser".
    public void bidsCompareProperly () {
        
        Auction testAuction = new Auction(new Item("Leather Jacket"));
        
        Bid firstBid = new Bid(new Person("Alison"), 5);
        Bid secondBid = new Bid(new Person("Beth"), 6);
        Bid thirdBid = new Bid(new Person("Rachel"), 3);
        Bid fourthBid = new Bid(new Person("Tony"), 6);
        
        testAuction.placeBid(firstBid);
        testAuction.placeBid(secondBid);
        testAuction.placeBid(thirdBid);
        testAuction.placeBid(fourthBid);
        
        assertTrue("First bid has less amount value than second bid, so first bid should compare negatively.", secondBid.compareTo(firstBid) < 0);
        assertTrue("Fourth bid was submitted later than second bid, so second bid should compare negatively.", fourthBid.compareTo(secondBid) > 0);
        
    }
    
    @Test
    //Ensuring auctions store all Bids, even if they are invalid.
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
        assertEquals("Auction should have five bids placed.", 5, testAuction.totalNumberOfBids());
        
    }
    
    @Test
    //Ensuring auctions store each person who places a bid as a participant in that auction.
    public void placingBidMakesPersonAParticipantInAuction () {
        
        Auction testAuction = new Auction(new Item("Leather Jacket"));
        
        testAuction.placeBid(new Bid(new Person("Alison"), 5));
        testAuction.placeBid(new Bid(new Person("Sarah"), 4));
        testAuction.placeBid(new Bid(new Person("Rachel"), 6));
        testAuction.placeBid(new Bid(new Person("Cosimo"), 5));
        
        assertEquals("There are four participants in this auction (Remember the Owner!)", 5, testAuction.participants().length);
        
    }
    
    @Test
    //Ensuring bids can be marked invalid and then valid again.
    public void togglingValidityOfBid () {
        
        Bid testBid = new Bid(new Person("Alison"), 5);
        
        assertEquals("Bid should be valid by default", true, testBid.validity());
        
        testBid.invalidate();
        
        assertEquals("Bid should be invalid", false, testBid.validity());
        
        testBid.validate();
        
        assertEquals("Bid should be valid", true, testBid.validity());
        
    }
    
    @Test
    //Checking our likeliness to bid formula is encouraging people to bid.
    public void howOftenDoPeoplePlaceBets () {
        
        Auction testAuction = new Auction(new Item("Leather Jacket"));
        
        Person firstPerson = new Person("Alison");
        Person secondPerson = new Person("Sarah");
        Person thirdPerson = new Person("Rachel");
        
        int numberOfBidsThatWillTakePlace = 0;
        
        for (boolean i : new boolean[100]) {
            
            if (firstPerson.willBid(testAuction.item(), testAuction.topBid())) numberOfBidsThatWillTakePlace++;
            if (secondPerson.willBid(testAuction.item(), testAuction.topBid())) numberOfBidsThatWillTakePlace++;
            if (thirdPerson.willBid(testAuction.item(), testAuction.topBid())) numberOfBidsThatWillTakePlace++;
            
        }
        
        System.out.println(numberOfBidsThatWillTakePlace + " bids placed out of a possible 300");
        
    }
    
    @Test
    //Checking to see what our bidding formula is asking our persons to bid at.
    public void whatDoPeopleBidAt () {
        
        Auction testAuction = new Auction(new Item("Leather Jacket"));
        
        Person firstPerson = new Person("Alison");
        Person secondPerson = new Person("Sarah");
        Person thirdPerson = new Person("Rachel");
        
        for (boolean i : new boolean[100]) {
            
            if (firstPerson.willBid(testAuction.item(), testAuction.topBid())) {
                
                Bid firstBid = firstPerson.determineBid(testAuction.item(), testAuction.topBid());
                
                testAuction.placeBid(firstBid);
                
                System.out.println(firstBid);
                
            }
            
            if (secondPerson.willBid(testAuction.item(), testAuction.topBid())) {
                
                Bid secondBid = secondPerson.determineBid(testAuction.item(), testAuction.topBid());
                
                testAuction.placeBid(secondBid);
                
                System.out.println(secondBid);
                
            }
            
            if (thirdPerson.willBid(testAuction.item(), testAuction.topBid())) {
                
                Bid thirdBid = thirdPerson.determineBid(testAuction.item(), testAuction.topBid());
                
                testAuction.placeBid(thirdBid);
                
                System.out.println(thirdBid);
                
            }
            
        }
        
        System.out.println("Top bid:" + testAuction.topBid());
        
    }    
    
}