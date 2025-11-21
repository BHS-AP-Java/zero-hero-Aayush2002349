package edu.bhscs;

import java.util.Random;

public class Order {

  // Fields and properties
  private final Food food;
  private final int timeToComplete;
  private int timeElapsed = 0;
  private final int payment;
  private int tip;

  private Boolean late = false;

  private final Random random = new Random();

  // Constructors

  // This order creates a specific order
  public Order(Food food, int timeToComplete, int payment, int tip) {
    this.food = food;
    this.timeToComplete = timeToComplete;
    this.payment = payment;
    this.tip = tip;
  }

  // This order creates a random order
  public Order(Food[] menu, int tip) {
    this.tip = tip;
    this.food = menu[random.nextInt(menu.length)];
    this.payment = this.food.getBaseCost();
    this.timeToComplete = 30;
  }

  // If an order is completed then it returns the payment, returns -1 if the food isnt correct
  public int orderCompleted(Food food) {
    if (food.matches(this.food)) {
      return this.payment + this.tip;
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

  public Food getFood(){
    return this.food;
  }

  public int getTimeToComplete() {
    return this.timeToComplete;
  }

  public int getTimeElapsed(){
    return this.timeElapsed;
  }

  public boolean isLate(){
    return this.late;
  }
}
