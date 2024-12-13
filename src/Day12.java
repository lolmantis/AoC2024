import java.util.HashSet;
import java.util.Scanner;

public class Day12 extends commons {

  public Day12() {super("Day12",1930,-1);}

  private int GridSize;
  private String[][] lines;
  private HashSet<Pair> visitedCoords;
  private final Integer[][] shifts = new Integer[][]{
    {-1, 0},
    {0, 1},
    {1,0},
    {0,-1}
  };

  @Override
  protected String[][] readFile() {
    Scanner sc = getInput();
    String[][] lines = new String[GridSize][GridSize];
    for (int i = 0; i < GridSize; i++) {
      lines[i] = sc.nextLine().split("");
    }
    return lines;
  }

  private String safeGet(Pair coord) {
    if (coord.getX() < 0 || coord.getX() >= GridSize || coord.getY() < 0 || coord.getY() >= GridSize) {
      return "";
    }
    return lines[coord.getY()][coord.getX()];
  }

  private Pair mapRegion(Pair coord) {
    int perimeter = 0, area = 1;
    visitedCoords.add(coord);
    String symbol = safeGet(coord);
    Pair perimeterArea;
    if (symbol.isEmpty()) {
      return new Pair(0, 0);
    }
    for (Integer[] shift : shifts) {
      Pair adjacency = new Pair(shift[0] + coord.getX(), shift[1] + coord.getY());
      if (!visitedCoords.contains(adjacency)) {
        if (!symbol.equals(safeGet(adjacency))) {
          perimeter++;
        }
        else if (symbol.equals(safeGet(adjacency))) {
          perimeterArea = mapRegion(adjacency);
          perimeter +=perimeterArea.getX();
          area +=perimeterArea.getY();
        }
      }
      else if (visitedCoords.contains(adjacency) && !symbol.equals(safeGet(adjacency))) {
        perimeter++;
      }
    }
    return new Pair(perimeter ,area);
  }

  private Pair partTwoVisit(Pair coord) {
    int edges = 0, area = 1;
    visitedCoords.add(coord);
    String symbol = safeGet(coord);
    Pair edgesArea;
    if (symbol.isEmpty()) {
      return new Pair(0,0);
    }

    for (int i = 0; i < 4; i++) {
      Pair adjacency = new Pair(
        shifts[i][0] + coord.getX(),
        shifts[i][1] + coord.getY());

      if (!visitedCoords.contains(adjacency)) {

        if (!symbol.equals(safeGet(adjacency))) {
          // check if this is  a corner, if yes add edges
          Pair left = new Pair(
            shifts[ (i==0 ? 3:i-1) ][0] + coord.getX(),
            shifts[ (i==0 ? 3:i-1) ][1] + coord.getY());
          Pair right = new Pair(
            shifts[ (i == 3 ? 0:i+1) ][0] + coord.getX(),
            shifts[ (i == 3 ? 0:i+1) ][1] + coord.getY()
          );
          if (!symbol.equals(safeGet(left)) || !symbol.equals(safeGet(right))) {
            // if left or right is a different symbol, not the edge case yet
            edges++;
          }
          // thowing in the towel for this one, you need to implement the following corner case:
          /*

          OX
          XX
          where you're checing the corner X to find that O
          i.e: the inner edges
          you also need to implement some sort of test for if an edge has already been looked at
          i.e: a history of adjacencies to track and prevent double adding of the same edge
          as well as some way to prevent two corners on the same line from adding the same edge
          this is fuckin' difficult my God

           */

//          if () {
//            edges++;
//          }
        }
        else if (symbol.equals(safeGet(adjacency))) {
          edgesArea = partTwoVisit(adjacency);
          edges +=edgesArea.getX();
          area +=edgesArea.getY();
        }
      }
      else if (visitedCoords.contains(adjacency) && !symbol.equals(safeGet(adjacency))) {

        if (!visitedCoords.contains(adjacency)) {

          if (!symbol.equals(safeGet(adjacency))) {
            // check if this is  a corner, if yes add edges
            Pair left = new Pair(
              shifts[(i == 0 ? 3 : i - 1)][0] + coord.getX(),
              shifts[(i == 0 ? 3 : i - 1)][1] + coord.getY());
            Pair right = new Pair(
              shifts[(i == 3 ? 0 : i + 1)][0] + coord.getX(),
              shifts[(i == 3 ? 0 : i + 1)][1] + coord.getY()
            );

            if (!symbol.equals(safeGet(left)) || !symbol.equals(safeGet(right))) {
              // if left or right is a different symbol, not the edge case yet
              edges++;
            }

//            if () {
//              edges++;
//            }

          }
        }
      }

    }

    return new Pair(edges, area);
  }

  private int exploreGrid() {
    int totalCost = 0;
    for (int yAxis = 0; yAxis < GridSize; yAxis++) {
      for (int xAxis = 0; xAxis < GridSize; xAxis++) {

        Pair coord = new Pair(xAxis, yAxis);

        if (!visitedCoords.contains(coord)) {

          Pair perimeterArea;
          if (!partTwo) {
            perimeterArea = mapRegion(coord);
          }
          else {
            perimeterArea = partTwoVisit(coord);
          }

          int localCost = perimeterArea.getX() * perimeterArea.getY();

          if (verbose) {
            System.out.printf("%s, Coords: %s\n", safeGet(coord), coord);
            System.out.printf(
              "Area: %d, %s: %d Cost: %d\n",perimeterArea.getY(), (partTwo?"Edges":"Area"),
              perimeterArea.getX(), localCost);
          }

          totalCost += localCost;
        }
      }
    }
    return totalCost;
  }

  @Override
  public void Solve(boolean test, boolean partTwo) {
    Solve(test, partTwo, false);
  }

  @Override
  public void Solve(boolean test, boolean partTwo, boolean verbose) {
    super.Solve(test, partTwo, verbose);

    GridSize = test ? 10 : 140;
    lines = readFile();
    visitedCoords = new HashSet<>();

    int totalCost = 0;
    if (!partTwo) {
      totalCost = exploreGrid();
    }
    if (partTwo) {
      totalCost = exploreGrid();
    }

    if (verbose) {
      System.out.printf("Total Cost: %d\n", totalCost);
    }

    checkTest(totalCost);
    displayResult(totalCost);

  }
}
