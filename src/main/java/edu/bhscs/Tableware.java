package edu.bhscs;

public class Tableware implements Pickupable {
  Edible edible;
  // Can be servingWare or cookingWare
  String wareType;
  String type;
  Boolean isEmpty = true;

  public Tableware(String ware, String wareType) {

    this.type = ware;
    this.wareType = wareType;
  }

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
