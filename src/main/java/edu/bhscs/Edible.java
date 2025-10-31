package edu.bhscs;

// I don't like how I had to do this
// The problem with using an interface here is that all fields are final, however many fields that exist in both the ingredient class and the food class are not final
// For lots of applications of foods and ingreidents their distinction does not matter
// The edibles class serves only to have a type that represents both foods and ingredients
public class Edible {

  int timeCooked = 0;
  Boolean isCooked = false;
  Boolean isOvercooked = false;

  Boolean isCut = false;

  Boolean isCuttable;

  public String getFoodTitle() {
    return null;
  }

  public void cut() {}

  public void cook() {}

  public Boolean isEdible() {
    return true;
  }
}
