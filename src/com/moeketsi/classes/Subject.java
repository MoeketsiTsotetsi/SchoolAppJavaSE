/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.moeketsi.classes;

/**
 *
 * @author Moeketsi Tsotetsi
 */
public class Subject {
    private  int subjectCode;
    private String subjectName;
    private SubjectStreams subjectStreams;

    public Subject( String subjectName, SubjectStreams subjectStreams) {
        this.subjectCode = subjectCode;
        setSubjecName(subjectName);
        setSubjectStreams(subjectStreams);
    }

    public Subject(int subjectCode, String subjectName, SubjectStreams subjectStreams) {
        setSubjectCode(subjectCode);
        setSubjecName(subjectName);
        setSubjectStreams(subjectStreams);
    }
    
    

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public SubjectStreams getSubjectStreams() {
        return subjectStreams;
    }

    public void setSubjectStreams(SubjectStreams subjectStreams) {
        
        if(subjectStreams == null){
            throw new IllegalArgumentException("Subject streams cannot be null");
        }
        this.subjectStreams = subjectStreams;
    }
    
    

    public int getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(int subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjecName() {
        return subjectName;
    }

    public void setSubjecName(String subjectName) {
        
        if( subjectName == null || subjectName.isBlank()||subjectName.length()<3){
            throw new  IllegalArgumentException("Subject name must be more than 3 characters");
        }
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return "Subject{" + "subjectCode=" + subjectCode + ", subjectName=" + subjectName + '}';
    }
    
    
    
    
    
    
}
