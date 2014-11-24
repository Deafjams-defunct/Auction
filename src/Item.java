/*
Emma Foster - November 2014
Auction Item
Representation of a physical item up for auction.
For Shawn at StreamingEdge.
*/
package com.emma.auction;

public class Item {
    
    private String name; //The name of this object, right now the only way to actual idenify an Item.
    
    //A few random qualities of the item, to make things interesting.
    private double usefulness;
    private double rarity;
    private double quality;
    
    //Constructing the item with a given name
    public Item (String inName) {
        
        this.name = inName;
        
        //Like I said, these are truly random qualities
        this.usefulness = Math.random();
        this.rarity = Math.random();
        this.quality = Math.random();
        
    }
    
    //Some rather un-Java-like getters for our item's properties
    public String name () { return this.name; }
    public double usefulness () { return this.usefulness; }
    public double rarity () { return this.rarity; }
    public double quality () { return this.quality; }
    
}