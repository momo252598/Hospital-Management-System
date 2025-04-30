package com.mycompany.project;


import com.mycompany.project.Check;
import com.mycompany.project.Connector;
import com.mycompany.project.interface_one;
import com.mycompany.project.login;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Hp
 */
public class Tech extends javax.swing.JFrame {

    /**
     * Creates new form Tech
     */
    public static JFrame temp_Tech;
    public Tech(JFrame parent) {
        initComponents();
        this.setVisible(true);
        this.setResizable(false); 
        this.setLocationRelativeTo(null);
        WelcomeLabel.requestFocusInWindow();
        temp_Tech=parent;
        fillSampleTable();
        //fillSampleTable();
        WelcomeLabel.setText("Welcome, "+ Check.getFullName("employee", login.EmpID)+"!");
                 SearchSampleTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchAndUpdateTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchAndUpdateTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not needed for plain text fields
            }

            private void searchAndUpdateTable() {
                String searchTerm = SearchSampleTextField.getText();
                
                if (searchTerm.trim().isEmpty() || searchTerm.trim().equals("Search Sample...")) {
                    // If the search term is empty, fill the table with all contents
                    fillSampleTable();
                    //System.out.println("yo exeption ooooooooooooorrrrrrrrrrrrrrrrrrr");
                } else {
                    // Perform the search based on the entered text
                   try {
                        String query = "SELECT * FROM proj_1.nurse_tech WHERE sample_no = ? and sample_status='false'";
                        Connection c = Connector.getConnection();

                        // Create a prepared statement
                        try (PreparedStatement preparedStatement = c.prepareStatement(query)) {
                            // Set the parameter in the prepared statement
                            //preparedStatement.setString(1, "%" + searchTerm.toLowerCase() + "%");
                            //preparedStatement.setString(1, "%" + searchTerm.toString() + "%");
                            try{
                                preparedStatement.setInt(1, Integer.parseInt(searchTerm.trim()));}
                            catch(Exception e){
                                //preparedStatement.setString(1, Integer.parseInt(searchTerm.trim()));
                                preparedStatement.setString(1, "%" + searchTerm + "%");


                            }

                            //preparedStatement.setString(3, (String) ChooseDepartment.getSelectedItem());
                            
                            // Execute the query and get the result set
                            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                                // Populate the jTable with the search results
                                // (Assuming jTable model is DefaultTableModel)
                                DefaultTableModel model = (DefaultTableModel) techTable.getModel();
                                model.setRowCount(0); // Clear existing rows

                                while (resultSet.next()) {
                                    // Assuming your jTable has columns id, name, blood_type
                                    String info[]= { resultSet.getString("sample_no"), resultSet.getString("sample_desc"),
                                    resultSet.getString("doc_id"), resultSet.getString("pid")};
                                    //System.out.println(name);
                                    model.addRow(info);

                                    // Add a new row to the jTable
                                    //model.addRow(new Object[]{id, name, bloodType});
                                }
                                resultSet.close();
                                preparedStatement.close();
                                c.close();
                            }
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        System.out.println("ayo hoon el error tech");
                    }
                }
            }
        });
         
