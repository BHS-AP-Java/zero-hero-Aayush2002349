package edu.bhscs;

public class Display {

  public Display() {}

  public void displayEverything(int ordersCompleted, int timeLeft, String[] orders, Bakery bakery) {
    this.displayOrdersCompleted(ordersCompleted);
    this.displayTime(timeLeft);
    this.displayOrders(orders);
    this.displayBakery(bakery);
  }

  // When called, displays the bakery
  public void displayBakery(Bakery bakery) {

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

    for (int y = 0; y < bakery.layout.length; y++) {
      for (int x = 0; x < bakery.layout[y].length; x++) {

        if (bakery.chefLocations[y][x] != null) {

          if (bakery.chefLocations[y][x].cake != null) {
            System.out.print("9");
          } else {
            System.out.print("8");
          }

        } else if (bakery.cakeLocations[y][x] != null) {

          Cake cake = bakery.cakeLocations[y][x];

          if (cake.isEdible) {
            System.out.print("s");
          } else if (cake.isOvercooked) {
            System.out.print("z");
          } else if (cake.isBaked) {
            System.out.print("j");
          } else {
            char[] numToLetter = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};
            System.out.print("" + numToLetter[bakery.cakeLocations[y][x].timeCooked]);
          }

        } else {

          if (bakery.layout[y][x] == 1) {
            System.out.print("0");
          } else {
            System.out.print("" + bakery.layout[y][x]);
          }
        }
      }
      System.out.println();
    }

    System.out.println();
  }

  // When called, this displays the currently pending orders
  public void displayOrders(String[] orders) {
    System.out.println("Pending orders: ");
    for (int i = 0; i < orders.length; i++) {

      if (orders[i] == null) {
        return;
      }

      System.out.println("  Order #" + (i + 1) + ": " + orders[i] + " cake");
    }

    System.out.println();
  }

  // Displays the time
  public void displayTime(int timeLeft) {
    System.out.println("Time Left: " + timeLeft);
    System.out.println();
  }

  // Displays the number of orders completed
  public void displayOrdersCompleted(int ordersCompleted) {
    System.out.println("Orders Completed: " + ordersCompleted);
    System.out.println();
  }
}
