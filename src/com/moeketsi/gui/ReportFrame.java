/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.moeketsi.gui;

import com.moeketsi.classes.SchoolClass;
import com.moeketsi.classes.Student;
import com.moeketsi.dao.ReportDAO;
import com.moeketsi.dao.SchoolClassDAO;
import com.moeketsi.dao.StudentDAO;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Moeketsi Tsotetsi
 */
public class ReportFrame extends javax.swing.JFrame {
    
    private List<Student> arStudents;
    
    public ReportFrame() {
        initComponents();
        loadClasses();
    }
    
    private void loadClasses() {
        try {
            List<SchoolClass> classes = SchoolClassDAO.getAllSchoolClasses();
            for (SchoolClass sc : classes) {
                cmbClass.addItem(sc);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading classes: " + e.getMessage());
        }
    }
    
    private void loadClassList() {
        SchoolClass sc = (SchoolClass) cmbClass.getSelectedItem();
        if (sc == null) return;
        
        int class_id = sc.getClassId();
        
        try {
            arStudents = StudentDAO.findByClass(class_id);
            
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
            
            for (Student student : arStudents) {
                model.addRow(new Object[]{
                    student.getStudentId(),    
                    student.getFirstName(),      
                    student.getLastName(),
                });
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    private void showReportPanel() {
        int selectedRow = jTable1.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student first");
            return;
        }
        
        // Get student ID from selected row
        int studentId = (int) jTable1.getValueAt(selectedRow, 0);
        String studentName = jTable1.getValueAt(selectedRow, 1) + " " + jTable1.getValueAt(selectedRow, 2);
        
        // Generate HTML report
        String html = ReportDAO.generateReportHTML(studentId);
        
        // Create a modal dialog to show the report panel
        javax.swing.JDialog dialog = new javax.swing.JDialog(this, "Report Card - " + studentName, true);
        dialog.setSize(900, 700);
        dialog.setLocationRelativeTo(this);
        
        // Create the report panel and load the HTML
        ReportPanel reportPanel = new ReportPanel();
        reportPanel.loadReport(html);
        
        // Add panel to dialog
        dialog.add(reportPanel);
        
        // Show the dialog
        dialog.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbClass = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnViewReport = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Student Reports");
        setPreferredSize(new java.awt.Dimension(800, 500));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14));
        jLabel1.setText("Select Class:");

        cmbClass.setFont(new java.awt.Font("Arial", 0, 14));
        cmbClass.addActionListener(evt -> loadClassList());

        jTable1.setFont(new java.awt.Font("Arial", 0, 13));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Student ID", "First Name", "Last Name", "Class"}
        ) {
            Class[] types = new Class [] {Integer.class, String.class, String.class, String.class};
            boolean[] canEdit = new boolean [] {false, false, false, false};

            public Class getColumnClass(int columnIndex) { return types[columnIndex]; }
            public boolean isCellEditable(int rowIndex, int columnIndex) { return canEdit[columnIndex]; }
        });
        jScrollPane1.setViewportView(jTable1);

        btnViewReport.setBackground(new java.awt.Color(56, 142, 60));
        btnViewReport.setFont(new java.awt.Font("Arial", 1, 12));
        btnViewReport.setForeground(new java.awt.Color(255, 255, 255));
        btnViewReport.setText("📄 View Report");
        btnViewReport.setBorderPainted(false);
        btnViewReport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnViewReport.setFocusPainted(false);
        btnViewReport.addActionListener(evt -> showReportPanel());

        btnClose.setBackground(new java.awt.Color(150, 150, 150));
        btnClose.setFont(new java.awt.Font("Arial", 1, 12));
        btnClose.setForeground(new java.awt.Color(255, 255, 255));
        btnClose.setText("❌ Close");
        btnClose.setBorderPainted(false);
        btnClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClose.setFocusPainted(false);
        btnClose.addActionListener(evt -> dispose());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbClass, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnViewReport, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbClass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnViewReport, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new ReportFrame().setVisible(true));
    }
    
    // Variables declaration
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnViewReport;
    private javax.swing.JComboBox<SchoolClass> cmbClass;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration
}