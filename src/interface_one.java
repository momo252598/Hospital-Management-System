/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.project;
import com.mycompany.project.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author momo
 */
public class interface_one extends javax.swing.JFrame {

    /**
     * Creates new form interface_one
     */
    
//public static ArrayList<String> info = new ArrayList<>();
    static public String PatientID = "";
    static public JFrame temp_interface_one;
    public interface_one(String name,String id,JFrame parent) {
        initComponents();
        this.setResizable(false); 
        this.setLocationRelativeTo(null);
        this.name.setText("Welcome, "+name+"!");
        this.name1.setText(this.name.getText());
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            String connInfo="jdbc:postgresql://localhost:5432/postgres";
            Connection con=DriverManager.getConnection(connInfo,"hmd","0000");
            String sql="select dep_name,floor from proj_1.department order by dep_name";
            Statement stmt=con.createStatement();
            ResultSet rs= stmt.executeQuery(sql);
            this.ChooseDepartment.removeAllItems();
            int flag=0;
            while(rs.next()){
                String depname=rs.getString(1);
                 this.ChooseDepartment.addItem(depname);
                if(flag==0){
                String depfloor=rs.getString(2);
                this.floor_lbl.setText(depfloor+" Floor");
                flag=1;}   
            }
            Integer beds=6;
            TableModel tableModel = PatientTable.getModel();
            int patient_count=tableModel.getRowCount();
            beds-=patient_count;
            this.beds_lbl.setText(beds.toString()+" Beds Available");
            con.close();
            this.hidden.setText(id);
    } catch (Exception ex) {
            //Logger.getLogger(ayo.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null,ex.toString());
        }
        
         SearchTextField.getDocument().addDocumentListener(new DocumentListener() {
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
                String searchTerm = SearchTextField.getText();

                if (searchTerm.trim().isEmpty() || searchTerm.trim().equals("Search for Patient ID/Name...")) {
                    // If the search term is empty, fill the table with all contents
                    fillTablePatient();
                } else {
                    // Perform the search based on the entered text
                    try {
                        String query = "SELECT * FROM proj_1.patient WHERE (LOWER(CONCAT_WS(' ', fname, lname)) LIKE LOWER(?) OR id_no LIKE ?) AND dep_name = ?";
                        Connection c = Connector.getConnection();

                        // Create a prepared statement
                        try (PreparedStatement preparedStatement = c.prepareStatement(query)) {
                            // Set the parameter in the prepared statement
                            preparedStatement.setString(1, "%" + searchTerm.toLowerCase() + "%");
                            preparedStatement.setString(2, "%" + searchTerm + "%");
                            preparedStatement.setString(3, (String) ChooseDepartment.getSelectedItem());
                            
                            // Execute the query and get the result set
                            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                                // Populate the jTable with the search results
                                // (Assuming jTable model is DefaultTableModel)
                                DefaultTableModel model = (DefaultTableModel) PatientTable.getModel();
                                model.setRowCount(0); // Clear existing rows

                                while (resultSet.next()) {
                                    // Assuming your jTable has columns id, name, blood_type
                                    String id = resultSet.getString("id_no");
                                    String name = resultSet.getString("fname") + " " + resultSet.getString("lname");
                                    String bloodType = resultSet.getString("blood_type");

                                    // Add a new row to the jTable
                                    model.addRow(new Object[]{id, name, bloodType});
                                }
                                resultSet.close();
                                preparedStatement.close();
                                c.close();
                                
                            }
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    
                    
                }
            }
        });
         
