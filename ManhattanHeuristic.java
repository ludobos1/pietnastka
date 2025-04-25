public class ManhattanHeuristic implements IncrementalHeuristic {
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
  @Override
  public int calculateHeuristicInc(int newIndex, int zeroPos, int tile, int h) {
    int oldDist = Math.abs((tile-1)%boardSize-newIndex%boardSize) + Math.abs((tile-1)/boardSize-newIndex/boardSize);
    int newDist = Math.abs((tile-1)%boardSize - zeroPos%boardSize) + Math.abs((tile-1)/boardSize-zeroPos/boardSize);
    return h + (newDist - oldDist);
  }
}
