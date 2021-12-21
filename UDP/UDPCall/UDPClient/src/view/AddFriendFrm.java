package view;

import control.ClientCtr;
import dto.FriendDTO;
import dto.RequestDTO;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ObjectWrapper;
import model.User;

public class AddFriendFrm extends javax.swing.JDialog {

    private Long id;
    private ClientCtr mySocket;
    private ArrayList<FriendDTO> users;
    private int selectedRow = -1;

    public AddFriendFrm(java.awt.Frame parent, boolean modal, Long id, ClientCtr mySocket) {
        super(parent, modal);
        initComponents();
        this.id = id;
        this.mySocket = mySocket;

        addBtn.setEnabled(false);
        cancelBtn.setEnabled(false);
        acceptBtn.setEnabled(false);

    }

    private void getUsers() {
        String username = usernameTxt.getText();
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.GET_LIST_USER, new User(username, this.id)));
        receiveDataFromServer();
    }

    private void receiveDataFromServer() {
        ObjectWrapper data = mySocket.receiveData();
        if (data != null) {
            if (data.getPerformative() == ObjectWrapper.REPLY_GET_LIST_USER) {
                ArrayList<FriendDTO> dataUsers = (ArrayList<FriendDTO>) data.getData();
                if (dataUsers.size() >= 0) {
                    this.users = dataUsers;
                    mapToTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Error in fetching!");
                }
            }
            if (data.getPerformative() == ObjectWrapper.REPLY_ADD_FRIEND) {
                Long id = (Long) data.getData();
                if (id != null) {
                    getUsers();
                    mapToTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Error in sending request");
                }
            }
            if(data.getPerformative() == ObjectWrapper.REPLY_CANCEL_ADD_FRIEND) {
                String mess = (String) data.getData();
                if(mess.equals("ok")) {
                    getUsers();
                    mapToTable();
                }
                else {
                    JOptionPane.showMessageDialog(rootPane, "Error in canceling the request");
                }
            }
            if(data.getPerformative() == ObjectWrapper.REPLY_CONFIRM_FRIEND) {
                RequestDTO requestDTO = (RequestDTO) data.getData();
                if(requestDTO != null) {
                   getUsers();
                    mapToTable();
                }
                else {
                    JOptionPane.showMessageDialog(rootPane, "Error in confirm the request");
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        usernameTxt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        userTbl = new javax.swing.JTable();
        addBtn = new javax.swing.JButton();
        usernameLabel = new javax.swing.JLabel();
        cancelBtn = new javax.swing.JButton();
        acceptBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Add a new friend");

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        userTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Users"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        userTbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userTblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(userTbl);

        addBtn.setText("Add");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        acceptBtn.setText("Accept");
        acceptBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(usernameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(108, 108, 108)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(usernameLabel)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(93, 93, 93)
                                .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(acceptBtn)))))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(usernameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(usernameLabel)
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addBtn)
                            .addComponent(cancelBtn)
                            .addComponent(acceptBtn))))
                .addContainerGap(300, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        getUsers();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void userTblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userTblMouseClicked
        int index = userTbl.getSelectedRow();
        this.selectedRow = index;
        usernameLabel.setText(this.users.get(index).getUsername());
        if (users.get(index).getFriendId() != null) {
            if (users.get(index).getConfirmed() == 0) {
                if (Objects.equals(users.get(index).getFriendIdUser1(), this.id)) {
                    cancelBtn.setEnabled(true);
                    acceptBtn.setEnabled(false);
                } else {
                    cancelBtn.setEnabled(false);
                    acceptBtn.setEnabled(true);
                }
            } else {
                addBtn.setEnabled(false);
                cancelBtn.setEnabled(false);
            }
            addBtn.setEnabled(false);
        } else {
            addBtn.setEnabled(true);
            cancelBtn.setEnabled(false);
            acceptBtn.setEnabled(false);
        }
    }//GEN-LAST:event_userTblMouseClicked

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        if (selectedRow != -1) {
            ArrayList<Long> ids = new ArrayList<>();
            ids.add(id);
            ids.add(users.get(selectedRow).getUserId());
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.ADD_FRIEND, ids));
            receiveDataFromServer();
        }
    }//GEN-LAST:event_addBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        if (selectedRow != -1) {
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.CANCEL_ADD_FRIEND, users.get(selectedRow).getFriendId()));
            receiveDataFromServer();
        }
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void acceptBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptBtnActionPerformed
        if (selectedRow != -1) {
            RequestDTO requestDTO = new RequestDTO();
            requestDTO.setFriendId(users.get(selectedRow).getFriendId());
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.CONFIRM_FRIEND, requestDTO));
            receiveDataFromServer();
        }
    }//GEN-LAST:event_acceptBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddFriendFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddFriendFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddFriendFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddFriendFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AddFriendFrm dialog = new AddFriendFrm(new javax.swing.JFrame(), true, null, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    private void mapToTable() {
        this.selectedRow = -1;
        usernameLabel.setText("");
        addBtn.setEnabled(false);
        cancelBtn.setEnabled(false);
        acceptBtn.setEnabled(false);

        if (users.size() >= 0) {
            String columns[] = {"No.", "Username"};
            String[][] values = new String[users.size()][columns.length];
            for (int i = 0; i < users.size(); i++) {
                values[i][0] = i + 1 + "";
                values[i][1] = users.get(i).getUsername() + "";
            }
            DefaultTableModel table = new DefaultTableModel(values, columns) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            userTbl.setModel(table);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acceptBtn;
    private javax.swing.JButton addBtn;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable userTbl;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTxt;
    // End of variables declaration//GEN-END:variables
}