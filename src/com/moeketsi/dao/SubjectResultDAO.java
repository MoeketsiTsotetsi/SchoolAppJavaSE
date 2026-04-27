/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.moeketsi.dao;

import com.moeketsi.classes.SchoolClass;
import com.moeketsi.classes.Student;
import com.moeketsi.classes.Subject;
import com.moeketsi.classes.SubjectResult;
import com.moeketsi.classes.SubjectStreams;
import com.moeketsi.util.DBConnection;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Moeketsi Tsotetsi
 */
public class SubjectResultDAO {

    public static String addSubjectResult(SubjectResult sr) throws SQLException {
        if (isDuplicate(sr.getStudent().getStudentId(), sr.getSubject().getSubjectCode(), sr.getGrade(),sr.getTerm())) {
            return "Duplicate marks found do you want to update ?";
        }

        String sql = "INSERT INTO subjectresult(studentId,subjectCode,cassMark,examMark,"
                + "totalMark,grade,term) values(?,?,?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement psmst = conn.prepareStatement(sql)) {
            psmst.setInt(1, sr.getStudent().getStudentId());
            psmst.setInt(2, sr.getSubject().getSubjectCode());
            psmst.setInt(3, sr.getCassMark());
            psmst.setInt(4, sr.getExamMark());
            psmst.setInt(5, sr.calculateFinalMark());
            psmst.setString(6, sr.getGrade());
            psmst.setInt(7, sr.getTerm());

            int rows = psmst.executeUpdate();

            if (rows > 0) {
                return "Marks for " + sr.getSubject().getSubjecName() + " added";
            } else {
                return "Could not add marks for " + sr.getSubject().getSubjecName();
            }
        } catch (Exception e) {
            System.out.println("error adding subject result :" + e.getMessage());
            throw e;
        }
    }

    public static boolean isDuplicate(int studentId, int subjectCode, String grade,int term) throws SQLException {
        String sql = "SELECT * FROM subjectResult WHERE subjectcode= ? AND studentId=? AND grade=? AND term=?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement psmst = conn.prepareStatement(sql)) {
            psmst.setInt(1, subjectCode);
            psmst.setInt(2, studentId);
            psmst.setString(3, grade);
            psmst.setInt(4, term);

            ResultSet res = psmst.executeQuery();

            if (res.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error checking duplicate " + e.getMessage());
            throw e;
        }
    }

    public static List<SubjectResult> getResultsByStudent(int studentId) {
        List<SubjectResult> results = new ArrayList<>();

        String sql = "SELECT "
                + "sr.resultId, "
                + "sr.studentId, "
                + "sr.subjectCode, "
                + "sr.cassMark, "
                + "sr.term,"
                + "sr.examMark, "
                + "sr.totalMark, "
                + "sr.grade, "
                + "s.firstname, "
                + "s.lastname, "
                + "c.classId, "
                + "c.gradelevel, "
                + "c.stream, "
                + "sub.subjectname, "
                + "sub.stream_id, "
                + "sub.updated_at, "
                + "st.id AS streamId, "
                + "st.stream_Name, "
                + "st.stream_Code, "
                + "st.description "
                + "FROM subjectresult sr "
                + "JOIN student s ON sr.studentId = s.studentid "
                + "JOIN class_room c ON s.classid = c.classId "
                + "JOIN subject sub ON sr.subjectCode = sub.subjectcode "
                + "LEFT JOIN subject_streams st ON sub.stream_id = st.id "
                + "WHERE sr.studentId = ? "
                + "ORDER BY sub.subjectname";

        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                // Create SubjectStreams object
                SubjectStreams stream = new SubjectStreams(
                    rs.getInt("streamId"),
                    rs.getString("stream_Name"),
                    rs.getString("stream_Code"),
                    rs.getString("description")
                );

                // Create SchoolClass object
                SchoolClass schoolClass = new SchoolClass(
                    rs.getInt("classId"),
                    SchoolClass.Gradelevel.valueOf(rs.getString("gradelevel")), 
                    rs.getString("stream")
                );

                // Create Student object
                Student student = new Student(
                    rs.getInt("studentId"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    schoolClass
                );

                // Create Subject object
                Subject subject = new Subject(
                    rs.getInt("subjectCode"),
                    rs.getString("subjectname"),
                    stream
                );

                // Add SubjectResult to list
                results.add(new SubjectResult(
                    subject,
                    rs.getInt("cassMark"),
                    rs.getInt("examMark"),
                    student,
                    rs.getString("grade"),
                    rs.getInt("term")
                ));
            }
            
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }
    
    public static double getGradeAvarage(int subjectCode,String grade) throws SQLException{
        double avg = 0;
        String sql = "SELECT AVG(finalMark) WHERE grade=? AND term=?";
        
        try(Connection conn = DBConnection.getConnection();
                PreparedStatement psmsmt = conn.prepareStatement(sql);) {
            
            psmsmt.setInt(1, subjectCode);
            psmsmt.setString(2, grade);
            
            ResultSet res = psmsmt.executeQuery();
            
            while(res.next()){
                avg = res.getDouble("avg");
            }
            
            
            
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return avg;
    }
}