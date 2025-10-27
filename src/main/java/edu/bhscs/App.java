package edu.bhscs;

public class App {
  public static void main(String[] args){
    date10_27();
  }

  public static void date10_27(){
    //Trying to display a cake ontop of a table
    Person Bob = new Person("Bob");
    Table table = new Table(25,10, 25);
    Food bDay = Bob.bakes(5, "Suzzie");
    bDay.draw(20,10,20,table);
  }
}
