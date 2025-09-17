package edu.bhscs;


public class Awesome {
  //fields/properties
  String name;
  String knowledge;

  //Need to construct something awesome
  public Awesome(String name){
    System.out.println("I am the most awesome " + name);
    this.name = name;
  }
  String getName(){
    return this.name;
  }

  void learnSomething(String knowledge){
    this.knowledge = knowledge;
  }

  String getKnowledge(){
    return this.knowledge;
  }


}