         SearchTextFieldOrder.getDocument().addDocumentListener(new DocumentListener() {
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
                String searchTerm = SearchTextFieldOrder.getText();

                if (searchTerm.trim().isEmpty() || searchTerm.trim().equals("Search for Order Number...")) {
                    // If the search term is empty, fill the table with all contents
                    fillTableOrder();
                    //System.out.println("yo exeption ooooooooooooorrrrrrrrrrrrrrrrrrr");
                } else {
                    // Perform the search based on the entered text
                    try {
                        String query = "SELECT * FROM proj_1.orders WHERE order_no = ?";
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
                                DefaultTableModel model = (DefaultTableModel) OrderTable.getModel();
                                model.setRowCount(0); // Clear existing rows

                                while (resultSet.next()) {
                                    // Assuming your jTable has columns id, name, blood_type
                                    String info[]= { resultSet.getString("order_no") ,resultSet.getString(6) ,
                                    Check.getDiagnosis(resultSet.getString(6), resultSet.getString("doc_id"))
                                    ,Check.getFullName("employee", resultSet.getString("nurse_id")),
                                    resultSet.getString("order_desc")};
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
                        System.out.println("ayo hoon el error");
                    }
                }
            }
        });
         
         if (SearchTextField.getText().isEmpty()) {
            SearchTextField.setText("Search for Patient ID/Name...");
            SearchTextField.setForeground(java.awt.Color.GRAY);
            //fillTablePatient();
        }
         
