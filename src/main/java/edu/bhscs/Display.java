package edu.bhscs;

import java.util.Random;

public class Display {

  // Constructor
  public Display() {}

  public static void displayGame(int timeLeft, Restaurant restaurant) {
    displayTime(timeLeft);
    displayRestaurantRating(restaurant.rating);
    displayRestaurantMoney(restaurant.money);
    displayRestaurantOrders(restaurant.orders);
    displayRestaurant(restaurant);
  }

  public static String[][] getLocationDisplay(int location) {
    // Layout:
    // 0 = empty
    // 1 = chef starting location(considered empty)
    // 2 = base station
    // 3 = power
    // 4 = counter
    // 5 = cutting station
    // 6 = delivery station
    // 7 = trash
    // 8 = ingredient station
    // 9 = ware station

    // $@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\|()1{}[]?-_+~<>i!lI;:,"^`'.
    if (location == 0 || location == 1) {
      String[][] art = {
        {":", ":", " ", " "},
        {":", ":", " ", " "},
        {" ", " ", ":", ":"},
        {" ", " ", ":", ":"}
      };
      return art;
    }
    if (location == 2) {
      String[][] art = {
        {"#", "#", "#", "#"},
        {"#", " F", "O ", "#"},
        {"#", " O", "D ", "#"},
        {"#", "#", "#", "#"}
      };
      return art;
    }

    if (location == 3) {
      String[][] art = {
        {"\\", " ", " ", "/"},
        {" ", "\\", "/", " "},
        {" ", "/", "\\", " "},
        {"/", " ", " ", "\\"}
      };
      return art;
    }

    if (location == 4) {
      String[][] art = {
        {"#", "#", "#", "#"},
        {"#", "#", "#", "#"},
        {"#", "#", "#", "#"},
        {"#", "#", "#", "#"}
      };
      return art;
    }

    if (location == 5) {
      String[][] art = {
        {"||", ".", "/-", "-\\"},
        {"| ", ".", "\\-", "-|"},
        {"|-", "-\\", ".", " |"},
        {"\\-", "-/", ".", "||"}
      };
      return art;
    }
    if (location == 6) {
      String[][] art = {
        {".", ".", "\\", "."},
        {"-", "-", "-", "\\"},
        {"-", "-", "-", "/"},
        {".", ".", "/", "."}
      };
      return art;
    }

    if (location == 7) {
      String[][] art = {
        {"#", "#", "#", "#"},
        {"#", " ", " ", "#"},
        {"#", " ", " ", "#"},
        {"#", "#", "#", "#"}
      };
      return art;
    }
    if (location == 8) {
      String[][] art = {
        {"#", "#", "#", "#"},
        {"#", " I", "N ", "#"},
        {"#", " G", "R ", "#"},
        {"#", "#", "#", "#"}
      };
      return art;
    }
    if (location == 9) {
      String[][] art = {
        {"#", "#", "#", "#"},
        {"#", " W", "A ", "#"},
        {"#", " R", "E ", "#"},
        {"#", "#", "#", "#"}
      };
      return art;
    }
    return null;
  }

  public static String[][] getChefDisplay(Person chef) {
    if (chef != null) {
      String[][] tile = {
        {"CH", "EF", " ", ">"},
        {" ", " ", " ", " "},
        {" ", " ", " ", " "},
        {"<", " ", "CH", "EF"}
      };

      Pickupable item = chef.item;
      if (item instanceof Tableware) {
        Tableware tableware = (Tableware) item;
        String[][] wareTile = Display.getTablewareDisplay(tableware);
        String[][] edibleTile = Display.getEdibleDisplay(tableware.edible);
        tile = Display.overlap(wareTile, tile);
        tile = Display.overlap(edibleTile, tile);
      }

      if (item instanceof Edible) {
        String[][] edibleTile = Display.getEdibleDisplay((Edible) item);
        tile = Display.overlap(edibleTile, tile);
      }

      return tile;
    } else {
      String[][] art = {
        {" ", " ", " ", " "},
        {" ", " ", " ", " "},
        {" ", " ", " ", " "},
        {" ", " ", " ", " "}
      };
      return art;
    }
  }

