package edu.bhscs;

public class Bakery2 {

  // Fields

  // Kitchen related stuff
  int[][] layout;
  Cake[][] cakeLocations;
  Person[][] chefLocations;
  int[][] cakeCookingTimers;

  int cookingTime = 3;

  int width;
  int height;

  // Customer related stuff
  String name;

  Cake[] storedCakes;

  // Layout:
  // 0 = empty
  // 1 = chef starting location(considered empty)
  // 2 = cake mix
  // 3 = oven
  // 4 = counter
  // 5 = cutting station
  // 6 = delivery station
  // 7 = trash
  public Bakery2(int[][] layout, int width, int height, int maxCakeStorage, String name) {
    this.name = name;
    this.width = width;
    this.height = height;
    this.cakeLocations = new Cake[height][width];
    this.chefLocations = new Person[height][width];
    this.layout = layout;
    this.storedCakes = new Cake[maxCakeStorage];

    this.cakeCookingTimers = new int[height][width];
    for (int y = 0; y < this.height; y++) {
      for (int x = 0; x < this.width; x++) {
        this.cakeCookingTimers[y][x] = 0;
      }
    }
  }

  // Boolean returns if the chef was hired or not
  public Boolean hireChef(Person chef) {
    for (int y = 0; y < this.height; y++) {
      for (int x = 0; x < this.width; x++) {

        if (this.layout[y][x] == 1) {
          if (this.chefLocations[y][x] == null) {
            this.chefLocations[y][x] = chef;
            return true;
          }
        }
      }
    }
    return false;
  }

  // Boolean returns whether the cake was or was not placed
  public Boolean placeCake(Cake cake, int x, int y) {

    // Place cake just places the cake (makes sure there isn't already a cake there and that a cake
    // can be placed at the given location)
    // All behavior of what to do when the cake is placed happens during "tick"
    if (this.cakeLocations[y][x] != null) {
      return false;
    }

    if (this.layout[y][x] >= 3) {
      this.cakeLocations[y][x] = cake;
      return true;
    }
    return false;
  }

  // Will return null if no cake is there
  public Cake pickUpCake(int x, int y) {
    Cake cakeTemp = this.cakeLocations[y][x];
    this.cakeLocations[y][x] = null;
    this.cakeCookingTimers[y][x] = 0;
    return cakeTemp;
  }

  // Cuts the cake at the given location
  public void cutCake(int x, int y) {
    this.cakeLocations[y][x].cut(6);
  }

  // Looks at all placed cakes and figures out what to do with them
  public void tick() {

    for (int y = 0; y < this.height; y++) {
      for (int x = 0; x < this.width; x++) {

        // Makes sure there is a cake there
        if (this.cakeLocations[y][x] != null) {

          // 3 = oven
          // 6 = delivery station
          // 7 = trash
          if (this.layout[y][x] == 3) {
            if (this.cakeCookingTimers[y][x] == this.cookingTime) {
              this.cakeCookingTimers[y][x] = 0;
              this.cakeLocations[y][x].bake();
            }
            this.cakeCookingTimers[y][x] += 1;
          }
          if (this.layout[y][x] == 6) {
            this.deliver(x, y);
          }
          if (this.layout[y][x] == 7) {
            // Picking up the cake without giving it to anyone is the equivalent of deleting it
            this.pickUpCake(x, y);
          }
        }
      }
    }
  }

  //The boolean returns whether or not the cake was delivered
  public Boolean deliver(int x, int y) {
    Cake cake = this.pickUpCake(x,y);
    if(cake.isEdible){
      this.storeCake(cake);
      return true;
    }
    return false;
  }

  // The boolean returns whether or not the cake was stored
  public Boolean storeCake(Cake cake) {

    for (int i = 0; i < this.storedCakes.length; i++) {
      if (this.storedCakes[i] == null) {
        this.storedCakes[i] = cake;
        return true;
      }
    }

    return false;
  }

  // Returns null when there is no cake of the given type
  public Cake getStoredCake(String type) {
    for (int i = 0; i < this.storedCakes.length; i++) {
      if (this.storedCakes[i].type == type) {
        return this.storedCakes[i];
      }
    }
    return null;
  }
}
