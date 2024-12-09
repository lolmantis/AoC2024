import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day7 {
  private static final boolean test = false;
  private static final boolean test2 = false;
  private static final boolean partTwo = true;
  private static final String path = test||test2 ?
    "./testfiles/Day7test.txt" : "./testfiles/Day7.txt";
  private static final Integer fileLength = test||test2 ? 9 : 850;
  private static final Integer bvtestvalue = 3749;
  private static final Integer testvalue2 = 11387;

  private static final ArrayList<Long>[] values;
  static {
    try {
      values = ReadFile();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private static ArrayList<Long>[] ReadFile() throws FileNotFoundException {
    Scanner scanner = new Scanner(new File(path));
    ArrayList<Long>[] values = new ArrayList[fileLength];
    int i = 0;
    while (scanner.hasNextLine()) {
      values[i] = new ArrayList<>();
      values[i++].addAll(
        Arrays.stream(scanner.nextLine().replace(": "," ").split(" "))
          .map(Long::parseLong).toList());
    } scanner.close();
    return values;
  }

  private static boolean CalcSum(
    long target, List<Long> values,
    int step, long current,
    boolean partTwo) {

    if (++step >= values.size()) {return target == current;}
    long next = values.get(step);
    if (partTwo) {
    return CalcSum(target, values, step,current+next, partTwo)
      || CalcSum(target, values, step,current*next, partTwo)
      || CalcSum(target, values, step, Long.parseLong(current+""+next), partTwo);
    }
    else {
      return CalcSum(target, values, step,current+next, partTwo)
        || CalcSum(target, values, step,current*next, partTwo);
    }
  }

  private static void validSums() {
    long validOperatorTally = 0;
    for (ArrayList<Long> valueList : values) {
      long sum = valueList.getFirst();
      validOperatorTally +=
        CalcSum(sum, valueList.subList(1,valueList.size()),0, valueList.get(1), partTwo) ?
      sum : 0;
    }
//    System.out.println("test 1: "+(test  && (validOperatorTally == testvalue )));
//    System.out.println("test 2: "+(test2 && (validOperatorTally == testvalue2)));
    System.out.println(validOperatorTally);
  }

  public static void main(String[] args) {
    validSums();
  }
}
