package runGame;

import java.util.Random;
import java.util.Vector;

import data.ClientPrimaryData;



public class defenceThread extends Thread
{
	Vector <travellingWords> words;
	Vector <String> oppWords;
	Vector <String> input;
	int gameplayDefencePort;
    Random randomGenerator;
    int vel1;
    String gType;
    ClientPrimaryData myClientPrimaryData;
	
	public defenceThread (Vector <travellingWords> words, Vector<String> input, Vector<String> oppWords, int gameplayDefencePort, ClientPrimaryData myClientPrimaryData)
	{
		this.words = words;
		this.input = input;
		this.gameplayDefencePort = gameplayDefencePort;
		this.myClientPrimaryData = myClientPrimaryData;
		randomGenerator = new Random();
		setDaemon (true);
	}
	
	/*public void run ()
	{
		try
		{
			DatagramSocket connectSock = new DatagramSocket(gameplayDefencePort);
			DatagramPacket receivePacket = null;
			byte[] receiveData = new byte[50];
			int randomInt;
			String opplevelrec;
			String str, received, oppwordreceived;
			String s1[];
			int i;
			
			while (input.elementAt(2).equals("gameOn") == true && myClientPrimaryData.gameOver == true)
			{
				randomInt = generateExclusiveRandom ();
				System.out.println ("Random number is: " + randomInt);
				
				// Search for UDP packets at port
				receivePacket = new DatagramPacket(receiveData, receiveData.length); 
				System.out.println ("waiting to receive in defence");
				connectSock.receive(receivePacket); 
				str = new String(receivePacket.getData()).trim();
				
				System.out.println ("Read " + str);
				// If this represents an attack from opponent
				if (str.length() > 7 && (str.substring(0, 7).equals("Attack_") == true))
				{
					received = str.substring((str.indexOf('_') + 1), str.indexOf('1')).trim();
					System.out.println ("n00bzzz" + str);
					opplevelrec = str.substring((str.indexOf('1') + 1), str.indexOf('#')).trim();
					System.out.println ("Check" + opplevelrec);
					if(checkAlreadyExists(words, received.toLowerCase()) == false)
					{
						vel1 = getVelocityFromLevel(opplevelrec);
						words.add(new travellingWords (received.toLowerCase(), 0, randomInt, vel1, 0));
						System.out.println ("Added" + str.substring((str.indexOf('_') + 1), str.length()).toLowerCase());
					}
				}
				
				// If this represents a defence by team member
				if (str.length() > 12 && (str.substring(0, 12).equals("Teamdefence_") == true))
					removeFromScreen(words, str.substring((str.indexOf('_') + 1), str.length()).toLowerCase());	
				
				if(str.length() > 8 && (str.substring(0, 8)).equals("Oppword_") == true)
				{
					str = str.trim();
					oppwordreceived = str.substring(str.indexOf('_')+1, str.length()).trim();
					oppWords.add(oppwordreceived);
				}
				
				if(str.length() > 6 && (str.substring(0, 6)).equals("Score_") == true)
				{
					str = str.trim();
					s1 = str.split("_");
					
					for(i = 0; i < myClientPrimaryData.myTeamSize; i++)
					{
						if(myClientPrimaryData.myTeamMembers[i].equals(s1[1]) == true)
							myClientPrimaryData.myTeamScores[i] = Integer.parseInt(s1[2]);
					}
					
					for(i = 0; i < myClientPrimaryData.oppTeamSize; i++)
					{
						if(myClientPrimaryData.oppTeamMembers[i].equals(s1[1]) == true)
							myClientPrimaryData.oppTeamScores[i] = Integer.parseInt(s1[2]);
					}
					
				}
				
			}
			connectSock.close();
		}
		catch (Exception e)
		{ System.out.println ("Exception in defenceThread");} 
	} */
	
	public int generateExclusiveRandom ()
	{
		boolean exclusiveFlag = false;
		int x = 20;
		
		while (!exclusiveFlag)
		{
		x = randomGenerator.nextInt(100);
		x = (x%26 + 4) * 20;
		
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
    	int randomInt = generateExclusiveRandom ();
    	
    	received = str.substring((str.indexOf('_') + 1), str.indexOf('1')).trim();
		System.out.println ("n00bzzz" + str);
		opplevelrec = str.substring((str.indexOf('1') + 1), str.indexOf('#')).trim();
		System.out.println ("Check" + opplevelrec);
		if(checkAlreadyExists(words, received.toLowerCase()) == false)
		{
			vel1 = getVelocityFromLevel(opplevelrec);
			words.add(new travellingWords (received.toLowerCase(), 0, randomInt, vel1, 0));
			System.out.println ("Added" + str.substring((str.indexOf('_') + 1), str.length()).toLowerCase());
		}
    	
    }
    
    public void teamdefenceString(String str)
    {
    	removeFromScreen(words, str.substring((str.indexOf('_') + 1), str.length()).toLowerCase());	
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
				myClientPrimaryData.myTeamScores[i] = Integer.parseInt(s1[2]);
		}
		
		for(i = 0; i < myClientPrimaryData.oppTeamSize; i++)
		{
			if(myClientPrimaryData.oppTeamMembers[i].equals(s1[1]) == true)
				myClientPrimaryData.oppTeamScores[i] = Integer.parseInt(s1[2]);
		}
		
    }
}