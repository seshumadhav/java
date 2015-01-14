package coffee;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class App {

  private static final ArrayList<Student> students = new ArrayList<Student>();

  private static Student getStudent(String name) {
    for (Student s : students) {
      if (s.name.equalsIgnoreCase(name)) {
        return s;
      }
    }

    System.out.println("Student does not exist!");
    return null;
  }

  private static Subject getSubject(String sub) {
    for (Subject subject : Subject.values()) {
      if (subject.toString().equalsIgnoreCase(sub)) {
        return subject;
      }
    }

    System.out.println("Invalid subject! Enter MATHS/PHYSICS/CHEMISTRY");
    return null;
  }

  public static void main(String args[]) throws IOException {
    String inputFile = readAndPrintFile("files/input.txt");

    System.out.println("STUDENTS IN FILE\n----------------");
    students.addAll(getStudentObjectsFromFile(inputFile));
    processListAllStudents();

    int choice = 0;
    while ((choice = printHelpTextGetHisChoice()) != 6) {
      processUsersChoice(choice);
      setStudentObjectsIntoFile("files/output.txt");
    }

    exit();
  }

  private static ArrayList<Student> getStudentObjectsFromFile(String inputFile) {
    ArrayList<Student> readStudents = new ArrayList<Student>();

    String[] lines = inputFile.split("\n");
    for (int i = 0; i < lines.length; i++) {
      if (i == 0) {
        continue;
      }

      String[] columns = lines[i].split("\\s+");
      String name = columns[1];
      try {
        Integer mathsMarks = toInt(columns[3]);
        Integer physicsMarks = toInt(columns[4]);
        Integer chemistryMarks = toInt(columns[5]);
        Student newStudent = new Student(name, Subject.MATHS, mathsMarks,
            Subject.PHYSICS, physicsMarks, Subject.CHEMISTRY, chemistryMarks);
        readStudents.add(newStudent);
      } catch (NumberFormatException e) {
        System.out.println("Invalid marks found in file. " + "\nSee entry: "
            + lines[i] + "\n\nExitting! Pls check!");
        System.exit(0);
      }
    }

    return readStudents;
  }

  private static int toInt(String s) {
    return Integer.valueOf(s);
  }

  private static void setStudentObjectsIntoFile(String file)
      throws FileNotFoundException {
    Formatter formatter = new Formatter();
    formatter.format("%20s %20s %20s %20s %20s %20s %20s",
        "SNO", "Name", "Total Marks", "Maths", "Physics", "Chemistry", "\n");

    PrintWriter out = new PrintWriter(file);

    int rowNumber = 1;
    for (Student stud : students) {
      String rowNum = "#" + rowNumber;
      rowNumber++;
      int m = stud.getMarks(Subject.MATHS);
      int p = stud.getMarks(Subject.PHYSICS);
      int c = stud.getMarks(Subject.CHEMISTRY);

      formatter.format("%20s %20s %20s %20s %20s %20s %20s",
          rowNum, stud.name, stud.totalMarks, m, p, c, "\n");
    }

    out.println(formatter.toString());
    formatter.close();
    out.close();
  }

  private static void exit() {
    System.out.println("\nExitting! Bye!");
  }

  private static void processUsersChoice(int choice) {
    switch (choice) {
    case 1:
      processInsertOption();
      break;
    case 2:
      processDeleteOption();
      break;
    case 3:
      processCalculateAverage();
      break;
    case 4:
      processUpdateMarks();
      break;
    case 5:
      processListAllStudents();
      break;
    default:
      exit();
    }
  }

  private static void processListAllStudents() {
    if (students.isEmpty()) {
      System.out.println("No students!");
      return;
    }

    for (Student s : students) {
      System.out.println(s);
    }
  }

  private static void processCalculateAverage() {
    String name = readStudentNameFromConsole();

    Student s = getStudent(name);
    if (s != null) {
      System.out.println("Average: " + s.getAverage());
    }
  }

  private static void processUpdateMarks() {
    String name = readStudentNameFromConsole();

    Student s = getStudent(name);
    if (s != null) {
      System.out.print("Enter subject(MATHS/PHYSICS/CHEMISTRY): ");
      String subject = readStringFromConsole();
      Subject subjectEnum = getSubject(subject);

      System.out.print("Enter marks: ");
      int marks = readIntegerFromConsole();
      s.setMarks(subjectEnum, marks);

      System.out.println("Marks updated successfully!");
    }
  }

  private static void processDeleteOption() {
    String name = readStudentNameFromConsole();

    Student s = getStudent(name);
    if (s != null) {
      students.remove(s);
      System.out.println("Deleted student successfully!");
    }
  }

  private static void processInsertOption() {
    String name = readStudentNameFromConsole();

    System.out.print("Marks in MATHS: ");
    Integer marksInMaths = readIntegerFromConsole();

    System.out.print("Marks in PHYSICS: ");
    Integer marksInPhysics = readIntegerFromConsole();

    System.out.print("Marks in CHEMISTRY: ");
    Integer marksInChemistry = readIntegerFromConsole();
    System.out.println();

    Student s = new Student(name, Subject.MATHS, marksInMaths, Subject.PHYSICS,
        marksInPhysics, Subject.CHEMISTRY, marksInChemistry);
    students.add(s);
    System.out.println(s.toString() + " added!");
  }

  private static void printArgs(String[] args) {
    System.out.println("\nARGS PASSED\n------------");
    for (String arg : args) {
      System.out.println(arg);
    }
  }

  private static int printHelpTextGetHisChoice() {
    StringBuffer sb = new StringBuffer();
    sb.append("\n\nOPTIONS:\n--------\n");
    sb.append("1. INSERT").append("\n");
    sb.append("2. DELETE").append("\n");
    sb.append("3. CALCULATE AVERAGE").append("\n");
    sb.append("4. UPDATE MARKS").append("\n");
    sb.append("5. LIST ALL STUDENTS").append("\n");
    sb.append("6. EXIT").append("\n");
    sb.append("Select the option number: ");
    System.out.print(sb.toString());

    int choice = readIntegerFromConsole();
    return choice;
  }

  private static String readStudentNameFromConsole() {
    System.out.println();
    System.out.print("Enter student name: ");
    return readStringFromConsole();
  }

  private static int readIntegerFromConsole() {
    Scanner reader = new Scanner(System.in);
    int choice = reader.nextInt();
    return choice;
  }

  private static String readStringFromConsole() {
    return new Scanner(System.in).next();
  }

  private static String readAndPrintFile(String filePath) throws IOException {
    String fileAsString = readFileAsString(filePath);
    System.out.println("\n\nFILE\n----\n" + fileAsString);
    return fileAsString;
  }

  private static String readFileAsString(String filePath) throws IOException {
    StringBuffer sb = new StringBuffer();

    BufferedReader br = new BufferedReader(new FileReader(filePath));
    String sCurrentLine;
    while ((sCurrentLine = br.readLine()) != null) {
      sb.append(sCurrentLine);
      sb.append("\n");
    }

    return sb.toString();
  }

}

class Student {
  String                 name;
  Map<Subject, Integer> marksList = new HashMap<Subject, Integer>();
  Integer               totalMarks;

  public Student(String name, Subject sub1, Integer marks1, Subject sub2,
      Integer marks2, Subject sub3, Integer marks3) {
    this.name = name;
    setMarks(sub1, marks1);
    setMarks(sub2, marks2);
    setMarks(sub3, marks3);
    getTotalMarks();
  }

  private float getTotalMarks() {
    totalMarks = 0;
    for (Entry<Subject, Integer> e : marksList.entrySet()) {
      totalMarks += e.getValue();
    }

    return totalMarks;
  }

  public float getAverage() {
    return getTotalMarks() / marksList.size();
  }

  public void setMarks(Subject subject, int marks) {
    marksList.put(subject, marks);

    getTotalMarks();
  }

  public int getMarks(Subject s) {
    return marksList.get(s);
  }

  @Override
  public String toString() {
    return "Student [name=" + name + ", totalMarks=" + totalMarks
        + ", marksList=" + marksList + "]";
  }

}

enum Subject {
  MATHS, PHYSICS, CHEMISTRY;
}