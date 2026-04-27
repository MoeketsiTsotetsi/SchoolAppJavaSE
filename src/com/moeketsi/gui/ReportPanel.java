/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.moeketsi.gui;

import javax.swing.JOptionPane;

/**
 *
 * @author Moeketsi Tsotetsi
 */
public class ReportPanel extends javax.swing.JPanel {
    
    public ReportPanel() {
        initComponents();
    }
    
    public void loadReport(String htmlContent) {
        reportTextPane.setText(htmlContent);
        reportTextPane.setCaretPosition(0);
    }
    
    private void printReport() {
        try {
            reportTextPane.print();
            JOptionPane.showMessageDialog(this, "Report sent to printer!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (java.awt.print.PrinterException e) {
            JOptionPane.showMessageDialog(this, "Print failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        reportTextPane = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();
        printButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        reportTextPane.setContentType("text/html");
        reportTextPane.setEditable(false);
        jScrollPane1.setViewportView(reportTextPane);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        printButton.setBackground(new java.awt.Color(56, 142, 60));
        printButton.setFont(new java.awt.Font("Arial", 1, 12));
        printButton.setForeground(new java.awt.Color(255, 255, 255));
        printButton.setText("🖨️ Print");
        printButton.setBorderPainted(false);
        printButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        printButton.setFocusPainted(false);
        printButton.addActionListener(evt -> printReport());

        closeButton.setBackground(new java.awt.Color(150, 150, 150));
        closeButton.setFont(new java.awt.Font("Arial", 1, 12));
        closeButton.setForeground(new java.awt.Color(255, 255, 255));
        closeButton.setText("❌ Close");
        closeButton.setBorderPainted(false);
        closeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(evt -> {
            // Find parent dialog and close it
            java.awt.Container parent = getParent();
            while (parent != null && !(parent instanceof javax.swing.JDialog)) {
                parent = parent.getParent();
            }
            if (parent instanceof javax.swing.JDialog) {
                ((javax.swing.JDialog) parent).dispose();
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(700, Short.MAX_VALUE)
                .addComponent(printButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(printButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.SOUTH);
    }

    // Variables declaration
    private javax.swing.JButton closeButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton printButton;
    private javax.swing.JTextPane reportTextPane;
    // End of variables declaration
}