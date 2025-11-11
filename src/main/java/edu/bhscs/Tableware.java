package edu.bhscs;

public class Tableware implements Pickupable {
  Edible edible;
  String wareType;
  String type;
  Boolean isEmpty = true;

  public void set(Edible edible) {
    this.edible = edible;
    this.isEmpty = false;
  }

  public Edible get() {
    Edible edible = this.edible;
    this.edible = null;
    this.isEmpty = false;
    return edible;
  }

  public String getTitle() {
    return this.type;
  }
}
