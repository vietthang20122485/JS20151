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
    private String ms;
    private String noiDung;
    private String a;
    private String b;
    private String c;
    private String d;
    private String dapAn;
    private String hard;
    public CauHoi() {
    }

    public CauHoi(String ms, String noiDung, String a, String b, String c, String d, String dapAn, String hard) {
        this.ms = ms;
        this.noiDung = noiDung;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.dapAn = dapAn;
        this.hard = hard;
    }

    public CauHoi(String ms) {
        this.ms = ms;
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
           pst.setString(1, ch.getMs());
           pst.setString(2,ch.getNoiDung());
           pst.setString(3,ch.getA());
           pst.setString(4,ch.getB());
           pst.setString(5,ch.getC());
           pst.setString(6,ch.getD());
           pst.setString(7,ch.getDapAn());
           pst.setString(8,ch.getHard());
           if(pst.executeUpdate()>0) JOptionPane.showMessageDialog(null, "Insert success!");
       } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null, "Error insert!");
       }
   }
    public void update(CauHoi ch){
       try {
           PreparedStatement pst = Connect.connection.prepareStatement("update Cauhoi set Noidung = ?, A = ?, B = ?, C = ?, D = ?, Dapan = ?, Hard = ? where MS = ?");
           pst.setString(1, ch.getNoiDung());
           pst.setString(2,ch.getA());
           pst.setString(3,ch.getB());
           pst.setString(4,ch.getC());
           pst.setString(5,ch.getD());
           pst.setString(6,ch.getDapAn());
           pst.setString(7,ch.getHard());
           pst.setString(8,ch.getMs());
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
            if(ch.getNoiDung().equals("")||ch.getMs().equals("")||ch.getA().equals("")||ch.getB().equals("")||ch.getC().equals("")||ch.getD().equals("")){
                JOptionPane.showMessageDialog(null, "Thông tin nhập vào không hợp lệ!");
                return false;
            }
            return true;
       }

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getDapAn() {
        return dapAn;
    }

    public void setDapAn(String dapAn) {
        this.dapAn = dapAn;
    }

    public String getHard() {
        return hard;
    }

    public void setHard(String hard) {
        this.hard = hard;
    }
    
    
    
}
