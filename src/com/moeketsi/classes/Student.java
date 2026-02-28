/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.moeketsi.classes;

/**
 *
 * @author Moeketsi Tsotetsi
 */
public class Student {
    
    private int studentId;
    private String firstName;
    private String lastName;
    private  SchoolClass schoolClass;

   

    public Student(int studentId, String firstName, String lastName, SchoolClass schoolClass) {
        setStudentId(studentId);
        setFirstName(firstName);
        setLastName(lastName);
        setSchoolClass(schoolClass);
    }
    
    

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        
        return firstName;
    }

    public void setFirstName(String firstName) {
        if(firstName.isEmpty() || firstName.length() <3){
            throw  new IllegalArgumentException(" First Name must be greater than  3 characters");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        
        if(lastName.isBlank() || lastName.isEmpty() || lastName.length()<3){
            throw  new IllegalArgumentException("Last Name must be greater than 3 characters");
        }
        this.lastName = lastName;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        
        if(schoolClass == null){
            throw  new IllegalArgumentException("Scholl class cannot be empty");
        }
        this.schoolClass = schoolClass;
    }

    @Override
    public String toString() {
        return "Student{" + "studentId=" + studentId + ", firstName=" + firstName + ", lastName=" + lastName + ", schoolClass=" + schoolClass + '}';
    }
    
    
    
    
    
}
