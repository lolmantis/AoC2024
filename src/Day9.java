import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day9 extends commons {

  Day9() {super("Day9",1928,2858);}

  @Override
  protected String readFile() {
    Scanner reader = getInput();
    String result = reader.nextLine();
    reader.close();
    // it's a one line file
    return result;
  }

  private ArrayList<String> ExpandString(String str) {
    ArrayList<String> newLine = new ArrayList<>();
    long currentID = 0;
    boolean alternateToEmpty = false;
    for (char c : str.toCharArray()) {
      if (alternateToEmpty) {
        for (int i = 0; i < Integer.parseInt(String.valueOf(c)); i++) {
          newLine.add(".");
        }
      }
      else {
        for (int i = 0; i < Integer.parseInt(String.valueOf(c)); i++) {
          newLine.add(String.valueOf(currentID));
        } currentID++;
      }
      alternateToEmpty = !alternateToEmpty;
    }
    return newLine;
  }

  private Integer[] collapseString(ArrayList<String> str) {
    Integer[] newString = new Integer[str.size()];
    for (int i = 0; i < str.size(); i++) {
      if (!str.get(i).equals(".")) {
        newString[i] = Integer.parseInt(str.get(i));
      }
      else {
        newString[i] = 0;
      }
    }
    int endCharToMove = str.size() - 1;

    for (int startSpace = 0; startSpace < str.size(); startSpace++) {
      if (startSpace > endCharToMove) {
        break;
      }
      while (str.get(endCharToMove).equals(".")) {
        endCharToMove--;
      }
      if (str.get(startSpace).equals(".")) {
        newString[startSpace] = Integer.valueOf(str.get(endCharToMove));
        newString[endCharToMove--] = 0;
      }
    }
    return newString;
  }

  private long partTwo(String str) {
    HashMap<Integer, Integer[]> values = new HashMap<>();
    ArrayList<Integer[]> freeSpaces = new ArrayList<>();

    int counter = 0;
    for (int index = 0; index < str.length(); index++) {
      int current = Integer.parseInt(String.valueOf(str.charAt(index)));
      int end = counter + current;
      if (index % 2 == 0) {
        values.put(index/2,new Integer[]{counter,end});
      }
      else if (current > 0) {
        freeSpaces.add(new Integer[]{counter,end});
      }
      counter += current;
    }

    int maxPointer = Collections.max(values.keySet());

    while (maxPointer >= 0) {
      int fileStart = values.get(maxPointer)[0],
        fileEnd= values.get(maxPointer)[1],
        fileLength = fileEnd-fileStart;

      int freePointer = 0;
      while (freePointer < freeSpaces.size()) {
        int freeStart = freeSpaces.get(freePointer)[0],
          freeEnd = freeSpaces.get(freePointer)[1],
          freeSpace = freeEnd-freeStart;

        if (freeStart > fileStart) {break;}

        if (fileLength <= freeSpace) {
          freeSpaces.remove(freePointer);

          int newFileStart = freeStart, newFileEnd = freeStart+fileLength; // redundant, but legible
          int newFreeStart = newFileEnd, newFreeEnd = freeEnd; // this entire project hurt my brain

          values.put(maxPointer, new Integer[]{newFileStart, newFileEnd});

          if (newFreeStart != newFreeEnd) {
            freeSpaces.add(freePointer, new Integer[]{newFreeStart, newFreeEnd});
          }
          break;
        }
        else {
          freePointer++;
        }
      }
      maxPointer--;
    }

    long tally = 0;
    for (Integer key : values.keySet()) {
      for (int i = values.get(key)[0]; i < values.get(key)[1]; i++) {
        tally += key*i;
      }
    }

    return tally;
  }

  private long calcChecksum(Integer[] vals) {
    long checksum = 0;
    for (int i = 0; i < vals.length; i++) {
      checksum += (long) i * vals[i];
    }
    return checksum;
  }

  @Override
  public void Solve(boolean test, boolean partTwo) {
    Solve(test, partTwo, false);
  }

  @Override
  public void Solve(boolean test, boolean partTwo, boolean verbose) {
    super.Solve(test, partTwo, verbose);

    String line = readFile();
    long checksum = 0;
    if (!partTwo) {
      checksum = calcChecksum(collapseString(ExpandString(line)));
    }
    if (partTwo) {
      checksum = partTwo(line);
    }
    checkTest(checksum);
    displayResult(checksum);


    if (verbose) {
      System.out.println("Evil file, this breaks current implementation");
      try {
        Scanner scanner = new Scanner(new File("./inputs/EvilDay9.txt"));
        String evilTest = scanner.nextLine();
        scanner.close();
        long testCase = 97898222299196L; // big boy
        long evil = partTwo(evilTest);
        System.out.println(evil);
        if (evil != testCase) {
          System.out.println("Day 9 evil file: It broke, I'm not fixing it lol");
        }
        else {System.out.println("Day 9 evil file: No way");}
      }
      catch (FileNotFoundException e) {System.out.println("Day9: uh oh, typo");}
    }
  }
}
