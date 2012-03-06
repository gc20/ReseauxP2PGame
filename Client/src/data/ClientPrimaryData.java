package data;

import gameInitiation.CommandManager;
import gameInitiation.ConnectionManager;
import gameInitiation.ListModel;
import gameInitiation.UserData;
import java.util.Vector;

import runGame.gameManage;

public class ClientPrimaryData {

	int permissibleFriends = 100;
	
	// Friend Details
	public String FUsernamesArr[] = new String [permissibleFriends];
	public String FIPsArr[] = new String [permissibleFriends];
	public int FLoginStatusArr[] = new int [permissibleFriends] ;
	public int FLevelArr[] = new int [permissibleFriends];
	public int FNumber;
	
	// "Rest" Details
	public String RUsernamesArr[] = new String [permissibleFriends];
	public String RIPsArr[] = new String [permissibleFriends];
	public int RLoginStatusArr[] = new int [permissibleFriends];
	public int RLevelArr[] = new int [permissibleFriends];
	public int RNumber;
	
	// Client Details
	public String UserName;
	public int level;
	
	// Store Game Requests
	public Vector<String> gameRequests = new Vector<String>();
	
	// Store All Words that have been defended through the game
	public Vector<String> defendedWords = new Vector<String>();
	
	// Game Details
	public String gameType;
	public int gameMetric;
	public String continent;
	public long startTime;
	
	// Game Participants' Details
	// User's team
	public String myTeamName;
	public int myTeamSize;
	public String myTeamMembers[];
	public String myTeamIPs[];
	public int myTeamLevels[];
	public int myTeamScores[];
	// Opposition team
	public String oppTeamName;
	public int oppTeamSize;
	public String oppTeamMembers[];
	public String oppTeamIPs[];
	public int oppTeamLevels[];
	public int oppTeamScores[];
	public int myScore = 0;
	
	public boolean gameOver = true;
	public int update = 0;
	
	public UserData FData[];
	public UserData RData[];
	public String str;


	public CommandManager cmdMgr;
	public ConnectionManager cnMgr;
	public gameManage myGameManage = null;

	public ListModel l;

	public int teamno = 1;
	
	public String gameStr = null;
	public int gameOptions = 0;

	public boolean scoreEnd [] = new boolean [1];
	
	
	public void startGame(String serverIP, int serverConnectPort) {
		FData = new UserData[FNumber];
		RData = new UserData[RNumber];

		for (int i = 0; i < FNumber; i++) {
			FData[i] = new UserData(FUsernamesArr[i], FIPsArr[i],
					FLoginStatusArr[i], FLevelArr[i]);
		}

		for (int i = 0; i < RNumber; i++) {
			RData[i] = new UserData(RUsernamesArr[i], RIPsArr[i],
					RLoginStatusArr[i], RLevelArr[i]);
		}
		
		l = new ListModel(UserName, FData, RData, str, this, serverIP, serverConnectPort);
		l.setVisible(true);
		
	}
}
