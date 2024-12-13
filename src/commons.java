import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

abstract class commons {


  protected boolean partTwo;
  protected boolean test;
  protected boolean verbose;
  protected long testResult1;
  protected long testResult2;
  protected final String day;

  commons(String day, long testResult1, long testResult2) {
    this.day = day;
    this.testResult1 = testResult1;
    this.testResult2 = testResult2;
  }

  protected Scanner getInput() {
    String path = (test ? "./inputs/%stest.txt" : "./inputs/%s.txt").formatted(day);
    File file = new File(path);
    Scanner scanner = null;
    try {scanner = new Scanner(file);} catch (FileNotFoundException e) {
      System.out.printf("couldn't find file: %s", path);
      System.exit(10);
    }
    return scanner;
  }

  protected void checkTest(long value) {
    if (!test) {
      System.out.println("Not a test: " + day);
      return;
    }
    if (
      !((!partTwo && (value == testResult1))
    || (partTwo && (value == testResult2)))
    ) {
      System.out.printf("%s Test %d failed.\n", day,(partTwo?2:1));
      System.exit(0);
    }
    System.out.printf("%s Test %d passed.\n", day,(partTwo?2:1));
  }

  protected abstract Object readFile();

  protected void displayResult(int value) {
    displayResult((long) value);
  }

  protected void displayResult(Long value) {
    System.out.printf("Test: %s Part %d: Value: %d\n", (test?"Y":"N"), partTwo?2:1, value);
  }

  public void Solve(boolean test, boolean partTwo) {
    this.test = test;
    this.partTwo = partTwo;
    this.verbose = false;
  }

  public void Solve(boolean test, boolean partTwo, boolean verbose) {
    this.test = test;
    this.partTwo = partTwo;
    this.verbose = verbose;
  }
}
