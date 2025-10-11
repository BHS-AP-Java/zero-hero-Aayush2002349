package edu.bhscs;

// Aayush Gupta
// P2
// Bake-Sale
// 09/29/2025

/*
 * DESCRIPTION: Restaurant coop thing (based on overcooked 2) (it was originally a bakery until burgers got added...)
 *  A game in which the player controls chefs to make them cook stuff according to orders that the player will be getting
 *  Certain foods like cakes and burgers need to be created add ingredient(s) to them, cooked and sometimes cut into slices
 *  There is a limited amount of time to complete as many orders as possible
 *  The goal is to get the most money (which serves as a score) by the end of the time.
 *  Completing orders gets you money and you get even more based on your restaurant's rating from tips, your rating can go up by completing orders and also completing them in order but will go down if you take too long
 * INPUT: Actions for each chef
 * OUTPUT: The results of the actions (either moving or performing an action on a food) for each chef
 * EDGE CASE: There are a lot, one example is in the case that the restaurant's rating goes below 0, it needs to be set to 0
 */

// Note: Springboot + sockets (figure out it)

class Main {

  public static void main(String[] args) {

    //Layout
    // 0 = empty
    // 2 = base station (get new stuff like cakes and burgers)
    // 3 = oven (a place to cook stuff)
    // 4 = counter (a place to store stuff currently not needed)
    // 5 = cutting station (put a food here to cut it)
    // 6 = delivery station (if a food is complete then put it here)
    // 7 = trash (throw away stuff)
    // 8 = ingrediant station (add ingrediant(s) to the cakes and burgers)
    // u = chef
    // U = chef holding food
    // a = food mix
    // a,b,c,d,e,f,h,i,j = food cooking
    // j = fully cooked
    // z = overcooked
    // s = fully ready (this overrides everything)

    //Controls:
    //wasd to move or select the place you want to do an action at

    //The default action is either placing down or picking up a cake
    //For all places except the base station you need to place the cake down at the location and then perform an action on it

    //To override the default do the following:
    //if at cake mix or ingredient station also type what you want to get/add with no space right after the direction (ex: wcake)
    //if at a cutting station then type x right after the direction to cut instead of picking the cake up (ex: sx)

    // Creating a new game with no arguments creates a default game
    Game game = new Game();
    game.doGameLoop();

    //Display display = new Display();
    //display.displayBurger(new Burger());
  }
}
