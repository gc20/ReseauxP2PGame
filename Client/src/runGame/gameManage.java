package runGame;

import gameInitiation.ConnectionManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.DatagramSocket;
import java.util.Date;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Timer;

import data.ClientPrimaryData;


@SuppressWarnings("serial")
public class gameManage extends JPanel implements ActionListener, KeyListener
{
	Vector <String> permissible;
 	DatagramSocket attackSocket;
 	DatagramSocket oppWordSocket;
 	DatagramSocket scoreSocket;

	public Timer t;
	Vector <String> input;
	Vector <travellingWords> words;
	Vector <String> oppWords;
	private static JLabel score1, level1;
	private static JLabel score2, level2;
	//private static JLabel startTime;
	private static JList teamList, otherWordsList, totalWordsList;
	private static JPanel listPanel, topPanel, consolePanel;
	private static JScrollPane teamScrollPane, otherWordsScrollPane, totalWordsScrollPane;
	private static JLabel teamLabel, otherWordsLabel, totalWordsLabel;
	private static DefaultListModel teamListModel, otherWordsModel, totalWordsModel;
	private static JTextField enterCity;
	boolean burnFlag = false;
	int burnX = 0;
	int burnY = 0;
	int wordCounter = 0;
	ClientPrimaryData myClientPrimaryData;
	ConnectionManager C;
	int gameplayAttackPort;
	Random randomGenerator;
	
