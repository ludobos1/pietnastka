/**
 * Heuristic for sliding tile puzzles
 * that uses the Manhattan distance.
 *
 * The Manhattan distance is the sum of vertical and horizontal
 * distances of each tile from its goal position.
 * The empty tile (0) is ignored.
 *
 * The board is represented as a one-dimensional array in row-major order.
 *
 * This implementation also supports incremental heuristic updates,
 * meaning the heuristic value can be efficiently updated after a move
 * without recalculating it for the entire board.
 */
public class ManhattanHeuristic implements IncrementalHeuristic {

  /** Size of one side of the square board (e.g., 3 for 3x3). */
  private final int boardSize;

  /**
   * Creates the heuristic for a board of the given size.
   *
   * @param boardSize width and height of the board
   */
  public ManhattanHeuristic(int boardSize) {
    this.boardSize = boardSize;
  }

  /**
   * Calculates the full Manhattan distance for a given board state.
   *
   * For each tile (except 0), the distance between its current
   * position and its goal position is computed and added to the sum.
   *
   * @param tiles board represented as a one-dimensional array
   * @return total Manhattan distance
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
    return heuristic;
  }

  /**
   * Updates the Manhattan distance incrementally after a single move.
   *
   * This method assumes that one tile has been moved into the position
   * previously occupied by the empty space.
   *
   * Instead of recalculating the heuristic for the entire board,
   * it adjusts the previous heuristic value by:
   * - subtracting the old Manhattan distance of the moved tile
   * - adding its new Manhattan distance
   *
   * @param newIndex index where the tile was before the move
   * @param zeroPos index where the tile moved to (previous empty position)
   * @param tile value of the moved tile
   * @param h previous heuristic value
   * @return updated heuristic value
   */
  @Override
  public int calculateHeuristicInc(int newIndex, int zeroPos, int tile, int h) {
    int oldDist = Math.abs((tile - 1) % boardSize - newIndex % boardSize)
            + Math.abs((tile - 1) / boardSize - newIndex / boardSize);

    int newDist = Math.abs((tile - 1) % boardSize - zeroPos % boardSize)
            + Math.abs((tile - 1) / boardSize - zeroPos / boardSize);

    return h + (newDist - oldDist);
  }
}