package edu.bhscs;

// Aayush Gupta
// P2
// Replacement-Assignment
// 09/15/2025

/*
 * DESCRIPTION: Replacement => abstraction
 * INPUT: Unknown
 * OUTPUT: Stuff in terminal
 * EDGE CASE: All
 */

class Main {

  public static final char A = 'a';

  public static void main(String[] args) {

    int num = 102;

    System.out.println("67!" + A + num);
    System.out.println(A + "?" + A + num);

    //Why is A + A + num 296 (maybe a as an int in bits is (296-102)/2 = 97 which is 01100001, 0110 may represent that this item is a char and then 1 may be the first letter (but i should expect that 0000 represents the first number))

    //I switched out the prints for println's so i could differentiate each message more easily
    System.out.println(A + A + num);

    System.out.println();
    System.out.println("done");
  }
}
