/*
Emma Foster - November 2014
Auction Person
A representation of a human being taking part in an Auction.
For Shawn at StreamingEdge.
*/
package com.emma.auction;

public class Person implements Bidder {
    
    private String name; //The name of this person, truly their only identity in this program.
    private int balance; //The account balance for this person
    private Personality personality; //The personality traits of this person
    
    public Person (String inName) {
        
        this.name = inName;
        this.balance = (int) (Math.random() * 150); //Give them somewhere between $0 and $150 to bid with.
        this.personality = new Personality(); //Give them a personality!
        
    }
    
    //Some getters for basic properties of our Person
    public String name () { return this.name; }
    public int balance () { return this.balance; }
    
    //Implementing the Bidder interface
    
    //Determining if this person will make a Bid on an Item based on the item itself and the current top bid.
    public boolean willBid (Item itemToBidOn, Bid currentTopBid) {
        
        return this.biddingLikelinessFormula(itemToBidOn, currentTopBid) > .74 ? true : false; //We really want to hide away the formula used to calculate if we're going to bid as it's really ugly.
    
    }
    
    //Determine what we actually want to bid for the given item based on its own properties and the current top bid.
    public Bid determineBid (Item itemToBidOn, Bid currentTopBid) {
        
        return new Bid(this, this.biddingFormula(itemToBidOn, currentTopBid)); //Again we're abstracting away an ugly formula.
        
    }
    
    //We won the Auction! let's celebrate!
    public void win (Item itemWon) {
        
        if (this.name.equals("Owner")) System.out.println("Owner: I guess no one wanted my " + itemWon.name() + "."); //Unless you're the owner of the item and you won, which means no one bids. Then you're very sad.
        else System.out.println(this.name + ": Woohoo! I've always wanted a " + itemWon.name() + "."); //But otherwise, celebrate!
        
        //Place billing code here! You know... eventually.
        
    }
    
    //We lost the auction. At least we tried hard, right?
    public void lose (Item itemLost) {
        
        if (this.name.equals("Owner")) System.out.println("Owner: I'm so glad to have finally gotten rid of that " + itemLost.name() + "."); //If we're the owner, we're happy to lose, cause it means someone outbid us on our stuff.
        else System.out.println(this.name + ": What was I ever going to do with a " + itemLost.name() + " anyway?"); //Otherwise, invoke apathy.
        
    }
    
    //Formula to compute likelyness to bid.
    private double biddingLikelinessFormula (Item itemToBidOn, Bid currentTopBid) {
        
        if (currentTopBid.bidder().equals(this)) return 0.0; //If we are the current top bid, we don't want to bid higher. So don't bid.
        
        //What we're shooting for here is this: based on the qualities given Item and this Person's personality,
        //we'll bump up their likeliness to bid. This formula is supposed to engender a kind of humanity
        //to this bidding process. So, if a person is "ambitious" and an Item is rare, they'll be more
        //likely to bid on the Item than someone who isn't very ambitious. Other personality traits
        //work the same way. If the quality of an Item is low and a person has a high amount of
        //discipline, the person will be less likely to bid on that Item. Of course, the amount
        //of money this person has in pocket and how much the current top bid is plays a factor.
        //People don't like bidding above the money they actually have, but it is possible
        //within this betting system and thus can be used strategically. More explained in the readme.
        double initialBiddingLikeliness = 0.15; //We're giving everyone a baseline interest in an item here.
        
        double bidLikelinessWithPersonalityApplied = initialBiddingLikeliness + 
                (this.personality.ambition() + itemToBidOn.rarity()) * 0.30 + //if an item is rare and a person is ambitious, they'll be more likely to bid.
                (itemToBidOn.quality() - this.personality.discipline()) * 0.30 + //If an item's quality is low and Person's discipline is high, they'll be less likely to bid.
                (1 + this.personality.resourcefulness()) * (this.balance - currentTopBid.amount()) * 0.015 + //People who are resourceful bid less, and are more concerned about the money they have in pocket.
                (1 + this.personality.passion()) * 0.20 + //People who are passionate about an item will be more likely to bid on the item.
                Math.random() / 6.0; //Of course, basing everything off of personality and item traits is boring, so we'll toss in another bit of randomness. The effect of this is less than +0.15.
        
        return bidLikelinessWithPersonalityApplied; //With this formula, higher numbers mean more likely to bid. If the above sums to greater than 0.75, the person bids. Otherwise, they do not.
        
    }
    
