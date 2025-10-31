package edu.bhscs;

public class App {

  public static void main(String[] args) {

    date10_27();
  }
  public static void date10_27() {
    // Trying to display a cake ontop of a table
    Person bob = new Person("Bob");
    Table table = new Table(3, 50, 15);
    Food bDay = bob.bakes(5, "Suzzie");
    bDay.draw(table);
  }
}