  public static String[][] getTablewareDisplay(Tableware ware) {
    // $@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\|()1{}[]?-_+~<>i!lI;:,"^`'.
    if (ware.type.matches("plate")) {
      String[][] art = {
        {" ", "$", "$", " "},
        {"$", "$", "$", "$"},
        {"$", "$", "$", "$"},
        {" ", "$", "$", " "}
      };
      return art;
    }
    if (ware.type.matches("cup")) {
      String[][] art = {
        {" ", "$-", "-$", " "},
        {"$", " ", " ", "$"},
        {"$", " ", " ", "$"},
        {" ", "$-", "-$", " "}
      };
      return art;
    }
    if (ware.type.matches("pan")) {
      String[][] art = {
        {" ", " |", "| ", " "},
        {" ", "$", "$", " "},
        {"$", "$", "$", "$"},
        {" ", "$", "$", " "}
      };
      return art;
    }
    if (ware.type.matches("oven")) {
      String[][] art = {
        {"-", "[]", "[]", "-"},
        {"|", " ", " ", "|"},
        {"|", " ", " ", "|"},
        {"-", "O", "O", "-"}
      };
      return art;
    }

    String[][] art = {
      {" ", " ", " ", " "},
      {" ", " ", " ", " "},
      {" ", " ", " ", " "},
      {" ", " ", " ", " "}
    };
    return art;
  }

  // This is a little lazy, i didn't want to have a unique display for every food so I just made the
  // display the name of the food
  public static String[][] getEdibleDisplay(Edible edible) {

    if (edible == null) {
      String[][] art = {
        {" ", " ", " ", " "},
        {" ", " ", " ", " "},
        {" ", " ", " ", " "},
        {" ", " ", " ", " "}
      };
      return art;
    }

    String name = edible.name.substring(0);
    System.out.println(name.length());
    int leftover = 6 - name.length();
    for (int i = 0; i < leftover; i++) {
      name += " ";
    }

    String cut = " ";
    if (edible.isCut) {
      cut = "X";
    }

    String cooked = " ";
    if (edible.isOvercooked) {
      cooked = "O";
    } else if (edible.isCooked) {
      cooked = "S";
    } else if (edible.timeCooked > 0) {
      String[] cookingTimeToLetter = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
      cooked = cookingTimeToLetter[edible.timeCooked - 1];
    }
    String[][] art = {
      {" ", " ", " ", " "},
      {" ", name.substring(0, 2), name.substring(2, 4), " "},
      {" ", name.substring(4, 6), cooked + cut, " "},
      {" ", " ", " ", " "}
    };
    return art;
  }

  // Overlaps one thing ontop of the other (spaces are omitted)
  public static String[][] overlap(String[][] top, String[][] bottom) {
    String[][] overlapped = new String[top.length][top[0].length];

    for (int i = 0; i < top.length; i++) {
      for (int j = 0; j < top[0].length; j++) {
        if (top[i][j].matches(" ")) {
          overlapped[i][j] = bottom[i][j];
        } else {
          overlapped[i][j] = top[i][j];
        }
      }
    }

    return overlapped;
  }

  public static String[][] getTileDisplay(Restaurant restaurant, int x, int y) {

    String[][] floorTile = Display.getLocationDisplay(restaurant.layout[y][x]);
    String[][] chefTile = Display.getChefDisplay(restaurant.chefLocations[y][x]);

    String[][] tile = floorTile;

    Pickupable item = restaurant.itemLocations[y][x];
    if (item instanceof Tableware) {
      Tableware tableware = (Tableware) item;
      String[][] wareTile = Display.getTablewareDisplay(tableware);
      String[][] edibleTile = Display.getEdibleDisplay(tableware.edible);
      tile = Display.overlap(wareTile, tile);
      tile = Display.overlap(edibleTile, tile);
    }

    if (item instanceof Edible) {
      String[][] edibleTile = Display.getEdibleDisplay((Edible) item);
      tile = Display.overlap(edibleTile, tile);
    }

    tile = Display.overlap(chefTile, tile);

    return tile;
  }

