package runGame;

import javax.swing.JFrame;

import scores.ScoreMain;
import data.ClientPrimaryData;

public class PointsChecker extends Thread {
	
	int CurrPoints1 = 0, CurrPoints2 = 0;
	int MaxPoints;
	ClientPrimaryData myClientPrimaryData;
	defenceThread A;
	gameManage myGameManage;
	JFrame F1;
	String serverIP;
	int serverConnectPort;
	

	
	
	public PointsChecker(int Points1, ClientPrimaryData myClientPrimaryData, defenceThread A, gameManage myGameManage, JFrame F1, String serverIP, int serverConnectPort)
	{
		MaxPoints = Points1;
		this.myClientPrimaryData = myClientPrimaryData;
		this.A = A;
		this.myGameManage = myGameManage;
		this.F1 = F1;
	    this.serverIP = serverIP;
	    this.serverConnectPort = serverConnectPort;
	}
	
	public void run()
	{
		int i;
		
		while (CurrPoints1 < MaxPoints && CurrPoints2 < MaxPoints)
		{
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			CurrPoints1 = 0;
			CurrPoints2 = 0;
			
			CurrPoints1 += myClientPrimaryData.myScore;
			for(i = 0; i < myClientPrimaryData.myTeamMembers.length; i++)
			{
				if (myClientPrimaryData.myTeamMembers[i].equals(myClientPrimaryData.UserName) == true)
					continue;
				CurrPoints1 += myClientPrimaryData.myTeamScores[i];
			}
			
			for(i = 0; i < myClientPrimaryData.oppTeamMembers.length; i++)
				CurrPoints2 += myClientPrimaryData.oppTeamScores[i];
			
			System.out.println ("CurrPoints " + CurrPoints1 + " CurrPoints 2" + CurrPoints2 + " My score " + myClientPrimaryData.myScore);
			
			if(CurrPoints1 >= MaxPoints || CurrPoints2 >= MaxPoints)
			{
				
				try {
					Thread.sleep (2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				String scoreString = "Score_" + myClientPrimaryData.UserName + "_" + myClientPrimaryData.myScore + '\n';
				// Send score to all users
				for (i=0; i<myClientPrimaryData.myTeamSize; i++)
				{
					try
					{myClientPrimaryData.cnMgr.SendData(myClientPrimaryData.myTeamMembers[i], scoreString);}
					catch (Exception ex)
					{System.out.println ("Could not send score information to team members in actionPerformed function");}
				}
				for (i=0; i<myClientPrimaryData.oppTeamSize; i++)
				{
					try
					{myClientPrimaryData.cnMgr.SendData(myClientPrimaryData.oppTeamMembers[i], scoreString);}
					catch (Exception ex)
					{System.out.println ("Could not send score information to team members in actionPerformed function");}
				}
				
				
				//Stop game threads
				myClientPrimaryData.gameOver = false;
				myGameManage.t.stop();
				F1.setVisible(false);
				A.interrupt();
				myClientPrimaryData.gameOver = false;
			
				//Results page
				ScoreMain s = new ScoreMain(myClientPrimaryData, serverIP, serverConnectPort);
				s.createScoreString();
				
				break;
			}
		}
	}
}