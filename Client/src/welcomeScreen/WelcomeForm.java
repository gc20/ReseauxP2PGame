package welcomeScreen;

import javax.swing.*;

public class WelcomeForm extends javax.swing.JFrame {

    String[] WelcomeInputs;
    
    public WelcomeForm() {
        initComponents();
        setDefaultCloseOperation(WelcomeForm.EXIT_ON_CLOSE);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPasswordField1 = new javax.swing.JPasswordField();
        jFrame1 = new javax.swing.JFrame();
        OptionPane = new javax.swing.JOptionPane();
        NewUserPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        NewUserName = new javax.swing.JTextField();
        NewPassword = new javax.swing.JPasswordField();
        NewConfirmPassword = new javax.swing.JPasswordField();
        NewUserCreateOK = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        NewSecretQuestion = new javax.swing.JTextField();
        NewSecretAnswer = new javax.swing.JTextField();
        LogInPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        UserNameField = new javax.swing.JTextField();
        LogInPassword = new javax.swing.JPasswordField();
        LogInOK = new javax.swing.JButton();
        ForgotPasswordPanel = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        ForgotUserName = new javax.swing.JTextField();
        ForgotAnswer = new javax.swing.JTextField();
        RetrievePassword = new javax.swing.JButton();
        GetQuestion = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        ForgotPassword = new javax.swing.JPasswordField();
        ForgotPasswordConfirm = new javax.swing.JPasswordField();

        jPasswordField1.setText("jPasswordField1");

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setTitle("Alphabet Wars");

        NewUserPanel.setBackground(new java.awt.Color(153, 255, 153));

        jLabel6.setText("New User");

        jLabel7.setText("Create Account");

        jLabel8.setText("Select a User Name");

        jLabel9.setText("Select Password");

        jLabel10.setText("Confirm Password");

        
        NewUserCreateOK.setText("OK");
        NewUserCreateOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewUserCreateOKActionPerformed(evt);
            }
        });

        jLabel13.setText("Secret Question");

        jLabel14.setText("Secret Answer");

        javax.swing.GroupLayout NewUserPanelLayout = new javax.swing.GroupLayout(NewUserPanel);
        NewUserPanel.setLayout(NewUserPanelLayout);
        NewUserPanelLayout.setHorizontalGroup(
            NewUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NewUserPanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(NewUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addGroup(NewUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, NewUserPanelLayout.createSequentialGroup()
                            .addComponent(jLabel14)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                            .addComponent(NewSecretAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(NewUserPanelLayout.createSequentialGroup()
                            .addComponent(jLabel13)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                            .addComponent(NewSecretQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(NewUserPanelLayout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                            .addComponent(NewConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(NewUserPanelLayout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                            .addComponent(NewPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, NewUserPanelLayout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addGap(43, 43, 43)
                            .addComponent(NewUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(NewUserCreateOK, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        NewUserPanelLayout.setVerticalGroup(
            NewUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NewUserPanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(NewUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(NewUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(NewUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(NewPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(NewUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(NewConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(NewUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(NewSecretQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(NewUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(NewSecretAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(NewUserCreateOK)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        LogInPanel.setBackground(new java.awt.Color(153, 153, 255));

        jLabel1.setText("Registered Users");

        jLabel3.setText("Log In");

        jLabel4.setText("User Name");

        jLabel5.setText("Password");

     
        LogInOK.setText("Log In");
        LogInOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogInOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout LogInPanelLayout = new javax.swing.GroupLayout(LogInPanel);
        LogInPanel.setLayout(LogInPanelLayout);
        LogInPanelLayout.setHorizontalGroup(
            LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LogInPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addGroup(LogInPanelLayout.createSequentialGroup()
                        .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(38, 38, 38)
                        .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(LogInOK, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(UserNameField, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                            .addComponent(LogInPassword))))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        LogInPanelLayout.setVerticalGroup(
            LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LogInPanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(UserNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(LogInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(LogInPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(LogInOK)
                .addGap(29, 29, 29))
        );

        ForgotPasswordPanel.setBackground(new java.awt.Color(0, 102, 153));

        jLabel11.setText("Forgot Password?");

        jLabel12.setText("User Name");

        jLabel16.setText("Your answer");

        RetrievePassword.setText("Change password");
        RetrievePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RetrievePasswordActionPerformed(evt);
            }
        });

        GetQuestion.setText("Get Question");
        GetQuestion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GetQuestionActionPerformed(evt);
            }
        });

        jLabel15.setText("New Password");

        jLabel17.setText("Confirm Password");

        ForgotPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ForgotPasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ForgotPasswordPanelLayout = new javax.swing.GroupLayout(ForgotPasswordPanel);
        ForgotPasswordPanel.setLayout(ForgotPasswordPanelLayout);
        ForgotPasswordPanelLayout.setHorizontalGroup(
            ForgotPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ForgotPasswordPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ForgotPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ForgotPasswordPanelLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 288, Short.MAX_VALUE))
                    .addComponent(GetQuestion)
                    .addGroup(ForgotPasswordPanelLayout.createSequentialGroup()
                        .addGroup(ForgotPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ForgotPasswordPanelLayout.createSequentialGroup()
                                .addGroup(ForgotPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel16))
                                .addGap(49, 49, 49))
                            .addComponent(jLabel15)
                            .addComponent(jLabel17))
                        .addGap(41, 41, 41)
                        .addGroup(ForgotPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ForgotAnswer, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                            .addComponent(ForgotUserName, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                            .addComponent(ForgotPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                            .addComponent(RetrievePassword, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ForgotPasswordConfirm, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE))))
                .addGap(27, 27, 27))
        );
        ForgotPasswordPanelLayout.setVerticalGroup(
            ForgotPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ForgotPasswordPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(ForgotPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(ForgotUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(ForgotPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ForgotPasswordPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(GetQuestion))
                    .addGroup(ForgotPasswordPanelLayout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(ForgotPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ForgotAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(ForgotPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ForgotPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(ForgotPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ForgotPasswordConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(RetrievePassword)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ForgotPasswordPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LogInPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32)
                .addComponent(NewUserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NewUserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(LogInPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(ForgotPasswordPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(219, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LogInOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogInOKActionPerformed
		String UserName = UserNameField.getText();
		String Password = LogInPassword.getText();
		
	    WelcomeInputs[1] = UserName;
	    WelcomeInputs[2] = Password;
	    WelcomeInputs[0] = "Login";
	    //this.setVisible(false); // Make the screen disappear when user is done entering inputs
		UserNameField.setText("");
	    LogInPassword.setText("");
        jLabel1.setText("Registered Users\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tLOADING ...");
	   
    }//GEN-LAST:event_LogInOKActionPerformed

   
    private void NewUserCreateOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewUserCreateOKActionPerformed

		String UserName = NewUserName.getText();
        String Password = NewPassword.getText();
        String ConfirmPassword = NewConfirmPassword.getText();
        String SecretQuestion = NewSecretQuestion.getText();
        String SecretAnswer = NewSecretAnswer.getText();
        
        if (Password.equals(ConfirmPassword))
        {
			//change flag
            WelcomeInputs[1]=UserName;
            WelcomeInputs[2]=Password;
            WelcomeInputs[3]=SecretQuestion;
            WelcomeInputs[4]=SecretAnswer;
            WelcomeInputs[0]="Register";
            //this.setVisible(false); // Make the screen disappear when user is done entering inputs
            NewUserName.setText("");
            NewPassword.setText("");
            NewConfirmPassword.setText("");
            NewSecretQuestion.setText("");
            NewSecretAnswer.setText("");
        } 
        else
		{
        	NewPassword.setText ("");
			NewConfirmPassword.setText ("");
        	DisplayWarning("Password mismatch. Please re-enter.");
		}

    }//GEN-LAST:event_NewUserCreateOKActionPerformed

    private void RetrievePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RetrievePasswordActionPerformed

		String UserName = ForgotUserName.getText();
        String SecretAnswer = ForgotAnswer.getText();
        String Password = ForgotPassword.getText();
        String ConfirmPassword = ForgotPasswordConfirm.getText();
        
		if (Password.equals(ConfirmPassword))
        {
			WelcomeInputs[1] = UserName;
			WelcomeInputs[2] = SecretAnswer;
			WelcomeInputs[3] = Password;
			WelcomeInputs[0] = "FPass";
			//this.setVisible(false);
			NewUserName.setText("");
			ForgotAnswer.setText("");
			ForgotPassword.setText("");
			ForgotPasswordConfirm.setText("");
        }
		else
		{
			ForgotPassword.setText("");
			ForgotPasswordConfirm.setText("");
        	DisplayWarning("Password mismatch. Please re-enter.");
		}

    }//GEN-LAST:event_RetrievePasswordActionPerformed

    private void GetQuestionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GetQuestionActionPerformed

        String UserName = ForgotUserName.getText();
        WelcomeInputs[1]=UserName;
        WelcomeInputs[0]="SQn";
        //this.setVisible(false);
        
        }//GEN-LAST:event_GetQuestionActionPerformed

    private void ForgotPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ForgotPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ForgotPasswordActionPerformed

    public void StartWelcome (String WelcomeInputs[]) 
	{
        this.WelcomeInputs = WelcomeInputs;
        java.awt.EventQueue.invokeLater (new Runnable() {public void run() {}});
    }

    public void DisplayWarning (String warningMessages)
    {
        jLabel1.setText("Registered Users");
        JOptionPane.showMessageDialog(OptionPane, warningMessages);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ForgotAnswer;
    private javax.swing.JPasswordField ForgotPassword;
    private javax.swing.JPasswordField ForgotPasswordConfirm;
    private javax.swing.JPanel ForgotPasswordPanel;
    private javax.swing.JTextField ForgotUserName;
    private javax.swing.JButton GetQuestion;
    private javax.swing.JButton LogInOK;
    private javax.swing.JPanel LogInPanel;
    private javax.swing.JPasswordField LogInPassword;
    private javax.swing.JPasswordField NewConfirmPassword;
    private javax.swing.JPasswordField NewPassword;
    private javax.swing.JTextField NewSecretAnswer;
    private javax.swing.JTextField NewSecretQuestion;
    private javax.swing.JButton NewUserCreateOK;
    private javax.swing.JTextField NewUserName;
    private javax.swing.JPanel NewUserPanel;
    private javax.swing.JOptionPane OptionPane;
    private javax.swing.JButton RetrievePassword;
    private javax.swing.JTextField UserNameField;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPasswordField jPasswordField1;
    // End of variables declaration//GEN-END:variables

}
