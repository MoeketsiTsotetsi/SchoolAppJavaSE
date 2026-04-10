/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.moeketsi.dao;
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
public class SubjectStreamsDAO {
    
    public static String addSubjectStream(SubjectStreams ss) throws SQLException{
        String sql = "INSERT INTO subject_streams(stream_name,stream_code,description)"
                + "values(?,?,?)";
        
        try(Connection conn = DBConnection.getConnection();
                PreparedStatement psmst = conn.prepareStatement(sql)) {
            psmst.setString(1, ss.getStreamName());
            psmst.setString(2, ss.getStreamCode());
            psmst.setString(3, ss.getDescription());
            
            int  rows = psmst.executeUpdate();
            return rows > 0 ? "Subject Stream Added Successfully" : "Failed To Add Subject Stream";
            
            
        } catch (Exception e) {
            System.err.println("Error adding subject stream: " + e.getMessage());
            throw e;
        }
 
    }
    
    public static List<SubjectStreams> getAllSubjectStreams()   throws SQLException{
        
        List<SubjectStreams> arSubjectSreams = new ArrayList<>();
        String sql = "SELECT *  FROM subject_streams";
        
        try(Connection conn = DBConnection.getConnection();
                PreparedStatement psmsnt = conn.prepareStatement(sql);) {
            ResultSet res = psmsnt.executeQuery();
            
            while (res.next()) {
                arSubjectSreams.add(new SubjectStreams(res.getInt("id"), res.getString("stream_name"),
                        res.getString("stream_code"), res.getString("description")));
                
            }
            
            
            return arSubjectSreams;
            
        } catch (Exception e) {
            System.err.println("Error retrieve subject streams "+ e.getMessage());
            throw  e;
        }
    }
    
}
 