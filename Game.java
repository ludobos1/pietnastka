import java.util.Random;

/**
 * Represents a sliding tile puzzle board.
 *
 * The board is stored as a one-dimensional array in row-major order.
 * Tiles are numbered from 1 to N−1, and 0 represents the empty space.
 *
 * The game generator creates a random but solvable starting configuration
 * by shuffling the tiles and ensuring the number of inversions is even.
 */
public class Game {

  /** Total number of positions on the board (size × size). */
  public int size;

  /** Array representing the current board state. */
  public int[] tiles;

  /**
   * Creates a game for a board of given width/height.
   *
   * @param size board dimension (e.g., 3 for 3x3, 4 for 4x4)
   */
  public Game(int size) {
    this.size = size * size;
  }

  /**
   * Generates a random solvable puzzle configuration.
   *
   * Tiles 1..N−1 are shuffled randomly, while the last position
   * is set to the empty tile (0). The shuffle is repeated until
   * the permutation has an even number of inversions, which
   * guarantees solvability for standard puzzles with the blank
   * in the last position.
   *
   * The generated board is printed to the console.
   */
  public void generateGame() {
    Random rand = new Random();
    tiles = new int[size];

    // Fill with ordered tiles
    for (int i = 0; i < size - 1; i++) {
      tiles[i] = i + 1;
    }

    // Shuffle until solvable permutation is obtained
    do {
      for (int i = size - 2; i > 3; i--) {
        int j = rand.nextInt(i + 1);
        int temp = tiles[i];
        tiles[i] = tiles[j];
        tiles[j] = temp;
      }
    } while (countInversions() % 2 != 0);

    // Place empty tile
    tiles[size - 1] = 0;

    System.out.println("Initial permutation:");
    printGame(tiles);
  }

  /**
   * Counts the number of inversions in the current tile array.
   *
   * An inversion is a pair of tiles that are in the wrong order
   * relative to the goal state. The empty tile is ignored.
   *
   * @return number of inversions
   */
  private int countInversions() {
    int inversions = 0;

    for (int i = 1; i < size - 1; i++) {
      for (int j = i - 1; j >= 0; j--) {
        if (tiles[j] > tiles[i]) {
          inversions++;
        }
      }
    }

    return inversions;
  }

  /**
   * Prints the board in grid form.
   *
   * @param tiles board to print
   */
  public void printGame(int[] tiles) {
    for (int i = 0; i < size; i++) {
      System.out.print(tiles[i] + " ");
      if ((i + 1) % Math.sqrt(size) == 0) {
        System.out.println();
      }
    }
  }
}