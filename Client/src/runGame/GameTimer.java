package runGame;

import javax.swing.JFrame;

import scores.ScoreMain;

import data.ClientPrimaryData;


public class GameTimer extends Thread {
	
	
	long CurrTime;
	long newCurrTime;
	long GameTime;
	ClientPrimaryData myClientPrimaryData;
	defenceThread A;
	gameManage myGameManage;
	JFrame F1;
	String serverIP;
	int serverConnectPort;
	
	
	public GameTimer(long Time1, ClientPrimaryData myClientPrimaryData, defenceThread A, gameManage myGameManage, JFrame F1, String serverIP, int serverConnectPort)
	{
		CurrTime = System.currentTimeMillis();
		GameTime = Time1*1000;
		this.myClientPrimaryData = myClientPrimaryData;
		this.A = A;
		this.myGameManage = myGameManage;
		this.F1 = F1;
	    this.serverIP = serverIP;
	    this.serverConnectPort = serverConnectPort;
	}
	
	public void run()
	{
		newCurrTime = System.currentTimeMillis();
		
		while((newCurrTime - CurrTime) < GameTime)
		{
			newCurrTime = System.currentTimeMillis();
			if((newCurrTime - CurrTime) >= GameTime)
			{
				//Stop game threads
				F1.setVisible(false);
				try {Thread.sleep(2000);} 
				catch (InterruptedException e) 
				{ System.out.println ("Game time sleep exception");} // Wait for 2 seconds
				
				myClientPrimaryData.gameOver = false;
				myGameManage.t.stop();
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