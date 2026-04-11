/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.moeketsi.dao;
import com.moeketsi.classes.SubjectResult;
import com.moeketsi.util.DBConnection;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Moeketsi Tsotetsi
 */
public class SubjectResultDAO {
    
    public static String addSubjectResult(SubjectResult sr) throws SQLException{
        if(isDuplicate(sr.getStudent().getStudentId(), sr.getSubject().getSubjectCode(), sr.getGrade())){
            return "Duplicate marks found do you wnt to update ?";
        }
        
        String sql = "INSERT INTO subjectresult(studentId,subjectCode,cassMark,examMark,"
                + "totalMark,grade) values(?,?,?,?,?,?)";
        
        try(Connection conn = DBConnection.getConnection();
                PreparedStatement psmst = conn.prepareStatement(sql)) {
            psmst.setInt(1, sr.getStudent().getStudentId());
            psmst.setInt(2, sr.getSubject().getSubjectCode());
            psmst.setInt(3, sr.getCassMark());
            psmst.setInt(4, sr.getExamMark());
            psmst.setInt(5, sr.calculateFinalMark());
            psmst.setString(6, sr.getGrade());
            
            int rows = psmst.executeUpdate();
            
            if(rows>0){
                return "Marks for "+sr.getSubject().getSubjecName()+" added";
            }else{
                return "Could not add marks for "+ sr.getSubject().getSubjecName();
            }
        } catch (Exception e) {
            System.out.println("error adding subject result :"+e.getMessage());
            throw e;
        }
        
    }
    
    public static boolean isDuplicate(int studentId,int subjectCode,String grade) throws SQLException{
       String sql = "SELECT * FROM subjectResult WHERE subjectcode= ? AND studentId=? AND grade=?" ;
       
        try(Connection conn = DBConnection.getConnection();
                PreparedStatement psmst = conn.prepareStatement(sql)) {
            psmst.setInt(1, subjectCode);
            psmst.setInt(2, studentId);
            psmst.setString(3, grade);
            
            ResultSet res = psmst.executeQuery();
            
            if(res.next()){
                return true;
            }else{
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("Error checking duplicate "+e.getMessage());
            throw  e;
        }
    }
    
}
