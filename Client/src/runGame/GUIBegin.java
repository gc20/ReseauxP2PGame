package runGame;

import gameInitiation.ConnectionManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;
import javax.swing.JFrame;

import data.ClientPrimaryData;



public class GUIBegin {

	Vector <String> input;
	static Vector <travellingWords> words;
	static Vector <String> permissible;
	static Vector <String> oppWords;
	static defenceThread A;
	ClientPrimaryData myClientPrimaryData;
	ConnectionManager C;
	int gameplayAttackPort;
	int gameplayDefencePort;
	long Time1;
	int Points1;
	String gType;
	//gameManage myGameManage;
	JFrame F1;
	String serverIP;
	int serverConnectPort;
    
	
	
	public GUIBegin (ClientPrimaryData myClientPrimaryData, int gameplayAttackPort, int gameplayDefencePort, ConnectionManager C, String serverIP, int serverConnectPort)
	{
		this.myClientPrimaryData = myClientPrimaryData;
		this.gameplayAttackPort = gameplayAttackPort;
		this.gameplayDefencePort = gameplayDefencePort;
		this.C = C;
		this.serverIP = serverIP;
		this.serverConnectPort = serverConnectPort;
	}
	
	
	public void initiateGameProcess ()
	{
		input = new Vector <String> (); // 0->User Input, 1->Score, 2->Gameplay Flag
		words = new Vector <travellingWords> ();
		permissible = new Vector <String> ();
		oppWords = new Vector <String> ();
		gType = myClientPrimaryData.gameType;
		filePopulate ();
		

		A = new defenceThread (words, input, oppWords, gameplayDefencePort, myClientPrimaryData);
		//A.start();
		launchGame ();
		
		if(gType.contentEquals("Time") == true)
		{
			Time1 = myClientPrimaryData.gameMetric;
			GameTimer gt = new GameTimer(Time1, myClientPrimaryData, A, myClientPrimaryData.myGameManage, F1, serverIP, serverConnectPort);
			gt.start();
		}
		System.out.println ("Content " + gType);
		if(gType.contentEquals("Points") == true)
		{
			Points1 = myClientPrimaryData.gameMetric;
			System.out.println ("Max points " + Points1);
			PointsChecker pc = new PointsChecker(Points1, myClientPrimaryData, A, myClientPrimaryData.myGameManage, F1, serverIP, serverConnectPort);
			pc.start();
		}
		
	}	
	
	public void launchGame ()
	{	
		input.add("false");
		input.add("0");
		input.add("gameOn");
		
		//ImagePanel bgPanel = new ImagePanel(new ImageIcon("background1.gif").getImage());
		//populateSample ();
		myClientPrimaryData.myGameManage = new gameManage (input, words, gameplayAttackPort, permissible, oppWords, myClientPrimaryData, C);
		
		F1 = new JFrame();
		//F1.add(bgPanel);
		F1.add(myClientPrimaryData.myGameManage);
		F1.setVisible(true);
		F1.setTitle("Reseaux - Game Window");
		F1.setSize(1000, 600);
		F1.setResizable(false);
		F1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	

	}
	
	/*public static void populateSample ()
	{
		words.add (new travellingWords ("kochi", 0, 20, 2, 2));
		words.add (new travellingWords ("guruvayur", 0, 40, 2, 2));
		words.add (new travellingWords ("thiruvananthapuram", 0, 60, 2, 2));
		words.add (new travellingWords ("kottayam", 0, 80, 2, 2));
		words.add (new travellingWords ("paris", 0, 100, 2, 2));
	}*/
	
	public void filePopulate ()
	{
		try
		{
			String str;
			BufferedReader br;
			String fileName;
			
			// Find file according to continent
			fileName = myClientPrimaryData.continent.toLowerCase() + ".txt";
			
			// Open and read file
			br = new BufferedReader(new FileReader(fileName));
			while ((str = br.readLine()) != null)
				permissible.add(str.toLowerCase());
			br.close();
		}
		catch (Exception e)
		{System.out.println ("Could not populate from file");}
	}
	
	
}
