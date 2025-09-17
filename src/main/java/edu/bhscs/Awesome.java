package edu.bhscs;


public class Awesome {
  //fields/properties
  String name;


  //Need to construct something awesome
  public Awesome(String name){
    System.out.println("I am the most awesome " + name);
    this.name = name;
  }
  String getName(){
    return this.name;
  }

}
