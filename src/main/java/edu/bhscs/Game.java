package edu.bhscs;

import java.util.Scanner;
public class Game {

  Bakery2 bakery;
  String[] orders = new String[6];
  int completedOrders = 0;
  int time = 200;

  public Game(int[][] layout) {
    this.bakery = new Bakery2(layout, layout[0].length, layout.length, 10, "The Bakery");
  }

  public void gameLoop(){

    Scanner scanner = new Scanner(System.in);
    for(int i = 0; i < time; i++){

      System.out.println();
      System.out.println("Time Left: " + (time - i));
      System.out.println();
      this.displayBakery();
      System.out.println();
      System.out.println("Baker actions: ");
      String actions = scanner.nextLine();

    }
  }

  public void doAction(int x,int y,Person baker){
    // 2 = cake mix
    // 3 = oven
    // 4 = counter
    // 5 = cutting station
    // 6 = delivery station
    // 7 = trash
    if(baker.cake != null && this.bakery.cakeLocations[y][x] != null){
      return;
    }
    if(baker.cake != null){
      this.bakery.placeCake(baker.giveCake(),x,y);
    } else if (this.bakery.cakeLocations[y][x] != null) {
      Cake cake = this.bakery.cakeLocations[y][x];
      int location = this.bakery.layout[y][x];
    }

    return;
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

      if (this.orders[i] != null) {

        Cake cake = this.bakery.getStoredCake(this.orders[i]);

        if (cake != null) {
          this.orders[i] = null;
          completedOrders += 1;
          indexShift += 1;
        } else {
          this.orders[i - indexShift] = this.orders[i];
          this.orders[i] = null;
        }
      }
    }
  }

  public void displayBakery() {
    // Layout:
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

    for(int y = 0; y < this.bakery.layout.length; y++){
      for(int x = 0; x < this.bakery.layout[x].length; x++){

        if(this.bakery.chefLocations[y][x] != null){

          if(this.bakery.chefLocations[y][x].cake != null){
            System.out.print("9");
          } else {
            System.out.print("8");
          }


        } else if(this.bakery.cakeLocations[y][x] != null) {

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

          if(this.bakery.layout[y][x] == 1){
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
