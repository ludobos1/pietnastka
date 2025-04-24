import java.util.*;

public class BucketQueue {
  private List<LinkedHashSet<Node>> buckets;
  private int minF=0;

  public BucketQueue() {
    buckets = new ArrayList<>(155);
    for (int i = 0; i <= 155; i++) {
      buckets.add(new LinkedHashSet<>());
    }
  }
  public void add(Node node) {
    buckets.get(node.f).add(node);
    if (node.f < minF) {
      minF = node.f;
    }
  }
  public Node poll() {
    while (minF < buckets.size() && buckets.get(minF).isEmpty()) {
      minF++;
    }
    if (minF >= buckets.size()) return null;

    Iterator<Node> it = buckets.get(minF).iterator();
    Node node = it.next();
    it.remove();
    return node;
  }
  public boolean isEmpty() {
    for (int i = minF; i < buckets.size(); i++) {
      if (!buckets.get(i).isEmpty()) return false;
    }
    return true;
  }
  public void remove(Node node) {
    buckets.get(node.f).remove(node);
  }
  public boolean contains(Node node) {
    return buckets.get(node.f).contains(node);
  }

}
