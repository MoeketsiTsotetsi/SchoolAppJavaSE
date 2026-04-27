/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.moeketsi.dao;

import com.moeketsi.classes.Student;
import com.moeketsi.classes.SubjectResult;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

/**
 *
 * @author Moeketsi Tsotetsi
 */
public class ReportDAO {
    
    /**
     * Generate complete HTML report for a student
     */
    public static String generateReportHTML(int studentId) {
        // Get all subject results for this student
        List<SubjectResult> results = SubjectResultDAO.getResultsByStudent(studentId);
        
        if (results.isEmpty()) {
            return "<html><body><h3>No subject results found for this student</h3></body></html>";
        }
        
        // Get student from first result (all results have same student)
        Student student = results.get(0).getStudent();
        
        return buildHTMLReport(student, results);
    }
    
    private static String buildHTMLReport(Student student, List<SubjectResult> results) {
        StringBuilder html = new StringBuilder();
        
        // Group results by term
        Map<Integer, List<SubjectResult>> resultsByTerm = groupResultsByTerm(results);
        
        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<head>");
        html.append("<meta charset='UTF-8'>");
        html.append("<style>");
        html.append("body { font-family: Arial, sans-serif; margin: 40px; }");
        html.append(".header { text-align: center; border-bottom: 3px solid #1a237e; margin-bottom: 20px; }");
        html.append(".school-name { font-size: 24px; font-weight: bold; color: #1a237e; }");
        html.append(".student-info { background: #f5f5f5; padding: 15px; margin: 20px 0; border-left: 4px solid #1a237e; }");
        html.append("table { width: 100%; border-collapse: collapse; margin: 20px 0; }");
        html.append("th { background: #1a237e; color: white; padding: 12px; border: 1px solid #ddd; }");
        html.append("td { padding: 10px; border: 1px solid #ddd; }");
        html.append(".term-title { background: #283593; color: white; padding: 10px; margin-top: 30px; margin-bottom: 10px; font-size: 18px; font-weight: bold; border-radius: 5px; }");
        html.append(".pass { color: green; font-weight: bold; }");
        html.append(".fail { color: red; font-weight: bold; }");
        html.append(".signatures { margin-top: 50px; display: flex; justify-content: space-between; }");
        html.append(".code-explanation { margin-top: 30px; font-size: 11px; text-align: center; background: #fff3e0; padding: 10px; }");
        html.append(".term-summary { background: #e3f2fd; padding: 10px; margin: 10px 0; text-align: center; border-radius: 5px; }");
        html.append("@media print {");
        html.append("    .no-print { display: none; }");
        html.append("    body { margin: 0; }");
        html.append("}");
        html.append("</style>");
        html.append("</head>");
        html.append("<body>");
        
        // Header
        html.append("<div class='header'>");
        html.append("<div class='school-name'>SUNRISE HIGH SCHOOL</div>");
        html.append("<p>Academic Report - Academic Year ").append(java.time.Year.now().getValue()).append("</p>");
        html.append("</div>");
        
        // Student Information
        html.append("<div class='student-info'>");
        html.append("<h3>Learner Information</h3>");
        html.append("<p><strong>Name:</strong> ").append(student.getFirstName()).append(" ").append(student.getLastName()).append("</p>");
        html.append("<p><strong>Class:</strong> ").append(student.getSchoolClass().getGradelevel()).append(" ").append(student.getSchoolClass().getStream()).append("</p>");
        html.append("<p><strong>Student ID:</strong> ").append(student.getStudentId()).append("</p>");
        html.append("</div>");
        
        // Display each term's table (ONE table per term with ALL subjects)
        for (Map.Entry<Integer, List<SubjectResult>> entry : resultsByTerm.entrySet()) {
            int term = entry.getKey();
            List<SubjectResult> termResults = entry.getValue();
            
            html.append("<div class='term-title'>TERM ").append(term).append(" RESULTS</div>");
            
            html.append("<table border='1' style='border-collapse: collapse; width: 100%;'>");
            
            // Table header
            html.append("<tr style='background: #1a237e; color: white;'>");
            html.append("<th style='padding: 10px; width: 30%;'>Subject</th>");
            html.append("<th style='padding: 10px; width: 20%;'>Final Mark</th>");
            html.append("<th style='padding: 10px; width: 15%;'>Code</th>");
            html.append("<th style='padding: 10px; width: 35%;'>Achievement</th>");
            html.append("<tr>");
            
            int termTotal = 0;
            int termPassCount = 0;
            
            // Sort subjects alphabetically for consistent display
            termResults.sort((r1, r2) -> r1.getSubject().getSubjecName().compareTo(r2.getSubject().getSubjecName()));
            
            for (SubjectResult result : termResults) {
                int finalMark = result.calculateFinalMark();
                int code = result.getAchievementCode();
                String descriptor = result.getAchievementDescriptor();
                String color = result.isPass() ? "green" : "red";
                String subjectName = result.getSubject().getSubjecName();
                
                html.append("<tr>");
                html.append("<td style='padding: 8px; border: 1px solid #ddd;'>").append(subjectName).append("</td>");
                html.append("<td style='padding: 8px; border: 1px solid #ddd; text-align: center; color: ").append(color).append("; font-weight: bold;'>").append(finalMark).append("%</td>");
                html.append("<td style='padding: 8px; border: 1px solid #ddd; text-align: center;'>").append(code).append("</td>");
                html.append("<td style='padding: 8px; border: 1px solid #ddd;'>").append(descriptor).append("</td>");
                html.append("</tr>");
                
                termTotal += finalMark;
                if (result.isPass()) termPassCount++;
            }
            
            html.append("</table>");
            
            // Term summary
            double termAverage = termResults.size() > 0 ? (double) termTotal / termResults.size() : 0;
            String termAverageColor = termAverage >= 40 ? "green" : "red";
            
            html.append("<div class='term-summary'>");
            html.append("<strong>Term ").append(term).append(" Summary:</strong> ");
            html.append("Average: <span style='color: ").append(termAverageColor).append("; font-weight: bold;'>").append(String.format("%.1f", termAverage)).append("%</span> | ");
            html.append("Subjects Passed: ").append(termPassCount).append(" / ").append(termResults.size());
            html.append("</div>");
        }
        
        // Overall Summary across all terms
        html.append("<div style='background: #1a237e; color: white; padding: 15px; margin: 30px 0 20px 0; text-align: center; border-radius: 5px;'>");
        html.append("<h3 style='margin: 0;'>YEARLY SUMMARY</h3>");
        html.append("</div>");
        
        // Calculate yearly averages
        double yearlyAverage = calculateOverallAverage(results);
        int totalSubjects = groupResultsBySubject(results).size();
        int yearlyPassedSubjects = calculateTotalPassedSubjects(results);
        
        String yearlyStatus = yearlyAverage >= 40 ? "PROMOTED" : "NOT PROMOTED";
        String yearlyColor = yearlyAverage >= 40 ? "#4caf50" : "#f44336";
        
        html.append("<div style='background: #e3f2fd; padding: 15px; margin: 20px 0; text-align: center; border-radius: 5px;'>");
        html.append("<span style='font-size: 18px; font-weight: bold;'>Yearly Average: ").append(String.format("%.1f", yearlyAverage)).append("%</span>");
        html.append("&nbsp;&nbsp;|&nbsp;&nbsp;");
        html.append("<span>Subjects Passed Yearly: ").append(yearlyPassedSubjects).append(" / ").append(totalSubjects).append("</span>");
        html.append("&nbsp;&nbsp;|&nbsp;&nbsp;");
        html.append("<span style='color: ").append(yearlyColor).append("; font-weight: bold; font-size: 16px;'>Overall Status: ").append(yearlyStatus).append("</span>");
        html.append("</div>");
        
        // Code Explanation
        html.append("<div class='code-explanation'>");
        html.append("<strong>National Coding System (CAPS):</strong><br>");
        html.append("Code 7 (80-100%) = Outstanding | ");
        html.append("Code 6 (70-79%) = Meritorious | ");
        html.append("Code 5 (60-69%) = Substantial | ");
        html.append("Code 4 (50-59%) = Moderate | ");
        html.append("Code 3 (40-49%) = Adequate | ");
        html.append("Code 2 (30-39%) = Elementary | ");
        html.append("Code 1 (0-29%) = Not Achieved");
        html.append("</div>");
        
        // Signatures
        html.append("<div class='signatures'>");
        html.append("<div>Parent/Guardian: ___________________</div>");
        html.append("<div>Teacher: ___________________</div>");
        html.append("<div>Principal: ___________________</div>");
        html.append("</div>");
        
        // Footer
        html.append("<div style='text-align: center; font-size: 10px; margin-top: 30px; color: #777;'>");
        html.append("Generated on: ").append(java.time.LocalDate.now().toString());
        html.append("</div>");
        
        html.append("</body></html>");
        
        return html.toString();
    }
    
