import java.util.List;

public class Test {
  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();
    Game game = new Game(4);
    game.generateGame();
    Heuristic manhattan = new ManhattanHeuristic(4);
    AStar solver = new AStar(game, manhattan);

    List<int[]> solution = solver.solve(game.tiles);

    for (int[] step : solution) {
      System.out.println("============");
      game.printGame(step);
    }
    System.out.println(solution.size());
    long endTime = System.currentTimeMillis();
    System.out.println("Time taken: " + (endTime - startTime)/1000 + "s");
  }
}
