import java.util.List;

public class TestNonInc {
  public static void main(String[] args) {
    int times;
    int lengths;
    int neighboursGenerated;
    int neighboursGeneratedAndSaved;
    int statesVisited;
    long startTime = System.currentTimeMillis();
    Game game = new Game(4);
    game.generateGame();
    Heuristic linear= new LinearConflictHeuristic(4);
    AStarNonIncremental solver = new AStarNonIncremental(game, linear);

    List<int[]> solution = solver.solve(game.tiles);

    for (int[] step : solution) {
      System.out.println("============");
      game.printGame(step);
    }
    lengths = solution.size() - 1;
    long endTime = System.currentTimeMillis();
    times = (int) (endTime - startTime) / 1000;
    neighboursGenerated = solver.neighboursGenerated;
    neighboursGeneratedAndSaved = solver.neighboursGeneratedAndSaved;
    statesVisited = solver.visited;
    System.out.println("czas:");
    System.out.println(times);
    System.out.println("utworzone stany:");
    System.out.println(neighboursGenerated);
    System.out.println("utworzone stany i zapisane do kolejki:");
    System.out.println(neighboursGeneratedAndSaved);
    System.out.println("stany odwiedzone:");
    System.out.println(statesVisited);
    System.out.println("długość rozwiązania:");
    System.out.println(lengths);
  }
}
