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

  void doJob() {
    this.job.doJob();
    System.out.println(this.name + " did their job");
  }

  void getCake(Cake cake) {
    this.cake = cake;
    System.out.println(this.name + " got a " + this.cake.type + " cake");
  }

  Cake giveCake() {
    Cake cakeTemp = this.cake;
    System.out.println(this.name + " gave their " + this.cake.type + " cake");
    this.cake = null;
    return cakeTemp;
  }

  void requestCake(Bakery bakery, String type){
    System.out.println(this.name + " requested a " + type + " cake from " + bakery.name);
    this.getCake(bakery.makeCake(type));

  }

  void eatCakeSlice() {
    if(this.cake != null){
      if (this.cake.isEdible) {
        this.cake.eatSlice();
        System.out.println(this.name + " ate a slice of " + this.cake.type + " cake. There are " + this.cake.slices + " slices left");
        if(this.cake.slices == 0){
          this.cake = null;
        }
      } else {
        System.out.println(this.name + " can't eat the " + this.cake.type + " cake.");
      }
    } else {
      System.out.println(this.name + " doesn't have a cake");
    }
  }
}