  // Displays the restaurant
  public static void displayRestaurant(Restaurant restaurant) {
    String[][] surface =
        Display.getSurface(4 * restaurant.layout[0].length, 4 * restaurant.layout.length);
    for (int y = 0; y < restaurant.layout.length; y++) {
      for (int x = 0; x < restaurant.layout[y].length; x++) {

        String[][] tile = Display.getTileDisplay(restaurant, x, y);
        for (int n = 4 * y; n < 4 * y + 4; n++) {
          for (int m = 4 * x; m < 4 * x + 4; m++) {

            String str = tile[n % 4][m % 4];
            if (str.length() == 1) {
              str = str + str;
            }
            surface[n][m] = str;
          }
        }
      }
    }

    Display.displaySurface(surface, 0, 0);
  }

  // Displays the currently pending orders
  public static void displayRestaurantOrders(Order[] orders) {
    System.out.println("Pending orders: ");
    for (int i = 0; i < orders.length; i++) {

      if (orders[i] == null) {
        System.out.println();
        return;
      }
      if (orders[i].late) {
        System.out.println(
            "  Order #" + (i + 1) + ": " + orders[i].food.getTitle() + ": This order is failed");
      } else {
        System.out.println(
            "  Order #"
                + (i + 1)
                + ": "
                + orders[i].food.getTitle()
                + ": Time left "
                + (orders[i].timeToComplete - orders[i].timeElapsed));
      }
    }

    System.out.println();
  }

  // Displays the time
  public static void displayTime(int timeLeft) {
    System.out.println("Time Left: " + timeLeft);
    System.out.println();
  }

  // Displays the number of orders completed
  public static void displayRestaurantRating(int rating) {
    System.out.println("Rating: " + rating);
    System.out.println();
  }

  // Displays the restaurant's money
  public static void displayRestaurantMoney(int rating) {
    System.out.println("Money: " + rating);
    System.out.println();
  }

  public static String[][] getFoodDisplay(
      String[][] surface, Food food, double width, double height, double depth) {
    String[][] newSurface = null;
    if (food.name.matches("cake")) {
      newSurface = getCakeDisplay(surface, food, width, height, depth);
    }
    return newSurface;
  }

  public static String[][] getTableDisplay(String[][] surface, Table table) {
    // Here is a small description of this computation:
    // #########
    //  #  #  #
    //  #  #  #
    //  #  #  #
    //  #  #  #
    // Total length of the spacing is length of the surface - 2(for the overhang on the left and
    // right) - the number of legs multiplied by the width of one of the legs
    // Then divide by the number of spaces which is the number of legs - 1
    int spacing = (int) (table.width - 2 - (table.legs * table.leg.length())) / (table.legs - 1);

    // Especially for smaller values of width, the table legs may not be able to perfectly fit
    // One potential solution for this is to reduce or increase the table's width to fit the tables
    // We can recalculate the width using the 2 + the number of legs multiplied by the width of each
    // leg + the length of the spacing multiplied by the number of spaces (which is the number of
    // legs - 1)

    double tableThickness = table.top.length();

    double tableWidth = 2 + table.legs * table.leg.length() + (table.legs - 1) * spacing;
    for (int i = 0; i < tableThickness; i++) {
      for (int j = 0; j < tableWidth; j++) {
        surface[i][j] = table.top.substring(i, i + 1) + table.top.substring(i, i + 1);
      }
    }

    for (int i = 0; i < table.legs; i++) {
      // This is the offset from the left side of the table
      int leftOffset = 1 + i * (spacing + table.leg.length());
      // Now we draw each table leg onto the surface
      for (int j = 0; j < table.leg.length(); j++) {
        for (int k = table.top.length(); k < table.height; k++) {

          surface[k][j + leftOffset] =
              table.leg.substring(j, j + 1) + table.leg.substring(j, j + 1);
        }
      }
    }

    return surface;
  }

