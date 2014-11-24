/*
Emma Foster - November 2014
Auction Tester
Barebones runner for our Auction program.
For Shawn at StreamingEdge.
*/
package com.emma.auction;

public class Tester {
    
    public static void main (String[] args) {
        
        final int NUMBER_OF_ROUNDS_OF_BIDDING = 20; //How many ticks AuctionRunner will go through
        
        AuctionRunner auctionRunner = new AuctionRunner(new Item("Leather Jacket"), NUMBER_OF_ROUNDS_OF_BIDDING); //Make an auction for a leather jacket
        
        auctionRunner.run(); //Do the magic
       
    }
    
}