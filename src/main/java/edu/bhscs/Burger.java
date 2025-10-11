package edu.bhscs;

// Check Food class for fields and properties
public class Burger extends Food {

  // fields and propeties (most are found in the food class)

  // Constructor
  public Burger() {
    this.isCookable = true;
    this.cookingTime = 3;
    this.overcookingTime = 6;

    this.isCuttable = false;

    this.hasMultipleIngredients = true;
    this.minIngredients = 1;

    this.foodType = "burger";

    this.baseCost = 15;
  }

  public String getFoodTitle() {
    if (this.ingredients.length == 0) {
      return "burger";
    }
    String ingredientNames = "";
    for (int i = 0; i < this.ingredients.length - 1; i++) {
      ingredientNames += this.ingredients[i] + ", ";
    }

    ingredientNames += "and " + this.ingredients[this.ingredients.length - 1];
    return "burger with " + ingredientNames;
  }
}
