/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DuLieu;

import NghiepVu.Connect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author vietthang
 */
public class TaiKhoan {
    private String MS;
    private String Username;
    private String Password;
    private String Role;

    public TaiKhoan(String MS, String Username, String Password, String Role) {
        this.MS = MS;
        this.Username = Username;
        this.Password = Password;
        this.Role = Role;
    }

    public TaiKhoan() {
    }
    
    public ResultSet getdata(){
           ResultSet rs =  null;
           try {
               Statement st = Connect.connection.createStatement();
               rs = st.executeQuery("select MS,Username, Role from Taikhoan");
           }
           catch(SQLException ex){
               System.out.println("Error");
           }
              return rs;
    }
    
    public void insert(TaiKhoan tk){
           try{
               PreparedStatement pst1 = Connect.connection.prepareStatement("insert into TaiKhoan values(?,?,?,?)");
               pst1.setString(1, tk.getMS());
               pst1.setString(2, tk.getUsername());
               pst1.setString(3, tk.getPassword());
               pst1.setString(4, tk.getRole());
           if(pst1.executeUpdate()>0) 
               JOptionPane.showMessageDialog(null, "Insert success!");
               }      
           catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Error insert!");
           }
    }
    
    public void delete(String MS){
           try{
               PreparedStatement pst1 = Connect.connection.prepareStatement("delete from Taikhoan where MS = ?");
               pst1.setString(1, MS);
               if(pst1.executeUpdate()>0) JOptionPane.showMessageDialog(null,"Delete success");
           }
           catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Error delete!");
         }   
    }
    
    public ResultSet getdata_MS(String MS){
          ResultSet rs = null;
           try {
             PreparedStatement pst = Connect.connection.prepareStatement("Select MS from TaiKhoan where MS =?");
             pst.setString(1, MS);
             rs = pst.executeQuery();
         } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "loi roi@@@@!");
         }
          return rs;
    }
    
    public String TimMS(String ten){
           ResultSet rs = null;
           String ms="";
         try {
             PreparedStatement pst = Connect.connection.prepareStatement("select MS from TaiKhoan where Username=?");
             pst.setString(1, ten);
             rs=pst.executeQuery();
             while (rs.next()){
             ms=rs.getString(1);
             }
         } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error99999!");
         }
           return ms;
    }
    
    public ResultSet getdata_Username(String Username){
          ResultSet rs = null;
                     try {
             PreparedStatement pst = Connect.connection.prepareStatement("Select Username from TaiKhoan where Username=?");
             pst.setString(1, Username);
             rs = pst.executeQuery();
         } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "loi roi!$$$$$");
         }
          return rs;
    }
    
    public int KiemTraGiangVien(String username, String password){
           int kt=0;
           ResultSet rs =null;   
       try {
           PreparedStatement pst = Connect.connection.prepareStatement("select Username, Password from TaiKhoan where Username = ? and Password = ? and Role = 'Teacher'");
           pst.setString(1, username);
           pst.setString(2, password);
           rs = pst.executeQuery();
           if(rs.next()) kt=1;
           else JOptionPane.showMessageDialog(null, "Tài khoản không hợp lệ!");
       } catch (SQLException ex) {
           System.out.println("Error3!");
       }
       return kt;
      }
    
      public int KiemTraSinhVien(String username, String password){
           int kt=0;
           ResultSet rs =null;   
       try {
           PreparedStatement pst = Connect.connection.prepareStatement("select Username, Password from TaiKhoan where Username = ? and Password = ? and Role = 'Student'");
           pst.setString(1, username);
           pst.setString(2, password);
           rs = pst.executeQuery();
           if(rs.next()) kt=1;
           else JOptionPane.showMessageDialog(null, "Tài khoản không hợp lệ!");
       } catch (SQLException ex) {
           System.out.println("Error3!");
       }
       return kt;
      }
      
      public int KiemTraAdmin(String username, String password){
          int kt=0;
          ResultSet rs=null;
          try{
              PreparedStatement pst = Connect.connection.prepareStatement("select Username, Password, Role from TaiKhoan where Username = ? and Password = ? and Role = 'Admin'");
           pst.setString(1, username);
           pst.setString(2, password);
           rs = pst.executeQuery();
           if(rs.next()) kt=1;
           else JOptionPane.showMessageDialog(null, "Tài khoản không hợp lệ!");
       } catch (SQLException ex) {
           System.out.println("Error3!");
       }
       return kt;
      }

    public String getMS() {
        return MS;
    }

    public void setMS(String MS) {
        this.MS = MS;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }
}
