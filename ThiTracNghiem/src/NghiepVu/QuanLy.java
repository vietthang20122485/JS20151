/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NghiepVu;

import java.awt.Dimension;
import java.awt.Toolkit;
import DuLieu.CauHoi;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import DuLieu.CauHoi;
import DuLieu.ChiTietDe;
import DuLieu.ThongTinSinhVien;
import DuLieu.KetQua;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author vietthang20122485
 */
public class QuanLy extends javax.swing.JFrame {

    /**
     * Creates new form QuanLy
     */
    private DefaultTableModel dmt = new DefaultTableModel();

    public QuanLy() {
        initComponents();
        loadData();
        loadMaDe();
        hienthi();
        loadDataTS();
    }

    public CauHoi getCauHoi() {
        String MS = txtMS.getText();
        String Noidung = txtNoidung.getText();
        String A = txtA.getText();
        String B = txtB.getText();
        String C = txtC.getText();
        String D = txtD.getText();
        String Dapan = txtDapan.getSelectedItem().toString();
        String Hard = txtHard.getSelectedItem().toString();
        return new CauHoi(MS, Noidung, A, B, C, D, Dapan, Hard);
    }

    public ChiTietDe getChiTietDe() {
        String MaDe = txtMaDe.getText();
        return new ChiTietDe(MaDe);
    }

    public ThongTinSinhVien getThiSinh() {
        String MSSV = txtMSTS.getText();
        String HoTen = txtTenTS.getText();
        java.sql.Date NgaySinh = new java.sql.Date(jcNgaySinh.getDate().getTime());
        String GioiTinh;
        if (cbGioiTinh.getSelectedIndex() == 0) {
            GioiTinh = "Nam";
        } else {
            GioiTinh = "Nu";
        }
        String Lop = txtLopTS.getText();
        return new ThongTinSinhVien(MSSV, HoTen, NgaySinh, GioiTinh, Lop);
    }

    public KetQua getKetQua() {
        SimpleDateFormat dinhDangThoiGian = new SimpleDateFormat("yyyy-MM-dd");
        String ngaythi = dinhDangThoiGian.format(jDateChooser1.getDate().getTime());
        return new KetQua(ngaythi);
    }

    public void loadDataTS() {
        ThongTinSinhVien thisinh = getThiSinh();
        DefaultTableModel dmt = new DefaultTableModel();
        try {
            ResultSet rs = thisinh.getdataTS();
            ResultSetMetaData rsmd = rs.getMetaData();
            int col = rsmd.getColumnCount();
            String[] arr = new String[col];
            for (int i = 0; i < col; i++) {
                arr[i] = rsmd.getColumnName(i + 1);
            }
            dmt.setColumnIdentifiers(arr);
            while (rs.next()) {
                for (int i = 0; i < col; i++) {
                    try {
                        arr[i] = rs.getString(i + 1);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error!");
                    }
                }
                dmt.addRow(arr);
            }
            tbThiSinh.setModel(dmt);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error!");
        }
    }

