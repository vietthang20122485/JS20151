package DuLieu;

import NghiepVu.Connect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class ChiTietDe {
   String maDe;
    String ms;
    
    public ChiTietDe(String maDe, String ms) {
        this.maDe = maDe;
        this.ms = ms;
    }

    public ChiTietDe(String maDe) {
        this.maDe = maDe;
    }

    public ChiTietDe() {
    }
    
    public ResultSet LayNoiDungDe(String s){
          ResultSet rs = null;
         try {
             PreparedStatement pst = Connect.connection.prepareStatement("select MaDe, ChiTietDe.MS as MaCauHoi,Hard as DoKho  from ChiTietDe,Cauhoi where MaDe=? and ChiTietDe.MS=Cauhoi.MS");
             pst.setString(1, s);
             rs = pst.executeQuery();
         } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Lỗi load nội dung đề!");
         }
         return rs;
      }
    
    public ResultSet getdata(){
        ResultSet rs = null;
         try {  
           Statement st = Connect.connection.createStatement();
           rs = st.executeQuery("select distinct MaDe from ChiTietDe");       
       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null,"Lỗi mã đề!");
       }
         return rs;
    }
    
    public void XoaDeThi(String made){
         try {
             PreparedStatement pst = Connect.connection.prepareStatement("delete from ChiTietDe where MaDe=?");
             pst.setString(1, made);
             if(pst.executeUpdate()>0) JOptionPane.showMessageDialog(null, "Delete success đề thi "+made);
         } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Mã đề thi không tồn tại!");
         }
    }
    public String RandomDe(){
         String made="";
         try {
             ResultSet rs =null;
             Statement st = Connect.connection.createStatement();
             rs = st.executeQuery("SELECT TOP 1 MaDe from ChiTietDe ORDER BY NEWID()");
             while (rs.next()){
                 made = rs.getString(1);
                 return made;
             }
         } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Lỗi random đề thi!");
         }
         return made;
    }
    
    public void ThemDe(String made, String mach){
         try {
             int x;
             PreparedStatement pst = Connect.connection.prepareStatement("insert into ChiTietDe values(?,?)");
             pst.setString(1, made);
             pst.setString(2, mach);
             if(pst.executeUpdate()>0) x=0;
         } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null,"Câu hỏi đã được thêm rồi!");
         }
    }
    
    public String getMaDe() {
        return maDe;
    }

    public void setMaDe(String MaDe) {
        this.maDe = MaDe;
    }

    public String getMS() {
        return ms;
    }

    public void setMS(String MS) {
        this.ms = MS;
    } 
}
