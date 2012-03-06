package scores;

import data.ClientPrimaryData;

public class ScoreMain {
	
	String scoreString = "";
	String teamNameString = "";
	String oppNameString = "";
	ClientPrimaryData myClientPrimaryData;
	int teamScore = 0, oppScore = 0;
	Results r;
	
	String serverIP;
	int serverConnectPort;
	
	public ScoreMain(ClientPrimaryData myClientPrimaryData, String serverIP, int serverConnectPort)
	{
		this.myClientPrimaryData = myClientPrimaryData;
		this.serverIP = serverIP;
		this.serverConnectPort = serverConnectPort;
	}
	
	
	public void createScoreString()
	{
		int i;
		// Sample: Alpha_3_Roger_12_Tom_15_Bill_3_2435_Gamma_3_Veronica_10_Celia_12_Bob_100_2435
		calculateTeamScore();
		
		teamNameString = "_" + myClientPrimaryData.UserName + "_" + Integer.toString(myClientPrimaryData.myScore);
		for(i = 0; i < myClientPrimaryData.myTeamSize; i++)
		{
			if (myClientPrimaryData.myTeamMembers[i].equals(myClientPrimaryData.UserName) == true)
				continue;
			teamNameString += "_" + myClientPrimaryData.myTeamMembers[i];
			teamNameString += "_" + myClientPrimaryData.myTeamScores[i];
			
		}
		
		for (i = 0; i < myClientPrimaryData.oppTeamSize; i++)
		{
			oppNameString += "_" + myClientPrimaryData.oppTeamMembers[i];
			oppNameString += "_" + myClientPrimaryData.oppTeamScores[i];
		
			
		}
		
		scoreString = myClientPrimaryData.myTeamName + "_" + Integer.toString(myClientPrimaryData.myTeamSize).trim() + teamNameString + "_" + Integer.toString(teamScore) + "_" + myClientPrimaryData.oppTeamName + "_" + Integer.toString(myClientPrimaryData.oppTeamSize).trim() + oppNameString + "_" + Integer.toString(oppScore);
		System.out.println ("Score string " + scoreString);
		displayResults();
	}
	

	public void displayResults()
	{	
		calculateTeamScore();
		r = new Results(this, myClientPrimaryData, serverIP, serverConnectPort);
		r.setVisible(true);
		//r.runThis(scoreString);
	}
	
	public void calculateTeamScore()
	{
		int i;
		
		teamScore = myClientPrimaryData.myScore;
		
		for(i = 0; i < myClientPrimaryData.myTeamSize; i++)
		{
			if (myClientPrimaryData.myTeamMembers[i].equals(myClientPrimaryData.UserName) == true)
				continue;
			teamScore += myClientPrimaryData.myTeamScores[i];
		}
		
		for(i=0; i <myClientPrimaryData.oppTeamSize; i++)
			oppScore += myClientPrimaryData.oppTeamScores[i];		
	}

}
