/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.moeketsi.classes;

/**
 *
 * @author Moeketsi Tsotetsi
 */
public class SubjectResult {

    private Subject subject;
    private String grade;
    private int cassMark;
    private int examMark;
    int term;
    private Student student;

    public SubjectResult(Subject subject, int cassMark, int examMark,Student student,String grade, int term) {
        setSubject(subject);
        setCassMark(cassMark);
        setExamMark(examMark);
        setStudent(student);
        setGrade(grade);
        setTerm(term);
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        
        if(term <=0 || term >4){
            throw new IllegalArgumentException("Term has to be a valaue from 1 to 4");
        }
        this.term = term;
    }
    
    
    

    public Subject getSubject() {
        return subject;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        
        if(grade.length()<2){
            throw new IllegalArgumentException("Grade cannot be be less than 2 characters");
        }
        this.grade = grade;
    }
    
    

    public void setSubject(Subject subject) {
        if (subject == null) {
            throw new IllegalArgumentException("Subject cannot be null");
        }
        this.subject = subject;
    }

    public int getCassMark() {
        return cassMark;
    }

    public void setCassMark(int cassMark) {
        if (cassMark < 0 || cassMark > 100) {
            throw new IllegalArgumentException("CASS Mark has to range from 0 to 100");
        }
        this.cassMark = cassMark;
    }

    public int getExamMark() {
        return examMark;
    }

    public void setExamMark(int examMark) {
        if (examMark < 0 || examMark > 100) {
            throw new IllegalArgumentException("Exam mark has to range from 0 to 100 ");
        }
        this.examMark = examMark;
    }

    public int calculateFinalMark() {
        int finalMark = 0;

        double rawMark = (cassMark * 0.25) + (examMark * 0.75);
        finalMark = (int) Math.round(rawMark);

        return finalMark;
    }
    
    
    public int getAchievementCode() {
        int finalMark = calculateFinalMark();
        if (finalMark >= 80) return 7;
        if (finalMark >= 70) return 6;
        if (finalMark >= 60) return 5;
        if (finalMark >= 50) return 4;
        if (finalMark >= 40) return 3;
        if (finalMark >= 30) return 2;
        return 1;
    }
    
    public String getAchievementDescriptor() {
        switch(getAchievementCode()) {
            case 7: return "Outstanding";
            case 6: return "Meritorious";
            case 5: return "Substantial";
            case 4: return "Moderate";
            case 3: return "Adequate";
            case 2: return "Elementary";
            default: return "Not Achieved";
        }
    }
    
    public boolean isPass() {
        return calculateFinalMark() >= 40;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        
        if(student == null){
            throw new IllegalArgumentException("Student cannot be null");
        }
        this.student = student;
    }

    @Override
    public String toString() {
        return "SubjectResult [subject=" + subject + ", cassMark=" + cassMark + ", examMark=" + examMark + "]";
    }

}
