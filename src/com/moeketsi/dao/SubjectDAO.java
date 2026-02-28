/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.moeketsi.dao;
import com.moeketsi.util.DBConnection;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import com.moeketsi.classes.Subject;


/**
 *
 * @author Moeketsi Tsotetsi
 */
public class SubjectDAO {
    
    
    
    public static String addSbject(Subject sb) throws SQLException{
       String sql = "INSERT INTO subject(subject_code,subject_name) values(?,?)";
        System.out.println("Student: "+sb);
        try(Connection conn = DBConnection.getConnection();
                PreparedStatement psmst = conn.prepareStatement(sql)) {
            psmst.setInt(1,sb.getSubjectCode() );
            psmst.setString(2, sb.getSubjecName());
            
            int rows = psmst.executeUpdate();
            if(rows > 0){
                return "Subject Added Successessfully";
            }else{
                return "Something went wrong";
            }
        } catch (Exception e) {
            System.err.println("Error Adding Subject: "+e.getMessage());
            throw  e;
        }
    }
    
}
