package edu.bhscs;

import java.util.Scanner;

public class Game {

  // fields + properties
  Bakery2 bakery;
  String[] orders = new String[6];
  int completedOrders = 0;
  int time = 200;
  int totalBakers;

  public Game(int[][] layout, int totalBakers, Person[] bakers) {
    this.bakery = new Bakery2(layout, layout[0].length, layout.length, 10, "The Bakery");
    this.totalBakers = totalBakers;
    this.bakery.hireChefs(bakers);
  }

  public void doGameLoop() {

    Scanner scanner = new Scanner(System.in);
    for (int i = 0; i < time; i++) {
      System.out.println("Orders Completed: " + this.completedOrders);
      this.createOrder("chocolate");

      System.out.println();
      System.out.println("Time Left: " + (time - i));
      System.out.println();

      this.displayBakery();
      System.out.println();

      this.displayOrders();
      System.out.println();

      System.out.println("Baker actions: ");
      String actions = scanner.nextLine();

      String[] seperatedActions = this.seperateActions(actions);
      Person[] bakers = this.getBakers();
      this.doBakerActions(seperatedActions, bakers);

      this.bakery.tick();
    }

    scanner.close();
  }

  // Seperates the actions got from the user into individual actions
  // Each action is in the form direction-additionalinfo
  // additionalinfo doesnt have to exist
  // wasd are the directions
  // Additional info could be the type of cake
  // Actions are seperated by spaces
  public Person[] getBakers() {

    Person[] bakers = new Person[this.totalBakers];
    int index = 0;
    for (int y = 0; y < this.bakery.chefLocations.length; y++) {
      for (int x = 0; x < this.bakery.chefLocations[y].length; x++) {

        if (this.bakery.chefLocations[y][x] != null) {
          bakers[index] = this.bakery.chefLocations[y][x];
          index += 1;
        }
      }
    }

    return bakers;
  }

  public void doBakerActions(String[] seperatedActions, Person[] bakers) {
    for (int i = 0; i < bakers.length; i++) {
      // The baker's move function returns the location of whatever is blocking him (unless it is
      // outside the bakery or is another chef)
      int[] actionLocation = bakers[i].move(seperatedActions[i].charAt(0), this.bakery);
      if (actionLocation != null) {

        this.doActionAtLocation(
            actionLocation[0],
            actionLocation[1],
            bakers[i],
            seperatedActions[i + this.totalBakers]);
      }
    }
  }

  // Given the exact string the user entered, this returns the actions seperated out
  public String[] seperateActions(String actions) {

    // There is 1 action per baker, so the #of actions is equal to the number of bakers
    String[] seperatedActions = new String[this.totalBakers * 2];
    int index = 0;

    String action = "";
    for (int i = 0; i < actions.length(); i++) {
      if (actions.charAt(i) == ' ') {
        seperatedActions[index] = action;
        action = "";
        index += 1;
      } else {
        action += actions.charAt(i);
      }
    }

    seperatedActions[index] = action;

    for (int i = 0; i < this.totalBakers; i++) {
      if (seperatedActions[i].length() != 1) {
        seperatedActions[i + this.totalBakers] = seperatedActions[i].substring(1);
        seperatedActions[i] = seperatedActions[i].substring(0, 1);
      }
    }

    return seperatedActions;
  }

  // Given a certain position, a baker to do the action and potentially a type (for creating a new
  // cake) does whatever the action is
  public void doActionAtLocation(int x, int y, Person baker, String type) {

    // If both the baker and location have a cake then one of them needs to be placed down before
    // doing anything
    if (baker.cake != null && this.bakery.cakeLocations[y][x] != null) {
      return;
    }

    // This gets the kind of thing at the given location according to the key below

    // 2 = cake mix
    // 3 = oven
    // 4 = counter
    // 5 = cutting station
    // 6 = delivery station
    // 7 = trash
    int location = this.bakery.layout[y][x];

    // If the baker is holding a cake then he just places down the cake at the given location
    if (baker.cake != null) {

      if (location >= 2 && location <= 7) {
        this.bakery.placeCake(baker.giveCake(), x, y);
      }

      // If the baker isnt holding a cake but the place that the baker is performing the action on
      // does then he either just picks up the cake or cuts it
    } else if (this.bakery.cakeLocations[y][x] != null) {

      if (location == 3 || location == 4) {
        baker.getCake(this.bakery.pickUpCake(x, y));
      }

      if (location == 5) {
        if (this.bakery.cakeLocations[y][x].isCut) {
          baker.getCake(this.bakery.pickUpCake(x, y));
        } else {
          this.bakery.cutCake(x, y);
        }
      }

      // In the case that nobody is holding a cake then we get a new cake (from the cake mix
      // station)
    } else {

      if (location == 2) {
        baker.getCake(new Cake(type));
      }
    }

    return;
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

  // When called, this creates a new order
  public void createOrder(String type) {
    for (int i = 0; i < this.orders.length; i++) {
      if (this.orders[i] == null) {
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
        } else {
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
