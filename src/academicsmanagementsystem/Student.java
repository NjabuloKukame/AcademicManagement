/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package academicsmanagementsystem;

import db.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nkuka
 */
public class Student {

    Connection con = (Connection) MyConnection.getConnection();
    PreparedStatement ps;

    public int getMax() {
        int id = 0;
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select max(id) from student_registration");

            while (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }

    public void insert(int id, String nameAndSurname, String date, String gender, String email, String phoneNumber, String address, String parentsName, String parentsNumber) {
        String sql = "insert into student_registration values(?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, nameAndSurname);
            ps.setString(3, date);
            ps.setString(4, gender);
            ps.setString(5, email);
            ps.setString(6, phoneNumber);
            ps.setString(7, address);
            ps.setString(8, parentsName);
            ps.setString(9, parentsNumber);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "New Student Added Successfully");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getStudent(JTable table, String searchValue) {
        String sql = "select * from student_registration where concat(id, name, email, phone_number) like ? order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[9];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                row[7] = rs.getString(8);
                row[8] = rs.getString(9);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(int id, String nameAndSurname, String date, String gender, String email, String phoneNumber, String address, String parentsName, String parentsNumber) {
        //Updating Student Data In The Program
        String sql = "update student_registration set name=?, date_of_birth=?, gender=?, email=?, phone_number=?, address=?, parents_name=?, parents_number=? where id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, nameAndSurname);
            ps.setString(2, date);
            ps.setString(3, gender);
            ps.setString(4, email);
            ps.setString(5, phoneNumber);
            ps.setString(6, address);
            ps.setString(7, parentsName);
            ps.setString(8, parentsNumber);
            ps.setInt(9, id);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Student's Information Updated Successfully");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean doesIDExist(int id) {
        //Checks if the id exists already in the database
        try {
            ps = con.prepareStatement("select * from student_registration where id =?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void delete(int id) {
        int yesOrNo = JOptionPane.showConfirmDialog(null, "Course And Scores Will Also Be Deleted", "Student Delete", JOptionPane.OK_CANCEL_OPTION, 0);
        if (yesOrNo == JOptionPane.OK_OPTION) {
            try {
                ps = con.prepareStatement("delete from student_registration where id=?");
                ps.setInt(1, id);
                
                if(ps.executeUpdate()>0){
                    JOptionPane.showMessageDialog(null, "Student's Information Deleted Successfully");
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
