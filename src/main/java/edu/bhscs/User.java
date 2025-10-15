package edu.bhscs;

import java.util.Scanner;

public class User {

  // Fields and properties
  Scanner scanner = new Scanner(System.in);
  String name;

  // Constructor
  public User(String name) {
    this.name = name;
  }

  public String answerQuestion(String question) {
    System.out.println(question);
    String answer = this.scanner.next();
    return answer;
  }

  public int getNumber(){
    System.out.println("Enter number: ");
    return this.scanner.nextInt();
  }
}
