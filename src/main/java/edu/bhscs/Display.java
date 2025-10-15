package edu.bhscs;

public class Display {

  // Constructor
  public Display() {}

  public void displayEverything(int timeLeft, Restaurant restaurant) {
    this.displayTime(timeLeft);
    this.displayRestaurantRating(restaurant.rating);
    this.displayRestaurantMoney(restaurant.money);
    this.displayRestaurantOrders(restaurant.orders);
    this.displayRestaurant(restaurant);
  }

  // Displays the restaurant
  public void displayRestaurant(Restaurant restaurant) {

    // Display is as follows:
    // 0 = empty
    // 1 = chef starting location(considered empty so it will be 0)
    // 2 = base station
    // 3 = oven
    // 4 = counter
    // 5 = cutting station
    // 6 = delivery station
    // 7 = trash
    // 8 = ingredient station
    // u = chef
    // U = chef holding edible
    // Uppercase represents a food while lowercase represents an ingredient
    // A = edible mix
    // A,B,C,D,E,F,H,I,J = edible cooking
    // J = fully cooked
    // K = also cut
    // Z = overcooked
    // S = ready to serve (this overrides everything)

    for (int y = 0; y < restaurant.layout.length; y++) {
      for (int x = 0; x < restaurant.layout[y].length; x++) {

        if (restaurant.chefLocations[y][x] != null) {

          if (restaurant.chefLocations[y][x].edible != null) {
            System.out.print("U");
          } else {
            System.out.print("u");
          }

        } else if (restaurant.edibleLocations[y][x] != null) {

          Edible edible = restaurant.edibleLocations[y][x];

          char character;

          if (edible.isEdible()) {
            character = 's';
          } else if (edible.isOvercooked) {
            character = 'z';
          } else if (edible.isCut) {
            character = 'k';
          } else if (edible.isCooked) {
            character = 'j';
          } else {
            char[] numToLetter = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};
            character = numToLetter[restaurant.edibleLocations[y][x].timeCooked];
          }

          if (edible instanceof Food) {
            System.out.print(Character.toUpperCase(character));
          } else {
            System.out.print(character);
          }

        } else {

          if (restaurant.layout[y][x] == 1) {
            System.out.print("0");
          } else {
            System.out.print("" + restaurant.layout[y][x]);
          }
        }
      }
      System.out.println();
    }

