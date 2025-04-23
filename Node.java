import java.util.*;

public class Node {
  int[] state;
  Node parent;
  int g;
  int f;

  public Node(int[] state, Node parent, int g, int f) {
    this.state = state;
    this.parent = parent;
    this.g = g;
    this.f = f;
  }

  @Override
  public boolean equals(Object o) {
    return Arrays.equals(state, ((Node) o).state);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(state);
  }
}

