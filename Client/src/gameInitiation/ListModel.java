package gameInitiation;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;

import data.ClientPrimaryData;

@SuppressWarnings("serial")
public class ListModel extends JFrame implements ActionListener {

    /**
     * @param args
     */
    //private String[] friend = {"Player1","Player2", "Player3", "Player4", "Player5"};
    //private String[] all_players = {"Player1","Player2", "Player3", "Player4", "Player5","Player6","Player7", "Player8", "Player9", "Player10"} ;
    // private String[] ip_list;
    // private Boolean[] ol_status;
    //private int no_of_ol;
    //private int myid;
    private UserData[] FData;
    private UserData[] RData;
    public String UserName;
    public ConnectionManager connMgr;
    private ClientPrimaryData parent;
    public boolean diag = false;
    public String result;
    private JList friendList, list, playerList1, playerList2;
    private DefaultListModel model, model2, playerModel1, playerModel2;
    private JPanel pane, chatPanel, chatPanel2, listPanel, panel3, panel4, panel, teamPanel1, teamPanel2, labelPane, playerPanel, startPanel, textPanel;
    private JLabel label1, label2, label3;
    private JTextField textArea,textArea2;
    private JTextArea chatArea,chatArea2;
    private JButton addFriend, removeFriend, startGameButton, gameOptions, addPlayer1, addPlayer2, logout, enterBtn, enterBtn2, removePlayer1, removePlayer2;
    private JScrollPane scrollPane, scrollPane2, chatScrollPane, chatScrollPane2, teamScrollPane1, teamScrollPane2;
    //private JMenuBar menuBar;
    //private JMenu menu;
    //private JRadioButtonMenuItem winningCriteria,selectContinent;
    
    // For communicating with server
    String serverIP;
    int serverConnectPort;
    
    public ListModel(String s, UserData[] FD, UserData[] RD, String opts, ClientPrimaryData c, String serverIP, int serverConnectPort) {
        //print_Diagnostic();
        // Uncomment if required to get some diagnostic meassage for file input
        //	try{
        //	create_list(fname);
        //} catch(Exception e) { if(diag) e.printStackTrace();
        //		else  System.err.println ("Please check the input file "+ fname);}
        FData = FD;
        RData = RD;
        UserName = s;
        parent = c;
        
        connMgr = new ConnectionManager(this, c);
        this.serverIP = serverIP;
        this.serverConnectPort = serverConnectPort;
        setLayout(new BorderLayout());
        setTitle("Chat Window");
        setSize(1200, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Panels

        pane = new JPanel();
        chatPanel = new JPanel();
        chatPanel2 = new JPanel();
        listPanel = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        panel = new JPanel();
        textPanel = new JPanel();
        teamPanel1 = new JPanel();
        teamPanel2 = new JPanel();
        labelPane = new JPanel();
        playerPanel = new JPanel();
        startPanel = new JPanel();


        pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));


        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel2.setLayout(new BoxLayout(chatPanel2, BoxLayout.Y_AXIS));
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        //Team lists
        playerModel1 = new DefaultListModel();
        playerModel2 = new DefaultListModel();
        
        //Populating the Friends List
        model = new DefaultListModel();
        for (int k = 0; k < FData.length; k++) {
            if (FData[k].LoginStatus == 1 && FData[k].Name.equals("server") == false) {
                model.addElement(FData[k].Name);
            }
        }
        friendList = new JList(model);

        //Populating the AllPlayers List

        model2 = new DefaultListModel();
        for (int k = 0; k < FData.length; k++) {
            if (FData[k].LoginStatus == 1 && FData[k].Name.equals("server") == false) {
                model2.addElement(FData[k].Name);
            }
        }
        for (int k = 0; k < RData.length; k++) {
            if (RData[k].LoginStatus == 1 && RData[k].Name.equals("server") == false) {
                model2.addElement(RData[k].Name);
            }
        }
        list = new JList(model2);

