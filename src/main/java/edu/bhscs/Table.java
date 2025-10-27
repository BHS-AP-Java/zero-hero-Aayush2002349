package edu.bhscs;

public class Table {

  //fields and properties
  int width;
  int height;
  int depth;

  //Constructor
  public Table(int width,int height,int depth){
    this.width = width;
    this.height = height;
    this.depth = depth;
  }

  public String[][] draw(String[][] surface){
    return Display.getTableDisplay(surface,this);
  }

}
