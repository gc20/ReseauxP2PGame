package Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

public class fpassDthread extends Thread
{
	String str;
	Vector <StoredInformation> info;
	Socket sock;
	
	public fpassDthread (String str, Vector <StoredInformation> info, Socket sock)
	{
		this.str = str;
		this.info = info;
		this.sock = sock;
		setDaemon(true); // This thread is a daemon thread of the main connect thread s
	}
	
	
	public void run ()
	{
		try
		{
		System.out.println ("Inside Fpass Daemon");
			
		String userEntry [] = new String [2]; // Stores parsed input data
		StoredInformation obj = null;
		int i;
		boolean check = false;
		DataOutputStream out;
		
		// Parse input and store it in userEntry array
		userEntry = str.split("_");
		
		// Determines object corresponding to username and stores it in "obj"
		for (i=0; i<info.size();i++)
		{
			if (info.elementAt(i).getusername().equals(userEntry[1]) == true)
			{
				obj = info.elementAt(i);
				check = true;
			}
		}
		
		out = new DataOutputStream(sock.getOutputStream());
		
		// If user is not present, notify client
		if (check == false)
		{
			System.out.println ("No user detected");
			out.writeBytes ("FPass_UserAbsent" + '\n');
		}
		
		// If user is present, obtain secret answer, check if answer is correct, and allow user to change password
		else
		{
			System.out.println (obj.getusername() + " found");
			
			// If secret answer is wrong, let client know
			if (obj.checkSecretAns(userEntry[2]) == false)
				out.writeBytes ("FPass_WrongAnswer" + '\n');
			
			// If secret answer is correct, let client know and allow password change to take place
			else
			{
				// Change password
				obj.changePassword(userEntry[3], "SSUGG");		
				out.writeBytes ("FPass_PasswordChanged" + '\n');
					
			}
		}
		
			out.close();
			sock.close();
		}
		catch (IOException e)
		{ System.out.println ("Fpass - IO Exception");}
	}
	
}
