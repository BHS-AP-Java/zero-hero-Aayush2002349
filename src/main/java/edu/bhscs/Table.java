package edu.bhscs;

public class Table {

  // fields and properties
  double width;
  double height;
  int legs;

  // These are some default values, but they can be changed
  String leg = "|_|";
  String top = "=----=";

  // Constructor
  public Table(int legs, double width, double height) {
    this.legs = legs;
    this.width = width;
    this.height = height;
  }

  public void draw() {
    String[][] surface = Display.getSurface((int) (width), (int) (height));
    surface = Display.getTableDisplay(surface, this);
    Display.cullUnusedParts(surface);
    Display.displaySurface(surface,0,0);
  }

  public void setLegs(String leg) {
    this.leg = leg;
  }

  public void setTop(String top) {
    this.top = top;
  }
}
