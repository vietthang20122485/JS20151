/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package NghiepVu;

import DuLieu.TaiKhoan;

/**
 *
 * @author vietthang20122485
 */
public class DangNhap extends javax.swing.JFrame {

    /**
     * Creates new form DangNhap
     */
    public DangNhap() {
        initComponents();
    }
    public static String ten="";
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Title = new javax.swing.JLabel();
        Username = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        Password = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        Title2 = new javax.swing.JLabel();
        cbxTitle = new javax.swing.JComboBox();
        btnDangNhap = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        Title.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        Title.setForeground(new java.awt.Color(0, 0, 255));
        Title.setText("Đăng nhập hệ thống");

        Username.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        Username.setText("Username");

        txtUsername.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        Password.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        Password.setText("Password");

        txtPassword.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        Title2.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        Title2.setText("Role");

        cbxTitle.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cbxTitle.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Student", "Teacher", "Admin" }));

        btnDangNhap.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        btnDangNhap.setText("Đăng nhập");
        btnDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangNhapActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(79, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(Title)
                        .addGap(102, 102, 102))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDangNhap)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Username)
                                    .addComponent(Password)
                                    .addComponent(Title2))
                                .addGap(56, 56, 56)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cbxTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(86, 86, 86))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(Title)
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Username)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Password)
                    .addComponent(txtPassword))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Title2)
                    .addComponent(cbxTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(btnDangNhap)
                .addContainerGap(121, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangNhapActionPerformed
        // TODO add your handling code here:
        DangNhap login = new DangNhap();
        TaiKhoan tk = new TaiKhoan();
        if(cbxTitle.getSelectedItem().toString().equals("Teacher"))
        {
            if(tk.KiemTraGiangVien(txtUsername.getText().toString(), txtPassword.getText().toString())==1) {
                ten = txtUsername.getText();
                QuanLy quanly = new QuanLy();
                quanly.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                quanly.setVisible(true);
            }
        }
        if(cbxTitle.getSelectedItem().toString().equals("Student"))
        {
            if(tk.KiemTraSinhVien(txtUsername.getText().toString(), txtPassword.getText().toString())==1) {
                ten = txtUsername.getText();
                KiemTra test = new KiemTra();
                test.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                test.setVisible(true);
            }
        }
        if(cbxTitle.getSelectedItem().toString().equals("Admin"))
        {
            if(tk.KiemTraAdmin(txtUsername.getText().toString(), txtPassword.getText().toString())==1) {
                ten = txtUsername.getText();
                QuanLyTaiKhoan admin = new QuanLyTaiKhoan();
                admin.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                admin.setVisible(true);
            }
        }
    }//GEN-LAST:event_btnDangNhapActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Password;
    private javax.swing.JLabel Title;
    private javax.swing.JLabel Title2;
    private javax.swing.JLabel Username;
    private javax.swing.JButton btnDangNhap;
    private javax.swing.JComboBox cbxTitle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
