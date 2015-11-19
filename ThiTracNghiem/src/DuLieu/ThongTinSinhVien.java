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
    String mssv;
    String hoTen;
    java.sql.Date ngaySinh;
    String gioiTinh;
    String lop;

    public ThongTinSinhVien(String mssv, String hoTen, java.sql.Date ngaySinh, String gioiTinh, String lop) {
        this.mssv = mssv;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.lop = lop;
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
             pst.setString(1, thisinh.getMssv());
             pst.setString(2, thisinh.getHoTen());
             pst.setDate(3, thisinh.getNgaySinh());
             pst.setString(4, thisinh.getGioiTinh());
             pst.setString(5, thisinh.getLop());
             if(pst.executeUpdate()>0) JOptionPane.showMessageDialog(null, "Đã thêm thí sinh!");
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Lỗi thêm!");
        }           
    }
    public void SuaTS(String msTS, String tenTS, String lopTS, java.sql.Date ngaySinh, String gioiTinh){
            PreparedStatement pst;
            try {
             pst = Connect.connection.prepareStatement("update ThiSinh set HoTen = ?, NgaySinh = ?, GioiTinh = ?, Lop = ? where MSSV = ?");
             pst.setString(5, msTS);
             pst.setString(1, tenTS);
             pst.setDate(2, ngaySinh);
             pst.setString(3, gioiTinh);
             pst.setString(4, lopTS);
             if(pst.executeUpdate()>0) JOptionPane.showMessageDialog(null, "Đã lưu thông tin thí sinh!");
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Lỗi thêm!");
        }           
    }
     public void XoaTS(String ma) {
            PreparedStatement pst;{
            try {
                pst = Connect.connection.prepareStatement("Delete ThiSinh where MSSV = ?");
                pst.setString(1, ma);
                if (pst.executeUpdate() > 0) JOptionPane.showMessageDialog(null,"Đã xóa thí sinh");
            } catch (SQLException ex) {
               JOptionPane.showMessageDialog(null, "Error delete!");
            }               
        }    
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public java.sql.Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(java.sql.Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

}
