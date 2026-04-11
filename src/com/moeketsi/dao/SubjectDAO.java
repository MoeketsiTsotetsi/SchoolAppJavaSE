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
import com.moeketsi.classes.SubjectStreams;
import java.sql.ResultSet;
import java.util.ArrayList;


/**
 *
 * @author Moeketsi Tsotetsi
 */
public class SubjectDAO {
    
    
    
    public static String addSbject(Subject sb) throws SQLException{
       String sql = "INSERT INTO subject(subjectname,stream_id) values(?,?)";
        
        try(Connection conn = DBConnection.getConnection();
                PreparedStatement psmst = conn.prepareStatement(sql)) {
            psmst.setString(1,sb.getSubjecName() );
            psmst.setInt(2, sb.getSubjectStreams().getId());
            
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
    
    public static ArrayList<Subject> getSubjectsByStreams(int stream_id) throws SQLException{
        ArrayList<Subject> arSubjects = new ArrayList<>();
        
        String sql
                = "SELECT s.subjectcode, s.subjectname, st.id, "
                + "st.stream_name, st.stream_code, st.description "
                + "FROM subject s "
                + "JOIN subject_streams st  ON s.stream_id = st.id "
                + "WHERE s.stream_id = ?";
        try(Connection conn = DBConnection.getConnection();
                PreparedStatement psmst = conn.prepareStatement(sql);) {
            psmst.setInt(1, stream_id);
            ResultSet res = psmst.executeQuery();
            
            while(res.next()){
                SubjectStreams ss = new SubjectStreams(res.getInt("id"), 
                        res.getString("stream_name"),
                        res.getString("stream_code"), res.getString("description"));
                
                arSubjects.add(new Subject(res.getInt("subjectcode"),res.getString("subjectname"), ss));
            }
            
            return arSubjects;
        } catch (Exception e) {
            System.err.println("Error Adding Subject: "+e.getMessage());
            throw  e;
        }
        
    }
    
}
