/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hp
 */
public class Check {
    public static boolean FoundEmp(String EmpType, String Username, String password){
     try {                                            
            // TODO add your handling code here:
            Connection c = Connector.getConnection();
            String sql = "SELECT * FROM proj_1."+ EmpType +" WHERE emp_id = ? AND pass_word = ?";

            
            try (PreparedStatement preparedStatement = c.prepareStatement(sql)) {
                // Setting parameters for each table
                
                    preparedStatement.setString(1, Username);
                    preparedStatement.setString(2, password);
                
                
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    
                    
                    boolean result = resultSet.next();
//                                resultSet.close();
//                                preparedStatement.close();
//                                c.close();
                    //return the boolean if found = true 
                    return result;
                    
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(welcome.class.getName()).log(Level.SEVERE, null, ex); 
        }   
     return false;
    }
    
    public static String getFullName(String EmpType, String Username, String password) {
    try {
        Connection c = Connector.getConnection();
        String sql = "SELECT fname, mname, lname FROM proj_1." + EmpType + " WHERE emp_id = ?";

        try (PreparedStatement preparedStatement = c.prepareStatement(sql)) {
            preparedStatement.setString(1, Username);
            //preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve first, middle, and last names from the result set
                    String firstName = resultSet.getString("fname");
                    String middleName = resultSet.getString("mname");
                    String lastName = resultSet.getString("lname");
//                                resultSet.close();
//                                preparedStatement.close();
//                                c.close();

                    // Concatenate the names and return the full name
                    return firstName + " " + middleName + " " + lastName;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    } catch (SQLException ex) {
        Logger.getLogger(welcome.class.getName()).log(Level.SEVERE, null, ex);
    }

    // Return an empty string or another appropriate value if the full name is not found
    return "";
}
    
    public static String getFullName(String EmpType, String Username) {
        
    try {
        Connection c = Connector.getConnection();
        String sql = "SELECT fname, lname FROM proj_1." + EmpType + " WHERE emp_id = ?";

        try (PreparedStatement preparedStatement = c.prepareStatement(sql)) {
            preparedStatement.setString(1, Username);
            //preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve first, middle, and last names from the result set
                    String firstName = resultSet.getString("fname");
                    //String middleName = resultSet.getString("mname");
                    String lastName = resultSet.getString("lname");

                                resultSet.close();
                                preparedStatement.close();
                                c.close();
                    // Concatenate the names and return the full name
                    System.out.println("this ->>>>> " + firstName + " " + lastName);
                    return firstName + " " + lastName;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    } catch (SQLException ex) {
        Logger.getLogger(welcome.class.getName()).log(Level.SEVERE, null, ex);
    }

    // Return an empty string or another appropriate value if the full name is not found
        System.out.println("noooooooooooooooooooooooo");
    return "";
}
    
    public static String getDiagnosis(String PatientID, String DoctorID) {
    try {
        Connection c = Connector.getConnection();
        String sql = "SELECT description FROM proj_1.diagnosis WHERE id_no = ? and doc_id = ?";

        try (PreparedStatement preparedStatement = c.prepareStatement(sql)) {
            preparedStatement.setString(1, PatientID);
            preparedStatement.setString(2, DoctorID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve first, middle, and last names from the result set
                    String Diagnosis = resultSet.getString("description");
                    //String middleName = resultSet.getString("mname");
                    //String lastName = resultSet.getString("lname");


                    // Concatenate the names and return the full name
                String[] lines = Diagnosis.split("\n");

                // Print each line without the date and "--"
               
                    // Remove the date and "--"
                     //lines[0] = lines[0].substring(lines[0].indexOf("--") + 2);
                                resultSet.close();
                                preparedStatement.close();
                                c.close();
                    
                    return lines[0].substring(lines[0].indexOf("--") + 2);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    } catch (SQLException ex) {
        Logger.getLogger(welcome.class.getName()).log(Level.SEVERE, null, ex);
    }

    // Return an empty string or another appropriate value if the full name is not found
    return "";
}
    
    public static String getID(String EmpType, String Username, String password) {
    try {
        Connection c = Connector.getConnection();
        String sql = "SELECT fname, mname, lname FROM proj_1." + EmpType + " WHERE emp_id = ?";

        try (PreparedStatement preparedStatement = c.prepareStatement(sql)) {
            preparedStatement.setString(1, Username);
            //preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve first, middle, and last names from the result set
                    String firstName = resultSet.getString("fname");
                    String middleName = resultSet.getString("mname");
                    String lastName = resultSet.getString("lname");
//                    resultSet.close();
//                    preparedStatement.close();
//                    c.close();
                    // Concatenate the names and return the full name
                    return firstName + " " + middleName + " " + lastName;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    } catch (SQLException ex) {
        Logger.getLogger(welcome.class.getName()).log(Level.SEVERE, null, ex);
    }

    // Return an empty string or another appropriate value if the full name is not found
    return "";
}
    public static String getPatientName(String PatientID) {
    try {
        Connection c = Connector.getConnection();
        String sql = "SELECT fname, mname, lname FROM proj_1.patient WHERE id_no = ?";

        try (PreparedStatement preparedStatement = c.prepareStatement(sql)) {
            preparedStatement.setString(1, PatientID);
            //preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve first, middle, and last names from the result set
                    String firstName = resultSet.getString("fname");
                    String middleName = resultSet.getString("mname");
                    String lastName = resultSet.getString("lname");
//                    resultSet.close();
//                    preparedStatement.close();
//                    c.close();
                    // Concatenate the names and return the full name
                    return firstName + " " + middleName + " " + lastName;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    } catch (SQLException ex) {
        Logger.getLogger(welcome.class.getName()).log(Level.SEVERE, null, ex);
    }

    // Return an empty string or another appropriate value if the full name is not found
    return "";
}
    
public static void setStatusOrderTrue(String OrderNum,String Patient_id,String note) {
        try {
            Connection c = Connector.getConnection();
            c.setAutoCommit(false);
            String sql = "UPDATE proj_1.orders set status = 'true' where order_no = '"+OrderNum+"' ";
            String sql2 = "UPDATE proj_1.patient set nurse_note = '"+note+"' where id_no = '"+Patient_id+"' ";
            Statement stmt=c.createStatement();
            stmt.executeUpdate(sql);
            if(!note.equals("no"))
            stmt.executeUpdate(sql2);
            c.commit();
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(Check.class.getName()).log(Level.SEVERE, "...", ex);
        }
        
}

public static void setStatusSampleTrue(int SampleNum,String doc_id,String sample,String tech_id) {
        try {
            Connection c = Connector.getConnection();
            c.setAutoCommit(false);
            String sql = "UPDATE proj_1.nurse_tech set sample_status = 'true' where sample_no = '"+SampleNum+"' ";
            String sql2 = "UPDATE proj_1.doctor set status = 'true',result_desc='"+sample+"',tech_id='"+tech_id+"' where emp_id = '"+doc_id+"' ";
            Statement stmt=c.createStatement();
            stmt.executeUpdate(sql);
            stmt.executeUpdate(sql2);
            c.commit();
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(Check.class.getName()).log(Level.SEVERE, "...", ex);
        }
        
}


    
}
