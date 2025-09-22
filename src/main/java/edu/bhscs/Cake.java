package edu.bhscs;

public class Cake {
  // fields/properties
  String type;
  int slices;
  boolean isBaked = false;
  boolean isCut = false;
  boolean isOvercooked = false;
  boolean isEdible = false;

  public Cake(String type) {
    this.type = type;
  }

  void bake() {
    if (this.isBaked) {
      this.isOvercooked = true;
      this.isEdible = false;
    } else {
      this.isBaked = true;
    }
  }

  void cut(int slices) {
    if (!(this.isCut) && this.isBaked) {
      this.slices = slices;
      this.isCut = true;

      if (!(this.isOvercooked)) {
        this.isEdible = true;
      }
    }
  }

  void eatSlice() {
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
