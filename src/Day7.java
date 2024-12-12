import java.util.*;

public class Day7 extends commons {

  Day7() {super("Day7",3749,11387);}

  private Integer fileLength;

  @Override
  protected ArrayList<Long>[] readFile() {
    Scanner scanner = getInput();
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

  private boolean CalcSum(
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

  private Long validSums(ArrayList<Long>[] values) {
    long validOperatorTally = 0;
    for (ArrayList<Long> valueList : values) {
      long sum = valueList.getFirst();
      validOperatorTally +=
        CalcSum(sum, valueList.subList(1,valueList.size()),0, valueList.get(1), partTwo) ?
      sum : 0;
    }

    return validOperatorTally;
  }

  @Override
  public void Solve(boolean test, boolean partTwo) {
    Solve(test, partTwo, false);
  }

  @Override
  public void Solve(boolean test, boolean partTwo, boolean verbose) {
    super.Solve(test, partTwo, verbose);

    fileLength = test ? 9 : 850;
    ArrayList<Long>[] values = readFile();
    Long tally = validSums(values);

    checkTest(tally);
    displayResult(tally);

  }
}
