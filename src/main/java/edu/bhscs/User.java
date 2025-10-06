package edu.bhscs;

import java.util.Scanner;

public class User {
  Scanner scanner = new Scanner(System.in);
  String name;

  public User(String name) {
    this.name = name;
  }

  public String answerQuestion(String question) {
    System.out.println(question);
    String answer = this.scanner.next();
    return answer;
  }

  public String answerYN(String question){
    String answer = "";

    System.out.println(question);
    while(answer != "yes" && answer != "no"){
      answer = scanner.nextLine();
    }

    return answer;
  }
}
