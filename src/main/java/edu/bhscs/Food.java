package edu.bhscs;

// Note: this class just serves as a template for all the other foods since they all work mostly the
// same with a few slight differences
class Food {
  // Fields and properties

  // All of these fields depend on the food and will be set in the constructor
  Boolean isCookable;
  int cookingTime;
  int overcookingTime;

  Boolean isCuttable;

  Boolean hasMultipleIngredients;
  int minIngredients;

  String foodType;

  int baseCost;

  // These fields store information about the state of the food

  int timeCooked = 0;
  Boolean isCooked = false;
  Boolean isOvercooked = false;

  Boolean isCut = false;
  int slices = -1;

  String[] ingredients = new String[0];
  String ingredient = null;

  Boolean isEaten = false;

  // There is no constructor it will be made by the class that extends this one

  // Returns whether or not the food is edible
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

    if (this.hasMultipleIngredients) {
      if (this.ingredients.length < minIngredients) {
        return false;
      }
    } else {
      if (this.ingredient == null) {
        return false;
      }
    }

    return true;
  }

  // Adds an ingredient to the ingredients or sets the ingredient (if there is only supposed to be
  // 1)
  public void addIngredient(String ingredient) {
    if(!(this.isCookable) || !(this.isCooked))
      if (this.hasMultipleIngredients) {
        String[] newIngredients = new String[this.ingredients.length + 1];
        for (int i = 0; i < this.ingredients.length; i++) {
          newIngredients[i] = this.ingredients[i];
        }
        newIngredients[newIngredients.length - 1] = ingredient;
        this.ingredients = newIngredients;
      } else {
        this.ingredient = ingredient;
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
  public void cut(int slices) {
    if (this.isCuttable) {
      if (!(this.isCut) && this.isCooked) {
        this.slices = slices;
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

    // If they do then there are 2 cases, either there are multiple ingredients (the order of the
    // ingredients does not matter) or there is only 1
    if (food.hasMultipleIngredients) {

      // If there are multiple ingredients first we check if they are the same number
      if (food.ingredients.length != this.ingredients.length) {
        return false;
      }

      // Then for each ingredient in one we see if it appears in the other, if it doesnt that means
      // the ingredients dont match
      for (int i = 0; i < food.ingredients.length; i++) {

        Boolean ingredientFound = false;

        for (int j = 0; j < this.ingredients.length; j++) {
          if (food.ingredients[i].equals(this.ingredients[j])) {
            ingredientFound = true;
          }
        }

        if (!(ingredientFound)) {
          return false;
        }
      }
    } else {

      // If there is only 1 then simply check if the ingredients are the same
      if (!(food.ingredient.equals(this.ingredient))) {
        return false;
      }
    }
    return true;
  }

  // All methods from here on out are set by the classes associated with each food

  // This gets whatever the food is called (ex: burger with cheese and lettuce or chocolate cake)
  public String getFoodTitle() {
    return null;
  }
}
