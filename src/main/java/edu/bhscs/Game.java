package edu.bhscs;

import java.util.Random;

public class Game {

  // fields + properties
  Bakery2 bakery;
  String[] orders = new String[6];
  int completedOrders = 0;
  int time = 200;
  int totalBakers;
  String[] menu;
  User user;
  Random random = new Random();
  Person[] bakers;

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

      //Creates an order every 3 turns
      if (i % 3 == 0) {
        this.createOrder();
      }


      // Displays the orders completed
      System.out.println("Orders Completed: " + this.completedOrders);

      //Displays the time
      System.out.println();
      System.out.println("Time Left: " + (time - i));
      System.out.println();

      //Displays the bakery
      this.displayBakery();
      System.out.println();

      //Displays the order
      this.displayOrders();
      System.out.println();

      for(int j = 0; j < this.bakers.length; j++){
        this.bakers[j].getAndDoAction();
      }

      //Has the bakery do all the bakery stuff (ex: cooking a cake in the oven)
      this.bakery.tick();

      //Based on the cakes in the bakery, completes pending orders
      this.completeOrders();
    }
  }
  // When called, this displays the currently pending orders
  public void displayOrders() {
    System.out.println("Pending orders: ");
    for (int i = 0; i < this.orders.length; i++) {

      if (this.orders[i] == null) {
        return;
      }

      System.out.println("  Order #" + (i + 1) + ": " + this.orders[i] + " cake");
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
          completedOrders += 1;
          indexShift += 1;

          // Here is where the orders are shifted
        } else if (indexShift != 0) {
          this.orders[i - indexShift] = this.orders[i];
          this.orders[i] = null;
        }
      }
    }
  }

  public void displayBakery() {

    // Display is as follows:
    // 0 = empty
    // 1 = chef starting location(considered empty so it will be 0)
    // 2 = cake mix
    // 3 = oven
    // 4 = counter
    // 5 = cutting station
    // 6 = delivery station
    // 7 = trash
    // 8 = chef
    // 9 = chef holding cake
    // a = cake mix
    // a,b,c,d,e,f,h,i,j = cake cooking
    // j = fully cooked
    // z = overcooked
    // s = also cut

    for (int y = 0; y < this.bakery.layout.length; y++) {
      for (int x = 0; x < this.bakery.layout[y].length; x++) {

        if (this.bakery.chefLocations[y][x] != null) {

          if (this.bakery.chefLocations[y][x].cake != null) {
            System.out.print("9");
          } else {
            System.out.print("8");
          }

        } else if (this.bakery.cakeLocations[y][x] != null) {

          Cake cake = this.bakery.cakeLocations[y][x];

          if (cake.isEdible) {
            System.out.print("s");
          } else if (cake.isOvercooked) {
            System.out.print("z");
          } else if (cake.isBaked) {
            System.out.print("j");
          } else {
            char[] numToLetter = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
            System.out.print("" + numToLetter[this.bakery.cakeCookingTimers[y][x]]);
          }

        } else {

          if (this.bakery.layout[y][x] == 1) {
            System.out.print("0");
          } else {
            System.out.print("" + this.bakery.layout[y][x]);
          }
        }
      }
      System.out.println();
    }
  }
}
