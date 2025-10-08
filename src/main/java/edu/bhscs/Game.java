package edu.bhscs;

import java.util.Random;

public class Game {

  // fields + properties
  Bakery2 bakery;
  String[] orders = new String[6];
  int ordersCompleted = 0;
  int time = 200;
  int totalBakers;
  String[] menu;
  User user;
  Random random = new Random();
  Person[] bakers;
  Display display = new Display();

  public Game(int[][] layout, int totalBakers, Person[] bakers, User user, String[] menu) {
    this.bakery = new Bakery2(layout, layout[0].length, layout.length, 10, "The Bakery");
    this.totalBakers = totalBakers;
    this.bakery.hireChefs(bakers);
    this.user = user;
    this.menu = menu;
    this.bakers = bakers;
  }

  public void doGameLoop() {

    for (int i = 0; i < time; i++) {

      // Creates an order every 3 turns
      if (i % 3 == 0) {
        this.createOrder();
      }

      this.display.displayEverything(this.ordersCompleted, (time - i), this.orders, this.bakery);


      for (int j = 0; j < this.bakers.length; j++) {
        this.bakers[j].getAndDoAction();
      }

      // Has the bakery do all the bakery stuff (ex: cooking a cake in the oven)
      this.bakery.tick();

      // Based on the cakes in the bakery, completes pending orders
      this.completeOrders();
    }
  }

  // When called, this creates a new order (currently picks a random item from the menu)
  public void createOrder() {
    for (int i = 0; i < this.orders.length; i++) {
      if (this.orders[i] == null) {
        String type = this.menu[random.nextInt(this.menu.length)];
        this.orders[i] = type;
        return;
      }
    }
  }

  // When called, this checks the bakery's inventories and completes any orders
  // It also slides all the orders down (so if order 4 is completed then order 5 becomes order 4)
  public void completeOrders() {
    int indexShift = 0;
    for (int i = 0; i < this.orders.length; i++) {

      if (this.orders[i] != null) {

        Cake cake = this.bakery.getStoredCake(this.orders[i]);
        // Here is where the order is completed
        if (cake != null) {
          this.orders[i] = null;
          this.ordersCompleted += 1;
          indexShift += 1;

          // Here is where the orders are shifted
        } else if (indexShift != 0) {
          this.orders[i - indexShift] = this.orders[i];
          this.orders[i] = null;
        }
      }
    }
  }
}