    /**
     * Group results by term number
     */
    private static Map<Integer, List<SubjectResult>> groupResultsByTerm(List<SubjectResult> results) {
        Map<Integer, List<SubjectResult>> grouped = new TreeMap<>(); // TreeMap for automatic sorting by term
        
        for (SubjectResult result : results) {
            int term = result.getTerm();
            grouped.computeIfAbsent(term, k -> new ArrayList<>()).add(result);
        }
        
        return grouped;
    }
    
    /**
     * Group results by subject name
     */
    private static Map<String, List<SubjectResult>> groupResultsBySubject(List<SubjectResult> results) {
        Map<String, List<SubjectResult>> grouped = new HashMap<>();
        
        for (SubjectResult result : results) {
            String subjectName = result.getSubject().getSubjecName();
            grouped.computeIfAbsent(subjectName, k -> new ArrayList<>()).add(result);
        }
        
        return grouped;
    }
    
    /**
     * Calculate overall average across all results
     */
    private static double calculateOverallAverage(List<SubjectResult> results) {
        if (results.isEmpty()) return 0;
        
        int totalMarks = 0;
        for (SubjectResult result : results) {
            totalMarks += result.calculateFinalMark();
        }
        return (double) totalMarks / results.size();
    }
    
    /**
     * Calculate total number of subjects passed (considering all terms)
     * A subject is considered passed if the average across all terms is >= 40%
     */
    private static int calculateTotalPassedSubjects(List<SubjectResult> results) {
        Map<String, List<SubjectResult>> bySubject = groupResultsBySubject(results);
        int passedSubjects = 0;
        
        for (List<SubjectResult> subjectResults : bySubject.values()) {
            int sum = 0;
            for (SubjectResult result : subjectResults) {
                sum += result.calculateFinalMark();
            }
            double avg = (double) sum / subjectResults.size();
            if (avg >= 40) {
                passedSubjects++;
            }
        }
        
        return passedSubjects;
    }
}