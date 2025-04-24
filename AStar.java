import java.util.*;

public class AStar {
  private final int boardSize;
  private Heuristic heuristic;
  private int edge;
  int[] solved;
  long solvedKey;
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
    //solvedKey = Arrays.toString(solved);
    this.solvedKey = Node.encode(solved);
    Comparator<Node> cmp = Comparator
            .comparingInt((Node n) -> n.f)
            .thenComparingInt(n -> -n.g);
    BucketQueue open = new BucketQueue();
    Set<Long> closed = new HashSet<>();
    Map<Long, Node> openMap = new HashMap<>();
    int h = heuristic.calculateHeuristic(tiles);
    Node firstNode = new Node(tiles, null, 0, h, h, boardSize-1);
    open.add(firstNode);
    openMap.put(firstNode.key, firstNode);
    while(!open.isEmpty()){
      Node current = open.poll();
      openMap.remove(current.key);
      if(isSolved(current.key)){
        return path(current);
      }
      closed.add(current.key);
      for(Node neighbor : findNeighbours(current)){
        if (closed.contains(neighbor.key)) continue;
        Node inOpen = openMap.get(neighbor.key);
        if (inOpen == null || neighbor.g < inOpen.g) {
          if (inOpen != null) {
            open.remove(inOpen);
            openMap.remove(neighbor.key);
          }
          open.add(neighbor);
          openMap.put(neighbor.key, neighbor);
        }
      }
    }
    return null;
  }

  private List<int[]> path(Node current){
    Node temp = current;
    List<int[]> path = new LinkedList<>();
    do {
      path.addFirst(temp.state);
      temp = temp.parent;
    }while(temp!=null);
    return path;
  }

  private List<Node> findNeighbours(Node current){
    List<Node> neighbors = new ArrayList<>();
    int zeroIndex = current.zeroPos;
    int x = zeroIndex % edge;
    int y = zeroIndex / edge;
    int[][] moves = { {0,1}, {1,0}, {0,-1}, {-1,0} };

    for (int[] move : moves) {
      int nx = x + move[0];
      int ny = y + move[1];
      if (nx >= 0 && nx < edge && ny >= 0 && ny < edge) {
        int newIndex = ny * edge + nx;
        int[] newState = Arrays.copyOf(current.state, current.state.length);
        int tile =newState[newIndex];
        newState[zeroIndex] = tile;
        newState[newIndex] = 0;
        int g = current.g + 1;
        int h = heuristic.calculateHeuristicInc(newIndex,zeroIndex,tile,current.h);
        neighbors.add(new Node(newState, current, g, g + h,h,newIndex));
      }
    }
    return neighbors;
  }

  private boolean isSolved(long currStateKey){
    return currStateKey==solvedKey;
  }
}