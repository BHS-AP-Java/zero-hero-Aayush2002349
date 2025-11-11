package edu.bhscs;

class Food extends Edible implements Offsetable, Pickupable {
  // Fields and properties

  // All of these fields depend on the food and will be set in the constructor
  // These commented ones also exist but are inside the edible class

  // Boolean isCookable;
  // int cookingTime;
  // int overcookingTime;

  // Boolean isCuttable;

  // String name;

  Ingredient[] requiredIngredients;

  // These fields store information about the state of the food

  // int timeCooked = 0;
  // Boolean isCooked = false;
  // Boolean isOvercooked = false;

  // Boolean isCut = false;

  int slices = -1;

  Ingredient[] essentialIngredients = new Ingredient[0];
  Ingredient[] specialtyIngredients = new Ingredient[0];

  Boolean isEaten = false;

  // A food may also be used to represent the menu
  Boolean isMenu = false;

  // This piece of information can be different for every food, it just stores something about the
  // state of the food
  String additionalInfo = null;
  int additionalInt;

  public Food(String name) {
    this.name = name;

    if (name.matches("cake")) {
      this.isCookable = true;
      this.cookingTime = 5;
      this.overcookingTime = 7;

      this.isCuttable = true;

      this.requiredIngredients = new Ingredient[2];
      this.requiredIngredients[0] = new Ingredient("egg");
      this.requiredIngredients[1] = new Ingredient("flour");
    }
    if (name.matches("burger")) {
      this.isCookable = false;
      // this.cookingTime;
      // this.overcookingTime;

      this.isCuttable = false;

      this.requiredIngredients = new Ingredient[1];
      this.requiredIngredients[0] = new Ingredient("bun");
    }
  }

  public int getBaseCost() {
    int cost = 0;

    for (int i = 0; i < this.essentialIngredients.length; i++) {
      cost += this.essentialIngredients[i].baseCost;
    }
    for (int i = 0; i < this.specialtyIngredients.length; i++) {
      cost += this.specialtyIngredients[i].baseCost;
    }

    return cost;
  }

  // Returns whether or not the food is edible
  @Override
  public Boolean isEdible() {

    // Checks if the food is eaten
    if (this.isEaten) {
      return false;
    }

    // Checks if its cookable and, if it is, if its not cooked or its overcooked
    if (this.isCookable && (!(this.isCooked) || this.isOvercooked)) {
      return false;
    }

    // Checks if its cuttable and, if it is, if it hasn't been cut yet
    if ((this.isCuttable && !(this.isCut))) {
      return false;
    }

    // Make sure that all the required ingredients are present
    if (!(this.matchIngredients(essentialIngredients, requiredIngredients))) {
      return false;
    }

    // Finally make sure that all ingredients in the food are also edible
    for (int i = 0; i < this.essentialIngredients.length; i++) {
      if (!(this.essentialIngredients[i].isEdible())) {
        return false;
      }
    }
    for (int i = 0; i < this.specialtyIngredients.length; i++) {
      if (!(this.specialtyIngredients[i].isEdible())) {
        return false;
      }
    }

    return true;
  }

  // Adds an ingredient to the ingredients
  public void addIngredient(Ingredient ingredient) {
    if (!(this.isCookable) || !(this.isCooked)) {

      Ingredient[] currentIngredients;

      if (ingredient.isEssential) {
        currentIngredients = this.essentialIngredients;
      } else {
        currentIngredients = this.specialtyIngredients;
      }

      Ingredient[] newIngredients = new Ingredient[currentIngredients.length + 1];
      for (int i = 0; i < currentIngredients.length; i++) {
        newIngredients[i] = currentIngredients[i];
      }
      newIngredients[newIngredients.length - 1] = ingredient;

      if (ingredient.isEssential) {
        this.essentialIngredients = newIngredients;
      } else {
        this.specialtyIngredients = newIngredients;
      }
    }
  }

  // The boolean returns whether or not the state was updated to be eaten
  public Boolean eat() {

    // First we make sure it can actually be eaten
    if (!(this.isEdible())) {
      return false;
    }

    // If it can then if it can be cut into slices then update state to have a slice be eaten
    // Otherwise the whole food is consumed
    if (this.isCuttable) {
      this.slices -= 1;
      if (this.slices == 0) {
        this.isEaten = true;
      }
    } else {
      this.isEaten = true;
    }

    return true;
  }