  // Displays a 3d cake on the surface
  public static String[][] getCakeDisplay(
      String[][] surface, Food cake, double width, double height, double depth) {

    // The process of drawing some 3d shape has a few steps
    // First we need to figure out what 3d points and polygons to actually draw
    // Then we need to convert those 3d points and polygons into 2d ones
    // Finally we draw all those polygons onto a surface, making sure we draw the ones further back
    // first so that closer ones cover the further ones
    // Then we display that surface

    // Here is where we get the polygons of a cake
    double[][] frontFace = {{0, 0, 0}, {0, 5, 0}, {4, 5, 20}, {4, 1, 20}};
    double[][] leftFace = {{0, 0, 0}, {0, 5, 0}, {10, 5, 0}, {10, 0, 0}};
    double[][] bottomFace = {{0, 0, 0}, {10, 0, 0}, {6, 1, 20}, {4, 1, 20}};
    double[][] topFace = {{0, 5, 0}, {10, 5, 0}, {6, 5, 20}, {4, 5, 20}};
    double[][] backFace = {{10, 0, 0}, {10, 5, 0}, {6, 5, 20}, {6, 1, 20}};
    double[][] rightFace = {{4, 1, 20}, {4, 5, 20}, {6, 5, 20}, {6, 1, 20}};

    // The 3d cake is a wedge-like shape
    double[][][] cake3d = {frontFace, leftFace, bottomFace, topFace, backFace, rightFace};

    // Then we move around this cake a bit and scale it as well
    for (int i = 0; i < cake3d.length; i++) {
      for (int j = 0; j < cake3d[i].length; j++) {
        cake3d[i][j][0] -= 5;
        cake3d[i][j][1] -= 2;
        cake3d[i][j][2] -= 10;
      }
    }

    for (int i = 0; i < cake3d.length; i++) {
      for (int j = 0; j < cake3d[i].length; j++) {
        cake3d[i][j][0] *= width / 10;
        cake3d[i][j][1] *= height / 5;
        cake3d[i][j][2] *= depth / 10;
      }
    }

    // To do all the specific renderering we need to find a specific point in space to be in as
    // well as a direction to face in and which way is up

    // These values for the direction and up look nice but they don't center the cake
    // well for drawing a table ontop
    double[] cameraPos = {10, -10, 0};
    double[] cameraDir = {1 / Math.sqrt(3), -1 / Math.sqrt(3), 1 / Math.sqrt(3)};
    double[] up = {1 / Math.sqrt(6), 2 / Math.sqrt(6), 1 / Math.sqrt(6)};

    // double[] cameraDir = {0, -1 / Math.sqrt(2), -1 / Math.sqrt(2)};
    // double[] up = {0, 1 / Math.sqrt(2), -1 / Math.sqrt(2)};

    // To convert our 3d points to 2d ones there are 2 steps.
    // First we need to figure out where the shapes are relative to the camera, if you move the
    // camera left then we would expect to see everything move to the right
    // Then we need to apply some process to take 3d points and make them 2d

    // This is where we calculate where the 3d points are relative to the camera
    double[][][] transformedPolygons = new double[cake3d.length][][];

    for (int i = 0; i < cake3d.length; i++) {
      double[][] transformedPoints =
          globalToLocalSpace(cake3d[i], crossProduct(cameraDir, up), up, cameraDir, cameraPos);
      transformedPolygons[i] = transformedPoints;
    }

    // One of the other things that we need to do is make sure that the polygons drawn first are
    // the ones at the very back, that is done here
    transformedPolygons = zSort(transformedPolygons);

    // Then the points are converted from 3d to 2d and also drawn onto the surface
    for (int i = 0; i < transformedPolygons.length; i++) {
      double[][] projectedPoints = projPoints(transformedPolygons[i]);
      // $@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\|()1{}[]?-_+~<>i!lI;:,"^`'.
      String[] faces = {"$$", "hh", "{}", "$$", "//", "::"};

      String additionalIngredient = null;
      if (cake.specialtyIngredients.length != 0) {
        if (cake.specialtyIngredients[0].name != null) {
          if (cake.specialtyIngredients[0].name.matches("chocolate")) {
            additionalIngredient = "SS";
          } else if (cake.specialtyIngredients[0].name.matches("spice")) {
            additionalIngredient = "-;";
          }
        }
      }

      drawConvexPolygon(projectedPoints, surface, faces[i], additionalIngredient);
    }

    return surface;
  }