//         if (SearchSampleTextField.getText().isEmpty()) {
//            SearchSampleTextField.setText("Search Sample...");
//            fillSampleTable();
//        }
//        
        
        
    }
    
    private void fillSampleTable(){
        try {
            // TODO add your handling code here:
            String fillTablesql = "";
            Connection c = Connector.getConnection();
            
            fillTablesql = "SELECT sample_no, sample_desc,doc_id, pid "
                        + "from proj_1.nurse_tech where tech_id='"+login.EmpID+"'" + 
                          "and doc_id IS NOT NULL and sample_status = 'false'";
            System.out.println("this is id " + login.EmpID);
            Statement stmt=c.createStatement();    
            ResultSet rs= stmt.executeQuery(fillTablesql);
            
            DefaultTableModel TechTableModel = (DefaultTableModel) techTable.getModel();
            TechTableModel.setRowCount(0);
            while(rs.next()){
                //System.out.println(Check.getDiagnosis(rs.getString("\"patient_ID\""), rs.getString("doc_id")));
                String info[]= {rs.getString("sample_no"), rs.getString("sample_desc"), rs.getString("doc_id"), 
                        rs.getString("pid") };
                
                //System.out.println(name);
                TechTableModel.addRow(info);
                System.out.println("alo?");
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(interface_one.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        techTable = new javax.swing.JTable();
        WelcomeLabel = new javax.swing.JLabel();
        SubmitButton = new javax.swing.JButton();
        SearchSampleTextField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(217, 244, 255));
        jPanel1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPanel1FocusGained(evt);
            }
        });
        jPanel1.setLayout(null);

        techTable.setBackground(new java.awt.Color(204, 204, 204));
        techTable.setForeground(new java.awt.Color(0, 0, 0));
        techTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#Sample", "Sample", "Doctor ID", "Patient ID"
            }
        ));
        jScrollPane1.setViewportView(techTable);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(130, 70, 452, 406);

        WelcomeLabel.setBackground(new java.awt.Color(204, 204, 204));
        WelcomeLabel.setForeground(new java.awt.Color(0, 0, 0));
        WelcomeLabel.setText("Welcome");
        WelcomeLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        WelcomeLabel.setOpaque(true);
        jPanel1.add(WelcomeLabel);
        WelcomeLabel.setBounds(20, 20, 250, 30);

        SubmitButton.setBackground(new java.awt.Color(153, 204, 255));
        SubmitButton.setForeground(new java.awt.Color(0, 0, 0));
        SubmitButton.setText("Sumbit to Doctor");
        SubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitButtonActionPerformed(evt);
            }
        });
        jPanel1.add(SubmitButton);
        SubmitButton.setBounds(450, 480, 130, 30);

        SearchSampleTextField.setBackground(new java.awt.Color(204, 204, 204));
        SearchSampleTextField.setForeground(new java.awt.Color(0, 0, 0));
        SearchSampleTextField.setText("Search Sample...");
        SearchSampleTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                SearchSampleTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                SearchSampleTextFieldFocusLost(evt);
            }
        });
        SearchSampleTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchSampleTextFieldActionPerformed(evt);
            }
        });
        jPanel1.add(SearchSampleTextField);
        SearchSampleTextField.setBounds(420, 30, 160, 22);

        jButton1.setBackground(new java.awt.Color(255, 204, 153));
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Logout");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(130, 480, 90, 23);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 707, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SearchSampleTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchSampleTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchSampleTextFieldActionPerformed

    private void SubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitButtonActionPerformed
        try {
            // TODO add your handling code here:
            Connection c = Connector.getConnection();
            int row = techTable.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(null, "Select Row", "Row Selection Error", JOptionPane.INFORMATION_MESSAGE);
                return;}
            
            DefaultTableModel TechModel = (DefaultTableModel) techTable.getModel();
            int sampleNum= (Integer.parseInt(TechModel.getValueAt(row, 0).toString()));
            String doc_id=TechModel.getValueAt(row, 2).toString();
            String userInput = JOptionPane.showInputDialog("Enter Result to Send to Doctor");
            if(userInput == null){
                return;
            }
            System.out.println(sampleNum);
            Check.setStatusSampleTrue(sampleNum,doc_id,userInput,login.EmpID);
            //sent result to doctor table result
            
            fillSampleTable();
            InputStream input;
            JasperDesign jd;
            JasperReport jr;
            JasperPrint jp;
            OutputStream output;
            //jasper file in constructor
            input = new FileInputStream(new File("Invoice_1.jrxml"));
            jd=JRXmlLoader.load(input);
            jr=JasperCompileManager.compileReport(jd);
            //hashmap with .put method with 2 arguments: the parameter name as in your jasper report
            //and the value of the parameter that you want you reportin
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("sample_no", sampleNum);
            jp=JasperFillManager.fillReport(jr, parameters, c);
            //that's it the rest is as usual
            JFrame f = new JFrame("Lab Report");
            f .getContentPane().add(new JRViewer(jp));
            f.pack();
            f.setSize(1920  , 1080);
            f.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(Tech.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_SubmitButtonActionPerformed

    private void jPanel1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanel1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1FocusGained

    private void SearchSampleTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchSampleTextFieldFocusGained
        // TODO add your handling code here:
          if (SearchSampleTextField.getText().equals("Search Sample...")) {
                    SearchSampleTextField.setText("");
                    SearchSampleTextField.setForeground(java.awt.Color.black);
        }
    }//GEN-LAST:event_SearchSampleTextFieldFocusGained

    private void SearchSampleTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchSampleTextFieldFocusLost
        // TODO add your handling code here:
        if (SearchSampleTextField.getText().isEmpty()) {
                    SearchSampleTextField.setText("Search Sample...");
                    SearchSampleTextField.setForeground(java.awt.Color.GRAY);
                }
    }//GEN-LAST:event_SearchSampleTextFieldFocusLost

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int result = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to Logout?",
                "Logout Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        // Check the user's choice
        if (result == JOptionPane.YES_OPTION) {
                    this.dispose();
                    new login();
            System.out.println("Logging out...");
        }
           
             
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Tech.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tech.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tech.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tech.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              JFrame temp= new JFrame();
                new Tech(temp).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField SearchSampleTextField;
    private javax.swing.JButton SubmitButton;
    private javax.swing.JLabel WelcomeLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable techTable;
    // End of variables declaration//GEN-END:variables
}
