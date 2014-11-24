# Emma's Auction System

This project is meant to immitate an auction for some kind of physical or digital item. It generates a random Auction with a few people, has them bid a few times and then lets everyone know if they won or lost the auction. You'll notice each auction you run is different, as almost everything about the People bidding and the items being bid on is randomized.

##How To
Simply clone this repository, run `gradle build` on the cloned repository, and then `java -jar build/libs/Auction.jar`.

Tested under Gradle 2.2 and JVM 1.6.0_65.

Test results are available in `build/reports/tests/index.html`

##Classes Overview
###Item
This is a representation of the actual items up for auction. Truly this is a wrapper for further expansion on the code. Right now items just have a name and a few randomly generated "qualities". Currently the randomly generated properties are:

 * Usefulness
 * Rarity
 * Quality
 
These qualities are used when People are determining if and how much they want to bid on this Item.

####Limitations

 * Items are incomparable. However, Items are not used a ton throughout the code, and nothing is complex enough to require comparing items.
 * Items are really just fancy Strings. There's no real, physical or digital, item attached to the object.
 
####Possible Improvements
 
 * Items could be more strongly linked to a real object. This could be something as basic as displaying an image of the object, up to actually shipping a real object to a Person's address when they win the auction!
 * With real items backing these objects, they could easily provide *real* quality and rarity information, instead of just randomly generated numbers.

###Bid
A represenation of a bid for an Item placed by a Person during an Auction. Bids contain a reference to the Person who placed the bid, the amount in whole dollars that the Bid was placed at, what time the Bid was submitted to an Auction, and wether or not the Bid is "valid". Bids are truly the bread and butter of this program, and thus are super comparable and printable and friendly and easy to use.

####Validity of Bids
Within this Auctioning system, it's possible to submit bids below the dollar value of the current top bid. It's weird. These Bids are stored for statistical and usage reasons (not implemented, but a possibility in the future!), but can't be considered genuine bids for the Item at auction. So, we have a validity toggle on Bids. For full details on the auctioning system used, see below.

####Limitations
 * Bids only accept values in whole dollars (integers). This is really for simplicity in writing bidding formulas (more on this later), but obviously real bids are in doubles and an extended implementation of Bids could incorperate that.

###Person
A representation of a human being taking part in an Auction. Has a name and "bank account" (an integer), and a Personality. Does its thinking via the methods given to it by the Bidder interface.

####Personality
Every person is given a randomly generated personality. When placing bids at Auction, this Personality really shines via the bidding formulas (discussed below). Personalities are just randomly generated numbers between 0 and 1. Personality traits have fun names like
 * Ambition
 * Discipline
 * Assertiveness
 * Passion
 * Resourcefulness
 
These effect how they interact with Items and their pocketbook, but nothing else.

####Bidder
An interface that is used by People to get it's "Auctioniness", it tells the Person have the methods willBid?() and determineBid(), which are called during an AuctionRunner session in order to know if a Person will bid and exactly how they will bid. It also is how an AuctionRunner tells each Person if they won or lost an Auction.

#####Likeliness to Bid Formula
The Person actually implements the likeliness to bid formula, which is supposed to make each Person take their Personality, the current top bid, and the qualities of the Item being bid on, and determine if this Person wants to bid on the Item at Auction. For example, if a person is ambitious (a randomly generated trait), and the Item at Auction is rare, the person is more likely to make a Bid. Conversely, if the quality (a randomly generated trait) of an Item is low, and the Person has a high amount of discipline, the Person is less likely to make a Bid on the Item. People also take how much money they have in comparison to the top bid into account (especially resourceful people). The goal of the formula is to bring some life to the bidding in the program, and to provide reasonably (but still randomly generated) results to Auctions within the program.

#####Bidding Formula
The bidding formula uses the same principles as the formula above. However, it has a slightly different implementation and is meant to generate a dollar amount rather than a probability.

###Limitations and Improvements
 * Everything here is randomly generated. Thus, some wonky things do tend to happen over enough tests. People bidding well over their means, and ridiculously high bets.
 * Given a large dataset on a person, we could generate less random personality traits and thus better represent a person than a few randomly generated numbers.
 * Currently, the amount of money a person has at the beginning of the auction is simply randomly generated, without even a Constructor option to seed the amount. It would be very easy to implement some way to set the amount of money a person starts with in a future implementation.
 * Formula improvements! I'm pretty happy with the formulas as they are, with the data that is generated by items and personalities, but it's always possible to write more in depth formulas to generate more interesting people, and their bidding habits.