  // Sorts polygons so they can be drawn from back to front (higher z coordinate = further back)
  public static double[][][] zSort(double[][][] polygons) {
    if (polygons.length == 1 || polygons.length == 0) {
      return polygons;
    }

    // Gets the z coordinates of all the polygons (for now its just their average center)
    double[] zCoords = new double[polygons.length];
    for (int i = 0; i < polygons.length; i++) {
      zCoords[i] = Math.abs((averagePoints(polygons[i])[2]));
    }

    // Sorts using bubble sort
    for (int i = 1; i < polygons.length; i++) {
      for (int j = 0; j < polygons.length - i; j++) {
        if (zCoords[j] < zCoords[j + 1]) {

          double[][] temp = polygons[j];
          polygons[j] = polygons[j + 1];
          polygons[j + 1] = temp;

          double temp1 = zCoords[j];
          zCoords[j] = zCoords[j + 1];
          zCoords[j + 1] = temp1;
        }
      }
    }

    return polygons;
  }

  public static double[] averagePoints(double[][] points) {
    double x = 0;
    double y = 0;
    double z = 0;
    for (int i = 0; i < points.length; i++) {
      x += points[i][0];
      y += points[i][1];
      z += points[i][2];
    }

    double[] average = {x / points.length, y / points.length, z / points.length};
    return average;
  }

  public static String[][] getSurface(int width, int height) {
    String[][] surface = new String[height][width];
    for (int i = 0; i < surface.length; i++) {
      for (int j = 0; j < surface[i].length; j++) {
        surface[i][j] = "  ";
      }
    }

    return surface;
  }

  // Display surface also culls the top and bottom,left and right edges
  // Additionally returns the new dimensions after the culling and has options to add a left or top
  // offset
  public static String[][] cullUnusedParts(String[][] surface) {

    int left = 0;
    int top = 0;
    int right = surface[0].length;
    int bottom = surface.length;

    Boolean foundLeftEdge = false;
    Boolean foundTopEdge = false;
    Boolean foundRightEdge = false;
    Boolean foundBottomEdge = false;

    for (int i = 0; i < surface.length; i++) {
      for (int j = 0; j < surface[i].length; j++) {
        if (!(surface[i][j].matches("  "))) {
          foundTopEdge = true;
        }
        if (!(surface[surface.length - i - 1][j].matches("  "))) {
          foundBottomEdge = true;
        }
      }

      if (!(foundTopEdge)) {
        top += 1;
      }
      if (!(foundBottomEdge)) {
        bottom -= 1;
      }
    }

    for (int i = 0; i < surface[0].length; i++) {
      for (int j = 0; j < surface.length; j++) {
        if (!(surface[j][i].matches("  "))) {
          foundLeftEdge = true;
        }
        if (!(surface[j][surface[0].length - i - 1].matches("  "))) {
          foundRightEdge = true;
        }
      }

      if (!(foundLeftEdge)) {
        left += 1;
      }
      if (!(foundRightEdge)) {
        right -= 1;
      }
    }

    String[][] newSurface = new String[bottom - top][right - left];

    for (int i = top; i < bottom; i++) {
      for (int j = left; j < right; j++) {

        newSurface[i - top][j - left] = surface[i][j];
      }
    }

    return newSurface;
  }

  public static void displaySurface(String[][] surface, int leftOffset, int topOffset) {

    // top offset here
    for (int i = 0; i < topOffset; i++) {
      System.out.println();
    }

    for (int i = 0; i < surface.length; i++) {

      // left offset here
      for (int j = 0; j < leftOffset; j++) {
        System.out.print(" ");
      }

      for (int j = 0; j < surface[i].length; j++) {
        System.out.print(surface[i][j]);
      }
      System.out.println();
    }
  }