	public gameManage (Vector <String> input, Vector <travellingWords> words, int gameplayAttackPort, Vector<String> permissible, Vector<String> oppWords, ClientPrimaryData myClientPrimaryData, ConnectionManager C)
	{
		try
		{
		Thread.sleep(10000);
		System.out.println ("Out of sleep");
		this.input = input;
		this.words = words;
		this.oppWords = oppWords;
		this.gameplayAttackPort = gameplayAttackPort;
		this.permissible = permissible;
		this.myClientPrimaryData = myClientPrimaryData;
		this.C = C;
		myClientPrimaryData.cnMgr = C;
		attackSocket = new DatagramSocket (); // Create UDP Socket
		oppWordSocket = new DatagramSocket();
		scoreSocket = new DatagramSocket();
		randomGenerator = new Random();
		}
		catch (Exception e)
		{System.out.println ("Could not create attack socket.");}
		
		//ImagePanel bgPanel = new ImagePanel(new ImageIcon("background1.gif").getImage());
		listPanel = new JPanel();
		listPanel.setSize(160, 600);
		topPanel = new JPanel();
		//gamePanel = new JPanel();
		consolePanel = new JPanel();
		teamLabel = new JLabel("Team Members");
		otherWordsLabel = new JLabel("Words You Had Intercepted");
		totalWordsLabel = new JLabel("Allowed words");
		teamListModel = new DefaultListModel();
		totalWordsModel = new DefaultListModel();
		enterCity = new JTextField("\t\t");

		enterCity.setEditable(true);
		enterCity.setPreferredSize(new Dimension(150, 30));
		setFocusable(true);
		setFocusTraversalKeysEnabled (false);
		enterCity.addKeyListener(this); 
		
		score1 = new JLabel ("Score: ");
		score1.setBounds(0, 800, 80, 50);
		score2 = new JLabel ("0\t");
		score2.setBounds(60, 900, 80, 50);
		level1 = new JLabel("Level:");
		level1.setBounds(300, 800, 80, 50);
		level2 = new JLabel(Integer.toString(myClientPrimaryData.level) + "\t");
		level2.setBounds (360, 900, 80, 50);
		
		//startTime = new JLabel("Game will start at: " + new Date (myClientPrimaryData.startTime));
		//startTime.setBounds(380, 280, 380, 350);
		
		setLayout(new BorderLayout());	
		
		
		for(int i = 0; i < myClientPrimaryData.myTeamSize; i++)
			teamListModel.add(i, myClientPrimaryData.myTeamMembers[i]);
		teamList = new JList(teamListModel);
		
		for(int i = 0; i < permissible.size(); i++)
			totalWordsModel.add(i, permissible.elementAt(i));
		totalWordsList = new JList(totalWordsModel);
		
		
		add(topPanel, BorderLayout.NORTH);
		add(listPanel, BorderLayout.EAST);
		add(consolePanel, BorderLayout.SOUTH);
		//add(bgPanel);
		
		consolePanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		consolePanel.add(enterCity);
		
				
		topPanel.setSize(new Dimension(500, 40));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		topPanel.add(score1);
		topPanel.add(score2);
		topPanel.add(level1);
		topPanel.add(level2);
		
		teamScrollPane = new JScrollPane(teamList);
		otherWordsScrollPane = new JScrollPane(otherWordsList);
		totalWordsScrollPane = new JScrollPane(totalWordsList);
		
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
		listPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		//listPanel.setBounds(0, 100, 600, 600);
		listPanel.add(teamLabel);
		listPanel.add(teamScrollPane);
		listPanel.add(otherWordsLabel);
		listPanel.add(otherWordsScrollPane);
		listPanel.add(totalWordsLabel);
		listPanel.add(totalWordsScrollPane);
		listPanel.setAlignmentX(Component.RIGHT_ALIGNMENT); 
		
		// Start game timer only when start time is reached
		System.out.println ("Waiting for: " + myClientPrimaryData.startTime);
		while (new Date().getTime() < myClientPrimaryData.startTime)
		{
			try {Thread.sleep(100);} 
			catch (InterruptedException e) 
			{System.out.println ("Error while waiting");}
			continue;
		}
		System.out.println ("Start time threshold crossed. Ready to start game.");
		
		
		t = new Timer (100,this);
		t.start();
		this.input = input;
		this.words = words;
	}
	
	
	public void paintComponent (Graphics g)
	{
		
		otherWordsModel = new DefaultListModel();
		for(int i = 0; i < myClientPrimaryData.defendedWords.size(); i++)
			otherWordsModel.add(i, myClientPrimaryData.defendedWords.elementAt(i));
		otherWordsList = new JList(otherWordsModel);
		
		
		
		int i;
		super.paintComponent(g);
		Font font = new Font("Serif", Font.ITALIC, 40);
		myClientPrimaryData.l.setVisible(false); // Make chat window disappear
		
		/* Image bgImg = new ImageIcon("background1.gif").getImage();
		 Dimension size = new Dimension(bgImg.getWidth(null), bgImg.getHeight(null));
		 setPreferredSize(size);
		 setMinimumSize(size);
		 setMaximumSize(size);
		 setSize(size);
		 setLayout(null);
		 
		 g.drawImage(bgImg, 0, 0, null); */
		
		g.setFont(font);
		g.setColor(Color.RED);
		
		
		for (i=0; i<words.size(); i++)
		{
			if (words.elementAt(i).getCity().equals("xx"))
				continue;
			g.drawString(words.elementAt(i).getCity(), words.elementAt(i).getX(), words.elementAt(i).getY());
			//System.out.println("Displaying " + words.elementAt(i).getCity());
		}
			
		if(burnFlag == true)
		{
			ImageIcon img = new ImageIcon("fire1.png");
			img.paintIcon(this, g, burnX, burnY);
			burnFlag = false;
		}
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		String word1;
		int v;
		repaint ();
		String enteredWord = input.elementAt(0).toLowerCase().trim();
		//byte[] scoreBytes = new byte[50];
		//DatagramPacket scorePacket;
		String scoreString;
																	
		if (input.elementAt(0).equals("false") == false)
		{														
			for (int i=0; i<words.size(); i++)
			{
				if (words.elementAt(i).getCity().equals("xx"))
				{
					words.remove(i);
					i--;
					continue;
				}
				
				System.out.println ("Sparta "+ words.elementAt(i).getCity() + " " + enteredWord);
				if (words.elementAt(i).getCity().equals(enteredWord) == true)
				{
					word1 =  words.elementAt(i).getCity();
					v = words.elementAt(i).getVelX();
					System.out.println ("Removing " + words.elementAt(i).getCity());
					burnFlag = true;
					burnX = words.elementAt(i).getX();
					burnY = words.elementAt(i).getY();
					System.out.println ("Before repaint"); // to be removed
					words.remove(i);
					i--;
			
					// Inform fellow team members of this change
					String teamdefenceString = "Teamdefence_" + enteredWord + '\n';
					//byte[] teamdefenceBytes = teamdefenceString.getBytes();
					//DatagramPacket teamdefencePacket;
					for (i=0; i<myClientPrimaryData.myTeamSize; i++)
					{
						try
						{
							C.SendData(myClientPrimaryData.myTeamMembers[i], teamdefenceString);
							//teamdefencePacket = new DatagramPacket (teamdefenceBytes, teamdefenceBytes.length, InetAddress.getByName(myClientPrimaryData.myTeamIPs[i]), gameplayAttackPort);
							//attackSocket.send(teamdefencePacket);
						}
						catch (Exception ex)
						{System.out.println ("Could not send information to team members in actionPerformed function");}
					}
				 
					
					// Update score
					int score = Integer.parseInt (input.elementAt(1));
					int wordScore = updateScoreAdd(word1, v);
					score += wordScore;
					input.setElementAt(Integer.toString (score), 1);
					myClientPrimaryData.myScore = Integer.parseInt(input.elementAt(1));
					scoreString = "Score_" + myClientPrimaryData.UserName + "_" + input.elementAt(1) + '\n';
					//scoreBytes = scoreString.getBytes();
					
					// Send score to all users
					for (i=0; i<myClientPrimaryData.myTeamSize; i++)
					{
						try
						{C.SendData(myClientPrimaryData.myTeamMembers[i], scoreString);}
						catch (Exception ex)
						{System.out.println ("Could not send score information to team members in actionPerformed function");}
					}
					for (i=0; i<myClientPrimaryData.oppTeamSize; i++)
					{
						try
						{C.SendData(myClientPrimaryData.oppTeamMembers[i], scoreString);}
						catch (Exception ex)
						{System.out.println ("Could not send score information to team members in actionPerformed function");}
					}
					
					
					/*for (i=0; i<myClientPrimaryData.myTeamSize; i++)
					{
						try
						{
							scorePacket = new DatagramPacket (scoreBytes, scoreBytes.length, InetAddress.getByName(myClientPrimaryData.myTeamIPs[i]), gameplayAttackPort);
							attackSocket.send(scorePacket);
						}
						catch (Exception ex)
						{System.out.println ("Could not send information to team members in actionPerformed function");}
					}
					
					for (i=0; i<myClientPrimaryData.oppTeamSize; i++)
					{
						try
						{
							scorePacket = new DatagramPacket (scoreBytes, scoreBytes.length, InetAddress.getByName(myClientPrimaryData.oppTeamIPs[i]), gameplayAttackPort);
							attackSocket.send(scorePacket);
						}
						catch (Exception ex)
						{System.out.println ("Could not send information to team members in actionPerformed function");}
					}*/
					
					score2.setText(Integer.toString(score) + "\t");
					//break;										//Added to remove just one city when there are two
					
				}
			}
			input.setElementAt("false", 0);
		}
		
		//System.out.println ("FRE3");
		
		for (int i=0; i<words.size(); i++)
		{
			if (words.elementAt(i).getX() > 765)
			{
				String word2 = words.elementAt(i).getCity();
				int v2 = words.elementAt(i).getVelX();
				int score = Integer.parseInt (input.elementAt(1));
				int teamScoreDeduction = updateScoreSubtract(word2, v2);
				score -= teamScoreDeduction;
				//score--;
				input.setElementAt(Integer.toString (score), 1);
				myClientPrimaryData.myScore = Integer.parseInt(input.elementAt(1));
				scoreString = "Score_" + myClientPrimaryData.UserName + "_" + input.elementAt(1) + '\n';
				
				C.SendDataToAll(scoreString);
				/*for (i=0; i<myClientPrimaryData.myTeamSize; i++)
				{
					try
					{
						scorePacket = new DatagramPacket (scoreBytes, scoreBytes.length, InetAddress.getByName(myClientPrimaryData.myTeamIPs[i]), gameplayAttackPort);
						attackSocket.send(scorePacket);
					}
					catch (Exception ex)
					{System.out.println ("Could not send information to team members in actionPerformed function");}
				}
				
				for (i=0; i<myClientPrimaryData.oppTeamSize; i++)
				{
					try
					{
						scorePacket = new DatagramPacket (scoreBytes, scoreBytes.length, InetAddress.getByName(myClientPrimaryData.oppTeamIPs[i]), gameplayAttackPort);
						attackSocket.send(scorePacket);
					}
					catch (Exception ex)
					{System.out.println ("Could not send information to team members in actionPerformed function");}
				}*/
				score2.setText(Integer.toString(score));
				words.remove(i); // Remove word from array
				i--;
			}
			else
				words.elementAt(i).updateX();
		}
		//System.out.println ("FRE4");
	}	
		

