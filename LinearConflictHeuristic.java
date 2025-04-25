public class LinearConflictHeuristic implements Heuristic{
  final int boardSize;
  public LinearConflictHeuristic(int boardSize){
    this.boardSize = boardSize;
  }
  public int calculateHeuristic(int[] tiles) {
    int heuristic = 0;
    for (int start = 0; start < tiles.length; start++) {
      int end = tiles[start]-1;
      if(tiles[start] == 0){ continue; }
      heuristic+=Math.abs(end % boardSize - start % boardSize) + Math.abs(end / boardSize - start / boardSize);
    }
    heuristic+=calculateConflicts(tiles)*2;
    return heuristic;
  }
  private int calculateConflicts(int[] tiles){
    int conflicts = 0;
    for (int start = 0; start < tiles.length; start++) {
      int row = start / boardSize;
      int col = start % boardSize;
      if(tiles[start] == 0){ continue; }
      for (int end = col + 1; end < boardSize; end++) {
        int ind1 = row * boardSize + end;
        int ind2 = row * boardSize + col;
        int t1 = tiles[ind1];
        int t2 = tiles[ind2];
        if(t1 == 0){ continue; }
        if((t1-1)/boardSize == (t2-1)/boardSize && t1<t2){
          conflicts++;
        }
      }
      for (int end = row + 1; end < boardSize; end++) {
        int ind1 = col + end*boardSize;
        int ind2 = col + row*boardSize;
        int t1 = tiles[ind1];
        int t2 = tiles[ind2];
        if(t1 == 0){ continue; }
        if((t1-1)%boardSize == (t2-1)%boardSize && t1<t2){
          conflicts++;
        }
      }
    }
    return conflicts;
  }
}