  // Draws a polygon given the points of the polygon as well as something to draw the polygon on
  // Note: 0,0 is the center of the surface
  public static void drawConvexPolygon(
      double[][] points, String[][] surface, String color, String additionalColor) {

    Random random = new Random();
    for (int i = 0; i < surface.length; i++) {
      for (int j = 0; j < surface[i].length; j++) {
        // We need to iterate thourgh all points and check whether or not (j,i) is inside the
        // polygon
        // One way to do this is to check if some line (ex a horizontal one) intersects one edge of
        // the polygon on both sides (ex: the left and right side)
        // The slope between (x1,y1),(x2,y2) is (y2-y1)/(x2,x1) and the equation would be
        // y=(y2-y1)/(x2,x1) * (x-x1)+y1. For a horizonal line we already know the y coordinate at
        // the location at which they intersect (since the line is horizontal)
        // y=i y=(y2-y1)/(x2,x1) * (x-x1)+y1, So we need to solve for x which is:
        // i=(y2-y1)/(x2,x1) * (x-x1)+y1 i-y1 = (y2-y1)/(x2,x1) * (x-x1) x-x1 =
        // (i-y1)(x2,x1)/(y2-y1) or x = (i-y1)(x2,x1)/(y2-y1)+x1
        // For a given point to intersect there must be an x on the left and one on the right
        // Finally if the y coordinate is inbetween y1 and y2 that means that there is an
        // intersection with the line segment of the polygon, if not, then there is no intersection

        Boolean foundLeft = false;
        Boolean foundRight = false;
        for (int pt = 0; pt < points.length; pt++) {

          // These are just centered coordinates of i and j (so that the center of surface can be
          // 0,0)
          double i1 = i - (surface.length / 2);
          double j1 = j - (surface[i].length / 2);

          // Getting the points
          double[] pt1 = points[pt];
          double[] pt2;

          if (pt == points.length - 1) {
            pt2 = points[0];
          } else {
            pt2 = points[pt + 1];
          }

          // Getting the coordinates of the points
          double x1 = pt1[0];
          double x2 = pt2[0];
          double y1 = pt1[1];
          double y2 = pt2[1];

          // Check to make sure the y coordinate is inbetween the y coordinates of the points on the
          // polygon
          if ((i1 >= y1 && i1 <= y2) || (i1 <= y1 && i1 >= y2)) {
            // Formula for x coord of intersection point
            double x;

            // If the line is close to vertical there will be a / by 0 error
            if (y2 - y1 < 0.0001 && y2 - y1 > 0.0001) {
              x = x1;
            } else {
              x = ((i1 - y1) * (x2 - x1) / (y2 - y1)) + x1;
            }
            // Finally we check whether this point is on the left or right (remember j represnts the
            // x coordinate)
            if (x <= j1) {
              foundLeft = true;
            }

            if (x >= j1) {
              foundRight = true;
            }
          }
        }

        if (foundLeft && foundRight) {
          if (random.nextInt(50) != 1 || additionalColor == null) {
            surface[i][j] = color;
          } else {
            surface[i][j] = additionalColor;
          }
        }
      }
    }
  }

  // This projection is a very simple orthographic projection (x,y,z) -> (x,y)
  public static double[][] projPoints(double[][] points) {

    double[][] projectedPoints = new double[points.length][3];

    // This is the second step
    for (int i = 0; i < points.length; i++) {
      double[] projectedPoint = new double[2];

      // Currently we are using a very simple orthographic projection (x,y,z) -> (x,y)
      projectedPoint[0] = points[i][0];
      projectedPoint[1] = points[i][1];

      projectedPoints[i] = projectedPoint;
    }

    return projectedPoints;
  }

  public static double[][] globalToLocalSpace(
      double[][] points, double[] basisX, double[] basisY, double[] basisZ, double[] origin) {

    double[][] transformedPoints = new double[points.length][3];

    for (int i = 0; i < points.length; i++) {

      // Translates the point so that the origin of the points is now the specified origin
      double[] translatedPoint = {
        points[i][0] - origin[0], points[i][1] - origin[1], points[i][2] - origin[2]
      };

      // Gets the lengths of the basis vectors associated with the point, this is now the fully
      // transformed point
      double[] transformedPoint = {
        dotProduct(translatedPoint, basisX),
        dotProduct(translatedPoint, basisY),
        dotProduct(translatedPoint, basisZ)
      };

      transformedPoints[i] = transformedPoint;
    }

    return transformedPoints;
  }

  public static double dotProduct(double[] vec1, double[] vec2) {

    double dot = 0;
    for (int i = 0; i < vec1.length; i++) {
      dot += vec1[i] * vec2[i];
    }
    return dot;
  }

  public static double[] crossProduct(double[] vec1, double[] vec2) {
    // https://en.wikipedia.org/wiki/Cross_product
    // The cross product formula used is found above ^^^
    double[] crossed = {
      vec1[1] * vec2[2] - vec1[2] * vec2[1],
      vec1[2] * vec2[0] - vec1[0] * vec2[2],
      vec1[0] * vec2[1] - vec1[1] * vec2[0]
    };
    return crossed;
  }
}
