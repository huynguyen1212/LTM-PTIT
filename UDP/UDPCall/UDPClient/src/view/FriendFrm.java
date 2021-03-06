/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package view;

import control.ClientCtr;
import dto.RequestDTO;
import dto.StatusDTO;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Friend;
import model.ObjectWrapper;
import model.User;

public class FriendFrm extends javax.swing.JDialog {

    /**
     * Creates new form FriendFrm
     */
    private ClientCtr mySocket;
    private Long id;
    private ArrayList<User> friends = new ArrayList<>();

    public FriendFrm(java.awt.Frame parent, boolean modal, ClientCtr mySocket, Long id) {
        super(parent, modal);
        initComponents();
        this.mySocket = mySocket;
        this.id = id;
        getFriends();
    }

    private void mapToTable() {
        if (friends.size() >= 0) {
            String columns[] = {"STT", "Username", "Status"};
            String[][] values = new String[friends.size()][columns.length];
            for (int i = 0; i < friends.size(); i++) {
                values[i][0] = i + 1 + "";
                values[i][1] = friends.get(i).getUsername() + "";
                values[i][2] = friends.get(i).getOnline() == 0 ? "OFF" : "ON";
            }
            DefaultTableModel table = new DefaultTableModel(values, columns) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            friendTbl.setModel(table);
        }
    }

    void getFriends() {
        if (this.mySocket.sendData(new ObjectWrapper(ObjectWrapper.GET_LIST_FRIEND, this.id))) {
            receiveDataFromServer();
        }
    }

    private void receiveDataFromServer() {
        ObjectWrapper data = (ObjectWrapper) mySocket.receiveData();
        if (data != null) {
            if (data.getPerformative() == ObjectWrapper.REPLY_GET_LIST_FRIEND) {
                this.friends = (ArrayList<User>) data.getData();
                mapToTable();
            }
            if (data.getPerformative() == ObjectWrapper.REPLY_CONFIRM_FRIEND) {
                RequestDTO requestDTO = (RequestDTO) data.getData();
                if (requestDTO.getToID().equals(this.id)) {
                    getFriends();
                    mapToTable();
                }
            }
            if (data.getPerformative() == ObjectWrapper.REPLY_TRIGGER_STATUS) {
                if (this.friends.size() > 0) {
                    StatusDTO statusDTO = (StatusDTO) data.getData();
                    Long friendId = statusDTO.getUserId();
                    for (int i = 0; i < this.friends.size(); i++) {
                        User currentUser = this.friends.get(i);
                        if (currentUser.getId().equals(friendId)) {
                            currentUser.setOnline(statusDTO.getStatus());
                            this.friends.set(i, currentUser);
                            break;
                        }
                    }
                    mapToTable();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        friendTbl = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Friends");

        friendTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Friends"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(friendTbl);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(FriendFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FriendFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FriendFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FriendFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FriendFrm dialog = new FriendFrm(new javax.swing.JFrame(), true, null, null);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable friendTbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
