package edu.bhscs;

class Food extends Edible {
  // Fields and properties

  // All of these fields depend on the food and will be set in the constructor
  Boolean isCookable;
  int cookingTime;
  int overcookingTime;

  Boolean isCuttable;

  String foodType;

  Ingredient[] requiredIngredients;

  // These fields store information about the state of the food

  // These commented ones also exist but are inside the edible class
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

  public Food(String foodType) {
    this.foodType = foodType;

    if (foodType.matches("cake")) {
      this.isCookable = true;
      this.cookingTime = 5;
      this.overcookingTime = 7;

      this.isCuttable = true;

      this.requiredIngredients = new Ingredient[2];
      this.requiredIngredients[0] = new Ingredient("egg");
      this.requiredIngredients[1] = new Ingredient("flour");
    }
    if (foodType.matches("burger")) {
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

      currentIngredients = newIngredients;

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
  @Override
  public void cook() {
    if (this.isCookable) {
      // Stop time cooked from becoming too high because display uses the time cooked as part of its
      // display process
      if (!(this.isOvercooked)) {
        this.timeCooked += 1;
      }
      if (this.timeCooked == this.overcookingTime) {
        this.isOvercooked = true;
      } else if (this.timeCooked == this.cookingTime) {
        this.isCooked = true;
      }
    }
  }

  // Updates the state of the food to be cut (if it can be cut)
  @Override
  public void cut() {
    if (this.isCuttable) {
      if (!(this.isCut) && this.isCooked) {
        this.slices = 6;
        this.isCut = true;
      }
    }
  }

  // Some applications of the foods require you to see if 2 foods are the same. Given another food
  // this returns whether or not the 2 match
  public Boolean matches(Food food) {
    // First checks if the food's types even match
    if (!(food.foodType.equals(this.foodType))) {
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
        System.out.println("failed match essential test");
        return false;
      }
    }
    if (!(this.matchIngredients(food.specialtyIngredients, this.specialtyIngredients))) {
      System.out.println("failed match specialty test");
      return false;
    }

    System.out.println("success");

    return true;
  }

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
  @Override
  public String getFoodTitle() {

    if ((!(this.matchIngredients(this.essentialIngredients, this.requiredIngredients))
            || this.specialtyIngredients.length == 0)
        && !(this.isMenu)) {
      return this.foodType;
    }

    if (this.specialtyIngredients.length == 1) {
      return this.specialtyIngredients[0].name + " " + this.foodType;
    }

    if (this.specialtyIngredients.length == 2) {
      return this.foodType
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
    return this.foodType + " with " + ingredientNames;
  }

  // draws the cake
  public void draw(double width, double height, double depth) {
    Display.displayFood(this, width, height, depth);
  }
}
