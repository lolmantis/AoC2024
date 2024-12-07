import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day5 {
  private static final String path = "./testfiles/Day5.txt";

  private static final Hashtable<Integer,HashSet<Integer>> rules;
  static {
    try {
      rules = ReadRules();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
  private static final ArrayList<ArrayList<Integer>> pageOrders;
  static {
    try {
      pageOrders = ReadPages();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private static Hashtable<Integer,HashSet<Integer>> ReadRules() throws FileNotFoundException {
    Scanner reader = new Scanner(new File(path));
    Hashtable<Integer, HashSet<Integer>> rules = new Hashtable<>();
    while (reader.hasNextLine()) {
      String line = reader.nextLine();
      if (line.isEmpty()) {
        break;
      }
      String[] values = line.split("\\|");
      Integer key = Integer.parseInt(values[0]);
      Integer value = Integer.parseInt(values[1]);
      HashSet<Integer> rule = rules.get(key);
      if (rule == null) {
        rule = new HashSet<>();
      }
      rule.add(value);
      rules.put(key, rule);
    } reader.close();
    return rules;
  }

  private static ArrayList<ArrayList<Integer>> ReadPages() throws FileNotFoundException {
    Scanner reader = new Scanner(new File(path));
    ArrayList<ArrayList<Integer>> pageOrders = new ArrayList<>();
    boolean skipRules = false;
    while (reader.hasNextLine()) {
      String line = reader.nextLine();
      if (line.isEmpty()) {
        skipRules = true;
        continue;
      }
      if (skipRules) {
        pageOrders.add(
          Arrays.stream(line.split(","))
            .mapToInt(Integer::parseInt)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll)
        );
      }
    } reader.close();
    return pageOrders;
  }

  private static void insert(Integer[] array, Integer value, Integer index) {
    for (int i = array.length-1; i > index; i--) {
      array[i] = array[i-1];
    }
    array[index] = value;
  }

  public static void main(String[] args) {
    int tally = 0;
    int tally2 = 0;
    for (ArrayList<Integer> currentPages : pageOrders) {

      boolean valid = true;
      Integer[] current = new Integer[currentPages.size()];

      for (int i = 0; i < currentPages.size(); i++) {
        HashSet<Integer> RuleSet = rules.get(currentPages.get(i));
        boolean hardStop = false;
        for (int currentStep = 0; currentStep < current.length; currentStep++) {
          try {
            if (RuleSet.contains(current[currentStep])) {
              insert(current, currentPages.get(i), currentStep);
              valid = false;
              hardStop = true;
              break;
            }
          } catch (NullPointerException e) {
            current[i] = currentPages.get(i);
          }
        }
        if (!hardStop) {current[i] = currentPages.get(i);}
      }
      tally += valid ? current[(current.length - 1)/2] : 0;
      tally2 += !valid ? current[(current.length - 1)/2] : 0;
    }
    System.out.println(tally);
    System.out.println(tally2);
  }
}
