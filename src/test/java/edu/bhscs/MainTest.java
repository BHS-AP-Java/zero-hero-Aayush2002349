// MainTest.java (src/test/java/edu/bhscs/MainTest.java)
package edu.bhscs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MainTest {

  // Student name
  // Class period
  // Project name
  // Date
  /*
   * TEST CLASS DESCRIPTION:
   *  - Verify Main.drawIt builds a right-angled staircase string.
   * TEST CASES:
   *  - height=3, stepChar='#' -> "#\n##\n###\n"
   *  - height=0 -> ""
   * EDGE CASE TESTS:
   *  - height<0 throws IllegalArgumentException
   */

  @Test
  void testConnected() {
    // Prints to the test runner’s output
    System.out.println("✅ Test connected for " + this.getClass().getSimpleName());
    // Always passes
    assert true;
  }

  @Disabled
  @Test
  @DisplayName("height=3 with '#': builds three lines 1..3")
  void drawIt_height3_hash() {
    String expected = "" + "#\n" + "##\n" + "###\n";
    // assertEquals(expected, Main.drawIt(3, '#'));
  }

  @Disabled
  @Test
  @DisplayName("height=0: empty string")
  void drawIt_height0() {
    // assertEquals("", Main.drawIt(0, '*'));
  }

  @Disabled
  @Test
  @DisplayName("negative height: throws IAE")
  void drawIt_negative_throws() {
    // assertThrows(IllegalArgumentException.class, () -> Main.drawIt(-1, '#'));
  }
}
