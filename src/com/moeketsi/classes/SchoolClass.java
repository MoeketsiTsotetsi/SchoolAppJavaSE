/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.moeketsi.classes;

/**
 *
 * @author Moeketsi Tsotetsi
 */
public class SchoolClass {
    private int classId;
    public enum Gradelevel{
        GRADE_8,GRADE_9,GRADE_10,GRADE_11,GRADE_12
    }
    
    private Gradelevel gradelevel ;
    private String stream;

    public SchoolClass(int classId, Gradelevel gradelevel, String stream) {
        this.classId = classId;
        this.gradelevel = gradelevel;
        this.stream = stream;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public Gradelevel getGradelevel() {
        return gradelevel;
    }

    public void setGradelevel(Gradelevel gradelevel) {
        if(gradelevel ==null){
            throw  new IllegalArgumentException("Grade Level Cannot  Be Null");
        }
        this.gradelevel = gradelevel;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        if(stream == null || stream.isEmpty() || stream.length()<1 || stream.length()>1){
            throw  new IllegalArgumentException("Stream cannot be less than 1 or greater than 1");
        }
        this.stream = stream;
    }

    @Override
    public String toString() {
        return  gradelevel + "-" + stream ;
    }
    
    
    
    
    
    
    
}
