package edu.bhscs;

public class Person {
  // fields/properties
  String name;
  Job job;
  int[] location;

  Cake cake;

  public Person(String name) {
    this.name = name;
  }

  // The boolean returns whetehr or not the person moved
  public Boolean move(char direction, Bakery2 bakery) {
    int x = this.location[0];
    int y = this.location[1];

    if (direction == 'w') {
      y -= 1;
    }

    if (direction == 's') {
      y += 1;
    }

    if (direction == 'a') {
      x -= 1;
    }

    if (direction == 'd') {
      x += 1;
    }

    if (x < 0 || y < 0 || x >= bakery.width || y >= bakery.height) {
      return false;
    }
    if (bakery.chefLocations[y][x] != null) {
      return false;
    }

    if (bakery.layout[y][x] == 0 || bakery.layout[y][x] == 1) {
      bakery.bakerMoved(this.location[0], this.location[1], x, y);
      this.location[0] = x;
      this.location[1] = y;
    } else {
      return false;
    }

    return true;
  }

  public void learnJob(Job job) {
    this.job = job;
  }

  public void doJob() {
    this.job.doJob();
    System.out.println(this.name + " did their job");
  }

  public void getCake(Cake cake) {
    this.cake = cake;
    System.out.println(this.name + " got a " + this.cake.type + " cake");
  }

  public Cake giveCake() {
    Cake cakeTemp = this.cake;
    System.out.println(this.name + " gave their " + this.cake.type + " cake");
    this.cake = null;
    return cakeTemp;
  }

  public void requestCake(Bakery bakery, String type) {
    System.out.println(this.name + " requested a " + type + " cake from " + bakery.name);
    this.getCake(bakery.makeCake(type));
  }

  public void eatCakeSlice() {
    if (this.cake != null) {
      if (this.cake.isEdible) {
        this.cake.eatSlice();
        System.out.println(
            this.name
                + " ate a slice of "
                + this.cake.type
                + " cake. There are "
                + this.cake.slices
                + " slices left");
        if (this.cake.slices == 0) {
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
