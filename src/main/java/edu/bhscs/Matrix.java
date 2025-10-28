package edu.bhscs;

public class Matrix {
  // fields and properties
  double[][] values;
  int rows;
  int columns;

  // constructor
  public Matrix(double[][] values) {
    this.values = values;
    this.rows = values.length;
    this.columns = values[0].length;
  }

  public Matrix multiplyMatrix(Matrix matrix) {
    if (this.rows != matrix.columns) {
      return null;
    }

    double[][] multiplied = new double[this.rows][matrix.columns];

    for (int i = 0; i < multiplied.length; i++) {
      for (int j = 0; j < multiplied[i].length; j++) {

        multiplied[i][j] = this.multiplyElement(matrix, i, j);
      }
    }

    return new Matrix(multiplied);
  }

  public Vector multiplyVector(Vector vector) {

    double[][] matrixVector = {{vector.x}, {vector.y}, {vector.z}, {1}};
    Matrix matrix = new Matrix(matrixVector);
    Matrix transformedMatrix = this.multiplyMatrix(matrix);

    return new Vector(
        transformedMatrix.values[0][0],
        transformedMatrix.values[0][1],
        transformedMatrix.values[0][2]);
  }

  public double multiplyElement(Matrix matrix, int rowIndex, int columnIndex) {
    double multipliedElement = 0;

    for (int i = 0; i < this.values[rowIndex].length; i++) {
      multipliedElement += this.values[rowIndex][i] * matrix.values[i][columnIndex];
    }

    return multipliedElement;
  }

  // Rot matrices found here:
  // https://math.libretexts.org/Bookshelves/Applied_Mathematics/Mathematics_for_Game_Developers_(Burzynski)/04%3A_Matrices/4.06%3A_Rotation_Matrices_in_3-Dimensions
  public static Matrix getXRotMatrix(double theta) {
    double[][] rotXMatrix = {
      {1, 0, 0, 0},
      {0, Math.cos(theta), -Math.sin(theta), 0},
      {0, Math.sin(theta), Math.cos(theta), 0},
      {0, 0, 0, 1}
    };
    return new Matrix(rotXMatrix);
  }

  public static Matrix getYRotMatrix(double theta) {
    double[][] rotYMatrix = {
      {Math.cos(theta), 0, Math.sin(theta), 0},
      {0, 1, 0, 0},
      {-Math.sin(theta), 0, Math.cos(theta), 0},
      {0, 0, 0, 1}
    };
    return new Matrix(rotYMatrix);
  }

  public static Matrix getZRotMatrix(double theta) {
    double[][] rotXMatrix = {
      {Math.cos(theta), -Math.sin(theta), 0, 0},
      {Math.sin(theta), Math.cos(theta), 0, 0},
      {0, 0, 1, 0},
      {0, 0, 0, 1}
    };
    return new Matrix(rotXMatrix);
  }

  // Gets a matrix that does a translation by the given vector
  public static Matrix getTranslationMatrix(Vector translation) {

    double[][] translationMatrix = {
      {1, 0, 0, translation.x},
      {0, 1, 0, translation.y},
      {0, 0, 1, translation.z},
      {0, 0, 0, 1}
    };

    return new Matrix(translationMatrix);
  }

  // Global to local space matrix
  public static Matrix getGlobalToLocalMatrix(
      Vector basisX, Vector basisY, Vector basisZ, Vector pos) {

    Matrix translation = getTranslationMatrix(pos.getScaled(-1));
    double[][] changeBasisVectors = {
      {basisX.x, basisX.y, basisX.z, 0},
      {basisY.x, basisY.y, basisY.z, 0},
      {basisZ.x, basisZ.y, basisZ.z, 0},
      {0, 0, 0, 1}
    };

    Matrix basisVectors = new Matrix(changeBasisVectors);
    Matrix toLocalSpace = basisVectors.multiplyMatrix(translation);
    return toLocalSpace;
  }

  public static Matrix getProjectionMatrix() {

    return null;
  }
}
