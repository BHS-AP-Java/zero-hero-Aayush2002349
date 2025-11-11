package edu.bhscs;

// I don't like how I had to do this
// The problem with using an interface here is that all fields are final, however many fields that
// exist in both the ingredient class and the food class are not final
// For lots of applications of foods and ingreidents their distinction does not matter
// The edibles class serves only to have a type that represents both foods and ingredients
public class Edible {

  // All of these will be set in the constructor
  Boolean isCookable;
  int cookingTime;
  int overcookingTime;
  Boolean isCuttable;
  String name;

  // These will be updated throughout
  int timeCooked = 0;
  Boolean isCooked = false;
  Boolean isOvercooked = false;

  Boolean isCut = false;
  int slices = 0;

  public void cut() {}

  public void cut(int slices) {
    if (this.isCuttable) {
      if (!(this.isCut) && (this.isCooked || !(this.isCookable))) {
        this.slices = slices;
        this.isCut = true;
      }
    }
  }

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

  public Boolean isEdible() {
    return true;
  }
}
