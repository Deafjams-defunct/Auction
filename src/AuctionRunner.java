/*
Emma Foster - November 2014
Auction Bid
Representation of an auctionhouse, auctioneer, and even time itself!
For Shawn at StreamingEdge.
*/
package com.emma.auction;

import java.util.Arrays; //Used for converting an array into a Collection (List)
import java.util.Set; //Used to store the people present at the Auction uniquely
import java.util.HashSet; //Actual implementation of set that we're using. Not particularly important. The actual Set interface is the important part.

public class AuctionRunner {
    
    private Auction auction; //The Auction taking place at this Auction"house"
    private Set<Person> peoplePresent; //The people who are AT the Auction but not necessarily PARTICIPATING in the Auction. This is an important distinction as peoplePresent and auction.participants() will often be different.
    private Bid winningBid; //Our winner
    private final int NUMBER_OF_TICKS; //How long the auction is going to run
    private final int VENUE_CAPACITY = 10; //How many people this auction will max out at. This is really only limited by how many names are in he peopleNames array.
    private final String[] peopleNames = {"Sarah", "Cosima", "Delphine", "Rachel", "Felix", "Tony", "Chris", "Alison", "Kira", "Helena"}; //Just some sample names to give to people.
    
    //Constructor, requires and Item and a number of ticks (duration) for the auction.
    public AuctionRunner (Item itemToAuction, int numberOfTicks) {
        
        this.auction = new Auction(itemToAuction);
        
        this.peoplePresent = new HashSet<Person>(this.VENUE_CAPACITY); //Hey, we can actually declare a size on this! We only want to store at many people as the venue can hold.
        
        this.determineInitialPeoplePresent(); //Sets up a few people to be at the auction, so we can pester them for Bids.
        
        this.NUMBER_OF_TICKS = numberOfTicks;
        this.winningBid = null; //No winners yet!
        
    }
    
    //Where all the magic happens. Conducts the auction and informs the winners and losers.
    public void run () {
        
        System.out.println("Beginning auction for " + this.auction.item().name() + " with bids starting at $" + this.auction.topBid().amount() + ".00.\nBidding will take place over " + NUMBER_OF_TICKS + " rounds.\n"); //let the user know the auction is starting
        
        for (boolean i : new boolean[this.NUMBER_OF_TICKS]) { //do NUMBER OF TICKS ticks
            
            this.tick();
            
        }
        
        System.out.println("\nThe auction has ended.\n");
        
        this.determineWinningBid(); //figure out who won
        
        System.out.println(this.winningBid.bidder().name() + " won the auction at $" + this.winningBid.amount() + ".00.\n");
        
        this.informWinner(); //tell the winner they won
        
        this.informLosers(); //tell the losers they lost
        
    }
    
    //Some basic getters
    public Person[] peoplePresent () {
        
        return this.peoplePresent.toArray(new Person[this.peoplePresent.size()]); //Generating a Person[] rather than an Object[] from our Set<Person>.
        
    }
    
    
    public Bid winningBid () { return this.winningBid; }
    public Auction auction () { return this.auction; }
    
    //This is "time" happening, all of the actions that occur during an auction happen here.
    private void tick () {
        
        this.determineIfNewPersonHasArrived(); //See if anyone new has walked into the auction. 20% of someone new showing up.
        
        for (Person currentPerson : this.peoplePresent) { //cycle over all the people who are at the auction
            
            if (currentPerson.willBid(this.auction.item(), this.auction.topBid())) { //check if each person wants to bid on the current Item
                
                Bid currentBid = currentPerson.determineBid(this.auction.item(), this.auction.topBid()); //They do want to bid, so get them to calculate a bid.
                
                System.out.println(currentBid.bidder().name() + " places a bid at $" + currentBid.amount() + ".00."); //tell everyone this person made a bid
                
                this.auction.placeBid(currentBid); //actually place the bid in our auction.
                
            }
            
        }
        
    }
    
    //Seeds the auction with some initial people.
    private void determineInitialPeoplePresent () {
        
        final int NUMBER_OF_PEOPLE_PRESENT = (int) Math.random() * 7 + 1; //generates a number between 1 and 8.
        
        for (int i = 0; i < NUMBER_OF_PEOPLE_PRESENT; i++) {
            
            this.peoplePresent.add(new Person(this.peopleNames[i])); //Generates these people, gives them unique names.
            
        }
        
    }
    
    //Simple formula to see if a new person has walked in on this auction
    private void determineIfNewPersonHasArrived () {
        
        final double CHANCE_NEW_PERSON_ARRIVES = 0.2; //20% chance of a new person arriving
        
        if (Math.random() < CHANCE_NEW_PERSON_ARRIVES && this.peoplePresent.size() < this.VENUE_CAPACITY) { //generate a random number, ensure it's less than our threshold, and ensure our venue isn't full
            
            this.peoplePresent.add(new Person(this.peopleNames[this.peoplePresent.size()])); //generate a new person, and give then a unique name.
            
        }
        
    }
    
    //Determining a winning bid is fairly difficult, actually!
    //Since people can bid beyond what they have in their wallet, invalid bids can be the top bid.
    //Thus, we need to check each person's wallets with their highest bids.
    //The person with the highest bid that they can actually pay is our winner.
    private void determineWinningBid () {
        
        for (Bid currentBid : this.auction.bids()) { //cycle through all the bids made
            
            if (currentBid.validity() && currentBid.amount() <= currentBid.bidder().balance()) { //check if the bid is valid, and if the person who made it can pay how much they bid.
                
                this.winningBid = currentBid; //they can, so we found the winning bid.
                
                return;
                
            }
            
        }
        
    }
    
    //Tell the winner that (and what) they won.
    private void informWinner () {
        
        this.winningBid.bidder().win(this.auction.item());
        
    }
    
    //Tell the losers that they lost.
    private void informLosers () {
        
        Set<Person> participants = new HashSet<Person>(Arrays.asList(this.auction.participants())); //Get all participants in the auction
        
        participants.remove(this.winningBid.bidder()); //take out the winner
        
        for (Person loser : participants) { //cycle through every participant but the winner (read: the losers)
            
            loser.lose(this.auction.item()); //tell them they lost.
            
        }
        
    }
    
}