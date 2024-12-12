import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Day6 extends commons {
  Day6() {super("Day6",41,6);}

  private Integer gridSize;
  private Integer startX;
  private Integer startY;

  private final Integer[][] shifts = new Integer[][]{
    {-1, 0},
    {0, 1},
    {1,0},
    {0,-1}
  };

  @Override
  protected String[][] readFile() {
    String[][] grid = new String[gridSize][gridSize];
    Scanner scanner = getInput();
    int line = 0;
    startX = null;
    startY = null;

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

  private String characterDirection(Integer direction) {
    return switch (direction) {
      case 0 -> "^";
      case 1 -> ">";
      case 2 -> "v";
      case 3 -> "<";
      default -> "";
    };
  }

  private Integer lookupDirection(String direction) {
    return switch (direction) {
      case "^" -> 0;
      case ">" -> 1;
      case "v" -> 2;
      case "<" -> 3;
      default -> -1;
    };
  }

  private boolean onEdge(Integer xCoord, Integer yCoord, Integer direction) {
    if (
      yCoord == 0 && direction == 0
      || gridSize.equals(yCoord+1) && direction == 2
      || gridSize.equals(xCoord+1) && direction == 1
    ) {
      return true;
    }
    return xCoord == 0 && direction == 3;
  }

  private int nightWatch(String[][] grid) {
    int moves;
    int xCoord = startX;
    int yCoord = startY;
    int direction = lookupDirection(grid[yCoord][xCoord]);

    while (true) {

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
      catch (ArrayIndexOutOfBoundsException e) {
        // stepping outside grid
        if (verbose) {printGrid(grid);}

        if (onEdge(xCoord, yCoord, direction)) {break;}
      }
    }
    moves = (int) Arrays.stream(grid)
      .flatMap(Arrays::stream)
      .filter(x->x.matches("[\\^<>vO]"))
      .count();
    return moves;
  }

  private void printGrid(String[][] grid) {
    for (int i = 0; i < gridSize; i++) {
      if (grid[i] != null) {
        System.out.println(Arrays.toString(grid[i]));
      }
    }
    System.out.println();
  }

  private boolean findLoop(String[][] grid) {
    HashMap<String, Integer> walkedPath = new HashMap<>();
    int xCoord = startX;
    int yCoord = startY;
    int direction = lookupDirection(grid[yCoord][xCoord]);

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

  private int partTwo(String[][] baseGrid, String[][] selectorGrid) {
    String[][] copyGrid = new String[gridSize][gridSize];
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
    return loops;
  }

  @Override
  public void Solve(boolean test, boolean partTwo) {
    Solve(test, partTwo, false);
  }

  @Override
  public void Solve(boolean test, boolean partTwo, boolean verbose) {
    super.Solve(test, partTwo, verbose);

    gridSize = test ? 10 : 130;
    final String[][] baseGrid = readFile();

    String[][] selectorGrid = new String[gridSize][gridSize];
    for (int i = 0; i < gridSize; i++) {selectorGrid[i] = Arrays.copyOf(baseGrid[i], gridSize);}

    int tally = nightWatch(selectorGrid);

    if (partTwo) {tally = partTwo(baseGrid, selectorGrid);} // we use the selector grid to speed up part two

    checkTest(tally);
    displayResult(tally);
  }
}
