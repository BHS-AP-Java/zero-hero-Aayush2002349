package edu.bhscs;

public class Burger {
  //Fields/properties

  String[] ingredients = new String[0];
  Boolean isCooked = false;
  Boolean isBurnt = false;
  Boolean isEdible = false;

  int cookingTime = 3;
  int burningTime = 5;
  int timeCooked = 0;

  //Constructor
  public Burger(){

  }

  //Adds an ingredient to the burger
  public void addIngredient(String ingredient){
    if(this.isCooked){
      String[] newIngredients = new String[this.ingredients.length + 1];
      for (int i = 0; i < this.ingredients.length; i++) {
        newIngredients[i] = this.ingredients[i];
      }
      newIngredients[newIngredients.length - 1] = ingredient;
      this.ingredients = newIngredients;
    }

  }

  //changes the state of the burger to be cooked
  public void cook(){
    if (!(this.isBurnt)) {
      this.timeCooked += 1;
    }
    if (this.timeCooked == this.burningTime) {
      this.isBurnt = true;
      this.isEdible = false;
    } else if (this.timeCooked == this.cookingTime) {
      this.isCooked = true;
    }
  }

}
