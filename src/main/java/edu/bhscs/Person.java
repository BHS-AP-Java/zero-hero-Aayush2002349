package edu.bhscs;

// A person can both be a customer and a chef, a chef theoretically can order a food item from the
// restaurant they work at and a customer can theoretically become a chef
public class Person {
  // fields/properties
  String name;
  int[] location = new int[2];

  User user;
  Restaurant restaurant;
  Boolean isHired = false;

  Order order;

  Food food;

  // Constructors

  // This one is to create a chef
  public Person(String name, User user) {
    this.name = name;
    this.user = user;
  }

  // This one is to create a customer
  public Person(String name) {
    this.name = name;
  }

  // All of these methods can be used by both customers and chefs
  public void getFood(Food food) {
    this.food = food;
    System.out.println(this.name + " got a " + this.food.getFoodTitle());
  }

  public Food giveFood() {
    Food foodTemp = this.food;
    this.food = null;
    return foodTemp;
  }

  public void eatFood() {
    if (this.food != null) {
      if (this.food.isEdible()) {
        this.food.eat();
        System.out.println(this.name + " ate a " + this.food.getFoodTitle());
      } else {
        System.out.println(this.name + " can't eat the " + this.food.getFoodTitle());
      }

      if (this.food.isEaten) {
        this.food = null;
      }
    } else {
      System.out.println(this.name + " doesn't have anything to eat");
    }
  }

  // All methods below are generally specific chefs
  public void getHired(Restaurant restaurant) {
    this.restaurant = restaurant;
    this.isHired = true;
  }

  // This gets and does the action given by the player
  public void getAndDoAction() {

    // Gets the action
    String action = this.user.answerQuestion("Enter " + this.name + "'s action: ");

    // Seperates the action into its 2 components
    String additionalInfo = null;

    if (action.length() != 1) {
      additionalInfo = action.substring(1);
    }
    char direction = action.charAt(0);

    // Moves the chef and if the chef doesn't move then has the chef do the action at the given
    // location
    int[] actionLocation = this.move(direction, this.restaurant);
    if(actionLocation != null){
      this.doActionAtLocation(actionLocation, additionalInfo);
    }

  }

  // Given a certain position to do the action on and potentially any additional information (like
  // what to create)
  // does whatever the action is
  public void doActionAtLocation(int[] actionLocation, String additionalInfo) {
    // If both the chef and location have a food then one of them needs to be placed down before
    // doing anything

    int x = actionLocation[0];
    int y = actionLocation[1];

    if (this.food != null && this.restaurant.foodLocations[y][x] != null) {
      return;
    }

    // This gets the kind of thing at the given location according to the key below

    // 2 = base station
    // 3 = oven
    // 4 = counter
    // 5 = cutting station
    // 6 = delivery station
    // 7 = trash
    // 8 = ingredient station
    int location = this.restaurant.layout[y][x];

    // If the chef is holding a food then he just places down the food at the given location
    if (this.food != null) {

      if (location >= 2 && location <= 8) {
        this.restaurant.placeFood(this.giveFood(), x, y);
        return;
      }

      // If the chef isnt holding a food but the place that the chef is performing the action on
      // does then he either just picks up the food or performs an action on it
    } else if (this.restaurant.foodLocations[y][x] != null) {
      //In the case there is no additional information about the action the default is to pick it up
      if (additionalInfo == null) {
        this.getFood(this.restaurant.pickUpFood(x, y));
        return;
      }
      //In the case of an oven or counter the chef simply picks it up
      if (location == 3 || location == 4) {
        this.getFood(this.restaurant.pickUpFood(x, y));
        return;
      }

      //In the case of a cutting station the chef cuts
      if (location == 5) {
        this.restaurant.cutFood(x, y);
        return;
      }

      //In the case of an ingrediant station the chef adds the ingrediant
      if (location == 8) {
        this.restaurant.addIngredient(x,y,additionalInfo);
        return;
      }

      // In the case that nobody is holding a food then we get a new food
    } else {

      if (location == 2) {
        if(additionalInfo != null){
          if (additionalInfo.equals("cake")) {
            this.getFood(new Cake());
          }
          if (additionalInfo.equals("burger")) {
            this.getFood(new Burger());
          }
        }

      }
      return;
    }
  }

  // This moves the chef. If the chef doesn't move it instead returns the location at which it did
  // the action on
  public int[] move(char direction, Restaurant restaurant) {

    if (direction == 'p') {
      return null;
    }

    int x = this.location[0];
    int y = this.location[1];

    if (direction == 'w') {
      y -= 1;
    }

    if (direction == 's') {
      y += 1;
    }

    if (direction == 'a') {
      x -= 1;
    }

    if (direction == 'd') {
      x += 1;
    }

    if (x < 0 || y < 0 || x >= restaurant.width || y >= restaurant.height) {
      return null;
    }
    if (restaurant.chefLocations[y][x] != null) {
      return null;
    }

    if (restaurant.layout[y][x] == 0 || restaurant.layout[y][x] == 1) {
      restaurant.chefMoved(this.location[0], this.location[1], x, y);
      this.location[0] = x;
      this.location[1] = y;
    } else {
      int[] returnLocation = {x, y};
      return returnLocation;
    }

    return null;
  }
}
