public interface IncrementalHeuristic extends Heuristic {
  int calculateHeuristicInc(int newIndex, int zeroIndex, int tile, int h);
}
