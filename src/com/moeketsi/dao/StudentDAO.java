/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.moeketsi.dao;

import com.moeketsi.classes.Student;
import com.moeketsi.util.DBConnection;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.moeketsi.classes.SchoolClass;
import com.moeketsi.classes.SchoolClass.Gradelevel;
import java.util.ArrayList;

/**
 *
 * @author Moeketsi Tsotetsi
 */
public class StudentDAO {

    // ADD
    public static String addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO student (student_id,first_name, last_name, class_id) "
                + "VALUES ( ?,?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, student.getStudentId());
            pstmt.setString(2, student.getFirstName());
            pstmt.setString(3, student.getLastName());
            pstmt.setInt(4, student.getSchoolClass().getClassId());

            int rows = pstmt.executeUpdate();
            return rows > 0 ? "Student added successfully." : "Failed to add student.";

        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
            throw e; // propagate exception
        }
    }

    // UPDATE
    public boolean updateStudent(Student student) throws SQLException {
        String sql = "UPDATE student SET first_name=?, last_name=?, class_id=? WHERE student_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setInt(3, student.getSchoolClass().getClassId());
            pstmt.setInt(4, student.getStudentId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
            throw e;
        }
    }

    // DELETE
    public boolean deleteStudent(int studentId) throws SQLException {
        String sql = "DELETE FROM student WHERE student_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
            throw e;
        }
    }

    // FIND BY ID (JOIN)
    public Student findById(int studentId) throws SQLException {
        String sql
                = "SELECT s.student_id, s.first_name, s.last_name, "
                + "c.class_id, c.grade_level, c.stream "
                + "FROM student s "
                + "JOIN class_room c ON s.class_id = c.class_id "
                + "WHERE s.student_id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                SchoolClass schoolClass = new SchoolClass(
                        rs.getInt("class_id"),
                        Gradelevel.valueOf(rs.getString("grade_level")),
                        rs.getString("stream")
                );

                return new Student(
                        rs.getInt("student_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        schoolClass
                );
            }

        } catch (SQLException e) {
            System.err.println("Error finding student by ID: " + e.getMessage());
            throw e;
        }
        return null;
    }

    // FIND BY CLASS
    public static ArrayList<Student> findByClass(int classId) throws SQLException {
        ArrayList<Student> students = new ArrayList<>();
        String sql
                = "SELECT s.student_id, s.first_name, s.last_name, "
                + "c.class_id, c.grade_level, c.stream "
                + "FROM student s "
                + "JOIN class_room c ON s.class_id = c.class_id "
                + "WHERE c.class_id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, classId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                SchoolClass schoolClass = new SchoolClass(
                        rs.getInt("class_id"),
                        Gradelevel.valueOf(rs.getString("grade_level")),
                        rs.getString("stream")
                );

                students.add(new Student(
                        rs.getInt("student_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        schoolClass
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error finding students by class: " + e.getMessage());
            throw e;
        }

        return students;
    }
}