 public void keyPressed(KeyEvent e)
	{
		try
		{
		int code, i;
		String enteredValue;
		boolean presentFlag = false;
		String attackString, oppWordString;
		//byte[] attackBytes = new byte[50];
		//byte[] oppWordBytes = new byte[50];
		//DatagramPacket attackPacket;
		//DatagramPacket oppWordPacket;
		
		code = e.getKeyCode ();
		
		if (code == KeyEvent.VK_ENTER)
		{
			enteredValue = enterCity.getText().toLowerCase().trim();
			//displayVector(words, "Words");
			
			if (isPermissible(enteredValue) == true)
			{
				System.out.println ("Permitted");
				for (i=0; i<words.size(); i++)
				{
					if (words.elementAt(i).getCity().equals (enteredValue))
						presentFlag = true;
				}
				System.out.println ("Present flag: " + presentFlag);
				if (presentFlag == true)
				{
					myClientPrimaryData.defendedWords.add(enteredValue.toLowerCase());
					input.setElementAt(enterCity.getText(), 0);
				}
				else
				{
					// Send attack word to all opponents
					//otherWordsModel.add(wordCounter, enteredValue);
					//wordCounter++;
					
					System.out.println ("My level is: " + myClientPrimaryData.level);
					attackString = "Attack_" + enteredValue + '1' + myClientPrimaryData.level + '#' + '\n';
					//attackBytes = attackString.getBytes();
					for (i=0; i<myClientPrimaryData.oppTeamSize; i++)
					{
						C.SendData(myClientPrimaryData.oppTeamMembers[i], attackString);
						//attackPacket = new DatagramPacket (attackBytes, attackBytes.length, InetAddress.getByName(myClientPrimaryData.oppTeamIPs[i]), gameplayAttackPort);
						//attackSocket.send(attackPacket);
					}
					System.out.println ("Finished attack string");
					oppWordString = "Oppword_" + enteredValue + '\n';
					System.out.println ("A");
					//oppWordBytes = oppWordString.getBytes();
					System.out.println ("Sending to my teammates: " + oppWordString);
					for(i=0; i<myClientPrimaryData.myTeamSize; i++)
					{
						System.out.println ("B");
						
						if (myClientPrimaryData.myTeamMembers[i].equalsIgnoreCase(myClientPrimaryData.UserName))
							continue;
						System.out.println ("Sending friendly fire to " + myClientPrimaryData.myTeamIPs[i]);
						C.SendData(myClientPrimaryData.myTeamMembers[i], oppWordString);
						//oppWordPacket = new DatagramPacket (oppWordBytes, oppWordBytes.length, InetAddress.getByName(myClientPrimaryData.myTeamIPs[i]), gameplayAttackPort);	
						//oppWordSocket.send(oppWordPacket);
						
					}
					oppWords.add(enteredValue);
				
				}
			}
			enterCity.setText("");
		}
		}
		catch (Exception e1)
		{ System.out.println ("Could not send word to defender");}
	}