        if (SearchTextFieldOrder.getText().isEmpty()) {
            SearchTextFieldOrder.setText("Search for Order Number...");
            SearchTextFieldOrder.setForeground(java.awt.Color.GRAY);
//            fillTableOrder();
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

        jPanel3 = new javax.swing.JPanel();
        OrdersTab = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        ChooseDepartment = new javax.swing.JComboBox<>();
        ManageButton = new javax.swing.JButton();
        name = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        PatientTable = new javax.swing.JTable();
        SearchTextField = new javax.swing.JTextField();
        LogoutButton = new javax.swing.JButton();
        floor_lbl = new javax.swing.JLabel();
        beds_lbl = new javax.swing.JLabel();
        hidden = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        OrderTable = new javax.swing.JTable();
        SubmitOrderButton = new javax.swing.JButton();
        SearchTextFieldOrder = new javax.swing.JTextField();
        SendLabButton = new javax.swing.JButton();
        name1 = new javax.swing.JLabel();
        LogoutButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 204));
        setSize(new java.awt.Dimension(500, 700));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        OrdersTab.setBackground(new java.awt.Color(204, 204, 204));
        OrdersTab.setForeground(new java.awt.Color(0, 0, 0));
        OrdersTab.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                OrdersTabFocusGained(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(217, 244, 255));
        jPanel4.setLayout(null);

        ChooseDepartment.setBackground(new java.awt.Color(204, 204, 204));
        ChooseDepartment.setForeground(new java.awt.Color(0, 0, 0));
        ChooseDepartment.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChooseDepartmentItemStateChanged(evt);
            }
        });
        ChooseDepartment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChooseDepartmentActionPerformed(evt);
            }
        });
        jPanel4.add(ChooseDepartment);
        ChooseDepartment.setBounds(0, 10, 180, 22);

        ManageButton.setBackground(new java.awt.Color(153, 204, 255));
        ManageButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ManageButton.setForeground(new java.awt.Color(0, 0, 0));
        ManageButton.setText("Manage");
        ManageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ManageButtonActionPerformed(evt);
            }
        });
        jPanel4.add(ManageButton);
        ManageButton.setBounds(350, 470, 160, 30);

        name.setBackground(new java.awt.Color(204, 204, 204));
        name.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        name.setForeground(new java.awt.Color(0, 0, 0));
        name.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.gray, java.awt.Color.gray));
        name.setOpaque(true);
        jPanel4.add(name);
        name.setBounds(550, 0, 280, 30);

        PatientTable.setAutoCreateRowSorter(true);
        PatientTable.setBackground(new java.awt.Color(204, 204, 204));
        PatientTable.setForeground(new java.awt.Color(0, 0, 0));
        PatientTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Full Name", "Blood Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(PatientTable);

        jPanel4.add(jScrollPane3);
        jScrollPane3.setBounds(200, 120, 452, 300);

        SearchTextField.setBackground(new java.awt.Color(204, 204, 204));
        SearchTextField.setForeground(new java.awt.Color(0, 0, 0));
        SearchTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                SearchTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                SearchTextFieldFocusLost(evt);
            }
        });
        SearchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchTextFieldActionPerformed(evt);
            }
        });
        jPanel4.add(SearchTextField);
        SearchTextField.setBounds(200, 80, 190, 20);

        LogoutButton.setBackground(new java.awt.Color(255, 204, 153));
        LogoutButton.setForeground(new java.awt.Color(0, 0, 0));
        LogoutButton.setText("Logout");
        LogoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutButtonActionPerformed(evt);
            }
        });
        jPanel4.add(LogoutButton);
        LogoutButton.setBounds(10, 520, 100, 23);

        floor_lbl.setBackground(new java.awt.Color(255, 204, 153));
        floor_lbl.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        floor_lbl.setForeground(new java.awt.Color(0, 0, 0));
        floor_lbl.setText("Floor");
        floor_lbl.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        floor_lbl.setOpaque(true);
        jPanel4.add(floor_lbl);
        floor_lbl.setBounds(10, 40, 70, 20);

        beds_lbl.setBackground(new java.awt.Color(255, 204, 153));
        beds_lbl.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        beds_lbl.setForeground(new java.awt.Color(0, 0, 0));
        beds_lbl.setText("Avl beds");
        beds_lbl.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        beds_lbl.setOpaque(true);
        jPanel4.add(beds_lbl);
        beds_lbl.setBounds(10, 60, 120, 20);

        hidden.setText("jLabel1");
        jPanel4.add(hidden);
        hidden.setBounds(750, 0, 37, 16);

        OrdersTab.addTab("Patients", jPanel4);

        jPanel5.setBackground(new java.awt.Color(217, 244, 255));
        jPanel5.setLayout(null);

        OrderTable.setBackground(new java.awt.Color(204, 204, 204));
        OrderTable.setForeground(new java.awt.Color(0, 0, 0));
        OrderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "#Order", "Patient Id", "Diagnosis", "Nurse Name", "Order Discription"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(OrderTable);

        jPanel5.add(jScrollPane2);
        jScrollPane2.setBounds(170, 80, 510, 330);

        SubmitOrderButton.setBackground(new java.awt.Color(153, 204, 255));
        SubmitOrderButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        SubmitOrderButton.setForeground(new java.awt.Color(0, 0, 0));
        SubmitOrderButton.setText("Confirm Order");
        SubmitOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitOrderButtonActionPerformed(evt);
            }
        });
        jPanel5.add(SubmitOrderButton);
        SubmitOrderButton.setBounds(230, 430, 130, 30);

        SearchTextFieldOrder.setBackground(new java.awt.Color(204, 204, 204));
        SearchTextFieldOrder.setForeground(new java.awt.Color(0, 0, 0));
        SearchTextFieldOrder.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                SearchTextFieldOrderFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                SearchTextFieldOrderFocusLost(evt);
            }
        });
        SearchTextFieldOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchTextFieldOrderActionPerformed(evt);
            }
        });
        jPanel5.add(SearchTextFieldOrder);
        SearchTextFieldOrder.setBounds(170, 50, 190, 20);

        SendLabButton.setBackground(new java.awt.Color(153, 204, 255));
        SendLabButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        SendLabButton.setForeground(new java.awt.Color(0, 0, 0));
        SendLabButton.setText("Send to Lab");
        SendLabButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendLabButtonActionPerformed(evt);
            }
        });
        jPanel5.add(SendLabButton);
        SendLabButton.setBounds(480, 430, 130, 30);

        name1.setBackground(new java.awt.Color(204, 204, 204));
        name1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        name1.setForeground(new java.awt.Color(0, 0, 0));
        name1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name1.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.gray, java.awt.Color.gray));
        name1.setOpaque(true);
        jPanel5.add(name1);
        name1.setBounds(550, 0, 280, 30);

        LogoutButton1.setBackground(new java.awt.Color(255, 204, 153));
        LogoutButton1.setForeground(new java.awt.Color(0, 0, 0));
        LogoutButton1.setText("Logout");
        LogoutButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutButton1ActionPerformed(evt);
            }
        });
        jPanel5.add(LogoutButton1);
        LogoutButton1.setBounds(10, 520, 100, 23);

        OrdersTab.addTab("Orders", jPanel5);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(OrdersTab, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(OrdersTab, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ChooseDepartmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChooseDepartmentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChooseDepartmentActionPerformed

    private void ChooseDepartmentItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChooseDepartmentItemStateChanged
        // TODO add your handling code here:
        fillTablePatient();
        Integer beds=6;
            TableModel tableModel = PatientTable.getModel();
            int patient_count=tableModel.getRowCount();
            beds-=patient_count;
            this.beds_lbl.setText(beds.toString()+" Beds Available");
    }//GEN-LAST:event_ChooseDepartmentItemStateChanged

    private void ManageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ManageButtonActionPerformed
       
        int row = PatientTable.getSelectedRow();
       if(row == -1){
            JOptionPane.showMessageDialog(null, "Select Patient Row", "Row Selection Error", JOptionPane.INFORMATION_MESSAGE);
            return;
       }DefaultTableModel PatientTableModel = (DefaultTableModel) PatientTable.getModel();
       
       PatientID = (PatientTableModel.getValueAt(row, 0).toString());
       
       manage_patient temp=new manage_patient(PatientID,login.EmpID,this);
       temp.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_ManageButtonActionPerformed

    private void SubmitOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitOrderButtonActionPerformed
        // TODO add your handling code here:
        if(login.IsDoctor){
            JOptionPane.showMessageDialog(null, "Permission Denied.", "Warning.", JOptionPane.INFORMATION_MESSAGE);
            return;}
        int row = OrderTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(null, "Select Row", "Row Selection Error", JOptionPane.INFORMATION_MESSAGE);
            return;}
        String userInput = JOptionPane.showInputDialog("Notes About Patient");
        DefaultTableModel OrderTableModel = (DefaultTableModel) OrderTable.getModel();
        Check.setStatusOrderTrue((OrderTableModel.getValueAt(row, 0).toString()),(OrderTableModel.getValueAt(row, 1).toString()),userInput);
        fillTableOrder();
        
    }//GEN-LAST:event_SubmitOrderButtonActionPerformed
    private void fillTableOrder(){
        try {
            // TODO add your handling code here:
            String fillTablesql = "";
            Connection c = Connector.getConnection();
            if(login.IsDoctor){
                fillTablesql = "SELECT \"patient_ID\", order_desc,nurse_id, doc_id, order_no "
                        + "from proj_1.orders where doc_id='"+login.EmpID+"'  and status='false'";}
            else if(login.IsNurse){
                fillTablesql = "SELECT \"patient_ID\", order_desc,nurse_id, doc_id, order_no "
                        + "from proj_1.orders where nurse_id='"+login.EmpID+"'  and status='false'";
                
            }
            Statement stmt=c.createStatement();    
            ResultSet rs= stmt.executeQuery(fillTablesql);
            
            DefaultTableModel OrderTableModel = (DefaultTableModel) OrderTable.getModel();
            OrderTableModel.setRowCount(0);
            while(rs.next()){
                //System.out.println(Check.getDiagnosis(rs.getString("\"patient_ID\""), rs.getString("doc_id")));
                String info[]= { rs.getString(5) ,rs.getString(1) ,
                                Check.getDiagnosis(rs.getString(1), rs.getString(4))
                        ,Check.getFullName("employee", rs.getString(3)),
                                rs.getString(2)};
                //System.out.println(name);
                OrderTableModel.addRow(info);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(interface_one.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void fillTablePatient(){
        try {         
            // TODO add your handling code here:
            DriverManager.registerDriver(new org.postgresql.Driver());
            String connInfo="jdbc:postgresql://localhost:5432/postgres";
            Connection con=DriverManager.getConnection(connInfo,"hmd","0000");
            String sql="select *  from proj_1.patient where dep_name='"+this.ChooseDepartment.getSelectedItem()+"'";
            Statement stmt=con.createStatement();
            ResultSet rs= stmt.executeQuery(sql);
            DefaultTableModel PatientTableModel = (DefaultTableModel) PatientTable.getModel();
            //mod.removeAllElements();
            //this.PatientTable.setModel(PatientTableModel);
            PatientTableModel.setRowCount(0);
            while(rs.next()){
                String name[]= {rs.getString(1) , rs.getString(2) +" "+rs.getString(4), rs.getString(6)};
                //System.out.println(name); 
               PatientTableModel.addRow(name);
            }
            sql="select floor from proj_1.department where dep_name='"+this.ChooseDepartment.getSelectedItem()+"'";
            rs=stmt.executeQuery(sql);
            while(rs.next()){
            String floor=rs.getString(1);
            this.floor_lbl.setText(floor+" Floor");
            }
            con.close();
    } catch (Exception ex) {
            //Logger.getLogger(ayo.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null,ex.toString());
        }
    }
    
    private void OrdersTabFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_OrdersTabFocusGained
        try {
            // TODO add your handling code here:
            String fillTablesql = "";
            Connection c = Connector.getConnection();
            if(login.IsDoctor){
                fillTablesql = "SELECT \"patient_ID\", order_desc,nurse_id, doc_id, order_no "
                        + "from proj_1.orders where doc_id='"+login.EmpID+"'  and status='false'";}
            else if(login.IsNurse){
                fillTablesql = "SELECT \"patient_ID\", order_desc,nurse_id, doc_id, order_no "
                        + "from proj_1.orders where nurse_id='"+login.EmpID+"'  and status='false'";
                
            }
            
            
            
            Statement stmt=c.createStatement();    
            ResultSet rs= stmt.executeQuery(fillTablesql);
            
            DefaultTableModel OrderTableModel = (DefaultTableModel) OrderTable.getModel();
            OrderTableModel.setRowCount(0);
            while(rs.next()){
                //System.out.println(Check.getDiagnosis(rs.getString("\"patient_ID\""), rs.getString("doc_id")));
                String info[]= { rs.getString("order_no") ,rs.getString(1) ,
                                Check.getDiagnosis(rs.getString(1), rs.getString("doc_id"))
                        ,Check.getFullName("employee", rs.getString("nurse_id")),
                                rs.getString("order_desc")};
                //System.out.println(name);
                OrderTableModel.addRow(info);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(interface_one.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
    }//GEN-LAST:event_OrdersTabFocusGained

    private void SearchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchTextFieldActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_SearchTextFieldActionPerformed

    private void SearchTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchTextFieldFocusGained
        // TODO add your handling code here:
            if (SearchTextField.getText().equals("Search for Patient ID/Name...")) {
            SearchTextField.setText("");
            SearchTextField.setForeground(java.awt.Color.black);
            //fillTablePatient();
        }
    }//GEN-LAST:event_SearchTextFieldFocusGained

    private void SearchTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchTextFieldFocusLost
        // TODO add your handling code here:
         if (SearchTextField.getText().isEmpty()) {
            SearchTextField.setText("Search for Patient ID/Name...");
            SearchTextField.setForeground(java.awt.Color.GRAY);
            fillTablePatient();
        }
    }//GEN-LAST:event_SearchTextFieldFocusLost

    private void SearchTextFieldOrderFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchTextFieldOrderFocusGained
        // TODO add your handling code here:
        if (SearchTextFieldOrder.getText().equals("Search for Order Number...")) {
            SearchTextFieldOrder.setText("");
            SearchTextFieldOrder.setForeground(java.awt.Color.black);
        //fillTableOrder();
        }
    }//GEN-LAST:event_SearchTextFieldOrderFocusGained

    private void SearchTextFieldOrderFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchTextFieldOrderFocusLost
        // TODO add your handling code here:
         if (SearchTextFieldOrder.getText().isEmpty()) {
            SearchTextFieldOrder.setText("Search for Order Number...");
            SearchTextFieldOrder.setForeground(java.awt.Color.GRAY);
            //fillTableOrder();
        
        }

    }//GEN-LAST:event_SearchTextFieldOrderFocusLost

    private void SearchTextFieldOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchTextFieldOrderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchTextFieldOrderActionPerformed

    private void SendLabButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendLabButtonActionPerformed
        // TODO add your handling code here:
        if(login.IsDoctor){
            JOptionPane.showMessageDialog(null, "Permission Denied.", "Warning.", JOptionPane.INFORMATION_MESSAGE);
            return;}
            
        int row = OrderTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(null, "Select Row", "Row Selection Error", JOptionPane.INFORMATION_MESSAGE);
            return;}
        DefaultTableModel OrderTableModel = (DefaultTableModel) OrderTable.getModel();
        int order_id
                = (Integer.parseInt(OrderTableModel.getValueAt(row, 0).toString()));

        
        String userInput = JOptionPane.showInputDialog("Enter Order to Send to Lab");

        // Check if the user clicked Cancel or closed the dialog
        if (userInput != null) {
            try {
                // User entered something
                //System.out.println("You entered: " + userInput);
                String sql = "select * from proj_1.orders where order_no ="+order_id+" ";
                Connection c = Connector.getConnection();
                Statement stmt=c.createStatement();
                ResultSet rs= stmt.executeQuery(sql);
                //
                String pid="";
                String doc_id="";
                String tech_id="";
                while(rs.next()){                
                pid=rs.getString("patient_ID");
                doc_id=rs.getString("doc_id");
                }
                String sql2="select tech_id from proj_1.nurse_tech where nurse_id='"+login.EmpID+"'";
                rs=stmt.executeQuery(sql2);
                while(rs.next()){                
                tech_id=rs.getString("tech_id");
                }
                String sql3="insert into proj_1.nurse_tech(nurse_id,tech_id,pid,doc_id,sample_desc) values('"+login.EmpID+"','"+tech_id+"','"+pid+"','"+doc_id+"','"+userInput+"')";
                stmt.executeUpdate(sql3);
                JOptionPane.showMessageDialog(null,
                "Sample Discription Sent Successfully!",
                "Sent Discription.",
                JOptionPane.INFORMATION_MESSAGE);
                rs.close();
                stmt.close();
                c.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(interface_one.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else {
            // User clicked Cancel or closed the dialog
            System.out.println("No input provided.");
        }
        
        Check.setStatusOrderTrue((OrderTableModel.getValueAt(row, 0).toString()),"no","no");
        fillTableOrder();
        
    }//GEN-LAST:event_SendLabButtonActionPerformed

    private void LogoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutButtonActionPerformed
        // TODO add your handling code here:
        int result = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to logout?",
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
            // User chose not to logout
            return;   
    }//GEN-LAST:event_LogoutButtonActionPerformed

    private void LogoutButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutButton1ActionPerformed
        // TODO add your handling code here:
        int result = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to logout?",
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
            // User chose not to logout
            return; 
    }//GEN-LAST:event_LogoutButton1ActionPerformed
    
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
            java.util.logging.Logger.getLogger(interface_one.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(interface_one.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(interface_one.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(interface_one.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String temp="";
                String temp2="";
                JFrame temp3 = new JFrame();
                new interface_one(temp,temp2,temp3).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ChooseDepartment;
    private javax.swing.JButton LogoutButton;
    private javax.swing.JButton LogoutButton1;
    private javax.swing.JButton ManageButton;
    private javax.swing.JTable OrderTable;
    private javax.swing.JTabbedPane OrdersTab;
    private javax.swing.JTable PatientTable;
    private javax.swing.JTextField SearchTextField;
    private javax.swing.JTextField SearchTextFieldOrder;
    private javax.swing.JButton SendLabButton;
    private javax.swing.JButton SubmitOrderButton;
    private javax.swing.JLabel beds_lbl;
    private javax.swing.JLabel floor_lbl;
    private javax.swing.JLabel hidden;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel name;
    private javax.swing.JLabel name1;
    // End of variables declaration//GEN-END:variables
}
