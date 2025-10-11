package edu.bhscs;

// Check Food class for fields and properties
public class Cake extends Food {

  // fields and propeties (most are found in the food class)

  // Constructor
  public Cake() {
    this.isCookable = true;
    this.cookingTime = 15;
    this.overcookingTime = 20;

    this.isCuttable = true;

    this.hasMultipleIngredients = false;

    this.foodType = "cake";

    this.baseCost = 30;
  }

  public String getFoodTitle() {
    if (this.ingredient == null) {
      return "cake mix";
    }
    return this.ingredient + " cake";
  }
}
