package edu.bhscs;

public class Bakery2 {

  //Fields
  String name;

  int[][] layout;
  Cake[][] cakeLocations;
  int[][] cakeCookingTimers;

  int cookingTime = 3;

  int width;
  int height;

  //Layout:
  //0 = empty
  //1 = chef starting location(considered empty)
  //2 = cake mix
  //3 = oven
  //4 = counter
  //5 = cutting station
  //6 = delivery station
  public Bakery2(int[][] layout, int width, int height, String name){
    this.name = name;
    this.width = width;
    this.height = height;
    this.cakeLocations = new Cake[height][width];
    this.layout = layout;

    this.cakeCookingTimers = new int[height][width];
    for(int y = 0; y < this.height; y++){
      for (int x = 0; x < this.width; x++) {
        this.cakeCookingTimers[y][x] = 0;
      }
    }
  }

  //Boolean returns whether the cake was or was not placed
  public Boolean placeCake(Cake cake,int x,int y){
    if(this.cakeLocations[y][x] != null){
      return false;
    }

    if(this.layout[y][x] >= 3){
      this.cakeLocations[y][x] = cake;
      return true;
    }
    return false;
  }

  //Will return null if no cake is there
  public Cake pickUpCake(int x, int y){

    return this.cakeLocations[y][x];

  }

  public void tick(){
    for(int y = 0; y < this.height; y++){
      for (int x = 0; x < this.width; x++) {

        if(this.cakeLocations[y][x] != null){

          if (this.cakeLocations[y][x] != null) {

            // 3 = oven
            // 6 = delivery station
            if(this.layout[y][x] == 3){
              if(this.cakeLocations[y][x] != null){

              }
            }
            if(this.layout[y][x] == 6){

            }

          }
        }

      }
    }
  }


}
