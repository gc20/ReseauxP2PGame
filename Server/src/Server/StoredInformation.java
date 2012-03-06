package Server;

import java.util.Date;
import java.util.Vector;

// Data is stored in the form of objects of this class.
// Methods and data members are self-explanatory
public class StoredInformation 
{
	private String username;
	private String password;
	int level;
	int bestscore;
	int totalscore;
	String IP;
	int port;
	int nofriends;
	Vector <String> friends;
	long timestamp;
	int status;
	String secretQn;
	String secretAns;
	
	public StoredInformation (String username, String password, String IP, String secretQn, String secretAns)
	{
		this.username = username;
		this.password = password;
		this.level = 1;
		this.bestscore = 0;
		this.totalscore = 0;
		this.IP = IP;
		this.port = -1;
		this.nofriends = 0;
		this.totalscore = 0;
		this.friends = new Vector <String> ();
		this.setTimestamp();
		this.status = 0;
		this.secretQn = secretQn;
		this.secretAns = secretAns;
	}

	public String getusername ()
	{
		return (username);
	}
	
	public String getencryptedpass ()
	{
		return (password);
	}
	
	
	public boolean checkPassword (String pass)
	{
		if (password.equals(pass) == true)
			return (true);
		else
			return (false);
	}
	
	public int getLevel()
	{
		return (level);
	}

	public String getIP ()
	{
		if (IP.charAt(0) == '/')
			return (IP.substring(1));
		else
			return (IP);
	}
	
	public int getStatus ()
	{
		return (status);
	}
	
	public String getSecretQn ()
	{
		return (secretQn);
	}
	
	public String getSecretAns ()
	{
		return (secretAns);
	}
	
	public int getBestScore ()
	{
		return (bestscore);
	}
	
	public boolean checkTimestamp ()
	{
		// False return implies that user login has timed-out
		// True implies that timestamp is valid
		
		long timediff = new Date().getTime() - this.timestamp;
		if (timediff > (1000 * 600)) // If no hello message has been received for 5 minutes
			return false;
		else
			return true;
	}
	
	public boolean checkSecretAns (String ans)
	{
		if (secretAns.equals(ans) == true)
			return (true);
		else
			return (false);
	}
	
	public void login ()
	{
		//System.out.println ("Login by user: " + this.getusername() + " at timestamp " + this.timestamp);
		status = 1;
		this.setTimestamp ();
	}
	
	public void logout ()
	{
		System.out.println ("Logout by user: " + this.getusername() + " at timestamp " + this.timestamp);
		status = 0;
	}
	
	public void changePassword (String s, String key)
	{
		if (key.equals("SSUGG") == true) // SSUGG is the authentication key
			password = s;
	}
	
	public void setTimestamp ()
	{
		this.timestamp = new Date().getTime();
		//System.out.println ("New time for " + this.getusername() + " is " + this.timestamp);
	}
	
	public void setIP (String IP)
	{
		this.IP = IP.substring(1);
	}
	
	public void setPort (int port)
	{
		this.port = port;
	}
	
	public void updateScore (int newscore)
	{
		this.totalscore += newscore;

		if (totalscore <= 100)
			this.level = 1;
		if (totalscore > 100)
			this.level = 2;
		if (totalscore > 200)
			this.level = 3;
		if (totalscore > 300)
			this.level = 4;
		if (totalscore > 400)
			this.level = 5;
		if (totalscore > 500)
			this.level = 6;
		if (totalscore > 600)
			this.level = 7;
		if (totalscore > 700)
			this.level = 8;
		if (totalscore > 800)
			this.level = 9;
		if (totalscore > 900)
			this.level = 10;
	}
	
}
