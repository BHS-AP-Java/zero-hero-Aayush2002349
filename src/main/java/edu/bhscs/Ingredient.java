package edu.bhscs;

public class Ingredient extends Edible implements Pickupable {
  // Fields and properties

  // All of these fields depend on the ingredient and will be set in the constructor
  // These commented ones also exist but are inside the edible class

  // Boolean isCookable;
  // int cookingTime;
  // int overcookingTime;
  // String cookingWare;

  // Boolean isCuttable;

  // String servingWare;

  // String name;
  private final int baseCost;

  private final Boolean essential;

  // These fields store information about the state of the food

  // int timeCooked = 0;
  // Boolean isCooked = false;
  // Boolean isOvercooked = false;

  // Boolean isCut = false;

  // Constructor

  // There are some preset ingredients
  public Ingredient(String name) {
    this.name = name;

    if (name.matches("egg")) {
      this.isCookable = false;

      this.isCuttable = false;

      this.baseCost = 10;

      this.essential = true;

      this.servingWare = "plate";
    }
    else if (name.matches("flour")) {
      this.isCookable = false;

      this.isCuttable = false;

      this.baseCost = 10;

      this.essential = true;

      this.servingWare = "plate";
    }
    else if (name.matches("meat")) {
      this.isCookable = true;
      this.cookingTime = 5;
      this.overcookingTime = 10;
      this.cookingWare = "pan";

      this.isCuttable = false;

      this.baseCost = 20;

      this.essential = false;

      this.servingWare = "plate";
    }
    else if (name.matches("cheese")) {
      this.isCookable = false;

      this.isCuttable = false;

      this.baseCost = 5;

      this.essential = false;

      this.servingWare = "plate";
    }
    else if (name.matches("lettuce")) {
      this.isCookable = false;

      this.isCuttable = true;

      this.baseCost = 5;

      this.essential = false;

      this.servingWare = "plate";
    }
    else if (name.matches("bun")) {
      this.isCookable = false;

      this.isCuttable = true;

      this.baseCost = 5;

      this.essential = true;

      this.servingWare = "plate";
    }
    else if (name.matches("chocolate")) {
      this.isCookable = false;

      this.isCuttable = true;

      this.baseCost = 10;

      this.essential = false;

      this.servingWare = "plate";
    }
    else if (name.matches("spice")) {
      this.isCookable = false;

      this.isCuttable = true;

      this.baseCost = 10;

      this.essential = false;

      this.servingWare = "plate";
    }

    else {
      this.isCookable = false;

      this.isCuttable = false;

      this.baseCost = 0;

      this.essential = false;

      this.servingWare = "plate";
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

  // public void cook() {}

  // Updates the state of the ingredient to be cut (if it can be cut)
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
  public String getTitle() {
    return this.name;
  }

  public int getCost(){
    return this.baseCost;
  }

  public boolean getIsEssential(){
    return this.essential;
  }
}
