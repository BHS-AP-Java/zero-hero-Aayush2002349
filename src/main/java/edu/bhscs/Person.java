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

  Edible edible;

  // Constructors

  // This one is to create a chef
  public Person(String name, User user) {
    this.name = name;
    this.user = user;
  }

  // This one is to create a customer (or a chef that isn't controlled by a player)
  public Person(String name) {
    this.name = name;
  }

  // All of these methods can be used by both customers and chefs
  public void getEdible(Edible edible) {
    this.edible = edible;
    System.out.println(this.name + " got a " + this.edible.getFoodTitle());
  }

  public Edible giveEdible() {
    Edible edibleTemp = this.edible;
    this.edible = null;
    return edibleTemp;
  }

  public void eatFood() {
    Food food;
    if ((this.edible instanceof Food)) {
      food = (Food) this.edible;
    } else {
      System.out.println(this.name + " can't eat the " + this.edible.getFoodTitle());
      return;
    }
    if (food != null) {
      if (food.isEdible()) {
        food.eat();
        System.out.println(this.name + " ate a " + food.getFoodTitle());
      } else {
        System.out.println(this.name + " can't eat the " + food.getFoodTitle());
      }

      if (food.isEaten) {
        this.edible = null;
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
    if (actionLocation != null) {
      this.doActionAtLocation(actionLocation, additionalInfo);
    }
  }

  // Given a certain position to do the action on and potentially any additional information (like
  // what to create)
  // does whatever the action is
  public void doActionAtLocation(int[] actionLocation, String additionalInfo) {

    int x = actionLocation[0];
    int y = actionLocation[1];

    // If both the chef and location have an edible then either they are traded or an ingredient is
    // added to the food
    if (this.edible != null && this.restaurant.edibleLocations[y][x] != null) {
      Edible bakerEdible = this.giveEdible();
      Edible restaurantEdible = this.restaurant.pickUpEdible(x, y);

      // If the if statement is true that means that both are ingredients or foods meaning they
      // cannot be combined
      if ((bakerEdible instanceof Ingredient && restaurantEdible instanceof Ingredient)
          || (bakerEdible instanceof Food && restaurantEdible instanceof Food)) {

        this.getEdible(restaurantEdible);
        this.restaurant.placeEdible(bakerEdible, x, y);
        return;
      }

      // Otherwise the ingredient is added to the food and the food is placed in the kitchen
      Food food;
      Ingredient ingredient;
      if ((bakerEdible instanceof Food)) {
        food = (Food) bakerEdible;
        ingredient = (Ingredient) restaurantEdible;
      } else {
        food = (Food) restaurantEdible;
        ingredient = (Ingredient) bakerEdible;
      }

      food.addIngredient(ingredient);
      this.restaurant.placeEdible(food, x, y);
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

    // If the chef is holding an edible then he just places down the edible at the given location
    if (this.edible != null) {

      if (location >= 2 && location <= 8) {
        this.restaurant.placeEdible(this.giveEdible(), x, y);
        return;
      }

      // If the chef isnt holding an edible but the place that the chef is performing the action on
      // does then he either just picks up the edible or performs an action on it
    } else if (this.restaurant.edibleLocations[y][x] != null) {
      // In the case there is no additional information about the action the default is to pick it
      // up
      if (additionalInfo == null) {
        this.getEdible(this.restaurant.pickUpEdible(x, y));
        return;
      }
      // In the case of an oven or counter the chef simply picks it up
      if (location == 3 || location == 4) {
        this.getEdible(this.restaurant.pickUpEdible(x, y));
        return;
      }

      // In the case of a cutting station the chef cuts
      if (location == 5) {
        this.restaurant.cutEdible(x, y);
        return;
      }

      // In the case that nobody is holding a edible then we get a new edible
    } else {
      if (additionalInfo == null) {
        return;
      }
      if (location == 2) {
        this.getEdible(new Food(additionalInfo));
      }
      // In the case of an ingrediant station the chef gets an ingredient
      if (location == 8) {
        this.getEdible(new Ingredient(additionalInfo));
        return;
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

  //This method isn't used anywhere in the game itself, it isn't useful for a baker to just suddenly create a cake
  //Instead, the bakers perform actions on cakes/burgers/other foods that are inside of a bakery (see doActionAtLocation)
  public Food bakes(int age, String name){
    Food cake = new Food("cake");
    cake.additionalInfo = name;
    cake.additionalInt = age;
    return cake;
  }
}
