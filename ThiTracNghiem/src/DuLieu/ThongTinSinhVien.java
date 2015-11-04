package DuLieu;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import NghiepVu.Connect;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class ThongTinSinhVien {
    String MSSV;
    String HoTen;
    java.sql.Date NgaySinh;
    String GioiTinh;
    String Lop;

    public ThongTinSinhVien(String MSSV, String HoTen, java.sql.Date NgaySinh, String GioiTinh, String Lop) {
        this.MSSV = MSSV;
        this.HoTen = HoTen;
        this.NgaySinh = NgaySinh;
        this.GioiTinh = GioiTinh;
        this.Lop = Lop;
    }
    public ResultSet getdataTS(){
        ResultSet rs = null;
         try {  
           Statement st = Connect.connection.createStatement();
           rs = st.executeQuery("select * from ThiSinh");       
        } catch (SQLException ex) {
           System.out.println("Error!");
        }
         return rs;
    }
    public void ThemTS(ThongTinSinhVien thisinh) {
            PreparedStatement pst;
            try {
             pst = Connect.connection.prepareStatement("insert into ThiSinh values(?,?,?,?,?)");
             pst.setString(1, thisinh.getMSSV());
             pst.setString(2, thisinh.getHoTen());
             pst.setDate(3, thisinh.getNgaySinh());
             pst.setString(4, GioiTinh);
             pst.setString(5, Lop);
             if(pst.executeUpdate()>0) JOptionPane.showMessageDialog(null, "Đã thêm thí sinh!");
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Lỗi thêm!");
        }           
    }
    public void SuaTS(String MSTS, String TenTS, String LopTS, java.sql.Date NgaySinh, String GioiTinh){
            PreparedStatement pst;
            try {
             pst = Connect.connection.prepareStatement("update ThiSinh set HoTen = ?, NgaySinh = ?, GioiTinh = ?, Lop = ? where MSSV = ?");
             pst.setString(5, MSTS);
             pst.setString(1, TenTS);
             pst.setDate(2, NgaySinh);
             pst.setString(3, GioiTinh);
             pst.setString(4, LopTS);
             if(pst.executeUpdate()>0) JOptionPane.showMessageDialog(null, "Đã lưu thông tin thí sinh!");
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Lỗi thêm!");
        }           
    }
     public void XoaTS(String Ma) {
            PreparedStatement pst;{
            try {
                pst = Connect.connection.prepareStatement("Delete ThiSinh where MSSV = ?");
                pst.setString(1, Ma);
                if (pst.executeUpdate() > 0) JOptionPane.showMessageDialog(null,"Đã xóa thí sinh");
            } catch (SQLException ex) {
               JOptionPane.showMessageDialog(null, "Error delete!");
            }               
        }    
    }
    public String getMSSV() {
        return MSSV;
    }

    public void setMSSV(String MSSV) {
        this.MSSV = MSSV;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public java.sql.Date getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(java.sql.Date NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getLop() {
        return Lop;
    }

    public void setLop(String Lop) {
        this.Lop = Lop;
    }
    
}
