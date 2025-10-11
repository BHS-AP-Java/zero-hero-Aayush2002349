package edu.bhscs;

public class Restaurant {

  // Fields and properties

  // Kitchen related stuff
  int[][] layout;
  Food[][] foodLocations;
  Person[][] chefLocations;

  int cookingTime = 3;

  int width;
  int height;

  // Customer related stuff
  String name;

  Food[] storedFoods;

  int money = 0;

  Order[] orders = new Order[5];
  int rating = 10;
  Food[] menu;

  // Layout:
  // 0 = empty
  // 1 = chef starting location(considered empty)
  // 2 = base station
  // 3 = oven
  // 4 = counter
  // 5 = cutting station
  // 6 = delivery station
  // 7 = trash
  // 8 = ingredient station
  public Restaurant(
      int[][] layout, int width, int height, int maxFoodStorage, String name, Food[] menu) {
    this.name = name;
    this.width = width;
    this.height = height;
    this.foodLocations = new Food[height][width];
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
            chef.location[0] = x;
            chef.location[1] = y;
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

  // Boolean returns whether the food was or was not placed
  // places a food at a given location
  public Boolean placeFood(Food food, int x, int y) {

    // Place food just places the food (makes sure there isn't already a food there and that a food
    // can be placed at the given location)
    // All behavior of what to do when the food is placed happens during "tick"
    if (this.foodLocations[y][x] != null) {
      return false;
    }

    if (this.layout[y][x] >= 3) {
      this.foodLocations[y][x] = food;
      return true;
    }
    return false;
  }

  // Will return null if no food is there
  // Returns the food at the given location
  public Food pickUpFood(int x, int y) {
    Food foodTemp = this.foodLocations[y][x];
    this.foodLocations[y][x] = null;
    return foodTemp;
  }

  // Cuts the food at the given location
  public void cutFood(int x, int y) {
    this.foodLocations[y][x].cut(6);
  }

  // Adds an ingredient to the food at the given location
  public void addIngredient(int x, int y,String ingredient) {
    this.foodLocations[y][x].addIngredient(ingredient);
  }

  // Looks at all placed foods and figures out what to do with them
  // Additionally completes any orders that can be completed and ticks the pending orders as well
  public void tick() {

    for (int y = 0; y < this.height; y++) {
      for (int x = 0; x < this.width; x++) {

        // Makes sure there is a food there
        if (this.foodLocations[y][x] != null) {

          // All of these are the 3 behaviors that need to be ticked
          // 3 = oven (it should be cooking)
          // 6 = delivery station (it should be delivered to the food stand)
          // 7 = trash (it should be thrown away)
          if (this.layout[y][x] == 3) {

            this.foodLocations[y][x].cook();
          }
          if (this.layout[y][x] == 6) {
            this.deliver(x, y);
          }
          if (this.layout[y][x] == 7) {
            // Picking up the food without giving it to anyone is the equivalent of deleting it
            this.pickUpFood(x, y);
          }
        }
      }
    }

    // Completes orders and ticks them as well and loses rating if orders are late
    this.completeOrders();
    for (int i = 0; i < this.orders.length; i++) {
      if (this.orders[i] != null) {
        if (this.orders[i].late) {
          this.rating -= 1;
          if (this.rating < 0) {
            this.rating = 0;
          }
        }
        this.orders[i].tick();
      }
    }
  }

  // The boolean returns whether or not the food was delivered
  // Delivering the food takes it from the kitchen onto the restaurant's inventory
  public Boolean deliver(int x, int y) {
    Food food = this.pickUpFood(x, y);
    if (food.isEdible()) {
      this.storeFood(food);
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
        if (this.storedFoods[i].matches(order.food)) {
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
          if (!(this.orders[i].late)) {
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
}
