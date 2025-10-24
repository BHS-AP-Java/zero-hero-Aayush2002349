package edu.bhscs;

public class Vector {
  //fields + properties
  double x;
  double y;
  double z;

  //The only reason w exists is for the purposes of matrix multiplication
  double w = 1;

  //Constructor
  public Vector(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Vector dot(Vector vector) {
    return new Vector(this.x * vector.x,this.y * vector.y,this.z * vector.z);
  }

  public Vector cross(Vector vector){
    // https://en.wikipedia.org/wiki/Cross_product
    // The cross product formula used is found above ^^^
    Vector crossed = new Vector(
      this.y * vector.z - this.z * vector.y,
      this.z * vector.x - this.x * vector.z,
      this.x * vector.y - this.y * vector.x
    );

    return crossed;
  }

  //Scales the vector by the given constant
  public void scale(double scalar){
    this.x *= scalar;
    this.y *= scalar;
    this.z *= scalar;
  }

}
