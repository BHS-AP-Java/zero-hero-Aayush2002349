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

  // This adjusts the width of the table so that the legs can be spaced evenly, see Display.getTableDisplay to understand how this works
  public void adjustWidth(){
    int spacing = (int) (this.width - 2 - (this.legs * this.leg.length())) / (this.legs - 1);
    this.width = 2 + this.legs * this.leg.length() + (this.legs - 1) * spacing;
  }
  public void draw(int leftOffset) {
    String[][] surface = Display.getSurface((int) (width), (int) (height));
    surface = Display.getTableDisplay(surface, this);
    Display.cullUnusedParts(surface);
    Display.displaySurface(surface, leftOffset, 0);

  }

  public void setLegs(String leg) {
    this.leg = leg;
  }

  public void setTop(String top) {
    this.top = top;
  }
}
