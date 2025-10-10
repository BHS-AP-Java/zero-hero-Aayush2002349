package edu.bhscs;

import java.util.Random;

public class Order {

  // Fields and properties
  String type;
  int timeToComplete;
  int timeElapsed = 0;
  int payment;
  int tip;

  Boolean late = false;

  Random random = new Random();

  // Constructors

  // This order creates a specific order
  public Order(String type, int timeToComplete, int payment, int tip) {
    this.type = type;
    this.timeToComplete = timeToComplete;
    this.payment = payment;
  }

  // This order creates a random order
  public Order(String[] menu, int tip) {
    this.payment = 30;
    this.tip = tip;
    this.type = menu[random.nextInt(menu.length)];
    this.timeToComplete = 30;
  }

  // If an order is completed then it returns the payment, returns -1 if the cake isnt correct
  public int orderCompleted(Cake cake) {
    if (cake.type.equals(type)) {
      return payment + tip;
    }
    return -1;
  }

  public void tick() {
    this.timeElapsed += 1;
    if (this.timeElapsed >= this.timeToComplete) {
      this.tip -= 1;
      this.late = true;
    }
    if (this.tip < 0) {
      this.tip = 0;
    }
  }
}
