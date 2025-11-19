package edu.bhscs;

import java.util.Scanner;

public class User {

  // Fields and properties
  private Scanner scanner = new Scanner(System.in);

  // Constructor
  public User() {}

  public String answerQuestion(String question) {
    System.out.println(question);
    String answer = this.scanner.next();
    return answer;
  }

  public double getNumber(String question) {
    System.out.println(question);
    return this.scanner.nextDouble();
  }
}
