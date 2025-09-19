package edu.bhscs;

// Aayush Gupta
// P2
// Zero-Hero
// 09/19/2025

/*
 * DESCRIPTION: Replacement => abstraction
 * INPUT: Unknown
 * OUTPUT: Stuff in terminal
 * EDGE CASE: All
 */

class Main {

  public static void main(String[] args) {

    Cake chocoCake = new Cake("chocolate");
    chocoCake.bake();
    chocoCake.bake();
    chocoCake.cut(6);
    chocoCake.eatSlice();
    chocoCake.eatSlice();
    chocoCake.eatSlice();
    chocoCake.eatSlice();
    chocoCake.eatSlice();
    chocoCake.eatSlice();
    chocoCake.eatSlice();

  }

  public static int awesome() {

    return 3;

  }

  public static String pushup(String food) {

    return "Sweat " + food;
    
  }
}
