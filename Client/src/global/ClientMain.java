package global;
import gameInitiation.ConnectionManager;
import gameInitiation.first;

import runGame.GUIBegin;
import runGame.PlanGame;
import welcomeScreen.InitiationControl;
import data.ClientPrimaryData;


public class ClientMain 
{
	// Fixed ports and IPs
	static String serverIP = "172.17.174.131";
	static int serverConnectPort = 1141;
	static int helloUDPPort = 1142;
	static int statusChangeUDPPort = 1152;
	static int gameplayAttackPort = 1145;
	static int gameplayDefencePort = 1145;
	static int invitePort = 1147;
	
	// Objects used
	static ClientPrimaryData myClientPrimaryData;
	static InitiationControl myInitiationControl;
	static PlanGame myPlanGame;
	static GUIBegin myGUIBegin;
	
	// Connection Manager variable
	static ConnectionManager connMgr;
	
	public static void main (String args[])
	{
		try
		{
		// Create data object
		myClientPrimaryData = new ClientPrimaryData ();
		
		
		// Welcome user (login/register/reset password)
		myInitiationControl = new InitiationControl (myClientPrimaryData, serverIP, serverConnectPort, statusChangeUDPPort, helloUDPPort);
		myInitiationControl.ClientConnect();
		
		
		
		// Start thread to prepare game invites
		myClientPrimaryData.scoreEnd[0] = false;
		System.out.println ("Before first");
		first myFirst = new first (myClientPrimaryData, serverIP, serverConnectPort);
		myFirst.gfirst();
		connMgr = myFirst.l.connMgr;
			
		
		while (true)
		{
			// After login, start thread to obtain game invites
			myPlanGame = new PlanGame (invitePort, myClientPrimaryData);
			myPlanGame.plan();
			
			// Once game has been setup, launch game
			myClientPrimaryData.l.setVisible(false); // Make chat window disappear
			myGUIBegin = new GUIBegin (myClientPrimaryData, gameplayAttackPort, gameplayDefencePort, connMgr, serverIP, serverConnectPort);
			myGUIBegin.initiateGameProcess();
			System.out.println ("Out of GUIBegin");
			
			while (myClientPrimaryData.scoreEnd[0] == false)
				{Thread.sleep(1000);}
			System.out.println ("YIPEE");
			myClientPrimaryData.gameRequests.removeAllElements();
			myClientPrimaryData.l.setVisible(true);
		}
		
		}
		catch (Exception e)
		{System.out.println ("Exception in Client Main thread");}
	}
}
	
