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

  Pickupable item;

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
  public void get(Pickupable item) {
    if (item != null) {
      this.item = item;
      System.out.println(this.name + " got a " + this.item.getTitle());
    }
  }

  public Pickupable give() {
    Pickupable item = this.item;
    this.item = null;
    return item;
  }

  public void eatFood() {
    Food food;
    if ((this.item instanceof Food)) {
      food = (Food) this.item;
    } else {
      System.out.println(this.name + " can't eat the " + this.item.getTitle());
      return;
    }
    if (food != null) {
      if (food.isEdible()) {
        food.eat();
        System.out.println(this.name + " ate a " + food.getTitle());
      } else {
        System.out.println(this.name + " can't eat the " + food.getTitle());
      }

      if (food.isEaten) {
        this.item = null;
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

  // Combine items essentially takes 2 items and combines them (one of those items may be null)
  public Pickupable[] combineItems(Pickupable item1, Pickupable item2) {
    // The way combining works is it either swaps the 2 locations if one of the items is null or
    // combines a food and ingreident and puts the combined thing as item2.
    // Additionally combining will also happen with things that are on tableware and the tableware
    // will always come as item 2
    // If no combining can happen then the items are returned as is

    // If either 1 of them is null then swapping their locations is the equivalent of picking up or
    // placing them
    if (item1 == null || item2 == null) {
      Pickupable[] items = {item2, item1};
      return items;
    }

    // This handles if one of them is a food and the other is an ingreident
    Food food;
    Ingredient ingredient;

    if (item1 instanceof Ingredient && item2 instanceof Food) {

      food = (Food) item2;
      ingredient = (Ingredient) item1;
      food.addIngredient(ingredient);

      Pickupable[] items = {null, food};
      return items;
    }
    if (item2 instanceof Ingredient && item1 instanceof Food) {

      food = (Food) item1;
      ingredient = (Ingredient) item2;
      food.addIngredient(ingredient);

      Pickupable[] items = {null, food};
      return items;
    }

    // This handles if there is tableware mixed in as well
    Tableware ware1;
    Tableware ware2;

    if (item1 instanceof Tableware && item2 instanceof Tableware) {
      ware1 = (Tableware) item1;
      ware2 = (Tableware) item2;
      Edible[] foods =
          (Edible[]) this.combineItems((Pickupable) ware1.get(), (Pickupable) ware2.get());
      ware1.set(foods[0]);
      ware2.set(foods[1]);
      Pickupable[] items = {(Pickupable) ware1, (Pickupable) ware2};
      return items;
    }

    // This handles if only one of them is tableware
    if (item1 instanceof Tableware) {
      ware1 = (Tableware) item1;
      Edible[] foods = (Edible[]) this.combineItems(item2, (Pickupable) ware1.get());

      ware1.set(foods[1]);
      Pickupable[] items = {(Pickupable) foods[0], (Pickupable) ware1};
      return items;
    }

    if (item1 instanceof Tableware) {
      ware2 = (Tableware) item2;
      Edible[] foods = (Edible[]) this.combineItems(item2, (Pickupable) ware2.get());

      ware2.set(foods[1]);
      Pickupable[] items = {(Pickupable) foods[0], (Pickupable) ware2};
      return items;
    }

    // Finally if no combining happened we return as is
    Pickupable[] items = {item1, item2};
    return items;
  }

  // Given a certain position to do the action on and potentially any additional information (like
  // what to create)
  // does whatever the action is

  public void doActionAtLocation(int[] actionLocation, String additionalPlayerAction) {

    int x = actionLocation[0];
    int y = actionLocation[1];
    Pickupable bakerItem = this.give();
    Pickupable restaurantItem = this.restaurant.pickUp(x, y);

    // The baker item and the restaurant item are combined according to this.combine

    // This gets the kind of thing at the given location according to the key below

    // 2 = base station
    // 3 = oven
    // 4 = counter
    // 5 = cutting station
    // 6 = delivery station
    // 7 = trash
    // 8 = ingredient station
    int location = this.restaurant.layout[y][x];

    if (additionalPlayerAction != null) {
      // Cutting

      if (location == 5
          && additionalPlayerAction.matches("x")
          && bakerItem == null
          && restaurantItem instanceof Edible) {

        this.restaurant.place(restaurantItem, x, y);
        this.restaurant.cut(x, y);

        return;
      }

      // Getting food/ingredients
      if (location == 2 && bakerItem == null && restaurantItem == null) {
        this.get(new Food(additionalPlayerAction));
        return;
      }
      if (location == 8 && bakerItem == null && restaurantItem == null) {
        this.get(new Ingredient(additionalPlayerAction));
        return;
      }
    }

    // If neither of those were the action then combining the 2 items from the restaurant and baker
    // is the action
    Pickupable[] newItems = this.combineItems(bakerItem, restaurantItem);
    this.get(newItems[0]);
    this.restaurant.place(newItems[1], x, y);
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

  // This method isn't used anywhere in the game itself, it isn't useful for a baker to just
  // suddenly create a cake
  // Instead, the bakers perform actions on cakes/burgers/other foods that are inside of a bakery
  // (see doActionAtLocation)
  public Food bakes(int age, String name) {
    Food cake = new Food("cake");
    cake.additionalInfo = name;
    cake.additionalInt = age;
    return cake;
  }
}
