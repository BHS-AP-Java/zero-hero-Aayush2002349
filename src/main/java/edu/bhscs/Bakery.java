package edu.bhscs;

public class Bakery {

  public Bakery() {}

  void trainChef(Person person) {
    Job chef = () -> {
      person.cake.bake();
      person.cake.cut(6);
    };
    person.learnJob(chef);
  }


}

interface Job{
  void doJob();
}
