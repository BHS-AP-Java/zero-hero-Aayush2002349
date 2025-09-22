package edu.bhscs;

public class Bakery {
  // Fields/properties
  Person[] chefs;
  int chefSlot = 0;
  int chefTurn = 0;
  String name;
  public Bakery(int chefCapacity,String name) {
    this.chefs = new Person[5];
    this.name = name;
    System.out.println("A new bakery named " + name + " opened");
  }

  void hireChef(Person person) {
    System.out.println(this.name + " hired " + person.name);
    if (chefSlot < chefs.length) {
      Job chef =
          () -> {
            person.cake.bake();
            person.cake.cut(6);
          };
      person.learnJob(chef);
      chefs[chefSlot] = person;
      chefSlot += 1;
    }
  }

  Cake makeCake(String type) {
    System.out.println(this.name + " started making a " + type + " cake");
    Cake cake = new Cake(type);
    chefs[chefTurn].getCake(cake);
    chefs[chefTurn].doJob();
    cake = chefs[chefTurn].giveCake();

    if(chefTurn == chefSlot - 1){
      chefTurn = 0;
    } else {
      chefTurn += 1;
    }
    System.out.println(this.name + " made a " + type + " cake");
    return cake;
  }
}

interface Job {
  void doJob();
}
