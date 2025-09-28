package edu.bhscs;

public class Game {

  Bakery2 bakery;
  String[] orders = new String[6];
  int completedOrders = 0;

  public Game(int[][] layout) {

    this.bakery = new Bakery2(layout, layout[0].length, layout.length, 10, "The Bakery");
  }

  public void createOrder(String type) {
    for(int i = 0; i < this.orders.length; i++){
      if(this.orders[i] == null){
        this.orders[i] = type;
        return;
      }
    }
  }

  public void completeOrders(){
    for(int i = 0; i < this.orders.length; i++){
      Cake cake = this.bakery.getStoredCake(this.orders[i]);
      if(cake != null){
        
      }
    }
  }
}
