package edu.bhscs;

public class Person {
  // fields/properties
  String name;
  Job job;

  Cake cake;

  public Person(String name) {
    this.name = name;
  }

  void learnJob(Job job) {
    this.job = job;
  }

  void doJob(){
    this.job.doJob();
    System.out.println(this.name + " did their job");
  }

  void getCake(Cake cake) {
    this.cake = cake;
    System.out.println(this.name + " got a " + this.cake.type + " cake");
  }

  void eatCakeSlice() {
    if (this.cake.isEdible) {
      this.cake.eatSlice();
      System.out.println(
          this.name
              + " ate a slice of "
              + this.cake.type
              + " cake. There are "
              + this.cake.slices
              + " slices left");
    } else {
      System.out.println(this.name + " can't eat the " + this.cake.type + " cake.");
    }
  }
}
