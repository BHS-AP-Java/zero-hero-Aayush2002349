package edu.bhscs;

import java.util.Scanner;

public class User {
  Scanner scanner = new Scanner(System.in);
  String name;

  public User(String name) {
    this.name = name;
  }

  public String answerQuestion(String question) {
    String answer = this.scanner.next();
    System.out.println("Your answer was: " + answer);
    return answer;
  }
}
