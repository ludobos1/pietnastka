/**
 * Heuristic for sliding tile puzzles
 * that combines Manhattan distance with linear conflict.
 *
 * For each tile, the Manhattan distance from its goal position
 * is calculated. Then linear conflicts are detected — pairs of
 * tiles in the same row or column that belong there in the goal
 * state but are in the wrong order.
 *
 * Each conflict adds 2 extra moves to the heuristic value.
 * The empty tile is represented by 0 and is ignored.
 *
 * The board is stored as a one-dimensional array in row-major order.
 */
public class LinearConflictHeuristic implements Heuristic {

  /** Size of one side of the square board (e.g., 3 for 3x3, 4 for 4x4). */
  final int boardSize;

  /**
   * Creates the heuristic for a board of the given size.
   *
   * @param boardSize width and height of the board
   */
  public LinearConflictHeuristic(int boardSize) {
    this.boardSize = boardSize;
  }

  /**
   * Calculates the heuristic value for a given board state.
   *
   * First, it sums the Manhattan distances of all tiles
   * from their goal positions (excluding the empty tile).
   * Then it adds twice the number of linear conflicts.
   *
   * @param tiles array representing the board in row-major order,
   *              where 0 represents the empty space
   * @return estimated number of moves to reach the goal state
   */
  @Override
  public int calculateHeuristic(int[] tiles) {
    int heuristic = 0;
    for (int start = 0; start < tiles.length; start++) {
      int end = tiles[start] - 1;
      if (tiles[start] == 0) { continue; }
      heuristic += Math.abs(end % boardSize - start % boardSize)
              + Math.abs(end / boardSize - start / boardSize);
    }
    heuristic += calculateConflicts(tiles) * 2;
    return heuristic;
  }

  /**
   * Counts linear conflicts in the board.
   *
   * A conflict occurs when two tiles are in the same row or column,
   * both belong in that row or column in the goal state,
   * but are reversed relative to their goal order.
   *
   * @param tiles board state
   * @return number of detected conflicts
   */
  private int calculateConflicts(int[] tiles) {
    int conflicts = 0;
    for (int start = 0; start < tiles.length; start++) {
      int row = start / boardSize;
      int col = start % boardSize;
      if (tiles[start] == 0) { continue; }

      // Check row conflicts (to the right)
      for (int end = col + 1; end < boardSize; end++) {
        int ind1 = row * boardSize + end;
        int ind2 = row * boardSize + col;
        int t1 = tiles[ind1];
        int t2 = tiles[ind2];
        if (t1 == 0) { continue; }
        if ((t1 - 1) / boardSize == (t2 - 1) / boardSize && t1 < t2) {
          conflicts++;
        }
      }

      // Check column conflicts (below)
      for (int end = row + 1; end < boardSize; end++) {
        int ind1 = col + end * boardSize;
        int ind2 = col + row * boardSize;
        int t1 = tiles[ind1];
        int t2 = tiles[ind2];
        if (t1 == 0) { continue; }
        if ((t1 - 1) % boardSize == (t2 - 1) % boardSize && t1 < t2) {
          conflicts++;
        }
      }
    }
    return conflicts;
  }
}