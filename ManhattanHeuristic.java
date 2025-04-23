public class ManhattanHeuristic implements Heuristic {
  private final int boardSize;

  public ManhattanHeuristic(int boardSize) {
    this.boardSize = boardSize;
  }

  @Override
  public int calculateHeuristic(int[] tiles) {
    int heuristic = 0;
    for (int start = 0; start < tiles.length; start++) {
      int end = tiles[start]-1;
      if(tiles[start] == 0){ continue; }
      heuristic+=Math.abs(end % boardSize - start % boardSize) + Math.abs(end / boardSize - start / boardSize);
    }
    return heuristic;
  }
}
