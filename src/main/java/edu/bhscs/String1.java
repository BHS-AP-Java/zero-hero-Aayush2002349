package edu.bhscs;

public class String1{

  char[] str;

  public String1(char[] str){
    this.str = str;
  }

  public String1 substring(int start, int end){

    char[] newStr = new char[end-start];

    for(int i = start; i < end; i++){
      newStr[i-start] = this.str[i];
    }

    return new String1(newStr);

  }

  public void draw(){
    for(int i = 0; i < this.str.length; i++){
      System.out.print(this.str[i]);
    }
  }
}