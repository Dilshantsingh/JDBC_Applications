package view;
import java.util.*;
import model.Student;
import controller.StudentDAO;

public class StudentApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentDAO dao = new StudentDAO();
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1 -> {
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Department: ");
                    String dept = sc.nextLine();
                    System.out.print("Enter Marks: ");
                    double marks = sc.nextDouble();
                    dao.addStudent(new Student(0, name, dept, marks));
                }
                case 2 -> {
                    List<Student> list = dao.getAllStudents();
                    System.out.println("ID\tName\tDept\tMarks");
                    System.out.println("-------------------------------");
                    for (Student s : list)
                        System.out.println(s.getStudentID() + "\t" + s.getName() + "\t" +
                                           s.getDepartment() + "\t" + s.getMarks());
                }
                case 3 -> {
                    System.out.print("Enter Student ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter New Name: ");
                    String n = sc.nextLine();
                    System.out.print("Enter New Department: ");
                    String d = sc.nextLine();
                    System.out.print("Enter New Marks: ");
                    double m = sc.nextDouble();
                    dao.updateStudent(new Student(id, n, d, m));
                }
                case 4 -> {
                    System.out.print("Enter Student ID to delete: ");
                    int id = sc.nextInt();
                    dao.deleteStudent(id);
                }
                case 5 -> exit = true;
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
