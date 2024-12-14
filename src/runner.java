public class runner {
  public static void main(String[] args) {

    Day13 current = new Day13();

    current.Solve(true, false, false);
    current.Solve(true,true, true);
    current.Solve(false,false, false);
    current.Solve(false,true);

  }
}
