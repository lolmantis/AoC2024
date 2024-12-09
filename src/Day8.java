import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day8 {
  private static final boolean PartTwo = true;

  private static final boolean Test1 = false;
  private static final boolean Test2 = false;
  private static final int testValueOne = 14;
  private static final int testValueTwo = 34;
  private static final String path = Test1||Test2 ? "./testfiles/Day8Test.txt" : "./testfiles/Day8.txt";
  private static final int gridSize = Test1||Test2 ? 12 : 50;

  private static final char[][] grid;
  static {
    try {
      grid = readFile();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private static char[][] readFile() throws FileNotFoundException {
    Scanner reader = new Scanner(new File(path));
    char[][] grid = new char[gridSize][gridSize];
    for (int row = 0; row < gridSize; row++) {
      grid[row] = reader.nextLine().toCharArray();
    }
    return grid;
  }

  private static void findAntinodes() {

    HashMap<Character, ArrayList<Integer[]>> characters = new HashMap<>();
    for (int y = 0; y < gridSize; y++) {
      for (int x = 0; x < gridSize; x++) {
        if (grid[y][x] != '.') {
          characters.putIfAbsent(grid[y][x], new ArrayList<>());
          characters.get(grid[y][x]).add(new Integer[]{x,y});
        }
      }
    }
    for (char character : characters.keySet()) {
      for (int first = 0; first < characters.get(character).size(); first++) {
        for (int second = 0; second < characters.get(character).size(); second++) {
          if (first != second) {
            Integer[] firstPair = characters.get(character).get(first);
            Integer[] secondPair = characters.get(character).get(second);

            Integer X1 = firstPair[0];
            Integer X2 = secondPair[0];
            int displacementX = X1 - X2;

            Integer Y1 = firstPair[1];
            Integer Y2 = secondPair[1];
            int displacementY = Y1 - Y2;

            int antiNodeX = X1 + (displacementX);
            int antiNodeY = Y1 + (displacementY);

            if (
              antiNodeX < gridSize
                && antiNodeY < gridSize
                && antiNodeX >= 0
                && antiNodeY >= 0
            ) {
              grid[antiNodeY][antiNodeX] = '#';
            }

            if (PartTwo) {
              grid[Y1][X1] = '#';
              while (
                antiNodeX+displacementX < gridSize
                  && antiNodeY+displacementY < gridSize
                  && antiNodeX+displacementX >= 0
                  && antiNodeY+displacementY >= 0
              ) {
                antiNodeX += displacementX;
                antiNodeY += displacementY;
              grid[antiNodeY][antiNodeX] = '#';
              }
            }
          }
        }
      }
    }
  }


  public static void main(String[] args) {

  findAntinodes();
    for (char[] row : grid) {
      System.out.println(row);
    }
    int tally = 0;
    for (char[] row : grid) {
      for (char c : row) {
        tally += c=='#' ? 1 : 0;
      }
    }
    System.out.println(tally);
  }
}
