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
 * EDGE CASE: There are a lot, one example is in the case that the bakeries rating goes below 0, it needs to be set to 0
 */

// Springboot + sockets

class Main {

  public static void main(String[] args) {

    // Creating a new game with no arguments creates a default game
    //Game game = new Game();
    //game.doGameLoop();

    Display display = new Display();
    display.displayBurger(new Burger());
  }
}