        friendList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        friendList.setSelectedIndex(0);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);

        //ScrollPanes for both lists

        scrollPane = new JScrollPane(friendList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane2 = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(200, 150));
        scrollPane2.setPreferredSize(new Dimension(200, 150));


        //All buttons

        addFriend = new JButton("Add to Friends");
        addFriend.setPreferredSize(new Dimension(200, 35));
        removeFriend = new JButton("Remove from Friends");
        removeFriend.setPreferredSize(new Dimension(200, 35));
        startGameButton = new JButton("Start Game");
        startGameButton.setPreferredSize(new Dimension(375, 30));
        gameOptions = new JButton("Game Options");
        gameOptions.setPreferredSize(new Dimension(200, 30));
        addPlayer1 = new JButton("My Team");
        addPlayer1.setPreferredSize(new Dimension(145, 30));
        addPlayer2 = new JButton("Opposing Team");
        addPlayer2.setPreferredSize(new Dimension(145, 30));
        removePlayer1 = new JButton("X");
        removePlayer1.setPreferredSize(new Dimension(50, 28));
        removePlayer2 = new JButton("X");
        removePlayer2.setPreferredSize(new Dimension(50, 28));
        logout = new JButton("Logout");
        logout.setPreferredSize(new Dimension(200, 30));
        enterBtn = new JButton("Enter");
        enterBtn.setPreferredSize(new Dimension(80, 30));
        enterBtn2 = new JButton("Enter");
        enterBtn2.setPreferredSize(new Dimension(80, 30));
        //enterFriend = new JTextField();

        label1 = new JLabel("Friends List");
        label1.setPreferredSize(new Dimension(200, 30));
        label2 = new JLabel("All Players");
        label3 = new JLabel("Team Chat");

        //Chat Area

        chatArea = new JTextArea("");
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setEditable(false);

        chatArea2 = new JTextArea("");
        chatArea2.setLineWrap(true);
        chatArea2.setWrapStyleWord(true);
        chatArea2.setEditable(false);

        playerList1 = new JList(playerModel1);
        playerList2 = new JList(playerModel2);

        textArea = new JTextField();
        textArea.setPreferredSize(new Dimension(290, 30));
        
        textArea2 = new JTextField();
        textArea2.setPreferredSize(new Dimension(290, 30));

        //chatScrollPane

        chatScrollPane = new JScrollPane(chatArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatScrollPane.setAutoscrolls(true);
        chatScrollPane.setPreferredSize(new Dimension(300, 300));
        
        // team chatScrollPane
        chatScrollPane2 = new JScrollPane(chatArea2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatScrollPane2.setAutoscrolls(true);
        chatScrollPane2.setPreferredSize(new Dimension(300, 350));

        //players scrollPane

        teamScrollPane1 = new JScrollPane(playerList1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        teamScrollPane1.setPreferredSize(new Dimension(200, 150));

        teamScrollPane2 = new JScrollPane(playerList2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        teamScrollPane2.setPreferredSize(new Dimension(200, 150));


        startPanel.add(startGameButton);


         
        //teamPanel 1 & 2

        teamPanel1.add(scrollPane);
        teamPanel1.add(scrollPane2);
        //teamPanel1.setBorder(BorderFactory.createLineBorder (Color.black, 1));
        teamPanel2.add(teamScrollPane1);
        teamPanel2.add(teamScrollPane2);
        //teamPanel2.setBorder(BorderFactory.createLineBorder (Color.black, 1));


        //panel4.add(Box.createRigidArea(new Dimension(190,0)));
        labelPane.add(label1);
        labelPane.add(Box.createRigidArea(new Dimension(20, 0)));
        labelPane.add(label2);

        //playerPanel.setLayout(new BorderLayout());
        playerPanel.add(addPlayer1);
        playerPanel.add(removePlayer1);
        playerPanel.add(addPlayer2);
        playerPanel.add(removePlayer2);



        panel3.add(gameOptions);
        panel3.add(logout);

        panel.add(addFriend);
        panel.add(removeFriend);
        //panel.add(Box.createHorizontalStrut(5));
        //panel.add(new JSeparator(SwingConstants.VERTICAL));
        //panel.add(Box.createHorizontalStrut(5));
        //panel.add(enterFriend);
        //panel.add(Box.createRigidArea(new Dimension(50,0)));
        //panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        //Panel 4 -> Logout & startGameButton


        panel4.add(textArea);
        panel4.add(enterBtn);
        
        textPanel.add(textArea2);
        textPanel.add(enterBtn2);
        //panel4.add(Box.createRigidArea(new Dimension(190,0)));


        // Main pane ->Panel 1 & Panel2
        pane.add(chatPanel);
        pane.add(listPanel);
        pane.add(chatPanel2);
        //end Main Pane

        //chatPanel.add(menuBar);
        chatPanel.add(chatScrollPane);
        chatPanel.add(panel4);
        chatPanel.add(startPanel);
        chatPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        
        chatPanel2.add(label3);
        chatPanel2.add(chatScrollPane2);
        chatPanel2.add(textPanel);
        chatPanel2.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        //Panel 2 start

        listPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        listPanel.add(labelPane);
        listPanel.add(teamPanel1);
        listPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        listPanel.add(playerPanel);
        listPanel.add(teamPanel2);
        listPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        listPanel.add(panel);
        listPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        listPanel.add(panel3);



        //Panel 2 end
        add(pane);


        addFriend.addActionListener(this);
        gameOptions.addActionListener(this);
        logout.addActionListener(this);
        removeFriend.addActionListener(this);
        enterBtn.addActionListener(this);
        enterBtn2.addActionListener(this);
        addPlayer1.addActionListener(this);
        addPlayer2.addActionListener(this);
        removePlayer1.addActionListener(this);
        removePlayer2.addActionListener(this);
        startGameButton.addActionListener(this);
        
        textArea.addKeyListener( 
        		//Action listener to listen to enter key stroke to input the chat string to group chat
	    		new KeyAdapter()
	    		{
	    		public void keyReleased( KeyEvent e )
	    		{
	    		if( e.getKeyCode() == KeyEvent.VK_ENTER )
	    		{
	    			if (textArea.getText().isEmpty()) {
	                    //JOptionPane.showMessageDialog(this, "Bad Word", "Error", JOptionPane.ERROR_MESSAGE);
	                } else {
	                    chatArea.append(UserName + ": " + textArea.getText() + "\n");
	                    connMgr.SendDataToAll("cg_" + UserName + ": " + textArea.getText() + "\n");
	                    textArea.setText("");
	                }
	    		}
	    		}
	    		}
	    		);
        textArea2.addKeyListener(
        		//Action listener to listen to enter key stroke to input the chat string to intra team chat
	    		new KeyAdapter()
	    		{
	    		public void keyReleased( KeyEvent e )
	    		{
	    		if( e.getKeyCode() == KeyEvent.VK_ENTER )
	    		{
	    			if (textArea2.getText().isEmpty()) {
	                    //JOptionPane.showMessageDialog(this, "Bad Word", "Error", JOptionPane.ERROR_MESSAGE);
	                } else {
	                    chatArea2.append(UserName + ": " + textArea2.getText() + "\n");
	                    if(parent.teamno==1){
	                    	SendDatatoTeam1("ct_" + UserName + ": " + textArea2.getText() + "\n");
	                    }
	                    else{
	                    	SendDatatoTeam2("ct_" + UserName + ": " + textArea2.getText() + "\n");
	                    }
	                    textArea2.setText("");
	                }
	    		}
	    		}
	    		}
	    		);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
            	//Function to kill all threads if the exit button (X) is clicked
                System.err.println("Closing");
                // Disconnect myself
                connMgr.SendDataToAll("r_" + UserName + "\n");
                System.err.println("Sent disconnect request to all");
                // end all server and client threads
                connMgr.endThread();
                System.err.println("Ended all threads");
                System.exit(0);
            }
        });

        // connMgr.ConnectTo(UserName);
        
        int delay = 5000; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
        	/*Interrupt Timer function which refreshes the friends list and all players list
        	 * when the variable update is set to 1 by the server
        	 * The timer value is set to 5000ms after which it overflows and triggers an 
        	 * interrupt*/
               @Override
            public void actionPerformed(ActionEvent e) {
                   if(parent.update==1){
                   refreshList();
                   //chatArea.append("Userdata refreshed \n"); // just to debug
                   parent.update = 0;
                   }
            }
        };
        new Timer(delay, taskPerformer).start();
    }
    
   
    
	public void SendDatatoTeam1(String Data) {
		//function used to send a string to all players in team1, useful for intra team chat
		for (int k = 0; k < playerModel1.size(); k++) {
			String player = (String) playerModel1.get(k);
			connMgr.SendData(player, Data);
		}

	}

	public void SendDatatoTeam2(String Data) {
		//function used to send a string to all players in team2, useful for intra team chat
		for (int k = 0; k < playerModel2.size(); k++) {
			String player = (String) playerModel2.get(k);
			connMgr.SendData(player, Data);

		}
	}

    public void ShowError(String errStr) {
        JOptionPane.showMessageDialog(this, errStr, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void addPlayerToTeam1(String player) {
    	/*FUNCTION USED TO ADD A PLAYER TO TEAM1. Does the following tasks
    	 * 1. Checks whether the player is already in any of the teams or is the person itself
    	 * 2. If not, opens a connection to the player passing the type (team number) indicating
    	 * the position where the person should add the sender
    	 * 3. Then shares the complete info about the existing players in the teams (two for iterators)*/
    	System.err.println("Tag Team 1 " + getPlayerIP(player));
        // add the new player only if it is not in the list already
        if (!(playerModel1.contains(player) || playerModel2.contains(player) || player.equals(UserName))) {

        	System.out.println ("Passed 1st if");
        	if (connMgr.ConnectTo(player, 1)) {
            	System.out.println ("Passed 2nd if");
                playerModel1.addElement(player);
                System.out.println ("Added to player model 1");
                for (int k = 0; k < playerModel1.size(); k++) {
                	connMgr.SendData(player, "a_" + playerModel1.get(k) + "_1" + "\n");
                	//connMgr.SendData(player, "a_" + parent.UserName + "_1" + "\n");
                	if (connMgr == null)
                		System.out.println ("hate this null shit");
                }
                for (int k = 0; k < playerModel2.size(); k++) {
                	//System.out.println (player + "a_" + playerModel1.get(k) + "_1" + "\n");
                	connMgr.SendData(player, "a_" + playerModel2.get(k) + "_2" + "\n");
                }
            }
        }
    }

    public void addPlayerToTeam2(String player) {
    	/*FUNCTION USED TO ADD A PLAYER TO TEAM2. Does the following tasks
    	 * 1. Checks whether the player is already in any of the teams or is the person itself
    	 * 2. If not, opens a connection to the player passing the type (team number) indicating
    	 * the position where the person should add the sender
    	 * 3. Then shares the complete info about the existing players in the teams (two for iterators)*/
    	System.err.println("Tag Team 2");
        // add the new player only if it is not in the list already
        if (!(playerModel1.contains(player) || playerModel2.contains(player) || player.equals(UserName))) {
            if (connMgr.ConnectTo(player, 2)) {
                playerModel2.addElement(player);
                for (int k = 0; k < playerModel1.size(); k++) {
                    connMgr.SendData(player, "a_" + playerModel1.get(k) + "_2" + "\n");
                }
                for (int k = 0; k < playerModel2.size(); k++) {
                    connMgr.SendData(player, "a_" + playerModel2.get(k) + "_1" + "\n");
                }
            }
        }
    }

    
    public void removePlayerFromTeam1(String player) {
    	/*FUNCTION USED TO ADD A PLAYER TO TEAM1. Does the following tasks
    	 * 1. Checks whether the player is already in any of the teams or is the person itself
    	 * 2. If not, opens a connection to the player passing the type (team number) indicating
    	 * the position where the person should add the sender
    	 * 3. Then shares the complete info about the existing players in the teams (two for iterators)*/
    	System.err.println("Tag Team 1 " + getPlayerIP(player));
        // add the new player only if it is not in the list already
        if (playerModel1.contains(player) || playerModel2.contains(player)) {

        	System.out.println ("Reached remove player from team1");
        	playerModel1.removeElement(player);
        	System.out.println ("X111");
            connMgr.DisconnectFrom(player);
            System.out.println ("Y111");
                    
        
        }
    }

    public void removePlayerFromTeam2(String player) {
    	/*FUNCTION USED TO ADD A PLAYER TO TEAM2. Does the following tasks
    	 * 1. Checks whether the player is already in any of the teams or is the person itself
    	 * 2. If not, opens a connection to the player passing the type (team number) indicating
    	 * the position where the person should add the sender
    	 * 3. Then shares the complete info about the existing players in the teams (two for iterators)*/
    	System.err.println("Tag Team 2");
        // add the new player only if it is not in the list already
    	if (playerModel1.contains(player) || playerModel2.contains(player))  {
        	System.out.println ("Reached remove player from team2");
        	playerModel2.removeElement(player);
            connMgr.DisconnectFrom(player);


        }
    }

    
    
  /*  public void removePlayer(String player) {
    	Function to remove a player from a team
    	 * 1. Removes from the team list
    	 * 2. Closes the sockets to the player
        if (playerModel1.contains(player)) {
            playerModel1.removeElement(player);
        } else if (playerModel2.contains(player)) {
            playerModel2.removeElement(player);
        }
        connMgr.DisconnectFrom(player);
        //connMgr.SendDataToAll("r_" + player + "\n");
    }*/

    public String getPlayerIP(String player) {
    	//Function to get the IP of a specific player
        if (player.equals(UserName)) {
            return connMgr.getMyIP();
        }
        
        for (int k = 0; k < RData.length; k++) {
            if (RData[k].Name.equals(player)) {
                return RData[k].IP;
            }
        }
        for (int k = 0; k < FData.length; k++) {
            if (FData[k].Name.equals(player)) {
                return FData[k].IP;
            }
        }
        return "";
    }

    public int getPlayerscore(String player) {
    	//Function to fetch the score of a player
        for (int k = 0; k < RData.length; k++) {
            if (RData[k].Name.equals(player)) {
                return RData[k].Level;
            }
        }
        for (int k = 0; k < FData.length; k++) {
            if (FData[k].Name.equals(player)) {
                return FData[k].Level;
            }
        }
        return 0;
    }

    public void showInChatWindow(String s) {
    	//Append a received message to group chat window
        chatArea.append(s + "\n");
    }

    public void showInChatWindow2(String s) {
    	//Append a received message to intra team chat window
        chatArea2.append(s + "\n");
      }
    
    public void refreshList() {
    	/*Function called if variable update is set to 1 by the server 
    	 * Incase there are any updates, the server changes the data in myclientprimarydata
    	 * and sets update =1. The timer interrupts calls this function in that case.
    	 * 1. It reloads the new data to UserData()
    	 * 2. Clears the old populated lists
    	 * 3. Re-populates the new data in the respective fields with changes
    	 * 4. Sets update=0*/
        UserData FData[] = new UserData[parent.FNumber];
        UserData RData[] = new UserData[parent.RNumber];
        for (int i = 0; i < parent.FNumber; i++) {
            FData[i] = new UserData(parent.FUsernamesArr[i], parent.FIPsArr[i], parent.FLoginStatusArr[i], parent.FLevelArr[i]);
        }
        for (int i = 0; i < parent.RNumber; i++) {
            RData[i] = new UserData(parent.RUsernamesArr[i], parent.RIPsArr[i], parent.RLoginStatusArr[i], parent.RLevelArr[i]);
        }

        model.clear();
        model2.clear();

        //model = new DefaultListModel();
        for (int k = 0; k < FData.length; k++) {
            if (FData[k].LoginStatus == 1) {
                model.addElement(FData[k].Name);
            }
        }
      //  friendList = new JList(model);

        //AllPlayers List

     //   model2 = new DefaultListModel();
        for (int k = 0; k < FData.length; k++) {
            if (FData[k].LoginStatus == 1) {
                model2.addElement(FData[k].Name);
            }
        }
        for (int k = 0; k < RData.length; k++) {
            if (RData[k].LoginStatus == 1) {
                model2.addElement(RData[k].Name);
            }
        }
       // list = new JList(model2);
        parent.update = 0;
    }
    
    //Following lines of code describe the various actions taken on detecting option selects
    public void actionPerformed(ActionEvent evt) {
    	// TO ADD A USER TO FRIENDS LIST
        if (evt.getSource() == addFriend) {

            removeFriend.setEnabled(true); // So that the user can remove if required (enables remove button)
        	try {
            int index = list.getSelectedIndex(); // index corresponding to model2
            if (model.contains(model2.getElementAt(index))) { //To check if already present as friend
                JOptionPane.showMessageDialog(this, "Player Already In List", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                model.addElement(model2.getElementAt(index)); //Add to the list
                System.out.println ("Model2: " + model2.getElementAt(index));
               
                // Inform peer that he has been added as a friend
                connMgr.SendData(model2.elementAt(index).toString(), "AddedYou_" + parent.UserName + '\n');
               
                // Inform server of add friend alteration
                Socket activeClientSocket = new Socket(serverIP, serverConnectPort);
    			DataOutputStream outToServer = new DataOutputStream(activeClientSocket.getOutputStream());
    			outToServer.writeBytes("AddFriend_" + parent.UserName + "_" +  model2.elementAt(index).toString() + '\n');

                // Add friend to your own data
                addFriendToArray(model2.elementAt(index).toString());
            }
        	}catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error", "Error", JOptionPane.ERROR_MESSAGE);
            }




        }
        if (evt.getSource() == removeFriend) {
            try {
                int index = friendList.getSelectedIndex();
                model.remove(index);
                
                // Inform peer that he has been removed as a friend
                connMgr.SendData(model2.elementAt(index).toString(), "RemovedYou_" + parent.UserName  + '\n');
               
                // Inform server of remove friend alteration
                Socket activeClientSocket = new Socket(serverIP, serverConnectPort);
    			DataOutputStream outToServer = new DataOutputStream(activeClientSocket.getOutputStream());
    			outToServer.writeBytes("RemoveFriend_" + parent.UserName + "_" +  model2.elementAt(index).toString() + '\n');

                // Remove friend from your own data
                removeFriendFromArray(model2.elementAt(index).toString());

                if (model.getSize() == 0) {
                    removeFriend.setEnabled(false);
                }
                else
                	removeFriend.setEnabled (true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error", "Error", JOptionPane.ERROR_MESSAGE);
            }


        }

        if (evt.getSource() == enterBtn) {

            if (textArea.getText().isEmpty()) {
                //JOptionPane.showMessageDialog(this, "Bad Word", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                chatArea.append(UserName + ": " + textArea.getText() + "\n");
                connMgr.SendDataToAll("cg_" + UserName + ": " + textArea.getText() + "\n");
                textArea.setText("");
            }
            

        }
        
        if (evt.getSource() == enterBtn2) {

            if (textArea2.getText().isEmpty()) {
                //JOptionPane.showMessageDialog(this, "Bad Word", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                chatArea2.append(UserName + ": " + textArea2.getText() + "\n");
                if(parent.teamno==1){
                	SendDatatoTeam1("ct_" + UserName + ": " + textArea2.getText() + "\n");
                }
                else{
                	SendDatatoTeam2("ct_" + UserName + ": " + textArea2.getText() + "\n");
                }
                //connMgr.SendDataToAll("c_" + UserName + ": " + textArea2.getText() + "\n");
                textArea2.setText("");
            }
            

        }
        if (evt.getSource() == gameOptions) {

            new InternalFrame(this, connMgr, parent);

        }

        
        if (evt.getSource() == logout) {
        	
        	try
        	{
				DatagramSocket TerminateSocket = new DatagramSocket();
				DatagramPacket terminatePacket;
				byte[] sendData = new byte[50];
				String terminatedata = "Terminate_" + parent.UserName;
				sendData = terminatedata.getBytes();         
				terminatePacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("172.17.174.131"), 1142);
				TerminateSocket.send(terminatePacket);
				TerminateSocket.close();
	        }
        	catch (Exception e)
        	{System.out.println ("Could not officially logout");}
			
        	// Disconnect myself
            connMgr.SendDataToAll("r_" + UserName + "\n");
            System.err.println("Sent disconnect request to all");
            // end all server and client threads
            connMgr.endThread();
            System.err.println("Ended all threads");
            System.exit(0);
        }
        
        
        if (evt.getSource() == addPlayer1) {

        	System.out.println ("Entered add button");
            removePlayer1.setEnabled(true);
            int index = friendList.getSelectedIndex();
            /*if (playerModel1.contains(model.getElementAt(index))) {
            JOptionPane.showMessageDialog(this, "Player Already In List", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
            playerModel1.addElement(model.getElementAt(index));
            }*/

        	System.out.println ("Pre call: Entered add button");
        	addPlayerToTeam1(model.getElementAt(index).toString());

        	System.out.println ("Post call: Entered add button");

        }
        if (evt.getSource() == addPlayer2) {
        	
        	removePlayer2.setEnabled(true);
            int index = list.getSelectedIndex();
            /*if (playerModel2.contains(model2.getElementAt(index))) {
            JOptionPane.showMessageDialog(this, "Player Already In List", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
            playerModel2.addElement(model2.getElementAt(index));
            }*/
            addPlayerToTeam2(model2.getElementAt(index).toString());

        }

        if (evt.getSource() == removePlayer1) {
            try {
            	System.out.println ("XX");
                int index = playerList1.getSelectedIndex();
                String player=playerModel1.getElementAt(index).toString();//playerModel1.get(index).toString();
                System.out.println ("Player is " + player);
                for (int k = 0; k < playerModel1.size(); k++) {
                	connMgr.SendData (player, "r_" + playerModel1.get(k) + "_1" + "\n");
                	connMgr.SendData (playerModel1.get(k).toString(), "r_" + player + "_1" + "\n");
                }
                for (int k = 0; k < playerModel2.size(); k++) {
                	connMgr.SendData(player, "r_" + playerModel2.get(k) + "_2" + "\n");
                	connMgr.SendData(playerModel2.get(k).toString(), "r_" + player + "_2" + "\n");
                }
            	connMgr.SendData(player, "r_" + parent.UserName + "_2" + "\n");
                removePlayerFromTeam1(playerModel1.elementAt(index).toString());
                
               /* SendDatatoTeam1("r_" + playerModel1.elementAt(index).toString() + "_1");
                SendDatatoTeam2("r_" + playerModel1.elementAt(index).toString() + "_2");*/
                if (playerModel1.isEmpty()) {
                    removePlayer1.setEnabled(false);
                }
                else
                    removePlayer1.setEnabled(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error", "Error", JOptionPane.ERROR_MESSAGE);
            }


        }

        if (evt.getSource() == removePlayer2) {
            try {
                int index = playerList2.getSelectedIndex();
                String player=playerModel2.getElementAt(index).toString();//playerModel2.get(index).toString();
                for (int k = 0; k < playerModel1.size(); k++) {
                	connMgr.SendData(player, "r_" + playerModel1.get(k) + "_2" + "\n");
                	connMgr.SendData(playerModel1.get(k).toString(), "r_" + player + "_2" + "\n");
                	if (connMgr == null)
                		System.out.println ("hate this null shit");
                }
                for (int k = 0; k < playerModel2.size(); k++) {
                	connMgr.SendData(player, "r_" + playerModel2.get(k) + "_1" + "\n");
                	connMgr.SendData(playerModel2.get(k).toString(), "r_" + player + "_1" + "\n");
                }
                connMgr.SendData(player, "r_" + parent.UserName + "_2" + "\n");
                removePlayerFromTeam2(playerModel2.elementAt(index).toString());
                
                /*SendDatatoTeam1("r_" + playerModel1.elementAt(index).toString() + "_2");
                SendDatatoTeam2("r_" + playerModel1.elementAt(index).toString() + "_1");*/

                if (playerModel2.isEmpty()) {
                    removePlayer2.setEnabled(false);
                }
                else
                    removePlayer2.setEnabled(true);
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }


        if (evt.getSource() == startGameButton) {
        	//Event when a person presses to start a game
        	//CHECK IF THE SCORE/LEVEL CRITERION IS SATISFIED
            int scorediff = 0;
            int score1 = parent.level;
            int score2 = 0;
            for (int i = 0; i < playerModel1.size(); i++) {
                String player = playerModel1.getElementAt(i).toString();
                score1 = score1 + getPlayerscore(player);
            }

            for (int i = 0; i < playerModel2.size(); i++) {
                String player = playerModel2.getElementAt(i).toString();
                score2 = score2 + getPlayerscore(player);
            }

            scorediff = Math.abs(score1 - score2);
           if (scorediff > 5) { // If level difference is more than five, do not allow
                JOptionPane.showMessageDialog(this, "Team Score Criterion NOT Satisfied!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
            	
            	if (parent.gameStr != null)//Proceed only if all parameters are set in game options
            	{
                String gamestr = parent.gameStr;
               //Generate the game initialisation string
                try {
					gamestr = gamestr + parent.myTeamName + "_" + (playerModel1.size() + 1) + "_" + parent.UserName + "_" + InetAddress.getLocalHost().getHostAddress() + "_" + parent.level + "_";
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
                for (int i = 0; i < playerModel1.size(); i++) {
                    String player = playerModel1.getElementAt(i).toString();
                    gamestr = gamestr + player + "_" + getPlayerIP(player) + "_" + getPlayerscore(player) + "_";
                }
                gamestr = gamestr + parent.oppTeamName + "_" + playerModel2.size() + "_";
                for (int i = 0; i < playerModel2.size(); i++) {
                    String player = playerModel2.getElementAt(i).toString();
                    gamestr = gamestr + player + "_" + getPlayerIP(player) + "_" + getPlayerscore(player) + "_";
                }
                
                System.out.println ("Game string: " + gamestr);
                gamestr = gamestr.substring(0, gamestr.length() - 1);
                //connMgr.SendDataToAll("c_" + gamestr + "\n");
                connMgr.SendDataToAll(gamestr + "\n");
                parent.gameRequests.add(gamestr);
            	}
            	else
            		JOptionPane.showMessageDialog(this, "Set Game Options", "Error", JOptionPane.ERROR_MESSAGE);
                
            }
        }


    }
    

    public void AddRequest(String str)
    {
    	parent.gameRequests.add(str);
    }
    
    public void addFriendToArray(String str) //TO add a friend to the friends array
    {
    	int i, l, j;

    	l = parent.FNumber;
    	
    	for(i=0; i<parent.RNumber; i++)
    	{
    		if(parent.RUsernamesArr[i].equals(str) == true)
    		{
    			parent.FUsernamesArr[l] = parent.RUsernamesArr[i];
    			parent.FIPsArr[l] = parent.RIPsArr[i];
    			parent.FLevelArr[l] = parent.RLevelArr[i];
    			parent.FLoginStatusArr[l] = parent.RLoginStatusArr[i];
    			
    			l = parent.RNumber - 1;
    			
    			for(j=i; j<l; j++)
    			{
    				parent.RUsernamesArr[j] = parent.RUsernamesArr[j+1];
    				parent.RLevelArr[j] = parent.RLevelArr[j+1];
    				parent.RIPsArr[j] = parent.RIPsArr[j+1];
    				parent.RLoginStatusArr[j] = parent.RLoginStatusArr[j+1];
    			}
    			break;
    		}
    	}
    	parent.RNumber--;
    	parent.FNumber++;
    	parent.update = 1;
    }
    
    public void removeFriendFromArray(String str) //Removing a friend from friends array
    {
    	int i, l, j;
	   	
    	for(i=0; i<parent.FNumber; i++)
    	{
    		if(parent.FUsernamesArr[i].equals(str) == true)
    		{
    			l = parent.RNumber;
    			parent.RUsernamesArr[l] = parent.FUsernamesArr[i];
    			parent.RLevelArr[l] = parent.FLevelArr[i];
    			parent.RIPsArr[l] = parent.FIPsArr[i];
    			parent.RLoginStatusArr[l] = parent.FLoginStatusArr[i]; 
    			
    			l = parent.RNumber - 1;
    			
    			for(j=i; j<l; j++)
    			{
    				parent.FUsernamesArr[j] = parent.FUsernamesArr[j+1];
    				parent.FLevelArr[j] = parent.FLevelArr[j+1];
    				parent.FIPsArr[j] = parent.FIPsArr[j+1];
    				parent.FLoginStatusArr[j] = parent.FLoginStatusArr[j+1];
    			}
    			break;
    		}
    	}

    	parent.RNumber++;
    	parent.FNumber--;
    	parent.update = 1;
    }
}



