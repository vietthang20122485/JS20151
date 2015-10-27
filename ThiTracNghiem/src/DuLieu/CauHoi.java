package DuLieu;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import NghiepVu.Connect;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class CauHoi {
    private String MS;
    private String Noidung;
    private String A;
    private String B;
    private String C;
    private String D;
    private String Dapan;
    private String Hard;
    public CauHoi() {
    }

    public CauHoi(String MS, String Noidung, String A, String B, String C, String D, String Dapan, String Hard) {
        this.MS = MS;
        this.Noidung = Noidung;
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        this.Dapan = Dapan;
        this.Hard = Hard;
    }

    public CauHoi(String MS) {
        this.MS = MS;
    }
    
    public ResultSet getdata(){
        ResultSet rs = null;
         try {  
           Statement st = Connect.connection.createStatement();
           rs = st.executeQuery("select * from Cauhoi");       
       } catch (SQLException ex) {
           System.out.println("Error!");
       }
         return rs;
    }
    
    public void insert (CauHoi ch){
       try {
           PreparedStatement pst = Connect.connection.prepareStatement("insert into Cauhoi values(?,?,?,?,?,?,?,?)");
           pst.setString(1, ch.getMS());
           pst.setString(2,ch.getNoidung());
           pst.setString(3,ch.getA());
           pst.setString(4,ch.getB());
           pst.setString(5,ch.getC());
           pst.setString(6,ch.getD());
           pst.setString(7,ch.getDapan());
           pst.setString(8,ch.getHard());
           if(pst.executeUpdate()>0) JOptionPane.showMessageDialog(null, "Insert success!");
       } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null, "Error insert!");
       }
   }
    public void update(CauHoi ch){
       try {
           PreparedStatement pst = Connect.connection.prepareStatement("update Cauhoi set Noidung = ?, A = ?, B = ?, C = ?, D = ?, Dapan = ?, Hard = ? where MS = ?");
           pst.setString(1, ch.getNoidung());
           pst.setString(2,ch.getA());
           pst.setString(3,ch.getB());
           pst.setString(4,ch.getC());
           pst.setString(5,ch.getD());
           pst.setString(6,ch.getDapan());
           pst.setString(7,ch.getHard());
           pst.setString(8,ch.getMS());
           if(pst.executeUpdate()>0) JOptionPane.showMessageDialog(null, "Update success!");
       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Error update!");
       }
   }
    public ResultSet Lay5CauHoiKho(){
           ResultSet rs = null;
           Statement st;
         try {
             st = Connect.connection.createStatement();
             rs= st.executeQuery("SELECT TOP 5 MS FROM Cauhoi where Hard='A' ORDER BY NEWID()");
         } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Loi lay ban ghi loai A!");
         }
         return rs;
    }
    
    public ResultSet Lay10CauHoiTrungBinh(){
           ResultSet rs = null;
           Statement st;
         try {
             st = Connect.connection.createStatement();
             rs= st.executeQuery("SELECT TOP 10 MS FROM Cauhoi where Hard='B' ORDER BY NEWID()");
         } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Loi lay ban ghi loai B!");
         }
         return rs;
    }
    
    public ResultSet Lay15CauHoiDe(){
           ResultSet rs = null;
           Statement st;
         try {
             st = Connect.connection.createStatement();
             rs= st.executeQuery("SELECT TOP 15 MS FROM Cauhoi where Hard='C' ORDER BY NEWID()");
         } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Loi lay ban ghi loai C!");
         }
         return rs;
    }
    
    public ResultSet LayCauHoi(String made){
           ResultSet rs = null;
         try {
             PreparedStatement pst = Connect.connection.prepareStatement("Select MS,Noidung,A,B,C,D,Dapan from Cauhoi where MS IN (select MS from ChiTietDe where MaDe=?)");
             pst.setString(1, made);
             rs = pst.executeQuery();
         } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi load câu hỏi từ đề!");
         }
           return rs;
    }
    
    public boolean check(CauHoi ch){
            if(ch.getNoidung().equals("")||ch.getMS().equals("")||ch.getA().equals("")||ch.getB().equals("")||ch.getC().equals("")||ch.getD().equals("")){
                JOptionPane.showMessageDialog(null, "Thông tin nhập vào không hợp lệ!");
                return false;
            }
            return true;
       }
    
    public String getMS() {
        return MS;
    }

    public void setMS(String MS) {
        this.MS = MS;
    }

    public String getNoidung() {
        return Noidung;
    }

    public void setNoidung(String Noidung) {
        this.Noidung = Noidung;
    }

    public String getA() {
        return A;
    }

    public void setA(String A) {
        this.A = A;
    }

    public String getB() {
        return B;
    }

    public void setB(String B) {
        this.B = B;
    }

    public String getC() {
        return C;
    }

    public void setC(String C) {
        this.C = C;
    }

    public String getD() {
        return D;
    }

    public void setD(String D) {
        this.D = D;
    }

    public String getDapan() {
        return Dapan;
    }

    public void setDapan(String Dapan) {
        this.Dapan = Dapan;
    }

    public String getHard() {
        return Hard;
    }

    public void setHard(String Hard) {
        this.Hard = Hard;
    }
    
}
