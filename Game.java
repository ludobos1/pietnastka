import java.util.Random;

public class Game {
  public int size;
  public int[] tiles;
  public Game (int size){
    this.size = size*size;
  }
  public void generateGame(){
    Random rand = new Random();
    tiles = new int[size];
    for(int i = 0; i < size-1; i++){
      tiles[i] = i+1;
    }
    do {
      for (int i = size - 2; i > 0; i--) {
        int j = rand.nextInt(i + 1);
        int temp = tiles[i];
        tiles[i] = tiles[j];
        tiles[j] = temp;
      }
    } while (countInversions() % 2 != 0);
    tiles[size-1] = 0;
    System.out.println("premutacja początkowa:");
    printGame(tiles);
  }
  private int countInversions(){
    int inversions = 0;
    for(int i = 1; i < size-1; i++){
      for(int j = i-1; j >= 0; j--){
        if(tiles[j]>tiles[i]){
          inversions++;
        }
      }
    }
    return inversions;
  }
  public void printGame(int[] tiles){
    for(int i = 0; i < size; i++){
      System.out.print(tiles[i]+" ");
      if((i+1)%Math.sqrt(size)==0){
        System.out.println();
      }
    }
  }
}