    //Formula to compute the amount of money this Person will Bid on an Item.
    //Note that this method is always called after determining that the Person //wants// to bid. Meaning they got above 0.75 on the above formula.
    private int biddingFormula (Item itemToBidOn, Bid currentTopBid) {
        
        int initialBid = currentTopBid.amount(); //People really want to start at the current top bid amount, so they can outbid the current leader.
        
        //Again, this formula is an attempt to humanize this rather cold, inhuman system we have going on.
        //Here we're actually generating a dollar amount to make a Bid at. Again, personaity of the Person
        //and qualities of the Item are taken into consideration. If someone is passionate about and Item
        //and the Item is of high quality, the Person will bid a bit more in order to gain an advantage
        //over the other bidders. But if a person is resourceful, they'll try to bid just slightly over
        //the current top bid in order to keep the amount being bid low and save some money. And lastly,
        //people keep how much money they have in mind when bidding.
        int bidWithPersonalityApplied = (int) (initialBid + 
                    (this.balance - currentTopBid.amount()) * 0.025 + //People are mindful of how much money they have
                    (this.personality.ambition() + itemToBidOn.rarity()) * 2 + //Ambitious people pay more for rare Items
                    (this.personality.passion() + itemToBidOn.quality()) * 2 + //Passionate people pay mroe for quality Items
                    (1 + this.personality.assertiveness()) * 3 - //Assertive people bid higher in an attempt to ward off competition
                    (1 + this.personality.resourcefulness()) * 3 + //Resourceful people bid lower in an attempt to keep bidding amounts low.
                    Math.random() * 3); //Of course, we need some more randomness to make things fun.
        
        return bidWithPersonalityApplied; //In most cases this formula bids up from the current bid, while taking into account a whole lots of factors of the Person and Item.
        
    }
    
    @Override
    //We need to figure out if Person objects are referencing the same Person during the Auction
    //Really the only identifying thing about a Person in this program is their name, so we go by that
    //if we can't get a quick reference based equality going.
    public boolean equals (Object inObject) {
        
        if (inObject == this) return true; //if inObject is referencing the same thing as this, equality is trivial. inObject is exactly the same as this.
        if (!(inObject instanceof Person)) return false; //If inObject isn't a Person, it can't possibly be equal to this.
        
        Person inPerson = (Person) inObject; //This is a safe cast as we just checked if inObject is a Person. It is, as we've gotten this far.
        
        return inPerson.name().equals(this.name) ? true : false;
        
    }
    
    //For pretty printing
    @Override
    public String toString () {
        
        return "<Person: Name: " + this.name + ">";
        
    }
    
    //People have qualities just like Items do, but I wanted to give People a few more than Items.
    //This keeps the People interesting and the Auctions fairly random. It seemed right to tuck all
    //these properties into their own little personality class as to not muck up the Person class.
    //Again, all of the traits are randomly generated and self explanatory.
    private class Personality {
        
        private double ambition;
        private double discipline;
        private double assertiveness;
        private double passion;
        private double resourcefulness;
        
        public Personality () {
            
            //See? Random!
            this.ambition = Math.random();
            this.discipline = Math.random();
            this.assertiveness = Math.random();
            this.passion = Math.random();
            this.resourcefulness = Math.random();
            
        }
        
        //Yet more un-Java-like getters
        public double ambition () { return this.ambition; }
        public double discipline () { return this.discipline; }
        public double assertiveness () { return this.assertiveness; }
        public double passion () { return this.passion; }
        public double resourcefulness () {return this.resourcefulness; }
        
    }
    
}