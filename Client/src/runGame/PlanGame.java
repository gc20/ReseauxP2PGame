package runGame;

import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import data.ClientPrimaryData;



public class PlanGame 
{
	int invitePort;
	ClientPrimaryData myClientPrimaryData;
	InviteListenerThread myInviteListenerThread;
	JFrame dialogFrame;
    
	public PlanGame (int invitePort, ClientPrimaryData myClientPrimaryData)
	{
		myClientPrimaryData.defendedWords.removeAllElements();
		this.invitePort = invitePort;
		this.myClientPrimaryData = myClientPrimaryData;
	}
	
	public void plan () throws InterruptedException
	{
		String str;
		String userEntry [] = new String [500];
		int userChoice;
		int i;
		
		try
		{
			// Launch thread to listen to invites
			myInviteListenerThread = new InviteListenerThread (invitePort, myClientPrimaryData);
			myInviteListenerThread.start();
			
			// Launch thread to initiate a game
			// XX Gaurav and Shruthi's method thread, which will add to vector requests
			
			// Check gameRequest vector to present requests to user
			while (true)
			{
				//System.out.println ("Bored in plangame thread");
				if (myClientPrimaryData.gameRequests.isEmpty())
					Thread.sleep(1000); // Check for new game requests every 1 second
				else
				{
					System.out.println ("We have a request! " + myClientPrimaryData.gameRequests.elementAt(0));
					// Extract first request
					str = myClientPrimaryData.gameRequests.firstElement();
					userEntry = str.split("_");
					
					// If packet does not fit protocol ("StartGame"), discard it
					if (userEntry[0].equals("StartGame") == false)
					{
						myClientPrimaryData.gameRequests.removeElementAt(0);
						continue;
					}
					
					System.out.println ("Before displaying request");
					
					// Display dialog request and obtain user reply
					userChoice = JOptionPane.showConfirmDialog(dialogFrame, obtainRequestString(userEntry), "New Game Request", JOptionPane.YES_NO_OPTION);
					

					System.out.println ("After displaying request");
					
					// Decipher user's reply
					if (userChoice == JOptionPane.NO_OPTION)
						myClientPrimaryData.gameRequests.removeElementAt(0); // Remove request if user declines
					else
					{

						// Close Invite Listener Thread
						//myInviteListenerThread.runningStatus = false;
						//myInviteListenerThread.serverSocket.close();
						
						// XX Close Gaurav-Shruthi Thread
						// XX gauravshruthiThread.interrupt();
						System.out.println ("prior to parsing of damned data");
						// Decrypt game request and store in myClientPrimaryData object for easy interpretation later
						myClientPrimaryData.gameType = userEntry[1];
						myClientPrimaryData.gameMetric = Integer.parseInt(userEntry[2]);
						myClientPrimaryData.continent = userEntry[3];
						myClientPrimaryData.startTime = Long.parseLong(userEntry[4]);
						System.out.println ("userEntry[1] " + userEntry[1]);
						if (userEntry[1].equals("Time") == true)
							myClientPrimaryData.gameMetric *= 60;
						myClientPrimaryData.myTeamName = userEntry[5];
						myClientPrimaryData.myTeamSize = Integer.parseInt(userEntry[6]);
						myClientPrimaryData.myTeamMembers = new String [myClientPrimaryData.myTeamSize]; // Initialize arrays
						myClientPrimaryData.myTeamIPs = new String [myClientPrimaryData.myTeamSize];
						myClientPrimaryData.myTeamLevels = new int [myClientPrimaryData.myTeamSize];
						myClientPrimaryData.myTeamScores = new int [myClientPrimaryData.myTeamSize];
						for (i=0; i<myClientPrimaryData.myTeamSize; i++) // Extract team names and IPs
						{
							myClientPrimaryData.myTeamMembers[i] = userEntry[7 + (3*i)];
							myClientPrimaryData.myTeamIPs[i] = userEntry[7 + (3*i) + 1];
							myClientPrimaryData.myTeamLevels[i] = Integer.parseInt(userEntry[7 + (3*i) + 2]);
							System.out.println (myClientPrimaryData.myTeamMembers[i] + " " + myClientPrimaryData.myTeamIPs[i] + " " + myClientPrimaryData.myTeamLevels[i]);
						}
						myClientPrimaryData.oppTeamName = userEntry[7 + (3*myClientPrimaryData.myTeamSize)];
						myClientPrimaryData.oppTeamSize = Integer.parseInt (userEntry[7 + (3*myClientPrimaryData.myTeamSize) + 1]);
						myClientPrimaryData.oppTeamMembers = new String [myClientPrimaryData.oppTeamSize]; // Initialize arrays
						myClientPrimaryData.oppTeamIPs = new String [myClientPrimaryData.oppTeamSize];
						myClientPrimaryData.oppTeamLevels = new int [myClientPrimaryData.oppTeamSize];
						myClientPrimaryData.oppTeamScores = new int [myClientPrimaryData.oppTeamSize];
						System.out.println ("Opp team size: " + myClientPrimaryData.oppTeamSize);
						for (i=0; i<myClientPrimaryData.oppTeamSize; i++) // Extract opposition names and IPs
						{
							System.out.println ("a");
							myClientPrimaryData.oppTeamMembers[i] = userEntry[7 + 3*(myClientPrimaryData.myTeamSize) + 2 + 3*i];
							System.out.println ("b");
							myClientPrimaryData.oppTeamIPs[i] = userEntry[7 + 3*(myClientPrimaryData.myTeamSize) + 2 + 3*i + 1];
							System.out.println ("c" + userEntry[7 + 3*(myClientPrimaryData.myTeamSize) + 2 + 3*i + 2]);
							System.out.println (Integer.parseInt((userEntry[7 + 3*(myClientPrimaryData.myTeamSize) + 2 + 3*i + 2]).trim()));
							myClientPrimaryData.oppTeamLevels[i] = Integer.parseInt((userEntry[7 + 3*(myClientPrimaryData.myTeamSize) + 2 + 3*i + 2]).trim());
							System.out.println ("d");
							System.out.println (myClientPrimaryData.oppTeamMembers[i] + " " + myClientPrimaryData.oppTeamIPs[i] + " " + myClientPrimaryData.oppTeamLevels[i]);
						}
						
						// Determine user's team
						teamShuffle();
						
						// Exit while loop
						break;
						
					} // Internal if
				} // External if
			} // While
		} // Try
		catch (Exception e)
		{System.out.println ("Exception in Plan() of PlanGame");}
	}

