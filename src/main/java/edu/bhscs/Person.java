package edu.bhscs;

public class Person {
  // fields/properties
  String name;
  int[] location = new int[2];

  User user;
  Bakery2 bakery;
  Boolean isHired = false;

  Cake cake;

  // Constructor
  public Person(String name, User user) {
    this.name = name;
    this.user = user;
  }

  public void getHired(Bakery2 bakery) {
    this.bakery = bakery;
    this.isHired = true;
  }

  // This gets and does the action given by the player
  public void getAndDoAction() {

    // Gets the action
    String action = this.user.answerQuestion("Enter " + this.name + "'s action: ");

    // Seperates the action into its 2 components
    String cakeType = null;

    if (action.length() != 1) {
      cakeType = action.substring(1);
    }
    char direction = action.charAt(0);

    // Moves the baker and if the baker doesn't move then has the baker do the action at the given
    // location
    int[] actionLocation = this.move(direction, bakery);

    if (actionLocation != null) {
      this.doActionAtLocation(actionLocation, cakeType);
    }
  }

  // Given a certain position to do the action on and potentially a type (for creating a new cake)
  // does whatever the action is
  public void doActionAtLocation(int[] actionLocation, String cakeType) {
    // If both the baker and location have a cake then one of them needs to be placed down before
    // doing anything

    int x = actionLocation[0];
    int y = actionLocation[1];

    if (this.cake != null && this.bakery.cakeLocations[y][x] != null) {
      return;
    }

    // This gets the kind of thing at the given location according to the key below

    // 2 = cake mix
    // 3 = oven
    // 4 = counter
    // 5 = cutting station
    // 6 = delivery station
    // 7 = trash
    int location = this.bakery.layout[y][x];

    // If the baker is holding a cake then he just places down the cake at the given location
    if (this.cake != null) {

      if (location >= 2 && location <= 7) {
        this.bakery.placeCake(this.giveCake(), x, y);
      }

      // If the baker isnt holding a cake but the place that the baker is performing the action on
      // does then he either just picks up the cake or cuts it
    } else if (this.bakery.cakeLocations[y][x] != null) {

      if (location == 3 || location == 4) {
        this.getCake(this.bakery.pickUpCake(x, y));
      }

      if (location == 5) {
        if (this.bakery.cakeLocations[y][x].isCut) {
          this.getCake(this.bakery.pickUpCake(x, y));
        } else {
          this.bakery.cutCake(x, y);
        }
      }

      // In the case that nobody is holding a cake then we get a new cake (from the cake mix
      // station)
    } else {

      if (location == 2) {
        this.getCake(new Cake(cakeType));
      }
    }

    return;
  }

  // This moves the baker. If the baker doesn't move it instead returns the location at which it did
  // the action on
  public int[] move(char direction, Bakery2 bakery) {

    if (direction == 'p') {
      return null;
    }

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
      return null;
    }
    if (bakery.chefLocations[y][x] != null) {
      return null;
    }

    if (bakery.layout[y][x] == 0 || bakery.layout[y][x] == 1) {
      bakery.bakerMoved(this.location[0], this.location[1], x, y);
      this.location[0] = x;
      this.location[1] = y;
    } else {
      int[] returnLocation = {x, y};
      return returnLocation;
    }

    return null;
  }

  public void getCake(Cake cake) {
    this.cake = cake;
  }

  public Cake giveCake() {
    Cake cakeTemp = this.cake;
    this.cake = null;
    return cakeTemp;
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
