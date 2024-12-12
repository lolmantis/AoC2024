import java.util.*;
import java.util.stream.Collectors;

public class Day11 extends commons{

  Day11() {super("Day11", 55312, 55312);}

  private final Map<String, Long> memory = new HashMap<>();

  @Override
  protected ArrayList<Long> readFile() {
      Scanner scanner = getInput();
      ArrayList<Long> result = Arrays.stream(scanner.nextLine().split(" "))
        .map(Long::parseLong).collect(Collectors.toCollection(ArrayList::new));
      scanner.close();
      return result;
    // it's one line
    }

  private Long BlinkAtStone(long NumberOfBlinks, long StoneValue) {
    String identifier = NumberOfBlinks + ":" + StoneValue;
    if (memory.containsKey(identifier)) {
      return memory.get(identifier);
    }
    if (NumberOfBlinks == 0) {
      return 1L;
    }
    if (StoneValue == 0) {
      long Stones = BlinkAtStone(NumberOfBlinks - 1, 1);
      memory.put(identifier, Stones);
      return Stones;
    }

    long digits = (long) Math.log10(StoneValue) + 1;
    if (digits %2 == 0) {
      long divisor = (long) Math.pow(10, digits >> 1);
      long left = StoneValue / divisor;
      long right = StoneValue % divisor;
      long CombinedStones = BlinkAtStone(NumberOfBlinks-1, left) +
        BlinkAtStone(NumberOfBlinks-1, right);
      memory.put(identifier, CombinedStones);
      return CombinedStones;
    }
    long Stones = BlinkAtStone(NumberOfBlinks-1, StoneValue*2024);
    memory.put(identifier, Stones);
    return Stones;
  }

  private Long ApplyRules(int duration, List<Long> listOfStones) {
    long tally = 0;
    for (long i : listOfStones) {
      tally += BlinkAtStone(duration, i);
    }
    return tally;
  }

  @Override
  public void Solve(boolean test, boolean partTwo) {
    Solve(test, partTwo, false);
  }

  @Override
  public void Solve(boolean test, boolean partTwo, boolean verbose) {
    super.Solve(test, partTwo);

    ArrayList<Long> listOfStones = readFile();
    Long total;
    int duration;
    if (partTwo && !test) {duration = 75;}
    else {duration = 25;}
    total = ApplyRules(duration, listOfStones);

    checkTest(total);
    displayResult(total);
  }
}
