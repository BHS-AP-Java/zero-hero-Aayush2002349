package edu.bhscs;

public class App {

  public static void main(String[] args) {

    apCSAResearch();
  }

  public static void date10_27() {
    // Trying to display a cake ontop of a table
    Person bob = new Person("Bob");
    Table table = new Table(3, 50, 15);
    Food bDay = bob.bakes(5, "Suzzie");
    bDay.draw(table);
    table.draw(bDay);
  }

  public static void apCSAResearch(){
    User input = new User("");
    int i = (int) input.getNumber("");
    while(i > 1){
      if(i % 2 == 1){
        i = 3*i+1;
      } else {
        i = i/2;
      }
      System.out.println(i);
    }

  }
}
