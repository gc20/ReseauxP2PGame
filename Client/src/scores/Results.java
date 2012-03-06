package scores;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import data.ClientPrimaryData;


@SuppressWarnings("serial")
public class Results extends javax.swing.JFrame {

	
	ScoreMain s;
	ClientPrimaryData myClientPrimaryData;
	String serverIP;
	int serverConnectPort;
    
	
    /** Creates new form Results */
    public Results(ScoreMain s, ClientPrimaryData myClientPrimaryData, String serverIP, int serverConnectPort) {
        
		this.s = s;
        this.myClientPrimaryData = myClientPrimaryData;
        this.serverIP = serverIP;
        this.serverConnectPort = serverConnectPort;
		
		initComponents();
        setLayout(null);
        setSize(482,550);
        TeamA.setBounds(58,100, 70, 20);
        A1.setBounds(58, 130, 70, 20);
        A2.setBounds(58, 160, 70, 20);
        A3.setBounds(58, 190, 70, 20);
        A4.setBounds(58, 210, 70, 20);
        A5.setBounds(58, 240, 70, 20);
        A6.setBounds(138, 130, 70, 20);
        A7.setBounds(138, 160, 70, 20);
        A8.setBounds(138, 190, 70, 20);
        A9.setBounds(138, 210, 70, 20);
        A10.setBounds(138, 240, 70, 20);

        AScore.setBounds(138, 280, 70, 20);

        TeamB.setBounds(244,100,70,20);
        B1.setBounds(244, 130, 70, 20);
        B2.setBounds(244, 160, 70, 20);
        B3.setBounds(244, 190, 70, 20);
        B4.setBounds(244, 210, 70, 20);
        B5.setBounds(244, 240, 70, 20);
        B6.setBounds(324, 130, 70, 20);
        B7.setBounds(324, 160, 70, 20);
        B8.setBounds(324, 190, 70, 20);
        B9.setBounds(324, 210, 70, 20);
        B10.setBounds(324, 240, 70, 20);

        BScore.setBounds(324, 280, 70, 20);

        jLabel1.setBounds(58, 30, 100, 20);
        winner.setBounds(188, 30, 220, 20);
        showButton.setBounds(138, 350, 206,25);
        chatButton.setBounds(138, 400, 206, 25);
        logoutButton.setBounds(138, 450, 206, 25);
    }

    
     //   DisplayScores("Alpha_3_Roger_12_Tom_15_Bill_3_2435_Gamma_3_Veronica_10_Celia_12_Bob_100_2435");
    
