package edu.bhscs;

public class Bakery {

  // Fields and properties

  // Kitchen related stuff
  int[][] layout;
  Cake[][] cakeLocations;
  Person[][] chefLocations;

  int cookingTime = 3;

  int width;
  int height;

  // Customer related stuff
  String name;

  Cake[] storedCakes;

  int money = 0;

  Order[] orders = new Order[5];
  int rating = 10;
  String[] menu;

  // Layout:
  // 0 = empty
  // 1 = chef starting location(considered empty)
  // 2 = cake mix
  // 3 = oven
  // 4 = counter
  // 5 = cutting station
  // 6 = delivery station
  // 7 = trash
  public Bakery(int[][] layout, int width, int height, int maxCakeStorage, String name, String[] menu) {
    this.name = name;
    this.width = width;
    this.height = height;
    this.cakeLocations = new Cake[height][width];
    this.chefLocations = new Person[height][width];
    this.layout = layout;
    this.storedCakes = new Cake[maxCakeStorage];
    this.menu = menu;
  }

  public void bakerMoved(int xi, int yi, int xf, int yf) {
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

  public void hireChefs(Person[] bakers) {
    for (int i = 0; i < bakers.length; i++) {
      this.hireChef(bakers[i]);
      bakers[i].getHired(this);
    }
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
    return cakeTemp;
  }

  // Cuts the cake at the given location
  public void cutCake(int x, int y) {
    this.cakeLocations[y][x].cut(6);
  }

  // Looks at all placed cakes and figures out what to do with them
  //Additionally completes any orders that can be completed and ticks the pending orders as well
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

            this.cakeLocations[y][x].bake();
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

    //Completes orders and ticks them as well and loses rating if orders are late
    this.completeOrders();
    for(int i = 0; i < this.orders.length; i++){
      if(this.orders[i] != null){
        if(this.orders[i].late){
          this.rating -= 1;
          if(this.rating < 0){
            this.rating = 0;
          }
        }
        this.orders[i].tick();
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
  public Cake getCake(Order order) {
    for (int i = 0; i < this.storedCakes.length; i++) {
      if (this.storedCakes[i] != null) {
        if (this.storedCakes[i].type.equals(order.type)) {
          Cake cake = this.storedCakes[i];
          this.storedCakes[i] = null;
          return cake;
        }
      }
    }
    return null;
  }

  //Takes an order
  public void takeOrder(Order order){
    for (int i = 0; i < this.orders.length; i++) {
      if (this.orders[i] == null) {
        this.orders[i] = order;
        return;
      }
    }
  }

  // When called, this checks the bakery's inventories and completes any orders
  // it will be called every tick
  public void completeOrders() {
    int indexShift = 0;
    for (int i = 0; i < this.orders.length; i++) {

      if (this.orders[i] != null) {

        Cake cake = this.getCake(this.orders[i]);
        // Here is where the order is completed
        if (cake != null) {
          if(!(this.orders[i].late)){
            this.rating += 15;
          }
          if(i == 0){
            this.rating += 5;
          }

          this.money += this.orders[i].orderCompleted(cake);

          this.orders[i] = null;

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
