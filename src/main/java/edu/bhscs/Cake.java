package edu.bhscs;

public class Cake {
  // fields/properties
  String type;
  int slices;
  boolean isBaked = false;
  boolean isCut = false;
  boolean isOverbaked = false;


  public Cake(String type) {
    this.type = type;
    System.out.println("The " + this.type + " cake was mixed");
  }

  void bake(){
    if(this.isBaked){
      this.isOverbaked = true;
      System.out.println("The " + this.type + " cake was overcooked");
    } else {
      this.isBaked = true;
      System.out.println("The " + this.type + " cake was baked");
    }
  }

  void cut(int slices){
    if(!(this.isCut) && this.isBaked){
      this.slices = slices;
      System.out.println("The " + this.type + " cake was cut into " + this.slices + " slices");
      this.isCut = true;
    } else {
      if(!(this.isBaked)){
        System.out.println("The " + this.type + " cake is not baked");
      } else {
        System.out.println("The " + this.type + " cake was already cut");
      }
    }

  }

  void eatSlice(){
    if(this.isCut && this.isBaked && !(this.isOverbaked)){
      if(this.slices == 0){
        System.out.println("The " + this.type + " cake was already eaten");
      } else {
        this.slices -= 1;
        System.out.println("A slice of " + this.type + " cake was eaten. There are " + this.slices + " left");
      }
    } else {
      if(!(this.isBaked)){
        System.out.println("The " + this.type + " cake is not baked");
      } else if(this.isOverbaked){
        System.out.println("The " + this.type + " cake is overcooked");
      } else {
        System.out.println("The " + this.type + " cake needs to be cut into slices");
      }

    }
  }
}
