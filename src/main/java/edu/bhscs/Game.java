package edu.bhscs;

public class Game {

  // fields + properties

  Bakery bakery;
  Person[] bakers;

  int time = 200;
  Display display = new Display();

  //Constructors below
  //This one creates a game with the given inputs
  public Game(int[][] layout, Person[] bakers, String[] menu) {
    this.bakery = new Bakery(layout, layout[0].length, layout.length, 10, "The Bakery",menu);
    this.bakery.hireChefs(bakers);
    this.bakers = bakers;
  }

  //Giving no inputs just creates a demo game
  public Game(){
    // 0 = empty
    // 1 = chef starting location(considered empty)
    // 2 = cake mix
    // 3 = oven
    // 4 = counter
    // 5 = cutting station
    // 6 = delivery station
    // 7 = trash
    int[][] layout = {
      {3, 0, 2, 0, 2, 0, 6},
      {5, 0, 1, 0, 0, 0, 6},
      {6, 0, 0, 4, 0, 0, 5},
      {4, 0, 0, 4, 0, 0, 5},
      {6, 0, 0, 4, 0, 0, 5},
      {5, 0, 0, 0, 0, 0, 7},
      {3, 0, 2, 0, 2, 0, 7}
    };

    this.bakers = new Person[1];

    User player1 = new User("");
    this.bakers[0] = new Person("Alice", player1);

    String[] menu = {"chocolate", "red-velvet", "spice"};

    this.bakery = new Bakery(layout, layout[0].length, layout.length, 10, "The Bakery",menu);
    this.bakery.hireChefs(bakers);
  }

  public void doGameLoop() {

    for (int i = 0; i < time; i++) {

      // Creates an order every 3 turns
      if (i % 3 == 0) {
        this.bakery.takeOrder(new Order(this.bakery.menu,this.bakery.rating));
      }

      this.display.displayEverything((time - i), this.bakery);

      for (int j = 0; j < this.bakers.length; j++) {
        this.bakers[j].getAndDoAction();
      }

      // Has the bakery do all the bakery stuff (ex: cooking a cake in the oven)
      this.bakery.tick();
    }
  }
}
