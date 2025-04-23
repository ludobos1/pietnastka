import java.util.*;

public class AStar {
  private final int boardSize;
  private Heuristic heuristic;
  private int edge;
  int[] solved;
  public AStar(Game game, Heuristic heuristic) {
    this.boardSize = game.size;
    this.heuristic = heuristic;
    edge = (int) Math.sqrt(boardSize);
  }
  public List<int[]> solve(int[] tiles){
    solved = new int[boardSize];
    for(int i = 0; i < boardSize-1; i++){
      solved[i] = i+1;
    }
    solved[boardSize-1] = 0;
    PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
    Set<Node> closed = new HashSet<>();
    Node firstNode = new Node(tiles, null, 0, heuristic.calculateHeuristic(tiles));
    open.add(firstNode);
    while(!open.isEmpty()){
      Node current = open.poll();
      if(isSolved(current.state)){
        return path(current);
      }
      closed.add(current);
      for(Node neighbor : findNeighbours(current)){
        if (closed.contains(neighbor)) continue;
        Optional<Node> existing = open.stream().filter(n -> Arrays.equals(n.state, neighbor.state)).findFirst();
        if (existing.isEmpty() || neighbor.g < existing.get().g) {
          open.remove(existing.orElse(null));
          open.add(neighbor);
        }
      }
    }
    return null;
  }

  private List<int[]> path(Node current){
    Node temp = current;
    List<int[]> path = new LinkedList<>();
    do {
      path.add(temp.state);
      temp = temp.parent;
    }while(temp!=null);
    return path;
  }

  private List<Node> findNeighbours(Node current){
    List<Node> neighbors = new ArrayList<>();
    int zeroIndex = findZero(current.state);
    int x = zeroIndex % edge;
    int y = zeroIndex / edge;
    int[][] moves = { {0,1}, {1,0}, {0,-1}, {-1,0} };

    for (int[] move : moves) {
      int nx = x + move[0];
      int ny = y + move[1];
      if (nx >= 0 && nx < edge && ny >= 0 && ny < edge) {
        int newIndex = ny * edge + nx;
        int[] newState = Arrays.copyOf(current.state, current.state.length);
        newState[zeroIndex] = newState[newIndex];
        newState[newIndex] = 0;
        int g = current.g + 1;
        int h = heuristic.calculateHeuristic(newState);
        neighbors.add(new Node(newState, current, g, g + h));
      }
    }
    return neighbors;
  }

  private boolean isSolved(int[] currState){
    return Arrays.equals(currState, solved);
  }
  private int findZero(int[] state) {
    for (int i = 0; i < state.length; i++)
      if (state[i] == 0) return i;
    return -1;
  }

}
