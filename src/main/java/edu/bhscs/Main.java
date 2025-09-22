package edu.bhscs;

// Aayush Gupta
// P2
// Zero-Hero
// 09/19/2025

/*
 * DESCRIPTION: Cake bake sale
 * INPUT: Unknown
 * OUTPUT: Stuff in terminal
 * EDGE CASE: All
 */

class Main {

  public static void main(String[] args) {
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println();


    Bakery bakery = new Bakery(4,"The Bakery");

    Person steve = new Person("Steve");
    Person linda = new Person("Linda");


    bakery.hireChef(steve);
    bakery.hireChef(linda);
    System.out.println();

    Person john = new Person("John");
    john.requestCake(bakery, "Chocolate");
    john.eatCakeSlice();
    System.out.println();

    Person alice = new Person("Alice");
    alice.requestCake(bakery, "Red Velvet");
    alice.eatCakeSlice();
    System.out.println();

    Person bob = new Person("Bob");
    bob.requestCake(bakery, "Cheese");
    bob.eatCakeSlice();
    bob.eatCakeSlice();
    bob.eatCakeSlice();
    bob.eatCakeSlice();
    bob.eatCakeSlice();
    bob.eatCakeSlice();
    bob.eatCakeSlice();
    System.out.println();

    bob.requestCake(bakery, "Cheese");
  }
}