  // Ticks the state of cooking (if it can cook)
  // public void cook() {}

  // Updates the state of the food to be cut (if it can be cut)
  @Override
  public void cut() {
    this.cut(6);
  }

  // Some applications of the foods require you to see if 2 foods are the same. Given another food
  // this returns whether or not the 2 match
  public Boolean matches(Food food) {
    // First checks if the food's types even match
    if (!(food.name.equals(this.name))) {
      return false;
    }

    // If they do then the specialty and essential ingredients must be matched (in the case that its
    // a menu item we are matching with then instead we check the required ingredients)
    if (this.isMenu) {

      if (!(this.matchIngredients(food.essentialIngredients, this.requiredIngredients))) {
        return false;
      }

    } else if (food.isMenu) {

      if (!(this.matchIngredients(food.requiredIngredients, this.essentialIngredients))) {
        return false;
      }

    } else {

      if (!(this.matchIngredients(food.essentialIngredients, this.essentialIngredients))) {
        return false;
      }
    }

    // If the specialty ingrients match
    if (!(this.matchIngredients(food.specialtyIngredients, this.specialtyIngredients))) {
      return false;
    }

    return true;
  }

  // Helper method for matches
  public Boolean matchIngredients(Ingredient[] set1, Ingredient[] set2) {

    if (set1.length != set2.length) {
      return false;
    }

    // Then for each ingredient in one we see if it appears in the other, if it doesnt that means
    // the ingredients dont match
    for (int i = 0; i < set1.length; i++) {

      Boolean ingredientFound = false;

      for (int j = 0; j < set2.length; j++) {
        if (set2[i].matches(set1[j])) {
          ingredientFound = true;
        }
      }

      if (!(ingredientFound)) {
        return false;
      }
    }

    return true;
  }

  // This gets whatever the food is called (ex: burger with cheese and lettuce or chocolate cake or
  // burger with cheese, lettuce, and meat)

  public String getTitle() {

    // Only the specialty ingredients matter for getting the title of a food
    if ((this.specialtyIngredients.length == 0)) {
      return this.name;
    }

    if (this.specialtyIngredients.length == 1) {
      return this.specialtyIngredients[0].name + " " + this.name;
    }

    if (this.specialtyIngredients.length == 2) {
      return this.name
          + " with "
          + this.specialtyIngredients[0].name
          + " and "
          + this.specialtyIngredients[1].name;
    }

    String ingredientNames = "";
    for (int i = 0; i < this.specialtyIngredients.length - 1; i++) {
      ingredientNames += this.specialtyIngredients[i].name + ", ";
    }

    ingredientNames +=
        "and " + this.specialtyIngredients[this.specialtyIngredients.length - 1].name;
    return this.name + " with " + ingredientNames;
  }

  // draws the food
  public void draw(double width, double height, double depth) {
    String[][] surface = Display.getSurface(75, 75);
    Display.getFoodDisplay(surface, this, width, height, depth);
    Display.displaySurface(surface, 0, 0);
    if (this.name.matches("cake")) {
      System.out.println(
          "This cake is for "
              + this.additionalInfo
              + " who is "
              + this.additionalInt
              + " years old");
    }
  }

  // draws the food ontop of a table
  public int getWidth() {
    String[][] surface = Display.getSurface(50, 50);
    Display.getFoodDisplay(surface, this, 20, 10, 20);
    surface = Display.cullUnusedParts(surface);
    return surface[0].length;
  }

  public void draw(Offsetable other) {

    // gets the food display
    String[][] surface = Display.getSurface(50, 50);
    Display.getFoodDisplay(surface, this, 20, 10, 20);
    surface = Display.cullUnusedParts(surface);

    Display.displaySurface(surface, this.getOffset(other), 0);

    if (this.name.matches("cake")) {
      System.out.println(
          "This cake is for "
              + this.additionalInfo
              + " who is "
              + this.additionalInt
              + " years old");
    }
  }
}