	public boolean isPermissible (String enteredValue)
	{						
		System.out.println("Function: isPermissible");
		for (int i=0; i<permissible.size(); i++)
		{
			System.out.println ("Comparing "+ permissible.elementAt(i) + " " + enteredValue);
			if (permissible.elementAt(i).equals(enteredValue) == true)
				{
					return true;
				}
		}
			
		System.out.println ("true was returned");
		return false;
	}
	
	public boolean isAlreadyExists (String enteredValue)
	{
		System.out.println("Function: isAlreadyExists");
		if(words.isEmpty() == true)
		{
			System.out.println("Words is empty");
			return false;
		}
		
		for(int i = 0; i < words.size(); i++)
		{
			System.out.println("Comparing " + words.elementAt(i).getCity()+ " " + enteredValue);
			if(words.elementAt(i).equals(enteredValue) == true)
					return true;
		}
		
		return false;
	}
	
	public void displayVector (Vector<travellingWords> words2, String vectortype)
	{
		System.out.println("Function: displayVector");
		System.out.println("Contents of " + vectortype + " are ");
		for(int i = 0; i < words2.size(); i++)
		{
			System.out.println(words2.elementAt(i).getCity());
		}
	}
	
	public int updateScoreAdd(String word, int level)
	{
		int s = 0;
		
		s = word.length() + (level * 2) ;
		
		return s;
	}
	
