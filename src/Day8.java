import java.util.*;
import java.util.regex.Pattern;

public class Day8 extends commons {

  Day8() {super("Day8", 14,34);}

  private int gridSize;

  @Override
  protected char[][] readFile() {
    Scanner reader = getInput();
    char[][] grid = new char[gridSize][gridSize];
    for (int row = 0; row < gridSize; row++) {
      grid[row] = reader.nextLine().toCharArray();
    }
    return grid;
  }

  private long findAntinodes(char[][] grid) {
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

            if (partTwo) {
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
    StringBuilder collapsedGrid = new StringBuilder();
    for (char[] row : grid) {
      collapsedGrid.append(row);
    }
    Pattern match = Pattern.compile("#");
    return match.matcher(collapsedGrid).results().count();
  }

  @Override
  public void Solve(boolean test, boolean partTwo) {
    Solve(test,partTwo, false);
  }

  @Override
  public void Solve(boolean test, boolean partTwo, boolean verbose) {
    super.Solve(test, partTwo, verbose);

    gridSize = test ? 12 : 50;
    char[][] grid = readFile();
    long tally = findAntinodes(grid);
    checkTest(tally);

    if (verbose) {
      for (char[] row : grid) {
        System.out.println(row);
      }
    }

    displayResult(tally);
  }
}
