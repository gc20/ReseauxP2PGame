package Server;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Vector;

public class registerDthread extends Thread

{
	String str;
	Vector <StoredInformation> info;
	Socket sock;

	public registerDthread (String str, Vector <StoredInformation> info, Socket sock)
	{
		this.str = str;
		this.info = info;
		this.sock = sock;
		setDaemon(true); // This thread is a daemon thread of the main connect thread
	}
	
	public void run ()
	{
		try
		{
			String userEntry [] = new String [6]; // Stores parsed data received from user
			StoredInformation newUser;
			DataOutputStream out;
			int i;
			boolean isPresent = false;
			
			// Create a new user object based on provided data
			userEntry = str.split("_");
			
			for (i=0; i<info.size(); i++)
			{
				if (userEntry[1].equals(info.elementAt(i).getusername()) == true)
				{
					isPresent = true;
					break;
				}
			}
			
			out = new DataOutputStream(sock.getOutputStream());
			
			if (isPresent == false)
			{
				newUser = new StoredInformation (userEntry[1], userEntry[2], userEntry[3], userEntry[4], userEntry[5]);
				// Add created user object to the main "info" vector
				info.addElement(newUser);
			
				// Inform user that registration was completed successfully
				out.writeBytes("Register_Success" + '\n');   
			}
			
			else
				out.writeBytes("Register_UsernameExists" + '\n');   
			
			out.close();
			sock.close();
		}
		catch (Exception e)
		{ System.out.println ("Exception in register thread");}
	}
}
