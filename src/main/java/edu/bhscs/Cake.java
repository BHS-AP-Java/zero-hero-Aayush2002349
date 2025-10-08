package edu.bhscs;

public class Cake {
  // fields/properties
  String type;
  int slices;
  boolean isBaked = false;
  boolean isCut = false;
  boolean isOvercooked = false;
  boolean isEdible = false;

  int bakingTime = 3;
  int overCookingTime = 6;
  int timeCooked = 0;

  // The cake's only job is to know what its state is
  public Cake(String type) {
    this.type = type;
  }

  // Updates the cake to be baked (or overcooked if it is baked twice)
  public void bake() {
    if(!(this.isOvercooked)){
      this.timeCooked += 1;
    }
    if (this.timeCooked == this.overCookingTime) {
      this.isOvercooked = true;
      this.isEdible = false;
    } else if(this.timeCooked == this.bakingTime) {
      this.isBaked = true;
    }
  }

  // Updates the cake to be cut into slices
  public void cut(int slices) {
    if (!(this.isCut) && this.isBaked) {
      this.slices = slices;
      this.isCut = true;

      if (!(this.isOvercooked)) {
        this.isEdible = true;
      }
    }
  }

  // Updates the cake to show that a slice of the cake has been eaten
  public void eatSlice() {
    if (this.isCut && this.isBaked && !(this.isOvercooked)) {
      if (this.slices != 0) {
        this.slices -= 1;
        if (this.slices == 0) {
          this.isEdible = false;
        }
      }
    }
  }
}
