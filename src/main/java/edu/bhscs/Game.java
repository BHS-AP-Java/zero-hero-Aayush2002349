package edu.bhscs;

public class Game {

  Bakery2 bakery;

  public Game(int[][] layout){

    this.bakery = new Bakery2(layout,layout[0].length,layout.length,10,"The Bakery");

  }

}
