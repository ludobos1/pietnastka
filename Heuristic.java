public interface Heuristic {
  int calculateHeuristic(int[] tiles);
  int calculateHeuristicInc(int newIndex, int zeroIndex, int tile, int h);
}
