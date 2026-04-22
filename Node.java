import java.util.*;

public class Node {
  final int[] state;
  final Node parent;
  final int g;
  final int f;
  final long key;
  final int h;
  final int zeroPos;

  public Node(int[] state, Node parent, int g, int f, int h, int zeroPos) {
    this.state = state;
    this.parent = parent;
    this.g = g;
    this.f = f;
    this.key = encode(state);
    this.h = h;
    this.zeroPos = zeroPos;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    return Arrays.equals(state, ((Node) o).state);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(state);
  }
  public static long encode(int[] state){
    long code = 0L;
    for(int i = 0; i < state.length; i++){
      code |= ((long)state[i] & 0xF) << (4*i);
    }
    return code;
  }

}