    public void DisplayScores(String scores)
    {

        // Split string and store in array
        String[] array = scores.split("_");
        // Extract team 1
        String team1name = array[0];
        //System.out.println(team1name);
        int team1No = Integer.parseInt(array[1]);
        //System.out.println(team1No);
        String [] team1Members = new String[team1No];
        String [] team1Scores = new String[team1No];
        int x=0;
        //Store team 1 names and scores in arrays
        for (int i=2; i<= 2*(team1No); i+=2)
        {
            team1Members[x] = array[i];
            team1Scores[x++] = array[i+1];
        }
        //Get position of Team score and store
        int currentposition = (2*team1No)+2;
        //System.out.println(currentposition);
        int team1Score = Integer.parseInt(array[currentposition]);
        //System.out.println(team1Score);

        
        // Get team 2 details
        String team2name = array[++currentposition];
        //System.out.println(team2name);
        int team2No = Integer.parseInt(array[++currentposition]);
        //System.out.println(team2No);
        currentposition++;
        //System.out.println(currentposition);

        String [] team2Members = new String[team2No];
        String [] team2Scores = new String[team2No];

        //Store team 2 names and scores in arrays
        int y = 0;
        for (int j= currentposition; j< currentposition+2*team2No; j+=2)
        {
            team2Members[y] = array [j];
            team2Scores[y++] = array [j+1];
        }
        currentposition += 2*team2No;
        //System.out.println(currentposition);
        int team2Score = Integer.parseInt(array[currentposition]);
        //System.out.println(team2Score);

        //Display name of winning team
        if(team1Score>team2Score)
        {
            winner.setText(team1name);
        } else if (team1Score<team2Score)
        {
            winner.setText(team2name);
        } else
        {
            winner.setText("Tie");
        }

        TeamA.setText(team1name);
        //System.out.println(team1Members.length);
        switch(team1Members.length)
        {
            case 5: A5.setText(team1Members[4]); A10.setText(team1Scores[4]);
            case 4: A4.setText(team1Members[3]); A9.setText(team1Scores[3]);
            case 3: A3.setText(team1Members[2]); A8.setText(team1Scores[2]);
            case 2: A2.setText(team1Members[1]); A7.setText(team1Scores[1]);
            case 1: A1.setText(team1Members[0]); A6.setText(team1Scores[0]);
            break;
        }
        AScore.setText(String.valueOf(team1Score));

        TeamB.setText(team2name);
        //System.out.println(team2Members.length);
        switch(team2Members.length)
        {
            case 5: B5.setText(team2Members[4]); B10.setText(team2Scores[4]);
            case 4: B4.setText(team2Members[3]); B9.setText(team2Scores[3]);
            case 3: B3.setText(team2Members[2]); B8.setText(team2Scores[2]);
            case 2: B2.setText(team2Members[1]); B7.setText(team2Scores[1]);
            case 1: B1.setText(team2Members[0]); B6.setText(team2Scores[0]);
            break;
        }
        BScore.setText(String.valueOf(team2Score));

        //System.out.println(team1name+" "+ team1No+" "+ team1Score);
        //System.out.println(team2name+" "+ team2No+" "+ team2Score);
        //System.out.println(team1Members[0]+team2Members[0]);


    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {

        new javax.swing.JColorChooser();
        showButton = new javax.swing.JButton();
        winner = new javax.swing.JLabel();
        AName = new javax.swing.JLabel();
        A1 = new javax.swing.JLabel();
        A2 = new javax.swing.JLabel();
        A3 = new javax.swing.JLabel();
        A4 = new javax.swing.JLabel();
        A5 = new javax.swing.JLabel();
        AScore = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        B5 = new javax.swing.JLabel();
        BScore = new javax.swing.JLabel();
        B3 = new javax.swing.JLabel();
        B4 = new javax.swing.JLabel();
        B1 = new javax.swing.JLabel();
        B2 = new javax.swing.JLabel();
        AName1 = new javax.swing.JLabel();
        TeamB = new javax.swing.JLabel();
        TeamA = new javax.swing.JLabel();
        chatButton = new javax.swing.JButton();
        logoutButton = new javax.swing.JButton();
        A6 = new javax.swing.JLabel();
        A7 = new javax.swing.JLabel();
        A8 = new javax.swing.JLabel();
        A9 = new javax.swing.JLabel();
        A10 = new javax.swing.JLabel();
        B6 = new javax.swing.JLabel();
        B7 = new javax.swing.JLabel();
        B8 = new javax.swing.JLabel();
        B9 = new javax.swing.JLabel();
        B10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setForeground(new java.awt.Color(51, 0, 255));

        showButton.setText("See how you did!");
        showButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showButtonActionPerformed(evt);
            }
        });

        winner.setBackground(new java.awt.Color(204, 204, 255));
        winner.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        winner.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        A1.setBackground(new java.awt.Color(255, 255, 255));
        A1.setFont(new java.awt.Font("Times New Roman", 0, 14));
        A1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        A2.setBackground(new java.awt.Color(255, 255, 255));
        A2.setFont(new java.awt.Font("Times New Roman", 0, 14));
        A2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        A3.setBackground(new java.awt.Color(255, 255, 255));
        A3.setFont(new java.awt.Font("Times New Roman", 0, 14));
        A3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        A4.setBackground(new java.awt.Color(255, 255, 255));
        A4.setFont(new java.awt.Font("Times New Roman", 0, 14));
        A4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        A5.setBackground(new java.awt.Color(255, 255, 255));
        A5.setFont(new java.awt.Font("Times New Roman", 0, 14));
        A5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        AScore.setBackground(new java.awt.Color(255, 255, 255));
        AScore.setFont(new java.awt.Font("Times New Roman", 2, 18));
        AScore.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("WINNER:");

        B5.setBackground(new java.awt.Color(255, 255, 255));
        B5.setFont(new java.awt.Font("Times New Roman", 0, 14));
        B5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        BScore.setBackground(new java.awt.Color(255, 255, 255));
        BScore.setFont(new java.awt.Font("Times New Roman", 2, 18));
        BScore.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        B3.setBackground(new java.awt.Color(255, 255, 255));
        B3.setFont(new java.awt.Font("Times New Roman", 0, 14));
        B3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        B4.setBackground(new java.awt.Color(255, 255, 255));
        B4.setFont(new java.awt.Font("Times New Roman", 0, 14));
        B4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        B1.setBackground(new java.awt.Color(255, 255, 255));
        B1.setFont(new java.awt.Font("Times New Roman", 0, 14));
        B1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        B2.setBackground(new java.awt.Color(255, 255, 255));
        B2.setFont(new java.awt.Font("Times New Roman", 0, 14));
        B2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        TeamB.setBackground(new java.awt.Color(255, 255, 255));
        TeamB.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        TeamB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        TeamA.setBackground(new java.awt.Color(255, 255, 255));
        TeamA.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        TeamA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TeamA.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        TeamA.setPreferredSize(new java.awt.Dimension(5, 2));
        TeamA.setRequestFocusEnabled(false);

        chatButton.setText("Continue to Chat");
        chatButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chatButtonActionPerformed(evt);
            }
        });

        logoutButton.setText("Logout");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        A6.setBackground(new java.awt.Color(255, 255, 255));
        A6.setFont(new java.awt.Font("Times New Roman", 0, 14));
        A6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        A7.setBackground(new java.awt.Color(255, 255, 255));
        A7.setFont(new java.awt.Font("Times New Roman", 0, 14));
        A7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        A8.setBackground(new java.awt.Color(255, 255, 255));
        A8.setFont(new java.awt.Font("Times New Roman", 0, 14));
        A8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        A9.setBackground(new java.awt.Color(255, 255, 255));
        A9.setFont(new java.awt.Font("Times New Roman", 0, 14));
        A9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        A10.setBackground(new java.awt.Color(255, 255, 255));
        A10.setFont(new java.awt.Font("Times New Roman", 0, 14));
        A10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        B6.setBackground(new java.awt.Color(255, 255, 255));
        B6.setFont(new java.awt.Font("Times New Roman", 0, 14));
        B6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        B7.setBackground(new java.awt.Color(255, 255, 255));
        B7.setFont(new java.awt.Font("Times New Roman", 0, 14));
        B7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        B8.setBackground(new java.awt.Color(255, 255, 255));
        B8.setFont(new java.awt.Font("Times New Roman", 0, 14));
        B8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        B9.setBackground(new java.awt.Color(255, 255, 255));
        B9.setFont(new java.awt.Font("Times New Roman", 0, 14));
        B9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        B10.setBackground(new java.awt.Color(255, 255, 255));
        B10.setFont(new java.awt.Font("Times New Roman", 0, 14));
        B10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(AName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TeamA, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                            .addComponent(A1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(A2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(A3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(A4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(A5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AScore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(AName1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TeamB, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(A8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(A7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(A6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(A10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(A9, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(B1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(B2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(B3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(B4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(B5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(BScore, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(51, 51, 51))
                            .addComponent(winner, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(B8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B9, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(showButton, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                .addGap(200, 200, 200))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(183, 183, 183)
                .addComponent(chatButton, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                .addGap(126, 126, 126))
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(logoutButton)
                .addContainerGap(308, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {A1, A2, A3, A4, A5, AScore, B1, B2, B3, B4, B5, BScore, TeamA, TeamB});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(winner, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AName, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AName1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(A8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(A7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(A6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(A10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(A9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(TeamA, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(A1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(A2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(A3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(A4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(A5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(AScore, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(B8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(B6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(B9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(B7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(B10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(TeamB, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(B1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(B2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(B3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(B4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(B5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(30, 30, 30)
                                .addComponent(BScore, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(26, 26, 26)
                .addComponent(showButton)
                .addGap(18, 18, 18)
                .addComponent(chatButton)
                .addGap(34, 34, 34)
                .addComponent(logoutButton)
                .addGap(191, 191, 191))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {A1, A2, A3, A4, A5, AScore, B1, B2, B3, B4, B5, BScore, TeamA, TeamB});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void showButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showButtonActionPerformed

		// Display scores
		DisplayScores(s.scoreString);
        
    	// Inform server of score change
    	try
    	{
	        Socket activeClientSocket = new Socket(serverIP, serverConnectPort);
			DataOutputStream outToServer = new DataOutputStream(activeClientSocket.getOutputStream());
			outToServer.writeBytes ("ScoreUpdate_" + myClientPrimaryData.UserName + "_" + myClientPrimaryData.myScore + "_" + '\n');
			BufferedReader in = new BufferedReader(new InputStreamReader(activeClientSocket.getInputStream()));
			String acceptLevel = in.readLine();
			System.out.println ("Level is " + acceptLevel);
			String splitArr [] = acceptLevel.split("_");
			if (splitArr[0].equals("Level") == true)
			{
				System.out.println ("Reached here");
				myClientPrimaryData.level = Integer.parseInt (splitArr[1]);
				System.out.println ("You new level as per server is " + Integer.parseInt (splitArr[1]));
			}
			in.close();
			activeClientSocket.close();
		}
    	catch (Exception e)
    	{System.out.println ("Could not register score updates");}
        
    }//GEN-LAST:event_showButtonActionPerformed

	
    private void chatButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chatButtonActionPerformed

		// Open chat window
		myClientPrimaryData.scoreEnd[0] = true;
        this.setVisible(false);

    }//GEN-LAST:event_chatButtonActionPerformed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed

        // Log Out
        this.dispose();
		
    }//GEN-LAST:event_logoutButtonActionPerformed

	 /**
    * @param args the command line arguments
    */
    /*public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Results().setVisible(true);
            }

        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel A1;
    private javax.swing.JLabel A10;
    private javax.swing.JLabel A2;
    private javax.swing.JLabel A3;
    private javax.swing.JLabel A4;
    private javax.swing.JLabel A5;
    private javax.swing.JLabel A6;
    private javax.swing.JLabel A7;
    private javax.swing.JLabel A8;
    private javax.swing.JLabel A9;
    private javax.swing.JLabel AName;
    private javax.swing.JLabel AName1;
    private javax.swing.JLabel AScore;
    private javax.swing.JLabel B1;
    private javax.swing.JLabel B10;
    private javax.swing.JLabel B2;
    private javax.swing.JLabel B3;
    private javax.swing.JLabel B4;
    private javax.swing.JLabel B5;
    private javax.swing.JLabel B6;
    private javax.swing.JLabel B7;
    private javax.swing.JLabel B8;
    private javax.swing.JLabel B9;
    private javax.swing.JLabel BScore;
    private javax.swing.JLabel TeamA;
    private javax.swing.JLabel TeamB;
    private javax.swing.JButton chatButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton logoutButton;
    private javax.swing.JButton showButton;
    private javax.swing.JLabel winner;
    // End of variables declaration//GEN-END:variables

}
