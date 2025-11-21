package edu.bhscs;

public class Restaurant {

  // Fields and properties

  // Kitchen related stuff
  private final int[][] layout;
  private Pickupable[][] itemLocations;
  private Person[][] chefLocations;

  private final int width;
  private final int height;

  // Customer related stuff
  private final String name;

  private Food[] storedFoods;

  private int money = 0;

  private Order[] orders = new Order[5];
  private int rating = 10;
  private final Food[] menu;

  // Layout:
  // 0 = empty
  // 1 = chef starting location(considered empty)
  // 2 = base station
  // 3 = power
  // 4 = counter
  // 5 = cutting station
  // 6 = delivery station
  // 7 = trash
  // 8 = ingredient station
  public Restaurant(
      int[][] layout,
      int width,
      int height,
      int maxFoodStorage,
      String name,
      Food[] menu,
      Pickupable[][] initalItemLocations) {
    this.name = name;
    this.width = width;
    this.height = height;
    this.itemLocations = initalItemLocations;
    this.chefLocations = new Person[height][width];
    this.layout = layout;
    this.storedFoods = new Food[maxFoodStorage];
    this.menu = menu;
  }

  public void chefMoved(int xi, int yi, int xf, int yf) {
    Person tempChef = this.chefLocations[yi][xi];
    this.chefLocations[yi][xi] = null;
    this.chefLocations[yf][xf] = tempChef;
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

            int[] location = {x,y};
            chef.setLocation(location);
            return true;
          }
        }
      }
    }
    return false;
  }

  public void hireChefs(Person[] chefs) {
    for (int i = 0; i < chefs.length; i++) {
      this.hireChef(chefs[i]);
      chefs[i].getHired(this);
    }
  }

  // Boolean returns whether the item was or was not placed
  // places a item at a given location
  public Boolean place(Pickupable item, int x, int y) {

    // Place just places the item (makes sure there isn't already an item there and that
    // an thing
    // can be placed at the given location)
    // All behavior of what to do when the thing is placed happens during "tick"
    if (this.itemLocations[y][x] != null) {
      return false;
    }

    if (this.layout[y][x] >= 2) {
      this.itemLocations[y][x] = item;
      return true;
    }
    return false;
  }

  // Will return null if no item is there
  // Returns the item at the given location
  public Pickupable pickUp(int x, int y) {
    Pickupable item = this.itemLocations[y][x];
    this.itemLocations[y][x] = null;
    return item;
  }

  public void tickLocation(int x, int y) {
    // All of these are the 3 behaviors that need to be ticked
    // 3 = power (it should be cooking)
    // 6 = delivery station (it should be delivered to the food stand)
    // 7 = trash (it should be thrown away)

    if (this.layout[y][x] == 3 && this.itemLocations[y][x] instanceof Tableware) {

      Tableware item = (Tableware) this.itemLocations[y][x];
      if (!(item.isEmpty())) {

        Edible containedFood = item.getEdible();
        if (containedFood.cookingWare.matches(item.getType()) && item.getWareType().matches("cookingWare")) {
          containedFood.cook();
        }
      }
    }
    if (this.layout[y][x] == 6 && this.itemLocations[y][x] instanceof Tableware) {

      Tableware item = (Tableware) this.pickUp(x, y);
      if (item.getWareType().matches("servingWare") && !(item.isEmpty())) {
        this.deliver(item);
      } else {
        this.place(item, x, y);
      }
    }
    if (this.layout[y][x] == 7 && this.itemLocations[y][x] instanceof Edible) {
      // Picking up the item without giving it to anyone is the equivalent of deleting it
      this.pickUp(x, y);
    }
  }

  // Looks at all placed items and figures out what to do with them
  // Additionally completes any orders that can be completed and ticks the pending orders as well
  public void tick() {

    for (int y = 0; y < this.height; y++) {
      for (int x = 0; x < this.width; x++) {
        // Makes sure there is a item there
        if (this.itemLocations[y][x] != null) {
          this.tickLocation(x, y);
        }
      }
    }

    // Completes orders and ticks them as well and loses rating if orders are late
    this.completeOrders();
    for (int i = 0; i < this.orders.length; i++) {
      if (this.orders[i] != null) {
        if (this.orders[i].isLate()) {
          this.rating -= 1;
        }
        this.orders[i].tick();
      }
    }

    if (this.rating < 0) {
      this.rating = 0;
    }
  }

  // The boolean returns whether or not the food was delivered
  // Delivering the food takes it from the kitchen onto the restaurant's inventory
  public Boolean deliver(Tableware item) {

    Edible containedFood = item.getEdible();
    if (item.getType().matches(
        containedFood.servingWare)
        && containedFood.isEdible()
        && containedFood instanceof Food) {
      this.storeFood((Food) containedFood);
      return true;
    }
    return false;
  }

  // The boolean returns whether or not the food was stored
  // Stores a food into the restaurant's inventory
  public Boolean storeFood(Food food) {

    for (int i = 0; i < this.storedFoods.length; i++) {
      if (this.storedFoods[i] == null) {
        this.storedFoods[i] = food;
        return true;
      }
    }

    return false;
  }

  // Returns null when there is no food of the given type
  // Gets a food of a certain type from the inventory (if they have it)
  public Food getFood(Order order) {
    for (int i = 0; i < this.storedFoods.length; i++) {
      if (this.storedFoods[i] != null) {
        if (this.storedFoods[i].matches(order.getFood())) {
          Food food = this.storedFoods[i];
          this.storedFoods[i] = null;
          return food;
        }
      }
    }
    return null;
  }

  // Takes an order
  public void takeOrder(Order order) {
    for (int i = 0; i < this.orders.length; i++) {
      if (this.orders[i] == null) {
        this.orders[i] = order;
        return;
      }
    }
  }

  // When called, this checks the restaurant's inventories and completes any orders
  // it will be called every tick
  public void completeOrders() {
    int indexShift = 0;
    for (int i = 0; i < this.orders.length; i++) {

      if (this.orders[i] != null) {

        Food food = this.getFood(this.orders[i]);
        // Here is where the order is completed
        if (food != null) {
          if (!(this.orders[i].isLate())) {
            this.rating += 15;
          }
          if (i == 0) {
            this.rating += 5;
          }

          this.money += this.orders[i].orderCompleted(food);

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

  public int getLocation(int x, int y){
    return this.layout[y][x];
  }

  public Person getChef(int x, int y) {
    return this.chefLocations[y][x];
  }

  public Pickupable getItem(int x, int y) {
    return this.itemLocations[y][x];
  }

  public Food[] getMenu(){
    return this.menu;
  }

  public Order[] getOrders() {
    return this.orders;
  }

  public int getRating(){
    return this.rating;
  }

  public int getWidth(){
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  public int getMoney(){
    return this.money;
  }
}
