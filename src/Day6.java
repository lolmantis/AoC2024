import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Day6 {
  private static final boolean test = false;
  private static final boolean verbose = false;
  private static final String path = test ? "./testfiles/Day6test.txt" : "./testfiles/Day6.txt";
  private static final Integer gridSize = test ? 10 : 130; // 10: test, 130: actual
  private static Integer startX = null;
  private static Integer startY = null;

  private static final Integer[][] shifts = new Integer[][]{
    {-1, 0},
    {0, 1},
    {1,0},
    {0,-1}
  };

  private static final String[][] baseGrid;
  static {
    try {
      baseGrid = ReadFile();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private static String[][] ReadFile() throws FileNotFoundException {
    String[][] grid = new String[gridSize][gridSize];
    Scanner scanner = new Scanner(new File(path));
    int line = 0;
    while (scanner.hasNextLine()) {
      grid[line++] = scanner.nextLine().split("");
      if (startX == null) {
        for (int xCoord = 0; xCoord < gridSize; xCoord++) {
          if (grid[line - 1][xCoord].equals("^")) {
            startX = xCoord;
            startY = line - 1;
            break;
          }
        }
      }
    } scanner.close();
    return grid;
  }

  private static String characterDirection(Integer direction) {
    return switch (direction) {
      case 0 -> "^";
      case 1 -> ">";
      case 2 -> "v";
      case 3 -> "<";
      default -> "";
    };
  }

  private static Integer lookupDirection(String direction) {
    return switch (direction) {
      case "^" -> 0;
      case ">" -> 1;
      case "v" -> 2;
      case "<" -> 3;
      default -> -1;
    };
  }

  private static boolean onEdge(Integer xCoord, Integer yCoord, Integer direction) {
    if (
      yCoord == 0 && direction == 0
      || gridSize.equals(yCoord+1) && direction == 2
      || gridSize.equals(xCoord+1) && direction == 1
    ) {
      return true;
    }
    return xCoord == 0 && direction == 3;
  }

  private static void nightWatch(String[][] grid) {
    int moves = 0;
    int xCoord = startX;
    int yCoord = startY;
    int direction = lookupDirection(grid[yCoord][xCoord]);
    while (!onEdge(xCoord, yCoord, direction)) {
      try {
        int shiftVertical = shifts[direction][0], shiftHorizontal = shifts[direction][1];
        if (grid[yCoord + shiftVertical][xCoord + shiftHorizontal].equals("#")) { // turn
          direction = (direction + 1) % 4;
          grid[yCoord][xCoord] = characterDirection(direction);
          shiftVertical = shifts[direction][0];
          shiftHorizontal = shifts[direction][1];
        }
        xCoord += shiftHorizontal;
        yCoord += shiftVertical;
        grid[yCoord][xCoord] = grid[yCoord][xCoord].equals(".") ?
          characterDirection(direction) : grid[yCoord][xCoord];
      }
      catch (ArrayIndexOutOfBoundsException e) { // stepping outside grid
        if (onEdge(xCoord, yCoord, direction)) {
          break;
        }
      }
    }
    for (int i = 0; i < gridSize; i++) {
      for (int j = 0; j < gridSize; j++) {
        moves += grid[i][j].matches("[\\^><vO]") ? 1 : 0;
      }
    }
    System.out.println("total moves: " + moves);
  }

  private static void printGrid(String[][] grid) {
    for (int i = 0; i < gridSize; i++) {
      if (grid[i] != null) {
        System.out.println(Arrays.toString(grid[i]));
      }
    }
  }

  private static boolean findLoop(String[][] grid) {
    int xCoord = startX;
    int yCoord = startY;
    int direction = lookupDirection(grid[yCoord][xCoord]);
    HashMap<String, Integer> walkedPath = new HashMap<>();
    while (true) {
      int xShift = shifts[direction][1], yShift = shifts[direction][0];
      try {
//        printGrid(grid);
        walkedPath.putIfAbsent(xCoord + "," + yCoord, 1);
        if (grid[yCoord + yShift][xCoord + xShift].equals("#")) { // turn
          direction = (direction + 1) % 4;
          grid[yCoord][xCoord] = characterDirection(direction);
          continue;
        }
        if (walkedPath.get(xCoord + "," + yCoord) > 4) {
          if (verbose) {
            printGrid(grid);
            System.out.println();
          }
          return true;
        }
        walkedPath.put(xCoord + "," + yCoord, walkedPath.get(xCoord + "," + yCoord)+1);

        xCoord += xShift;
        yCoord += yShift;

        grid[yCoord][xCoord] = grid[yCoord][xCoord].equals(".") ?
          characterDirection(direction) : grid[yCoord][xCoord];
      }
      catch (ArrayIndexOutOfBoundsException e) {
        if (onEdge(xCoord, yCoord, direction)) {
          return false;
        }
      }
    }
  }

  public static void main(String[] args) {
    final String[][] selectorGrid = new String[gridSize][gridSize];
    for (int i = 0; i < gridSize; i++) {
      selectorGrid[i] = Arrays.copyOf(baseGrid[i], gridSize);
    }
    nightWatch(selectorGrid);

    final String[][] copyGrid = new String[gridSize][gridSize];

    int loops = 0;
    for (int xCoord = 0; xCoord < gridSize; xCoord++) {
      for (int yCoord = 0; yCoord < gridSize; yCoord++) {
        if (
          selectorGrid[yCoord][xCoord].matches("[\\^<>v]")
          && !(xCoord==startX && yCoord==startY)
        ) {
          for (int copy = 0; copy < gridSize; copy++) {
            copyGrid[copy] = Arrays.copyOf(baseGrid[copy], gridSize);
          }
          copyGrid[yCoord][xCoord] = "#";
          loops += findLoop(copyGrid) ? 1 : 0;
          copyGrid[yCoord][xCoord] = ".";
        }
      }
    }
    System.out.println("loops: " + loops);
  }
}
