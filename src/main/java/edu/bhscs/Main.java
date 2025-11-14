package edu.bhscs;

// Aayush Gupta
// P2
// Bake-Sale
// 09/29/2025

/*
 * DESCRIPTION: Restaurant coop thing (based on overcooked 2) (it was originally a bakery until burgers got added...)
 *  A game in which the player controls chefs to make them cook stuff according to orders that the player will be getting
 *  Certain foods like cakes and burgers need to be created add ingredient(s) to them, sometimes cooked and sometimes cut into slices (both ingredients and cakes/burgers can be cooked and cut)
 *  There is a limited amount of time to complete as many orders as possible
 *  The goal is to get the most money (which serves as a score) by the end of the time.
 *  Completing orders gets you money and you get even more based on your restaurant's rating from tips, your rating can go up by completing orders and also completing them in order but will go down if you take too long
 * INPUT: Actions for each chef
 * OUTPUT: The results of the actions (either moving or performing an action on a food) for each chef
 * EDGE CASE: There are a lot, one example is in the case that the restaurant's rating goes below 0, it needs to be set to 0
 */

// Note: Springboot + sockets (figure out it)

public class Main {

  public static void main(String[] args) {

    // Creating a new game with no arguments creates a default game

    Game game = new Game();
    game.doGameLoop();
  }
}