###Auction
A representation of an Auction using the Emma's Fun Time Auction Rules (described below). Bids are placed at an Auction, where the Bid is stored, timestamped, and made the top Bid via a Priority Queue (if the submitted Bid is the highest given yet). Priority Queue is used so that we have easy access to the highest bid (that which was submitted earliest, and has the highest value), but can look back at all the other bids given to us. This storage is useful both for finding the 'real' winner of the Auction, as well as for statistical and usage data later down the road (nothing implemented yet).

###Limitations and Improvements
 * Currently Auctions need to be started with an Item. We could have a more general Constructor that doesn't require an item, and thus implement more setters in the code. However, I avoided writing setters (read notes for why).
 * Auctions cannot be reset in any way, so we need to make a new Auction for each Item we wish to Auction off. Again, fixing this would require some more setters, which didn't feel needed for such a small project.

###AuctionRunner
If the Auction class represents the abstract idea of an auction, AuctionRunner is the auctionhouse it takes place in, the auctioneer, and the phone-in-bidders booth all in one. There are a few important concepts to the AuctionRunner:

####People Present
Thinking of AuctionRunner like an auctionhouse, there can be people around the auctionhouse that are present at the auction, but not neccesarily participating in the auction. It's possible that these people will jump in on the auction eventually, but not all will. Participants in the auction will be drawn from this 'pool' of people, but it's important to differentiate between possible participants and actual participants in the auction. AuctionRunner asks each Person who is present at the Auction if they want to participate in the auction during each Tick.

####Ticks
Rather than implementing some time-based multi-threaded bidding approach, this program uses the simple (and fake) concept of time passing via ticks. Ticks are where all the action of the auctionhouse happens. New people can join in the auctionhouse, people can place bids, and the auctioneer (STD_OUT) speaks. The AuctionRunner is seeded how many ticks an Auction will last, similarly to how an Auction may last 5 minutes, with many opportunities for People to bid. Ticks represent time, but are much simpler to program (if less cool).

####The AuctionRunner Algorithm
AuctionRunner goes through the following steps to start and complete an auction:

1. Generate an Auction for the Item given during construction
2. Set the number of ticks, N, (how long the auction will last), as given by the constructor.
3. Generate a few initial people to be present at the auction (seeded to be 1-8 people randomly).
4. Start a Tick
5. Randomly decide if a new person has arrived at the auction. There's a 20% chance of this happening. Maximum of 10 people at the auction (this is just how many names I hardcoded, there's no technical limit this is set at 10).
6. Cycle through every person present at the auction and check if they want to bid.
7. If a person wants to bid, ask them how much they wish to bid for.
8. Place any bids that are given by People.
9. End Tick
10. Repeat Tick a total of N times. (In my Tester, N = 20, so AuctionRunner ticks 20 times.).
11. End Auction.
12. Determine the winner (details in Emma's Fun Time Auction Rules).
13. Inform the winner they won.
14. Inform those that did not win that they lost. (A total of one time for each person, no matter how many bids they placed).
15. End.

###Limitations and Improvements
 * AuctionRunner can only run one Auction during its liftime. It would be reasonable to implement a reset() method along with some setters to allow new Auctions to be run from the same instance of AuctionRunner.
 * Ticks are an unrealistic notion of time, and doesn't provide a realistic basis for bidding. It would be possible to set up AuctionRunner to run for X minutes, and acrue bids throughout that period, but it would be much more complicated and likely require some threading to work well.

###Tester
Really basic runner for all of this. Provides an example auction of a leather jacket. Runs and completely the auction, prints the bids, and the winner.

##Emma's Fun Time Auction Rules and Assumptions
Auctions in this program work as follows:

 * Bids can be placed at any amount at any time.
 * Bids that are below the current highest bid are marked invalid.
 * Bids that were once the top bid, but are currently below the top bid are still valid.
 * Bids can be placed that are above the amount of money a Person currently has.
 * At the end of the auction, the highest bid that is valid and can actually be paid by the person who placed the bid is considered the winning bid, and thus the person who placed that bid is the winner.
 * All bids are kept for statistical and usage reasons.
 
##Project Notes
This was a fairly small and quick cut project, so a lot of corners we cut that would make this particularly cool. Threading (and thus real time bids) are the most obvious cut. Most subtle, almost all objects in the project are written to immutable (at least from outside of the object). This cut down on complexity of writing classes that deal with setting and getting their data a lot. It also means you need to creative new instances of objects in order to run them again, which can be frustrating.

Overall, I think the project is a success. There's a cool little world of bidding happening inside the program when it's run, and it feels alive. That's a pretty good accomplishment for a project that could be rigidly implemented in basic tests, I think.

I hope you enjoy my code and I hear back from you soon.

Emma