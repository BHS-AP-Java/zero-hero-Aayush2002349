package edu.bhscs;

public class Polygon {
  //fields and properties
  Vector[] points;

  //constructors
  public Polygon(){
    this.points = new Vector[0];
  }

  public Polygon(Vector[] points){
    this.points = points;
  }

  public void addPoint(Vector point){
    Vector[] newPoints = new Vector[this.points.length + 1];

    for(int i = 0; i < this.points.length; i++){
      newPoints[i] = this.points[i];
    }

    newPoints[newPoints.length - 1] = point;
    this.points = newPoints;
  }
}
