/*
Emma Foster - November 2014
Auction Bidder
All of the important methods for a Person participating in an Auction.
For Shawn at StreamingEdge.
*/
package com.emma.auction;

//These methods are broken out from just the Person class in order to note that they're
//and entirely seperate entity from the Person. It's not particularly important that
//a Person is using them. The same could be given to a Computer and these
//methods would be equally valid in that context as well.
public interface Bidder {
    
    public boolean willBid (Item itemToBidOn, Bid currentTopBid); //Sorry, this isn't a very Java-like name. In Ruby, we'd call this method willBid?(), but I can't put question marks in function names here!
    public Bid determineBid (Item itemToBidOn, Bid currentTopBid); //these two methods are explained in depth in the Person class.
    public void win (Item itemWon); //listen for if we won the auction
    public void lose (Item itemLost); //listen for if we lost the auction
    
}