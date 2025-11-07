package edu.bhscs;

public class Table implements Offsetable {

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
    this.adjustWidth();
  }

  // This adjusts the width of the table so that the legs can be spaced evenly, see
  // Display.getTableDisplay to understand how this works
  public void adjustWidth() {
    int spacing = (int) (this.width - 2 - (this.legs * this.leg.length())) / (this.legs - 1);
    this.width = 2 + this.legs * this.leg.length() + (this.legs - 1) * spacing;
  }

  public void draw(Offsetable other) {
    String[][] surface = Display.getSurface((int) (width), (int) (height));
    surface = Display.getTableDisplay(surface, this);
    Display.cullUnusedParts(surface);

    Display.displaySurface(surface, this.getOffset(other), 0);
  }

  public int getWidth() {
    return (int) (this.width);
  }

  public void setLeg(String leg) {
    this.leg = leg;
    this.adjustWidth();
  }

  public void setTop(String top) {
    this.top = top;
  }
}