    System.out.println();
  }

  // Displays the currently pending orders
  public void displayRestaurantOrders(Order[] orders) {
    System.out.println("Pending orders: ");
    for (int i = 0; i < orders.length; i++) {

      if (orders[i] == null) {
        System.out.println();
        return;
      }
      if (orders[i].late) {
        System.out.println(
            "  Order #"
                + (i + 1)
                + ": "
                + orders[i].food.getFoodTitle()
                + ": This order is failed");
      } else {
        System.out.println(
            "  Order #"
                + (i + 1)
                + ": "
                + orders[i].food.getFoodTitle()
                + ": Time left "
                + (orders[i].timeToComplete - orders[i].timeElapsed));
      }
    }

    System.out.println();
  }

  // Displays the time
  public void displayTime(int timeLeft) {
    System.out.println("Time Left: " + timeLeft);
    System.out.println();
  }

  // Displays the number of orders completed
  public void displayRestaurantRating(int rating) {
    System.out.println("Rating: " + rating);
    System.out.println();
  }

  // Displays the restaurant's money
  public void displayRestaurantMoney(int rating) {
    System.out.println("Money: " + rating);
    System.out.println();
  }

  public void displayFood(Food food){
    if(food.foodType == "cake"){
      this.displayCake(food);
    }
  }

  //Displays a 3d cake
  public void displayCake(Food food){
    for(double test = 0; test < 6.3; test = test + 0.1){
      String[][] surface = this.getSurface(30,30);
      double[][] points = {{10,10,3},{20,10,4},{10,20,2}};

      double[] cameraPos = {12,12,7};

      double[] cameraDir = {Math.sin(test),0,Math.cos(test)};
      /*for (int i = 0; i < cameraDir.length; i++) {
        System.out.print(cameraDir[i]);
        System.out.print(" ");
      }*/
      double[] up = {0,1,0};

      double[][] projectedPoints = this.projPoints(points,cameraPos,cameraDir,up);

      /*for(int i = 0; i < projectedPoints.length; i++){
        System.out.print("  ");
        for (int j = 0; j < projectedPoints[i].length; j++) {
          System.out.print(projectedPoints[i][j]);
          System.out.print(" ");
        }
      }*/


      this.drawConvexPolygon(projectedPoints,surface,"##");
      this.displaySurface(surface);
    }
  }

  public String[][] getSurface(int width,int height){
    String[][] surface = new String[height][width];
    for (int i = 0; i < surface.length; i++) {
      for (int j = 0; j < surface[i].length; j++) {
        surface[i][j] = "  ";
      }
    }

    return surface;
  }

  public void displaySurface(String[][] surface){
    for(int i = 0; i < surface.length; i++){
      for(int j = 0; j < surface[i].length; j++){
        System.out.print(surface[i][j]);
      }
      System.out.println();
    }
  }

  //Draws a polygon given the points of the polygon as well as something to draw the polygon on
  //Note: 0,0 is the center of the surface
  public void drawConvexPolygon(double[][] points, String[][] surface,String color){

    for(int i = 0; i < surface.length; i++){
      for (int j = 0; j < surface[i].length; j++) {
        //We need to iterate thourgh all points and check whether or not (j,i) is inside the polygon
        //One way to do this is to check if some line (ex a horizontal one) intersects one edge of the polygon on both sides (ex: the left and right side)
        //The slope between (x1,y1),(x2,y2) is (y2-y1)/(x2,x1) and the equation would be y=(y2-y1)/(x2,x1) * (x-x1)+y1. For a horizonal line we already know the y coordinate at the location at which they intersect (since the line is horizontal)
        //y=i y=(y2-y1)/(x2,x1) * (x-x1)+y1, So we need to solve for x which is:
        //i=(y2-y1)/(x2,x1) * (x-x1)+y1 i-y1 = (y2-y1)/(x2,x1) * (x-x1) x-x1 = (i-y1)(x2,x1)/(y2-y1) or x = (i-y1)(x2,x1)/(y2-y1)+x1
        //For a given point to intersect there must be an x on the left and one on the right
        //Finally if the y coordinate is inbetween y1 and y2 that means that there is an intersection with the line segment of the polygon, if not, then there is no intersection

        Boolean foundLeft = false;
        Boolean foundRight = false;
        for(int pt = 0; pt < points.length; pt++){

          //These are just centered coordinates of i and j (so that the center of surface can be 0,0)
          double i1 = i - (surface.length / 2);
          double j1 = j - (surface[i].length / 2);

          //Getting the points
          double[] pt1 = points[pt];
          double[] pt2;

          if(pt == points.length - 1){
            pt2 = points[0];
          } else {
            pt2 = points[pt+1];
          }

          //Getting the coordinates of the points
          double x1 = pt1[0];
          double x2 = pt2[0];
          double y1 = pt1[1];
          double y2 = pt2[1];

          //Check to make sure the y coordinate is inbetween the y coordinates of the points on the polygon
          if ((i1 >= y1 && i1 <= y2) || (i1 <= y1 && i1 >= y2)) {
            // Formula for x coord of intersection point
            double x;

            // If the line is close to vertical there will be a / by 0 error
            if (y2 - y1 < 0.0001 && y2 - y1 > 0.0001) {
              x = x1;
            } else {
              x = ((i1 - y1) * (x2 - x1) / (y2 - y1)) + x1;
            }
            //Finally we check whether this point is on the left or right (remember j represnts the x coordinate)
            if(x <= j1){
              foundLeft = true;
            }

            if(x >= j1){
              foundRight = true;
            }

          }

        }

        if(foundLeft && foundRight){
          surface[i][j] = color;
        }

      }
    }

  }

  //This projection is a very simple perspective projection (x,y,z) -> (x/z,y/z)
  public double[][] projPoints(double[][] points,double[] cameraPos, double[] cameraDir, double[] up){


    //Projecting a point involves 2 steps, the first is to convert the points from global space to the camera's local space
    //The second step is to perform some projection to convert those 3d points to 2d points
    double[][] projectedPoints = new double[points.length][2];

    //This is the first step
    double[][] transformedPoints = this.globalToLocalSpace(points,this.crossProduct(cameraDir,up),up,cameraDir,cameraPos);

    //This is the second step
    for(int i = 0; i < points.length; i++){
      double[] projectedPoint = new double[2];

      //Currently we are using a very simple orthographic projection (x,y,z) -> (x,y)
      projectedPoint[0] = transformedPoints[i][0];
      projectedPoint[1] = transformedPoints[i][1];

      projectedPoints[i] = projectedPoint;
    }

    return projectedPoints;

  }

  public double[][] globalToLocalSpace(double[][] points,double[] basisX,double[] basisY, double[] basisZ, double[] origin){

    double[][] transformedPoints = new double[points.length][3];

    for(int i = 0; i < points.length; i++){

      //Translates the point so that the origin of the points is now the specified origin
      double[] translatedPoint = {
        points[i][0] - origin[0],
        points[i][1] - origin[1],
        points[i][2] - origin[2]
      };

      //Gets the lengths of the basis vectors associated with the point, this is now the fully transformed point
      double[] transformedPoint = {
        this.dotProduct(translatedPoint,basisX),
        this.dotProduct(translatedPoint, basisY),
        this.dotProduct(translatedPoint, basisZ)
      };

      transformedPoints[i] = transformedPoint;
    }


    return transformedPoints;
  }

  public double dotProduct(double[] vec1, double[] vec2){

    double dot = 0;
    for(int i = 0; i < vec1.length; i++){
      dot += vec1[i] * vec2[i];
    }
    return dot;

  }

  public double[] crossProduct(double[] vec1,double[] vec2){
    // https://en.wikipedia.org/wiki/Cross_product
    // The cross product formula used is found above ^^^
    double[] crossed = {
      vec1[1] * vec2[2] - vec1[2] * vec2[1],
      vec1[2] * vec2[0] - vec1[0] * vec2[2],
      vec1[0] * vec2[1] - vec1[1] * vec2[0]
    };
    return crossed;
  }

  public void displayBurger() {
    int size = 12;
    this.displayBun(size);
    this.displayFilling(size);
    this.displayBun(size);
    String burgerDisplay =
        """

                                    ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓
                                ▓▓▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓▓▓
                            ▓▓▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓▓▓
                        ▓▓▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓▓▓
                    ▓▓▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒  ▒▒▒▒▒▒▒▒▒▒▒▒  ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓▓▓
                  ▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒  ▒▒▒▒▒▒▒▒▒▒▒▒▓▓
                ▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓
              ▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒  ▒▒▒▒▒▒▒▒▒▒▒▒▒▒  ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓
              ▓▓▒▒▒▒▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓
            ▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒  ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓
            ▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒  ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓
          ▓▓▒▒▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒  ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓
        ▓▓▓▓▒▒▒▒▒▒▒▒  ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓
        ▓▓▓▓▒▒▓▓▒▒▒▒▓▓▒▒▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒  ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓
        ▓▓▒▒▒▒▒▒▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓
        ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓
      ▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓▓▓▓▓
  ▓▓▓▓▒▒▒▒▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓▒▒▒▒▒▒▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓▒▒▒▒▒▒▒▒▒▒▓▓▒▒▒▒▓▓▒▒▒▒▓▓▓▓▓▓▓▓▓▓▒▒▒▒▒▒▓▓
  ▓▓▒▒▒▒▒▒▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▒▒▒▒▒▒▒▒▒▒▒▒▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▒▒▒▒▓▓▒▒▒▒▒▒▒▒▓▓▓▓▒▒▒▒▒▒▒▒▓▓▓▓▒▒▒▒▓▓
  ▓▓▓▓▓▓▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓▓▓▓▓▓▓▓▓▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓▒▒▒▒▒▒▒▒▓▓▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓▓▓▓▓
  ░░░░░░▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒░░░░░░
        ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓████████▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓
      ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓
      ▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓
      ░░▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓░░
      ▒▒▒▒▒▒░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░▒▒▒▒██
    ██████████░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░████████
    ████▓▓▓▓▓▓▓▓▓▓▓▓░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░▓▓▓▓▓▓▓▓▓▓████
    ████▓▓██▓▓▓▓▓▓▓▓▓▓██▓▓░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░▓▓██▓▓▓▓▓▓▓▓██████
    ████▓▓██▓▓██▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓████
    ████▓▓▓▓██▓▓▓▓██▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓░░░░░░░░░░░░░░░░░░░░░░░░░░▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓██▓▓▓▓████
    ████▓▓▓▓▓▓▓▓▓▓▓▓▓▓██▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓░░░░░░░░░░▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓██▓▓▓▓▓▓▓▓██▓▓████
    ████▓▓▓▓▓▓██▓▓▓▓▓▓▓▓▓▓██▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓██▓▓▓▓▓▓████
    ░░██████████████████████████████████████████████████████████████████████████████████████████░░
      ░░▓▓▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓▓▓
        ▓▓▒▒▓▓▒▒▓▓▒▒▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓▒▒▒▒▓▓▒▒▒▒▒▒▓▓
        ▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓▒▒▒▒▒▒▒▒▒▒▒▒▓▓▒▒▓▓
        ▓▓▒▒▒▒▓▓▒▒▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓▒▒▒▒▒▒▒▒▓▓▓▓
        ▓▓▓▓▒▒▒▒▒▒▒▒▒▒▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓▒▒▒▒▒▒▒▒▒▒▓▓▒▒▓▓▓▓
          ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓
        """;
    // System.out.println(burgerDisplay);
  }

  private void displayBun(int size) {
    String crust = "HH";
    int width = size * 7 / 8;
    int height = size / 4;
    String layer = "";

    // A space is half of the total
    for (int i = 0; i < size - (size * 7 / 8); i++) {
      layer += " ";
    }
    for (int i = 0; i < width; i++) {
      layer += crust;
    }
    for (int j = 0; j < height; j++) {
      System.out.println(layer);
    }
  }

  private void displayFilling(int size) {
    String crust = "##";
    int width = size;

    int height = size / 5;
    String layer = "";
    for (int i = 0; i < width; i++) {
      layer += crust;
    }
    for (int j = 0; j < height; j++) {
      System.out.println(layer);
    }
  }
}
