package controller;
import java.sql.*;
import model.Student;
import java.util.*;

public class StudentDAO {
    private final String URL = "jdbc:mysql://localhost:3306/college";
    private final String USER = "root";
    private final String PASSWORD = "your_password";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addStudent(Student s) {
        String query = "INSERT INTO Student(Name, Department, Marks) VALUES (?, ?, ?)";
        try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getDepartment());
            ps.setDouble(3, s.getMarks());
            ps.executeUpdate();
            System.out.println("Student added successfully!");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        try (Connection con = connect();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Student")) {
            while (rs.next()) {
                list.add(new Student(rs.getInt(1), rs.getString(2),
                                     rs.getString(3), rs.getDouble(4)));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public void updateStudent(Student s) {
        String query = "UPDATE Student SET Name=?, Department=?, Marks=? WHERE StudentID=?";
        try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getDepartment());
            ps.setDouble(3, s.getMarks());
            ps.setInt(4, s.getStudentID());
            ps.executeUpdate();
            System.out.println("Student updated successfully!");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void deleteStudent(int id) {
        String query = "DELETE FROM Student WHERE StudentID=?";
        try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Student deleted successfully!");
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
