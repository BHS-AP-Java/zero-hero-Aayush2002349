package edu.bhscs;

public class Ingredient extends Edible {
  // Fields and properties

  // All of these fields depend on the ingredient and will be set in the constructor
  String name;

  Boolean isCookable;
  int cookingTime;
  int overcookingTime;

  Boolean isCuttable;

  int baseCost;

  Boolean isEssential;

  // These fields store information about the state of the food

  // These commented ones also exist but are inside the edible class
  // int timeCooked = 0;
  // Boolean isCooked = false;
  // Boolean isOvercooked = false;

  // Boolean isCut = false;

  // Constructor

  public Ingredient(
      String name,
      Boolean isCookable,
      int cookingTime,
      int overcookingTime,
      Boolean isCuttable,
      int baseCost) {
    this.name = name;

    this.isCookable = isCookable;
    this.cookingTime = cookingTime;
    this.overcookingTime = overcookingTime;

    this.isCuttable = isCuttable;

    this.baseCost = baseCost;
  }

  // There are some preset ingredients
  public Ingredient(String name) {
    this.name = name;

    if (name.matches("egg")) {
      this.isCookable = false;
      // this.cookingTime;
      // this.overcookingTime;

      this.isCuttable = false;

      this.baseCost = 10;

      this.isEssential = true;
    }
    if (name.matches("flour")) {
      this.isCookable = false;
      // this.cookingTime;
      // this.overcookingTime;

      this.isCuttable = false;

      this.baseCost = 10;

      this.isEssential = true;
    }
    if (name.matches("meat")) {
      this.isCookable = true;
      this.cookingTime = 5;
      this.overcookingTime = 10;

      this.isCuttable = false;

      this.baseCost = 20;

      this.isEssential = false;
    }
    if (name.matches("cheese")) {
      this.isCookable = false;
      // this.cookingTime;
      // this.overcookingTime;

      this.isCuttable = false;

      this.baseCost = 5;

      this.isEssential = false;
    }
    if (name.matches("lettuce")) {
      this.isCookable = false;
      // this.cookingTime;
      // this.overcookingTime;

      this.isCuttable = true;

      this.baseCost = 5;

      this.isEssential = false;
    }
    if (name.matches("bun")) {
      this.isCookable = false;
      // this.cookingTime;
      // this.overcookingTime;

      this.isCuttable = true;

      this.baseCost = 5;

      this.isEssential = true;
    }
    if (name.matches("chocolate")) {
      this.isCookable = false;
      // this.cookingTime;
      // this.overcookingTime;

      this.isCuttable = true;

      this.baseCost = 10;

      this.isEssential = false;
    }
    if (name.matches("spice")) {
      this.isCookable = false;
      // this.cookingTime;
      // this.overcookingTime;

      this.isCuttable = true;

      this.baseCost = 10;

      this.isEssential = false;
    }
  }

  // Returns whether or not the ingredient is fully prepared
  @Override
  public Boolean isEdible() {

    // Checks if the food is eaten

    // Checks if its cookable and, if it is, if its not cooked or its overcooked
    if (this.isCookable && (!(this.isCooked) || this.isOvercooked)) {
      return false;
    }

    // Checks if its cuttable and, if it is, if it hasn't been cut yet
    if ((this.isCuttable && !(this.isCut))) {
      return false;
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
      this.isCut = true;
    }
  }

  // Some applications of the ingredients require you to see if 2 of them are the same. Given
  // another ingredient
  // this returns whether or not the 2 match
  public Boolean matches(Ingredient ingredient) {
    return ingredient.name.equals(this.name);
  }

  @Override
  public String getFoodTitle() {
    return this.name;
  }
}
