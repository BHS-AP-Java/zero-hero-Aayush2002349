package edu.bhscs;

public interface Offsetable {
  public int getWidth();

  // Default centering logic — always clamps to 0 so alignment never goes negative.
  default public int getOffset(Offsetable other) {
    if (other == null) return 0;
    return Math.max(0, (other.getWidth() - this.getWidth()));
  }

  // Each Offsetable thing knows how to draw itself
  // relative to whatever is beneath it.
  public void draw(Offsetable other);
}
