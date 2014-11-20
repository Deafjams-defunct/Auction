package com.emma.auction;

public class Person {
    
    private String name;
    
    public Person (String inName) {
        
        this.name = inName;
        
    }
    
    //Fetch name
    public String name () {
        
        return this.name;
        
    }
    
    //For pretty printing
    @Override
    public String toString () {
        
        return "<Person: Name: " + this.name + ">";
        
    }
    
}