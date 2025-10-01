package edu.bhscs;

// Aayush Gupta
// P2
// Bake-Sale
// 09/29/2025

/*
 * DESCRIPTION: Cake bake sale (based on overcooked 2)
 *  A game in which the player controls bakers to make them bake cakes
 *  Cakes need to be created, baked (without overcooking them) cut and then delivered according to orders that are currently randomly generated
 *  There is a limited amount of time to bake as many cakes as possible
 * INPUT: Actions for each baker
 * OUTPUT: The results of the actions (either moving or performing an action on a cake) for each baker
 * EDGE CASE: During baker collisions the bakers do move if an earlier executed one runs into a later executed baker's path
 *  For example if a baker 1 was at (0,0) and baker 2 was at (1,1) then telling baker 1 to move down and baker 2 to move left, baker 1 will move down and baker 2 won't move left (because baker 1 is there)
 */

class Main {

  public static void main(String[] args) {

    // 0 = empty
    // 1 = chef starting location(considered empty)
    // 2 = cake mix
    // 3 = oven
    // 4 = counter
    // 5 = cutting station
    // 6 = delivery station
    // 7 = trash

    int totalBakers = 4;

    int[][] layout = {
      {3, 0, 2, 0, 2, 0, 6},
      {5, 0, 0, 0, 0, 0, 6},
      {6, 1, 0, 4, 0, 1, 5},
      {4, 0, 0, 4, 0, 0, 5},
      {6, 1, 0, 4, 0, 1, 5},
      {5, 0, 0, 0, 0, 0, 7},
      {3, 0, 2, 0, 2, 0, 7}
    };

    Person[] bakers = new Person[totalBakers];
    bakers[0] = new Person("Alice");
    bakers[1] = new Person("Bob");
    bakers[2] = new Person("Carol");
    bakers[3] = new Person("Dan");

    Game game = new Game(layout, totalBakers, bakers);
    game.doGameLoop();

    /*
     * System.out.println(); System.out.println(); System.out.println(); System.out.println();
     *
     * Bakery bakery = new Bakery(4, "The Bakery");
     *
     * Person steve = new Person("Steve"); Person linda = new Person("Linda");
     *
     * bakery.hireChef(steve); bakery.hireChef(linda); System.out.println();
     *
     * Person john = new Person("John"); john.requestCake(bakery, "Chocolate"); john.eatCakeSlice();
     * System.out.println();
     *
     * Person alice = new Person("Alice"); alice.requestCake(bakery, "Red Velvet");
     * alice.eatCakeSlice(); System.out.println();
     *
     * Person bob = new Person("Bob"); bob.requestCake(bakery, "Cheese"); bob.eatCakeSlice();
     * bob.eatCakeSlice(); bob.eatCakeSlice(); bob.eatCakeSlice(); bob.eatCakeSlice();
     * bob.eatCakeSlice(); bob.eatCakeSlice(); System.out.println();
     *
     * bob.requestCake(bakery, "Cheese");
     */
  }
}
