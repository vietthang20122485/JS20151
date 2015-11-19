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
    private String ms;
    private String userName;
    private String passWord;
    private String role;

    public TaiKhoan(String ms, String username, String password, String role) {
        this.ms = ms;
        this.userName = username;
        this.passWord = password;
        this.role = role;
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
               pst1.setString(1, tk.getMs());
               pst1.setString(2, tk.getUserName());
               pst1.setString(3, tk.getPassWord());
               pst1.setString(4, tk.getRole());
           if(pst1.executeUpdate()>0) 
               JOptionPane.showMessageDialog(null, "Insert success!");
               }      
           catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Error insert!");
           }
    }
    
    public void delete(String ms){
           try{
               PreparedStatement pst1 = Connect.connection.prepareStatement("delete from Taikhoan where MS = ?");
               pst1.setString(1, ms);
               if(pst1.executeUpdate()>0) JOptionPane.showMessageDialog(null,"Delete success");
           }
           catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Error delete!");
         }   
    }
    
    public ResultSet getdata_MS(String ms){
          ResultSet rs = null;
           try {
             PreparedStatement pst = Connect.connection.prepareStatement("Select MS from TaiKhoan where MS =?");
             pst.setString(1, ms);
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
    
    public ResultSet getdata_Username(String username){
          ResultSet rs = null;
                     try {
             PreparedStatement pst = Connect.connection.prepareStatement("Select Username from TaiKhoan where Username=?");
             pst.setString(1, username);
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

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
