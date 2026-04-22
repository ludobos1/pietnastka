import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * A priority queue implementation based on buckets indexed by f-cost.
 *
 * Each bucket stores nodes with the same f value. Nodes are retrieved from the
 * lowest non-empty bucket, which makes this structure efficient when
 * f values are small integers within a known range.
 *
 * Buckets use LinkedHashSet to preserve insertion order and allow
 * fast add, remove, and membership checks.
 */
public class BucketQueue {

  /** List of buckets indexed by f value. */
  private List<LinkedHashSet<Node>> buckets;

  /** Current smallest f index that may contain elements. */
  private int minF = 0;

  /**
   * Creates a bucket queue capable of storing nodes with f values
   * from 0 up to maxF (inclusive).
   *
   * @param maxF maximum expected f value
   */
  public BucketQueue(int maxF) {
    buckets = new ArrayList<>(maxF);
    for (int i = 0; i <= maxF; i++) {
      buckets.add(new LinkedHashSet<>());
    }
  }

  /**
   * Adds a node to the queue based on its f value.
   *
   * @param node node to add
   */
  public void add(Node node) {
    buckets.get(node.f).add(node);
    if (node.f < minF) {
      minF = node.f;
    }
  }

  /**
   * Removes and returns a node with the smallest available f value.
   * If multiple nodes share the same f value, the one inserted first
   * is returned.
   *
   * @return node with minimal f, or null if the queue is empty
   */
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

  /**
   * Checks whether the queue contains no nodes.
   *
   * @return true if empty, false otherwise
   */
  public boolean isEmpty() {
    for (int i = minF; i < buckets.size(); i++) {
      if (!buckets.get(i).isEmpty()) return false;
    }
    return true;
  }

  /**
   * Removes a specific node from the queue.
   *
   * @param node node to remove
   */
  public void remove(Node node) {
    buckets.get(node.f).remove(node);
  }

  /**
   * Checks whether a node is present in the queue.
   *
   * @param node node to check
   * @return true if the node is in the queue
   */
  public boolean contains(Node node) {
    return buckets.get(node.f).contains(node);
  }
}