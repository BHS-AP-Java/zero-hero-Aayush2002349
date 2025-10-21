package edu.bhscs;

public class App {
  public static void main(String[] args){

    // Just getting the food (normally all of these steps are done by the user in the game)
    Food food = new Food("cake");
    food.addIngredient(new Ingredient("egg"));
    food.addIngredient(new Ingredient("flour"));
    Ingredient choco = new Ingredient("chocolate");
    choco.cut();
    food.addIngredient(choco);
    for (int i = 0; i < 6; i++) {
      food.cook();
    }
    food.cut();

    User user = new User("");
    System.out.println(user);

    double width = user.getNumber("Width: ");
    double height = user.getNumber("Height: ");
    double depth = user.getNumber("Depth: ");

    food.draw(width, height, depth);

  }
}
