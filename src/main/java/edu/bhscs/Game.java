package edu.bhscs;

public class Game {

  Bakery2 bakery;
  String[] orders = new String[6];
  int completedOrders = 0;

  public Game(int[][] layout) {

    this.bakery = new Bakery2(layout, layout[0].length, layout.length, 10, "The Bakery");
  }

  public void createOrder(String type) {
    for (int i = 0; i < this.orders.length; i++) {
      if (this.orders[i] == null) {
        this.orders[i] = type;
        return;
      }
    }
  }

  public void completeOrders() {
    int indexShift = 0;
    for (int i = 0; i < this.orders.length; i++) {

      if(this.orders[i] != null){

        Cake cake = this.bakery.getStoredCake(this.orders[i]);

        if (cake != null) {
          this.orders[i] = null;
          completedOrders += 1;
          indexShift += 1;
        } else {
          this.orders[i-indexShift] = this.orders[i];
          this.orders[i] = null;
        }
      }
    }
  }

  public void displayBakery() {
    // Layout:
    // 0 = empty
    // 1 = chef starting location(considered empty)
    // 2 = cake mix
    // 3 = oven
    // 4 = counter
    // 5 = cutting station
    // 6 = delivery station
    // 7 = trash
    // 8 = chef
    
  }
}
