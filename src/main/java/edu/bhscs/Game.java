package edu.bhscs;

public class Game {

  // fields + properties

  Restaurant restaurant;
  Person[] bakers;

  int time = 200;
  Display display = new Display();

  // Constructors below
  // This one creates a game with the given inputs
  public Game(int[][] layout, Person[] bakers, Food[] menu) {
    this.restaurant =
        new Restaurant(layout, layout[0].length, layout.length, 10, "The Restaurant", menu);
    this.restaurant.hireChefs(bakers);
    this.bakers = bakers;
  }

  // Giving no inputs just creates a demo game
  public Game() {
    // 0 = empty
    // 1 = chef starting location(considered empty)
    // 2 = base station
    // 3 = oven
    // 4 = counter
    // 5 = cutting station
    // 6 = delivery station
    // 7 = trash
    // 8 = ingrediant station
    int[][] layout = {
      {3, 0, 2, 8, 2, 0, 6},
      {5, 0, 1, 0, 1, 0, 6},
      {3, 0, 0, 4, 0, 0, 5},
      {3, 0, 0, 4, 0, 0, 5},
      {3, 0, 0, 4, 0, 0, 5},
      {5, 0, 0, 0, 0, 0, 7},
      {3, 0, 2, 8, 2, 0, 7}
    };

    this.bakers = new Person[2];

    User player1 = new User("");
    //
    this.bakers[0] = new Person("Alice", player1);
    this.bakers[1] = new Person("Bob", player1);
    // this.bakers[1] = new Person("Bob", player1);

    Food[] menu = new Food[5];
    menu[0] = new Food("cake");
    menu[0].isMenu = true;
    menu[0].addIngredient(new Ingredient("chocolate"));
    menu[1] = new Food("cake");
    menu[1].isMenu = true;
    menu[1].addIngredient(new Ingredient("spice"));
    menu[2] = new Food("burger");
    menu[2].isMenu = true;
    menu[2].addIngredient(new Ingredient("meat"));
    menu[3] = new Food("burger");
    menu[3].isMenu = true;
    menu[3].addIngredient(new Ingredient("meat"));
    menu[3].addIngredient(new Ingredient("cheese"));
    menu[4] = new Food("burger");
    menu[4].isMenu = true;
    menu[4].addIngredient(new Ingredient("meat"));
    menu[4].addIngredient(new Ingredient("cheese"));
    menu[4].addIngredient(new Ingredient("lettuce"));

    this.restaurant =
        new Restaurant(layout, layout[0].length, layout.length, 10, "The Restaurant", menu);
    this.restaurant.hireChefs(bakers);
  }

  public void doGameLoop() {

    for (int i = 0; i < time; i++) {

      // Creates an order every 3 turns
      if (i % 3 == 0) {
        this.restaurant.takeOrder(new Order(this.restaurant.menu, this.restaurant.rating));
      }

      this.display.displayEverything((time - i), this.restaurant);

      for (int j = 0; j < this.bakers.length; j++) {
        this.bakers[j].getAndDoAction();
      }

      // Has the restaurant do all the restaurant stuff (ex: cooking a cake in the oven)
      this.restaurant.tick();
    }
  }
}