    public int KiemTraBanGhi() {
        int count = 0;
        try {
            ResultSet rs = null;
            PreparedStatement pst = Connect.connection.prepareStatement("select count(MS) from ChiTietDe where MaDe=? group by MaDe");
            pst.setString(1, txtMaDe.getText());
            rs = pst.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
                return count;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "$$$$$$$");
        }
        return count;
    }

    //kiem tra ma cau hoi sap them co bi trung khong
    public boolean KiemTraCauHoi() {
        CauHoi cauhoi = getCauHoi();
        if (cauhoi.check(cauhoi) == false) {
            return false;
        }
        ResultSet rs = cauhoi.getdata();
        int dem = 0;
        try {
            while (rs.next()) {
                dem++;
            }
            for (int i = 0; i < dem; i++) {
                if (txtMS.getText().compareTo((String) jTable1.getValueAt(i, 0)) == 0) {
                    return false;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error!");
        }
        return true;
    }

    public boolean KiemTraMaDe() {
        ChiTietDe dethi = new ChiTietDe();
        if (txtMaDe.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập mã đề!");
            return false;
        }
        ResultSet rs = dethi.getdata();
        int dem = 0;
        try {
            while (rs.next()) {
                if (txtMaDe.getText().compareTo(rs.getString(1)) == 0) {
                    JOptionPane.showMessageDialog(null, "Mã đề đã tồn tại!");
                    return false;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error!");
        }
        return true;
    }

    public void KiemTraCauHoi2() {
        CauHoi cauhoi = getCauHoi();
        if (cauhoi.check(cauhoi) == true) {
            cauhoi.update(cauhoi);
        }
        loadData();
    }

    public void hienthi() {
        int row = jTable1.getSelectedRow();
        if (row >= 0) {
            btnUpdate.setEnabled(true);
            txtMS.setText((String) jTable1.getValueAt(row, 0));
            txtNoidung.setText((String) jTable1.getValueAt(row, 1));
            txtA.setText((String) jTable1.getValueAt(row, 2));
            txtB.setText((String) jTable1.getValueAt(row, 3));
            txtC.setText((String) jTable1.getValueAt(row, 4));
            txtD.setText((String) jTable1.getValueAt(row, 5));
            txtDapan.setSelectedItem((String) jTable1.getValueAt(row, 6));
            txtHard.setSelectedItem((String) jTable1.getValueAt(row, 7));
        }
    }

    public void loadData() {
        CauHoi cauhoi = new CauHoi();
        DefaultTableModel dmt = new DefaultTableModel();
        try {
            ResultSet rs = cauhoi.getdata();
            ResultSetMetaData rsmd = rs.getMetaData();
            int col = rsmd.getColumnCount();
            String[] arr = new String[col];
            for (int i = 0; i < col; i++) {
                arr[i] = rsmd.getColumnName(i + 1);
            }
            dmt.setColumnIdentifiers(arr);
            while (rs.next()) {
                for (int i = 0; i < col; i++) {
                    try {
                        arr[i] = rs.getString(i + 1);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error!");
                    }
                }
                dmt.addRow(arr);
            }
            jTable1.setModel(dmt);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error!");
        }
    }

    public void loadDe() {
        ChiTietDe dethi = getChiTietDe();
        DefaultTableModel dmt = new DefaultTableModel();
        try {
            ResultSet rs = dethi.LayNoiDungDe(dethi.getMaDe());
            ResultSetMetaData rsmd = rs.getMetaData();
            int col = rsmd.getColumnCount();
            String[] arr = new String[col];
            for (int i = 0; i < col; i++) {
                arr[i] = rsmd.getColumnName(i + 1);
            }
            dmt.setColumnIdentifiers(arr);
            while (rs.next()) {
                for (int i = 0; i < col; i++) {
                    try {
                        arr[i] = rs.getString(i + 1);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error!");
                    }
                }
                dmt.addRow(arr);
            }
            jTable2.setModel(dmt);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error!");
        }
    }

    public void loadMaDe() {
        ChiTietDe dethi = new ChiTietDe();
        ResultSet rs = dethi.getdata();
        try {
            while (rs.next()) {
                cbxMaDe.addItem(rs.getString(1));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi load mã đề!");
        }
    }

    public boolean loadTS() {
        int row = tbThiSinh.getSelectedRow();
        if (row >= 0) {
            txtMSTS.setText((String) tbThiSinh.getValueAt(row, 0));
            txtMSTS.setEnabled(false);
            txtTenTS.setText((String) tbThiSinh.getValueAt(row, 1));
            txtLopTS.setText((String) tbThiSinh.getValueAt(row, 4));
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn thí sinh cần sửa thông tin");
            return false;
        }
    }

    public void loadSort_KQ(String sort_Type) {
        DefaultTableModel dmt = new DefaultTableModel();
        KetQua ketqua = getKetQua();
        try {
            SimpleDateFormat dinhDangThoiGian = new SimpleDateFormat("yyyy-MM-dd");
            String ngaythi = dinhDangThoiGian.format(jDateChooser1.getDate().getTime());
            ResultSet rs = ketqua.getdataKQ_Sort(sort_Type, ngaythi);
            ResultSetMetaData rsmd = rs.getMetaData();
            int col = rsmd.getColumnCount();
            String[] arr = new String[col];
            for (int i = 0; i < col; i++) {
                arr[i] = rsmd.getColumnName(i + 1);
            }
            dmt.setColumnIdentifiers(arr);
            while (rs.next()) {
                for (int i = 0; i < col; i++) {
                    try {
                        arr[i] = rs.getString(i + 1);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error!");
                    }
                }
                dmt.addRow(arr);
            }
            jTable4.setModel(dmt);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error!");
        }
    }

    public void loadMax_KQ() {
        DefaultTableModel dmt = new DefaultTableModel();
        KetQua ketqua = getKetQua();
        try {
            SimpleDateFormat dinhDangThoiGian = new SimpleDateFormat("yyyy-MM-dd");
            String ngaythi = dinhDangThoiGian.format(jDateChooser1.getDate().getTime());
            ResultSet rs = ketqua.getdataKQ_Max(ngaythi);
            ResultSetMetaData rsmd = rs.getMetaData();
            int col = rsmd.getColumnCount();
            String[] arr = new String[col];
            for (int i = 0; i < col; i++) {
                arr[i] = rsmd.getColumnName(i + 1);
            }
            dmt.setColumnIdentifiers(arr);
            while (rs.next()) {
                for (int i = 0; i < col; i++) {
                    try {
                        arr[i] = rs.getString(i + 1);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error!");
                    }
                }
                dmt.addRow(arr);
            }
            jTable4.setModel(dmt);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error!");
        }
    }

    public void loadMin_KQ() {
        DefaultTableModel dmt = new DefaultTableModel();
        KetQua ketqua = getKetQua();
        try {
            SimpleDateFormat dinhDangThoiGian = new SimpleDateFormat("yyyy-MM-dd");
            String ngaythi = dinhDangThoiGian.format(jDateChooser1.getDate().getTime());
            ResultSet rs = ketqua.getdataKQ_Min(ngaythi);
            ResultSetMetaData rsmd = rs.getMetaData();
            int col = rsmd.getColumnCount();
            String[] arr = new String[col];
            for (int i = 0; i < col; i++) {
                arr[i] = rsmd.getColumnName(i + 1);
            }
            dmt.setColumnIdentifiers(arr);
            while (rs.next()) {
                for (int i = 0; i < col; i++) {
                    try {
                        arr[i] = rs.getString(i + 1);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error!");
                    }
                }
                dmt.addRow(arr);
            }
            jTable4.setModel(dmt);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error!");
        }
    }

    public void loadTB_KQ() {
        DefaultTableModel dmt = new DefaultTableModel();
        KetQua ketqua = getKetQua();
        try {
            SimpleDateFormat dinhDangThoiGian = new SimpleDateFormat("yyyy-MM-dd");
            String ngaythi = dinhDangThoiGian.format(jDateChooser1.getDate().getTime());
            ResultSet rs = ketqua.getdataKQ_Tb(ngaythi);
            ResultSetMetaData rsmd = rs.getMetaData();
            int col = rsmd.getColumnCount();
            String[] arr = new String[col];
            for (int i = 0; i < col; i++) {
                arr[i] = rsmd.getColumnName(i + 1);
            }
            dmt.setColumnIdentifiers(arr);
            while (rs.next()) {
                for (int i = 0; i < col; i++) {
                    try {
                        arr[i] = rs.getString(i + 1);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error!");
                    }
                }
                dmt.addRow(arr);
            }
            jTable4.setModel(dmt);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error!");
        }
    }

    public void xoaText() {
        txtMSTS.setText("");
        txtTenTS.setText("");
        txtLopTS.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        txtMS = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtNoidung = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtA = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtB = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtC = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtD = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtHard = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        txtDapan = new javax.swing.JComboBox();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cbxMaDe = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        txtMaDe = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtMSTS = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtTenTS = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jcNgaySinh = new com.toedter.calendar.JDateChooser();
        jLabel22 = new javax.swing.JLabel();
        cbGioiTinh = new javax.swing.JComboBox();
        jLabel23 = new javax.swing.JLabel();
        txtLopTS = new javax.swing.JTextField();
        ThemTS = new javax.swing.JButton();
        SuaTS = new javax.swing.JButton();
        XoaTS = new javax.swing.JButton();
        LuuTS = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbThiSinh = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        TangDan = new javax.swing.JButton();
        GiamDan = new javax.swing.JButton();
        CaoNhat = new javax.swing.JButton();
        ThapNhat = new javax.swing.JButton();
        TrungBinh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane2.setBackground(new java.awt.Color(255, 255, 204));

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        jLabel25.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 204));
        jLabel25.setText("Ngân hàng câu hỏi");

        jTable1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable1);

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel12.setText("Mã câu hỏi");

        txtMS.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel11.setText("Nội dung");

        txtNoidung.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel10.setText("A");

        txtA.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel13.setText("B");

        txtB.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel14.setText("C");

        txtC.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel15.setText("D");

        txtD.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel16.setText("Độ khó");

        txtHard.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtHard.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "B", "C" }));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel8.setText("Đáp án đúng");

        txtDapan.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtDapan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "B", "C", "D" }));

        btnAdd.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnUpdate.setText("Sửa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnSave.setText("Lưu");
        btnSave.setEnabled(false);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addGap(309, 309, 309))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel11)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel12)
                            .addGap(35, 35, 35)
                            .addComponent(txtMS, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtNoidung, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addGap(104, 104, 104)
                            .addComponent(txtA))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel13)
                                .addComponent(jLabel15))
                            .addGap(103, 103, 103)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtD)
                                .addComponent(txtB)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel14)
                            .addGap(103, 103, 103)
                            .addComponent(txtC)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDapan, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(61, 61, 61)
                            .addComponent(txtHard, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(104, 104, 104)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(125, 125, 125)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(116, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtMS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(txtNoidung, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(txtC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtHard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtDapan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnUpdate)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(131, 131, 131))
        );

        jTabbedPane2.addTab("Ngân hàng câu hỏi", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 204));
        jLabel2.setText("Tạo đề thi");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel9.setText("Mã đề thi đã có");

        cbxMaDe.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel7.setText("Mã đề thi");

        txtMaDe.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jTable2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        btnThem.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXoa.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(374, 374, 374)
                        .addComponent(jLabel2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 672, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addGap(43, 43, 43)
                                    .addComponent(cbxMaDe, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addGap(87, 87, 87)
                                    .addComponent(txtMaDe, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(121, 121, 121)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(181, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbxMaDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtMaDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnXoa))
                .addContainerGap(230, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Tạo đề thi", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 204));

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 204));
        jLabel18.setText("Quản lý thí sinh");

        jLabel19.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel19.setText("Mã sinh viên");

        txtMSTS.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel20.setText("Họ tên");

        txtTenTS.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel21.setText("Ngày sinh");

        jLabel22.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel22.setText("Giới tính");

        cbGioiTinh.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cbGioiTinh.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nam", "Nữ" }));
        cbGioiTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGioiTinhActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel23.setText("Lớp");

        txtLopTS.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        ThemTS.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        ThemTS.setText("Thêm");
        ThemTS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThemTSActionPerformed(evt);
            }
        });

        SuaTS.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        SuaTS.setText("Sửa");
        SuaTS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuaTSActionPerformed(evt);
            }
        });

        XoaTS.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        XoaTS.setText("Xóa");
        XoaTS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XoaTSActionPerformed(evt);
            }
        });

        LuuTS.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        LuuTS.setText("Lưu");
        LuuTS.setEnabled(false);
        LuuTS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LuuTSActionPerformed(evt);
            }
        });

        tbThiSinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tbThiSinh);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(337, 337, 337)
                .addComponent(jLabel18)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 668, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel19)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(ThemTS, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(SuaTS, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
                                .addComponent(XoaTS, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)
                                .addComponent(LuuTS, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 450, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jcNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMSTS, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtLopTS, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(27, 27, 27)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTenTS, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(169, 169, 169))))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel18)
                .addGap(36, 36, 36)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMSTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(txtTenTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jcNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel22)
                        .addComponent(cbGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtLopTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ThemTS, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(XoaTS, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SuaTS, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LuuTS))
                .addGap(32, 32, 32)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(321, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Quản lý thí sinh", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 204));

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 204));
        jLabel17.setText("Kết quả thi");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel3.setText("Ngày thi");

        jDateChooser1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jTable4);

        TangDan.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        TangDan.setText("Tăng dần");
        TangDan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TangDanActionPerformed(evt);
            }
        });

        GiamDan.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        GiamDan.setText("Giảm dần");
        GiamDan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GiamDanActionPerformed(evt);
            }
        });

        CaoNhat.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        CaoNhat.setText("Cao nhất");
        CaoNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CaoNhatActionPerformed(evt);
            }
        });

        ThapNhat.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        ThapNhat.setText("Thấp nhất");
        ThapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThapNhatActionPerformed(evt);
            }
        });

        TrungBinh.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        TrungBinh.setText("Trung bình");
        TrungBinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TrungBinhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(363, 363, 363)
                        .addComponent(jLabel17))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(TangDan)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(GiamDan)
                                    .addGap(67, 67, 67)
                                    .addComponent(CaoNhat)
                                    .addGap(88, 88, 88)
                                    .addComponent(ThapNhat)
                                    .addGap(71, 71, 71)
                                    .addComponent(TrungBinh))
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 699, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(144, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel17)
                .addGap(38, 38, 38)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GiamDan)
                    .addComponent(CaoNhat)
                    .addComponent(TangDan)
                    .addComponent(TrungBinh)
                    .addComponent(ThapNhat))
                .addContainerGap(399, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Kết quả", jPanel4);

        jScrollPane1.setViewportView(jTabbedPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 923, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {                                       
        // TODO add your handling code here:
        CauHoi cauhoi = getCauHoi();
        if (KiemTraCauHoi() == true) {
            cauhoi.insert(cauhoi);
        } else {
            JOptionPane.showMessageDialog(null, "Mã câu hỏi đã tồn tại!");
        }
        loadData();
    }                                      

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        hienthi();
        btnSave.setEnabled(true);
    }                                         

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
        KiemTraCauHoi2();
        btnSave.setEnabled(false);
    }                                       

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
        CauHoi cauhoi = new CauHoi();
        if (KiemTraBanGhi() >= 30) {
            JOptionPane.showMessageDialog(rootPane, "Mã đề đã đủ câu hỏi!");
            return;
        }
        if (KiemTraMaDe() == true) {
            ResultSet rs;
            int i = 0;
            String[] MaCH = new String[30];
            rs = cauhoi.Lay15CauHoiDe();
            try {
                while (rs.next()) {
                    MaCH[i] = rs.getString(1);
                    i++;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Loi1!");
            }
            rs = cauhoi.Lay10CauHoiTrungBinh();
            try {
                while (rs.next()) {
                    MaCH[i] = rs.getString(1);
                    i++;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Loi2!");
            }
            rs = cauhoi.Lay5CauHoiKho();
            try {
                while (rs.next()) {
                    MaCH[i] = rs.getString(1);
                    i++;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Loi3!");
            }
            ChiTietDe dethi = getChiTietDe();
            for (i = 0; i < 30; i++) {
                dethi.ThemDe(dethi.getMaDe(), MaCH[i]);
                cbxMaDe.removeAllItems();
                loadMaDe();
            }
            loadDe();
        }
    }                                       

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {                                       
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn xóa đề thi " + cbxMaDe.getSelectedItem().toString().substring(0, 5) + "?", "Warning", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            ChiTietDe dethi = getChiTietDe();
            dethi.XoaDeThi(cbxMaDe.getSelectedItem().toString());
            cbxMaDe.removeAllItems();
            loadMaDe();
        }
    }                                      

    private void cbGioiTinhActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          

    private void ThemTSActionPerformed(java.awt.event.ActionEvent evt) {                                       
        ThongTinSinhVien thisinh = getThiSinh();
        thisinh.ThemTS(thisinh);
        loadDataTS();
    }                                      

    private void SuaTSActionPerformed(java.awt.event.ActionEvent evt) {                                      
        if (loadTS()) {
            LuuTS.setEnabled(true);
        }
    }                                     

    private void XoaTSActionPerformed(java.awt.event.ActionEvent evt) {                                      
        int row = tbThiSinh.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(null, "Bạn phải chọn 1 dòng để xóa", "Lỗi xóa!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn xóa thí sinh này ?", "Warning", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            ThongTinSinhVien thisinh = getThiSinh();
            thisinh.XoaTS(tbThiSinh.getValueAt(row, 0).toString());
            loadDataTS();
            txtMSTS.setEnabled(true);
            xoaText();
        }
    }                                     

    private void LuuTSActionPerformed(java.awt.event.ActionEvent evt) {                                      
        String MSTS = txtMSTS.getText();
        String TenTS = txtTenTS.getText();
        String LopTS = txtLopTS.getText();
        java.sql.Date NgaySinh = new java.sql.Date(jcNgaySinh.getDate().getTime());
        String GioiTinh;
        if (cbGioiTinh.getSelectedIndex() == 0) {
            GioiTinh = "Nam";
        } else {
            GioiTinh = "Nu";
        }
        ThongTinSinhVien thisinh = getThiSinh();
        thisinh.SuaTS(MSTS, TenTS, LopTS, NgaySinh, GioiTinh);
        LuuTS.setEnabled(false);
        txtMSTS.setEnabled(true);
        loadDataTS();
        xoaText();
    }                                     

    private void TangDanActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
        loadSort_KQ("ASC");
    }                                       

    private void GiamDanActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
        loadSort_KQ("DESC");
    }                                       

    private void CaoNhatActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
        loadMax_KQ();
    }                                       

    private void ThapNhatActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        loadMin_KQ();
    }                                        

    private void TrungBinhActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        loadTB_KQ();
    }                                         

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify                     
    private javax.swing.JButton CaoNhat;
    private javax.swing.JButton GiamDan;
    private javax.swing.JButton LuuTS;
    private javax.swing.JButton SuaTS;
    private javax.swing.JButton TangDan;
    private javax.swing.JButton ThapNhat;
    private javax.swing.JButton ThemTS;
    private javax.swing.JButton TrungBinh;
    private javax.swing.JButton XoaTS;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox cbGioiTinh;
    private javax.swing.JComboBox cbxMaDe;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable4;
    private com.toedter.calendar.JDateChooser jcNgaySinh;
    private javax.swing.JTable tbThiSinh;
    private javax.swing.JTextField txtA;
    private javax.swing.JTextField txtB;
    private javax.swing.JTextField txtC;
    private javax.swing.JTextField txtD;
    private javax.swing.JComboBox txtDapan;
    private javax.swing.JComboBox txtHard;
    private javax.swing.JTextField txtLopTS;
    private javax.swing.JTextField txtMS;
    private javax.swing.JTextField txtMSTS;
    private javax.swing.JTextField txtMaDe;
    private javax.swing.JTextField txtNoidung;
    private javax.swing.JTextField txtTenTS;
    // End of variables declaration                   
}
