import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day10 {
  private static final boolean PartTwo = true;
  private static final boolean Test1 = false;
  private static final boolean Test2 = false;
  private static final int testValue1 = 36;
  private static final int testValue2 = 81;

  private static final String Path = Test1||Test2 ?
    "./testfiles/Day10Test.txt" : "./testfiles/Day10.txt";

  private static final int GridSize = Test1||Test2 ? 8 : 47;

  private static final Integer[][] grid;

  static {
    try {
      grid = ReadFile();
    }
    catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private static boolean safeGridComparison(int X, int Y, int Var) {
    if (X >= GridSize || X < 0 || Y >= GridSize || Y < 0 || Var > 9) {
      return false;
    }
    return grid[Y][X] == Var;
  }

  private static Integer[][] ReadFile() throws FileNotFoundException {
    Integer[][] grid = new Integer[GridSize][GridSize];
    Scanner scanner = new Scanner(new File(Path));
    int currentStep = 0;
    while (scanner.hasNextLine()) {
      grid[currentStep++] = Arrays.stream(scanner.nextLine().split(""))
        .map(Integer::parseInt).toArray(Integer[]::new);
    } scanner.close();
    return grid;
  }

  private static void WalkTrail(
    int gridX, int gridY,
    int step, HashSet<Pair> found, List<Pair> partTwo) {

    if (safeGridComparison(gridX, gridY, 9)
      && !found.contains(new Pair(gridX, gridY)) && !PartTwo
    ) {
      System.out.println("Pt1: Found a new path! Coords: ("+gridX+", "+gridY+")");
      found.add(new Pair(gridX, gridY));
    }
    else if (safeGridComparison(gridX, gridY, 9)
    && PartTwo) {
      System.out.println("Pt2: Found a new path! Coords: ("+gridX+", "+gridY+")");
      partTwo.add(new Pair(gridX, gridY));
    }

    if (safeGridComparison(gridX, gridY+1, step+1) && step < 9) {
      WalkTrail(gridX, gridY+1, step+1, found, partTwo);
    }
    if (safeGridComparison(gridX, gridY-1, step+1) && step < 9) {
      WalkTrail(gridX, gridY-1, step+1, found, partTwo);
    }
    if (safeGridComparison(gridX-1,gridY,step+1) && step < 9) {
      WalkTrail(gridX-1, gridY, step+1, found, partTwo);
    }
    if (safeGridComparison(gridX+1,gridY,step+1) && step < 9) {
      WalkTrail(gridX+1, gridY, step+1, found, partTwo);
    }
  }

  private static int PartOne() {
    int FinalTally = 0;

    for (int gridY = 0; gridY < GridSize; gridY++) {
      for (int gridX = 0; gridX < GridSize; gridX++) {
        if (safeGridComparison(gridX, gridY, 0)) {
          HashSet<Pair> found = new HashSet<>();
          List<Pair> partTwo = new ArrayList<>();
          int localTally;
          WalkTrail(gridX, gridY, 0, found, partTwo);
          if (!PartTwo) {
            localTally = found.size();
          }
          else {
            localTally = partTwo.size();
          }
          System.out.println("("+gridX + ", " + gridY + ") Trails: " + localTally);
          FinalTally += localTally;
        }
      }
    }
    return FinalTally;
  }

  public static void main(String[] args) {
    int FinalTally = PartOne();
    System.out.println("Total trails: "+FinalTally);
  }

  static class Pair {
    private final Integer X;
    private final Integer Y;

    Pair(Integer X, Integer Y) {
      this.X = X;
      this.Y = Y;
    }

    public boolean ComparePair(Pair p) {
      return X.equals(p.getX()) && Y.equals(p.getY());
    }
    public Integer getX() {
      return X;
    }
    public Integer getY() {
      return Y;
    }

    @Override
    public String toString() {
      return "{%d, %d}".formatted(X, Y);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) {
        return true;
      }
      if (obj == null) {
        return false;
      }

      if (obj instanceof Pair) {
        return this.ComparePair((Pair) obj);
      }
      return false;
    }

    @Override
    public int hashCode() {
      return Objects.hash(X, Y);
    }
  }
}
