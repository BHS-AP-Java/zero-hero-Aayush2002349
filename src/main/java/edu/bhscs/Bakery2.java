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

  public void bakerMoved(int xi, int yi, int xf, int yf){
    Person tempBaker = this.chefLocations[yi][xi];
    this.chefLocations[yi][xi] = null;
    this.chefLocations[yf][xf] = tempBaker;
  }

  // Boolean returns if the chef was hired or not
  // Hires a chef and places him at one of the specified starting locations in the layout
  public Boolean hireChef(Person chef) {
    for (int y = 0; y < this.height; y++) {
      for (int x = 0; x < this.width; x++) {

        // 1 = chef starting location
        if (this.layout[y][x] == 1) {
          if (this.chefLocations[y][x] == null) {
            this.chefLocations[y][x] = chef;
            chef.location[0] = x;
            chef.location[1] = y;
            return true;
          }
        }
      }
    }
    return false;
  }

  // Boolean returns whether the cake was or was not placed
  // places a cake at a given location
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
  // Returns the cake at the given location
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

          // All of these are the 3 behaviors that need to be ticked
          // 3 = oven (it should be cooking)
          // 6 = delivery station (it should be delivered to the cake stand)
          // 7 = trash (it should be thrown away)
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

  // The boolean returns whether or not the cake was delivered
  // Delivering the cake takes it from the kitchen onto the bakery's inventory
  public Boolean deliver(int x, int y) {
    Cake cake = this.pickUpCake(x, y);
    if (cake.isEdible) {
      this.storeCake(cake);
      return true;
    }
    return false;
  }

  // The boolean returns whether or not the cake was stored
  // Stores a cake into the bakeries inventory
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
  // Gets a cake of a certain type from the inventory (if they have it)
  public Cake getStoredCake(String type) {
    for (int i = 0; i < this.storedCakes.length; i++) {
      if (this.storedCakes[i].type == type) {
        return this.storedCakes[i];
      }
    }
    return null;
  }
}