	public int updateScoreSubtract(String word, int level)
	{
		int s = 0;
		int t = myClientPrimaryData.myTeamSize;
		
		s = (word.length() + (level * 2))/t;
		
		return s;
	}
	
	public void keyReleased(KeyEvent arg0) 
	{}

	public void keyTyped(KeyEvent arg0) 
	{}
	
	public int generateExclusiveRandom ()
	{
		boolean exclusiveFlag = false;
		int x = 20;
		
		while (!exclusiveFlag)
		{
		x = randomGenerator.nextInt(100);
		x = (x%22 + 4) * 20;
		
		exclusiveFlag = true;
		for (int i = 0; i<words.size(); i++)
		{
			if (words.elementAt(i).getX() == x)
			{
				exclusiveFlag = false;
				break;
			}
		}
		}
		
		return x;
	}
	
	public int checkInput (String str)
	{
		int i;
		
		for(i = 0; i < words.size(); i++)
		{
			if(words.isEmpty() == false && words.elementAt(i).equals(str) == true)
				return 1;
			
		}
		
		return 0;
	}
	
	public boolean checkAlreadyExists(Vector <travellingWords> words, String enteredCity)
	{
		if(words.isEmpty() == true)
			return false;
		
		for (int i = 0; i < words.size(); i++)
		{
			if(words.elementAt(i).getCity().contentEquals(enteredCity) == true)
				return true;
		}
		
		return false;
		
	}
	
	public void removeFromScreen (Vector <travellingWords> words, String enteredCity)
	{
		for (int i = 0; i < words.size(); i++)
		{
			if(words.elementAt(i).getCity().contentEquals(enteredCity) == true)
				words.elementAt(i).burnCity();
		}
		
	}
	
    public int getVelocityFromLevel (String opplevelrec)
	{
    	int v;
    	v = (Integer.parseInt(opplevelrec)) * 2;
    	return v;
	}
    
    public void attackString(String str)
    {
    	String received;
    	String opplevelrec;
    	int vel1;
    	int randomInt = generateExclusiveRandom ();
    	
    	received = str.substring((str.indexOf('_') + 1), str.indexOf('1')).trim();
		opplevelrec = str.substring((str.indexOf('1') + 1), str.indexOf('#')).trim();
		if(checkAlreadyExists(words, received.toLowerCase()) == false)
		{
			vel1 = getVelocityFromLevel(opplevelrec);
			words.add(new travellingWords (received.toLowerCase(), 0, randomInt, vel1, 0));
		}
    	
    }
    
    public void teamdefenceString(String str)
    {
    	int i = 0;
    	String defendedCity = str.substring((str.indexOf('_') + 1), str.length()).toLowerCase();
    	removeFromScreen(words, defendedCity);
    	for (i=0; i<words.size(); i++)
    	{
    		if (words.elementAt(i).getCity().equals(defendedCity) == true)
    			words.remove(i);
    	}
    }
    
    public void oppwordString(String str)
    {
    	String oppwordreceived;
    	str = str.trim();
		oppwordreceived = str.substring(str.indexOf('_')+1, str.length()).trim();
		oppWords.add(oppwordreceived);
    }
    
    public void scoreString(String str)
    {
 
    	String s1[];
    	int i;
    	
    	str = str.trim();
		s1 = str.split("_");
		
		for(i = 0; i < myClientPrimaryData.myTeamSize; i++)
		{
			if(myClientPrimaryData.myTeamMembers[i].equals(s1[1]) == true)
				myClientPrimaryData.myTeamScores[i] = Integer.parseInt(s1[2].trim());
		}
		
		for(i = 0; i < myClientPrimaryData.oppTeamSize; i++)
		{
			if(myClientPrimaryData.oppTeamMembers[i].equals(s1[1]) == true)
				myClientPrimaryData.oppTeamScores[i] = Integer.parseInt(s1[2].trim());
		}
		
    }
}

