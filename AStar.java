import java.util.*;

/**
 * Implementation of the A* search algorithm for solving sliding tile puzzles
 * (e.g., 8-puzzle, 15-puzzle).
 *
 * The algorithm searches for the shortest sequence of moves from a given
 * starting board to the solved configuration using an admissible heuristic.
 * This implementation uses an incremental heuristic to efficiently update
 * heuristic values after each move.
 *
 * The open set (frontier) is stored in a bucket-based priority queue ordered
 * by f = g + h. The closed set stores already visited states together with
 * the best known cost to reach them.
 */
public class AStar {

  /** Total number of positions on the board (size × size). */
  private final int boardSize;

  /** Heuristic used to estimate remaining cost. */
  private IncrementalHeuristic heuristic;

  /** Width/height of the board. */
  private int edge;

  /** Target solved state. */
  int[] solved;

  /** Encoded representation of the solved state. */
  long solvedKey;

  /** Number of neighbors generated during the search. */
  int neighboursGenerated = 0;

  /** Number of visited states (closed set size). */
  int visited;

  /** Number of generated neighbors that were actually added to open set. */
  int neighboursGeneratedAndSaved = 0;

  /** Maximum expected f value for the bucket queue (tuned for Manhattan). */
  final int manhattanMaxF = 155;

  /**
   * Creates an A* solver for a given game and heuristic.
   *
   * @param game puzzle definition
   * @param heuristic heuristic with incremental update support
   */
  public AStar(Game game, IncrementalHeuristic heuristic) {
    this.boardSize = game.size;
    this.heuristic = heuristic;
    edge = (int) Math.sqrt(boardSize);
  }

  /**
   * Solves the puzzle starting from the given state.
   *
   * @param tiles initial board state
   * @return list of board states from start to goal, or null if no solution found
   */
  public List<int[]> solve(int[] tiles) {

    // Build solved state
    solved = new int[boardSize];
    for (int i = 0; i < boardSize - 1; i++) {
      solved[i] = i + 1;
    }
    solved[boardSize - 1] = 0;

    solvedKey = Node.encode(solved);

    BucketQueue open = new BucketQueue(manhattanMaxF);
    Map<Long, Integer> closed = new HashMap<>();
    Map<Long, Node> openMap = new HashMap<>();

    int h = heuristic.calculateHeuristic(tiles);
    Node firstNode = new Node(tiles, null, 0, h, h, boardSize - 1);

    open.add(firstNode);
    openMap.put(firstNode.key, firstNode);

    while (!open.isEmpty()) {
      Node current = open.poll();
      openMap.remove(current.key);

      if (isSolved(current.key)) {
        visited = closed.size();
        return path(current);
      }

      closed.put(current.key, current.g);

      for (Node neighbor : findNeighbours(current)) {
        neighboursGenerated++;

        if (closed.containsKey(neighbor.key)
                && closed.get(neighbor.key) <= neighbor.g) {
          continue;
        }

        Node inOpen = openMap.get(neighbor.key);

        if (inOpen == null || neighbor.g < inOpen.g) {
          if (inOpen != null) {
            open.remove(inOpen);
            openMap.remove(neighbor.key);
          }

          neighboursGeneratedAndSaved++;
          open.add(neighbor);
          openMap.put(neighbor.key, neighbor);
        }
      }
    }

    return null;
  }

  /**
   * Reconstructs the solution path from the goal node back to the start.
   *
   * @param current goal node
   * @return ordered list of states from start to goal
   */
  private List<int[]> path(Node current) {
    Node temp = current;
    List<int[]> path = new LinkedList<>();

    do {
      path.addFirst(temp.state);
      temp = temp.parent;
    } while (temp != null);

    return path;
  }

  /**
   * Generates all valid neighboring states by moving the empty tile
   * in four possible directions (up, down, left, right).
   *
   * @param current current node
   * @return list of neighboring nodes
   */
  private List<Node> findNeighbours(Node current) {
    List<Node> neighbors = new ArrayList<>();

    int zeroIndex = current.zeroPos;
    int x = zeroIndex % edge;
    int y = zeroIndex / edge;

    int[][] moves = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} };

    for (int[] move : moves) {
      int nx = x + move[0];
      int ny = y + move[1];

      if (nx >= 0 && nx < edge && ny >= 0 && ny < edge) {
        int newIndex = ny * edge + nx;

        int[] newState = Arrays.copyOf(current.state, current.state.length);

        int tile = newState[newIndex];
        newState[zeroIndex] = tile;
        newState[newIndex] = 0;

        int g = current.g + 1;
        int h = heuristic.calculateHeuristicInc(newIndex, zeroIndex, tile, current.h);

        neighbors.add(new Node(newState, current, g, g + h, h, newIndex));
      }
    }

    return neighbors;
  }

  /**
   * Checks whether the given encoded state is the solved state.
   *
   * @param currStateKey encoded board state
   * @return true if solved
   */
  private boolean isSolved(long currStateKey) {
    return currStateKey == solvedKey;
  }
}