/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.moeketsi.dao;

import com.moeketsi.classes.SchoolClass;
import com.moeketsi.classes.SchoolClass.Gradelevel;
import com.moeketsi.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Moeketsi Tsotetsi
 */
public class SchoolClassDAO {

    public static String addSchoolClass(SchoolClass schoolClass) throws SQLException {
        String sql = "insert into class_room(grade_level,stream) values(?,?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement psmst = conn.prepareStatement(sql)) {
            psmst.setString(1, schoolClass.getGradelevel().name());
            psmst.setString(2, schoolClass.getStream());

            int rows = psmst.executeUpdate();

            return rows > 0 ? "Class Added Successfully" : "Failed To Add Class";

        } catch (SQLException e) {
            System.err.println("Error adding class: " + e.getMessage());
            throw e;
        }
    }

    public SchoolClass findByGradeAndStream(Gradelevel grade_level, String stream) throws SQLException {
        String sql = "SELECT * FROM class_room WHERE grade_level=? AND stream=?";
        SchoolClass sc = null;

        try (Connection conn = DBConnection.getConnection(); PreparedStatement psmst = conn.prepareStatement(sql);) {
            psmst.setString(1, grade_level.name());
            psmst.setString(2, stream);

            ResultSet res = psmst.executeQuery();

            if (res.next()) {
                sc = new SchoolClass(res.getInt("class_id"), Gradelevel.valueOf(res.getString("grade_level")), res.getString("stream"));
            }

            return sc;

        } catch (Exception e) {
            System.err.println("Failed to retrive class " + e.getMessage());
            throw e;
        }

    }

    public static ArrayList<SchoolClass> getAllSchoolClasses() throws SQLException {
        ArrayList<SchoolClass> arSchoolClass = new ArrayList<>();
        String sql = "SELECT * FROM class_room";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement psmst = conn.prepareStatement(sql);) {
            ResultSet res = psmst.executeQuery();

            while (res.next()) {
                arSchoolClass.add(new SchoolClass(
                        res.getInt("class_id"),
                        Gradelevel.valueOf(res.getString("grade_level")),
                        res.getString("stream")
                ));
            }

            return arSchoolClass;

        } catch (SQLException e) {
            System.err.println("Failed to retrieve school classes " + e.getMessage());
            throw e;
        }

    }

}
