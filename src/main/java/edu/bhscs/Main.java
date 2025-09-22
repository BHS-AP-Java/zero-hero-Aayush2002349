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

    Cake chocolate_cake = new Cake("Chocolate");
    Cake red_velvet_cake = new Cake("Red Velvet");

    Bakery bakery = new Bakery();

    Person steve = new Person("Steve");
    Person linda = new Person("Linda");

    bakery.trainChef(steve);
    bakery.trainChef(linda);

    steve.getCake(chocolate_cake);
    linda.getCake(red_velvet_cake);

    steve.eatCakeSlice();
    linda.eatCakeSlice();

    steve.doJob();
    linda.doJob();

    steve.eatCakeSlice();
    steve.eatCakeSlice();
    steve.eatCakeSlice();
    linda.eatCakeSlice();
    linda.eatCakeSlice();
    linda.eatCakeSlice();
  }
}
