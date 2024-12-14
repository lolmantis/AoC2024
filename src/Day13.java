import java.util.Arrays;
import java.util.Scanner;

public class Day13 extends commons {
  
  public Day13() {super("Day13",480,-10000000000000000L);}
  
  @Override
  protected Long readFile() {
    Scanner reader = getInput();
    long  A = 0, B = 0, Ax = 0, Ay = 0, Bx = 0, By = 0, Cx = 0, Cy = 0, finalValue = 0;
        /* Cx = Ax*A +Bx*B
           Cy = Ay*A + By*B
          solve for A & B, given Cx, Ax, & Bx
          
          A = Cx -B*Bx / Ax
          A = Cy -B*By / Ay
          
          Ay * (Cx -B*Bx) = Ax * (Cy -B*By)
          Cx*Ay -B*Bx*Ay = Ax*Cy -B*By*Ax
          Cx*Ay -Ax*Cy = B*Bx*Ay -B*By*Ax
          Cx*Ay -Ax*Cy = B(Bx*Ay -By*Ax)
          B = (Cx*Ay -Ax*Cy)/(Bx*Ay -By*Ax)
          
          then sub in for A
        */
    while (reader.hasNextLine()) {
      Integer[] line1 = Arrays.stream(reader.nextLine().replaceAll("[^\\d. ]", "")
        .strip().split(" ")).map(Integer::parseInt).toArray(Integer[]::new);
      Integer[] line2 = Arrays.stream(reader.nextLine().replaceAll("[^\\d. ]", "")
        .strip().split(" ")).map(Integer::parseInt).toArray(Integer[]::new);
      Integer[] line3 = Arrays.stream(reader.nextLine().replaceAll("[^\\d. ]", "")
        .strip().split(" ")).map(Integer::parseInt).toArray(Integer[]::new);
      reader.nextLine();
      long tokenCost = 0;
      Ax = line1[0];
      Ay = line1[1];
      Bx = line2[0];
      By = line2[1];
      Cx = partTwo ? line3[0]+10000000000000L : line3[0];
      Cy = partTwo ? line3[1]+10000000000000L : line3[1];
      
      B = ((Cx*Ay) -(Ax*Cy))/((Bx*Ay) -(By*Ax));
      A = (Cx -(B*Bx))/Ax;
      if (((A*Ax +B*Bx) == Cx) && ((A*Ay +B*By) == Cy)) {
        tokenCost = A*3 +B;
        finalValue += tokenCost;
      }
    } reader.close();
    return finalValue;
  }
  
  @Override
  public void Solve(boolean test, boolean partTwo) {
    Solve(test, partTwo, false);
  }
  
  @Override
  public void Solve(boolean test, boolean partTwo, boolean verbose) {
    super.Solve(test, partTwo, verbose);
    
    long tally = readFile();
    
    checkTest(tally);
    displayResult(tally);
  }
}