	//Crafts request message to be displayed to user in dialog box
	public String obtainRequestString (String userEntry[])
	{
		String requestString;
		int i;
		
		// Include threshold metric
		if (userEntry[1].equals("Time"))
			requestString = "Maximum Duration: " + (Integer.parseInt (userEntry[2]) *60) + " seconds\n";
		else
			requestString = "Maximum Points: " + userEntry[2] + " points\n";
		
		// Include start time
		requestString += "Start Time: At " + new Date (new Date ().getTime() + 5000) + "\n";
		
		// Include continent
		requestString += "Continent: " + userEntry[3] + "\n";
		
		// Include first team players
		requestString += "Team " + userEntry[5] + ": "; 
		for (i=1; i<=Integer.parseInt(userEntry[6]); i++)
			requestString += userEntry[4 + 3*i] + " ";
		requestString += "\n";
		
		// Include second team players
		requestString += "Team " + userEntry[7 + 3*Integer.parseInt(userEntry[6])] + ": "; 
		for (i=1; i<=Integer.parseInt(userEntry[7 + 3*Integer.parseInt(userEntry[6]) + 1]); i++)
			requestString += userEntry[6 + 3*Integer.parseInt(userEntry[6]) + 3*i] + " ";
		requestString += "\n";

		requestString += "Do you want to play?";
			
		return requestString;
	}
	
	// If user is in the "opposition" team by default, swap "my team" and "opposition team" data
	public void teamShuffle ()
	{
		System.out.println ("Team shuffle begin");
		boolean check = false;
		int i;
		String tempArr [];
		int tempArr1 [];
		int tempSize;
		String tempName;
		
		for (i=0; i<myClientPrimaryData.oppTeamSize; i++)
		{
			if (myClientPrimaryData.oppTeamMembers[i].equals(myClientPrimaryData.UserName) == true)
			{
				check = true; // Implies that user is a member of the "opposition"
				break;
			}
		}
		
		// No need to swap if check is false
		if (check == false)
			return;
		
		// Swap "opposition team" and "my team" details
		
		// Swap member name array
		tempArr = new String [myClientPrimaryData.oppTeamSize];
		tempArr = myClientPrimaryData.oppTeamMembers;
		myClientPrimaryData.oppTeamMembers = new String [myClientPrimaryData.myTeamSize];
		myClientPrimaryData.oppTeamMembers = myClientPrimaryData.myTeamMembers;
		myClientPrimaryData.myTeamMembers = new String [myClientPrimaryData.oppTeamSize];
		myClientPrimaryData.myTeamMembers = tempArr;
		
		// Swap member IPs array
		tempArr = new String [myClientPrimaryData.oppTeamSize];
		tempArr = myClientPrimaryData.oppTeamIPs;
		myClientPrimaryData.oppTeamIPs = new String [myClientPrimaryData.myTeamSize];
		myClientPrimaryData.oppTeamIPs = myClientPrimaryData.myTeamIPs;
		myClientPrimaryData.myTeamIPs = new String [myClientPrimaryData.oppTeamSize];
		myClientPrimaryData.myTeamIPs = tempArr;
		
		// Swap member levels array
		tempArr1 = new int [myClientPrimaryData.oppTeamSize];
		tempArr1 = myClientPrimaryData.oppTeamLevels;
		myClientPrimaryData.oppTeamLevels = new int [myClientPrimaryData.myTeamSize];
		myClientPrimaryData.oppTeamLevels = myClientPrimaryData.myTeamLevels;
		myClientPrimaryData.myTeamLevels = new int [myClientPrimaryData.oppTeamSize];
		myClientPrimaryData.myTeamLevels = tempArr1;
		
		// Swap member levels array
		tempArr1 = new int [myClientPrimaryData.oppTeamSize];
		tempArr1 = myClientPrimaryData.oppTeamScores;
		myClientPrimaryData.oppTeamScores = new int [myClientPrimaryData.myTeamSize];
		myClientPrimaryData.oppTeamScores = myClientPrimaryData.myTeamLevels;
		myClientPrimaryData.myTeamScores = new int [myClientPrimaryData.oppTeamSize];
		myClientPrimaryData.myTeamScores = tempArr1;
		
		// Swap team names
		tempName = myClientPrimaryData.oppTeamName;
		myClientPrimaryData.oppTeamName = myClientPrimaryData.myTeamName;
		myClientPrimaryData.myTeamName = tempName;
		
		// Swap team sizes
		tempSize = myClientPrimaryData.oppTeamSize;
		myClientPrimaryData.oppTeamSize = myClientPrimaryData.myTeamSize;
		myClientPrimaryData.myTeamSize = tempSize;
		
		myClientPrimaryData.teamno = 2;
	}
	
}
