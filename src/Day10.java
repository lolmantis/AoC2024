import java.util.*;

public class Day10 extends commons{

  Day10() {super("Day10",36,81);}

  private static final int MAX_PATH_LENGTH = 9;
  private int gridSize;
  private Integer[][] grid = null;

  @Override
  protected Integer[][] readFile() {
    Integer[][] grid = new Integer[gridSize][gridSize];
    Scanner scanner = getInput();
    int currentStep = 0;
    while (scanner.hasNextLine()) {
      grid[currentStep++] = Arrays.stream(scanner.nextLine().split(""))
        .map(Integer::parseInt).toArray(Integer[]::new);
    } scanner.close();
    return grid;
  }

  private boolean safeGridComparison(int X, int Y, int Var) {
    if (X >= gridSize || X < 0 || Y >= gridSize || Y < 0 || Var > MAX_PATH_LENGTH) {
      return false;
    }
    return grid[Y][X] == Var;
  }

  private void WalkTrail(
    int gridX, int gridY,
    int step, HashSet<Pair> found, List<Pair> paths) {

    if (safeGridComparison(gridX, gridY, MAX_PATH_LENGTH)
      && !found.contains(new Pair(gridX, gridY)) && !partTwo
    ) {
      found.add(new Pair(gridX, gridY));
      if (verbose) {
        System.out.printf("Pt.1: Found a new path! Coords: (%d, %d)\n", gridX, gridY);
      }
    }
    else if (safeGridComparison(gridX, gridY, MAX_PATH_LENGTH)
      && partTwo) {
      paths.add(new Pair(gridX, gridY));
      if (verbose) {
        System.out.printf("Pt.2: Found a new path! Coords: (%d, %d)\n", gridX, gridY);
      }
    }

    if (safeGridComparison(gridX, gridY+1, step+1) && step < 9) {
      WalkTrail(gridX, gridY+1, step+1, found, paths);
    }
    if (safeGridComparison(gridX, gridY-1, step+1) && step < 9) {
      WalkTrail(gridX, gridY-1, step+1, found, paths);
    }
    if (safeGridComparison(gridX-1,gridY,step+1) && step < 9) {
      WalkTrail(gridX-1, gridY, step+1, found, paths);
    }
    if (safeGridComparison(gridX+1,gridY,step+1) && step < 9) {
      WalkTrail(gridX+1, gridY, step+1, found, paths);
    }
  }

  private int walkTrail(boolean partTwo) {
    int FinalTally = 0;

    for (int gridY = 0; gridY < gridSize; gridY++) {
      for (int gridX = 0; gridX < gridSize; gridX++) {
        if (safeGridComparison(gridX, gridY, 0)) {
          HashSet<Pair> found = new HashSet<>();
          List<Pair> paths = new ArrayList<>();
          int localTally;
          WalkTrail(gridX, gridY, 0, found, paths);
          if (!partTwo) {
            localTally = found.size();
          }
          else {
            localTally = paths.size();
          }
          if (verbose) {
            System.out.printf("(%d, %d) Trails: %d%n\n", gridX, gridY, localTally);
          }
          FinalTally += localTally;
        }
      }
    }
    return FinalTally;
  }

  @Override
  public void Solve(boolean test, boolean partTwo) {
    Solve(test, partTwo, false);
  }

  @Override
  public void Solve(boolean test, boolean partTwo, boolean verbose) {
    super.Solve(test, partTwo, verbose);

    gridSize = test ? 8 : 47;
    grid = readFile();

    int FinalTally = walkTrail(partTwo);
    if (test) {checkTest(FinalTally);}
    System.out.println("Part "+(partTwo ? 2:1)+": Total trails: "+FinalTally);
  }
}
