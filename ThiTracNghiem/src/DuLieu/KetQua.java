/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DuLieu;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;
import NghiepVu.Connect;
/**
 *
 * @author vietthang
 */

public class KetQua {
    String mssv;
    float diem;
    String ngayThi;
    
    public KetQua() {
    }

    public KetQua(String ngayThi) {
        this.ngayThi = ngayThi;
    }
    

    public KetQua(String mssv, float diem, String ngayThi) {
        this.mssv = mssv;
        this.diem = diem;
        this.ngayThi = ngayThi;
    }


    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public float getDiem() {
        return diem;
    }

    public void setDiem(float diem) {
        this.diem = diem;
    }

    public String getNgayThi() {
        return ngayThi;
    }

    public void setNgayThi(String ngayThi) {
        this.ngayThi = ngayThi;
    }
    
    
    
    public ResultSet XemDiem(String mssv){
           ResultSet rs =null;
         try {
             PreparedStatement pst = Connect.connection.prepareStatement("Select * from KetQua where MSSV=?");
             pst.setString(1, mssv);
             rs = pst.executeQuery();
         } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Không tìm thấy sinh viên trên!");
         }
         return rs;
    }
    
    public void GhiKetQua(String ms,float diem,String ngaythi){
         try {
             PreparedStatement pst = Connect.connection.prepareStatement("insert into KetQua values(?, ?, ?)");
             pst.setString(1, ms);
             pst.setFloat(2, diem);
             pst.setString(3, ngaythi);
             if(pst.executeUpdate()>0) System.out.println("Ghi thành công!");;
         } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Lỗi ghi kết quả!");
         }
    }
    
    public ResultSet getdataKQ_Tb(String ngayThi){
        ResultSet rs = null;
         try {  
           Statement st = Connect.connection.createStatement();
           rs = st.executeQuery("select AVG(Diem) as DiemTrungBinh from KetQua where Ngaythi = '"+ngayThi+"'");
        } catch (SQLException ex) {
           System.out.println("Error!");
        }
         return rs;
    }
    
    public ResultSet getdataKQ_Max(String Ngaythi){
        ResultSet rs = null;
         try {  
           Statement st = Connect.connection.createStatement();
           rs = st.executeQuery("select * from KetQua where Ngaythi = '"+Ngaythi+"' and Diem = (select MAX(Diem) from KetQua where Ngaythi = '" +Ngaythi+"')");
        } catch (SQLException ex) {
           System.out.println("Error!");
        }
         return rs;
    }
    
    public ResultSet getdataKQ_Min(String Ngaythi){
        ResultSet rs = null;
         try {  
           Statement st = Connect.connection.createStatement();
           rs = st.executeQuery("select * from KetQua where Ngaythi = '"+Ngaythi+"' and Diem = (select MIN(Diem) from KetQua where Ngaythi = '" +Ngaythi+"')");
        } catch (SQLException ex) {
           System.out.println("Error!");
        }
         return rs;
    }
    
    public ResultSet getdataKQ_Sort(String sort_Type, String Ngaythi){
        ResultSet rs = null;
         try {  
           Statement st = Connect.connection.createStatement();
           rs = st.executeQuery("select * from KetQua where Ngaythi = '"+Ngaythi+"' order by Diem "+sort_Type);
        } catch (SQLException ex) {
           System.out.println("Error!");
        }
         return rs;
    }
    
    public float LamTronDiem(int n){
           float diem;
           diem = (float)n/3;
           int nguyen = (int) diem;
           float du = diem - (float) nguyen;
           if (du<0.25f) diem = (float) nguyen;
           if(0.25f<=du&&du<0.5f) diem = (float)nguyen + 0.5f;
           if(0.5f<=du&&du<0.75f) diem = (float) nguyen+0.5f;
           if(du>=0.75f) diem = (float) nguyen +1;
           return diem;
    }
    
    public ResultSet check(String ms, String ngayThi) {
        ResultSet rs = null;
         try {  
           Statement st = Connect.connection.createStatement();
           rs = st.executeQuery("select * from KetQua where MSSV = '" + ms + "'" + " and Ngaythi = '" + ngayThi +"'");
        } catch (SQLException ex) {
           System.out.println("Error!");
        }
         return rs;
    } 
}
