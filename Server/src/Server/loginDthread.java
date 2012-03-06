package Server;

import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;

// This class manages login requests
public class loginDthread extends Thread 
{
	String str;
	Vector <StoredInformation> info;
	Socket sock;
	int lock = 0;
	boolean loginInform [];

	
	public loginDthread (String str, Vector <StoredInformation> info, Socket sock, boolean loginInform [])
	{
		System.out.println ("Creating Login Thread - Inside Constructor");
		this.str = str;
		this.info = info;
		this.sock = sock;
		this.loginInform = loginInform;
		setDaemon(true); // This thread is a daemon thread of the main connect thread
	}
	
	
	public void run ()
	{
		try
		{
			System.out.println ("Inside Login Daemon");
			String userEntry [] = new String [3]; // Stores parsed input data
			int i,j,k;
			DataOutputStream out;
			StoredInformation obj = null;
			boolean check = false;
				
		    userEntry = str.split("_"); // Format: "Login_<username>_<password>". Parses this data
		    out = new DataOutputStream (sock.getOutputStream());
		    
		    // Run a lookup of all stored data. If such login info exists, authenticate user.
		    for (i=0; i<info.size(); i++)
		    {
		    	obj = info.elementAt(i);
		    	
		    	if ((userEntry[1].equals (obj.getusername()) == true) && (obj.checkPassword(userEntry[2]) == true))
		    	{
		    		// If login details are correct, change user status to "online" and update his timestamp
		    		obj.login ();
		    		
		    		while(lock != 0);
		    		obj.setIP(sock.getInetAddress().toString());
		    		lock = 1;
		    		obj.setPort (sock.getPort());		    		
		    		System.out.println (obj.getusername() + " is now online");
		    		check = true;
		    		break;
		    	}
		    }
		    
		    // If no such user was found, inform client
		    if (check == false)
		    {
		    	System.out.println ("Login failed");
		    	out.writeBytes("Login_Failed\n"); // Notify client that login has failed
		    	System.out.println ("Sent login failed message to client");
		    }
		    else
		    {
		    	// Initialize data that is to be sent to client
		    	String friends_username = obj.getusername() + "_";
		    	obj.IP = sock.getInetAddress().toString();
		    	System.out.println (obj.IP);
		    	String friends_IP = InetAddress.getLocalHost().getHostAddress().toString() + "_";
		    	String friends_status = "1_";
		    	int friends_count = 1;
		    	String friends_level = obj.getLevel() + "_";  
		    	String rest_username = "Stalker_";
		    	String rest_IP = "172.0.0.1_";
		    	String rest_status = "0_";
		    	int rest_count = 1;
		    	String rest_level = "0_";
		    	
		    	
		    	// Send information of all users (categorize as "friends" and the "rest") - username, IP, status
		    	for (j=0; j<info.size();j++)
		    	{
		    		// If user himself/herself is encountered, skip the check
		    		boolean check2 = false;
		    		String curr_username = info.elementAt(j).getusername();
		    		if (curr_username.equals(obj.getusername()) == true)
		    			continue;
		    		
		    		for (k=0;k<obj.friends.size();k++)
		    		{
		    			if (obj.friends.elementAt(k).equals(curr_username) == true)
		    			{
		    				check2 = true;
		    				friends_username += curr_username + "_";
		    				friends_IP += info.elementAt(j).getIP() + "_";
		    				friends_status += Integer.toString(info.elementAt(j).getStatus()) + "_";
		    				friends_level += Integer.toString(info.elementAt(j).getLevel()) + "_";
		    				friends_count++;
		    				break;
		    			}
		    		}
		    		
		    		if (check2 == false)
		    		{
		    			rest_username += curr_username + "_";
		    			rest_IP += info.elementAt(j).getIP() + "_";
		    			rest_status += Integer.toString(info.elementAt(j).getStatus()) + "_";
		    			rest_level += Integer.toString(info.elementAt(j).getLevel()) + "_";
		    			rest_count++;
		    			System.out.println ("Rest:" + rest_username + " " + rest_IP + " " + rest_status + " " + rest_level + " " + rest_count);
		    		}
		    	}
		    	
		    	// Notify client that login has succeeded
		    	out.writeBytes("Login_Success" + '\n');
		    	Thread.sleep(1000); // Wait for client to register this information
		    	out.writeBytes("Level_" + obj.getLevel() + "\n");
		    	Thread.sleep(1500); // Wait for client to process level related information
		    	
		    	
		    	// Send all friend and rest data to user
		    	out.writeBytes("Friends_" + Integer.toString(friends_count) +'\n'
		    			+"Names_" + friends_username + '\n'+
		    			"IP_" + friends_IP + '\n' +
		    			"Status_" + friends_status + '\n' + 
		    			"Level_" + friends_level + '\n' +
		    			"Rest_" + Integer.toString(rest_count) + '\n' + 
		    			"Names_" + rest_username + '\n' + 
		    			"IP_" + rest_IP + '\n' +
		    			"Status_" + rest_status + '\n' +
		    			"Level_" + rest_level + '\n');
		    	
		    	
		    	/*// Send number of friends + list of friends
		    	Thread.sleep(3000);
		    	out.writeBytes("Friends_" + Integer.toString(friends_count) +'\n');
		    	Thread.sleep(1000);
		    	out.writeBytes("Names_" + friends_username + '\n');
		    	Thread.sleep(1000);
		    	out.writeBytes("IP_" + friends_IP + '\n');
		    	Thread.sleep(1000);
		    	out.writeBytes("Status_" + friends_status + '\n');
		    	Thread.sleep(1000);
		    	out.writeBytes("Level_" + friends_level + '\n');
		    	Thread.sleep(1000);
		    	
		    	
		    	// Send number of remaining user + list of remaining persons
		    	out.writeBytes("Rest_" + Integer.toString(rest_count) + '\n');
		    	Thread.sleep(1000);
		    	out.writeBytes("Names_" + rest_username + '\n');
		    	Thread.sleep(1000);
		    	out.writeBytes("IP_" + rest_IP + '\n');
		    	Thread.sleep(1000);
		    	out.writeBytes("Status_" + rest_status + '\n');
		    	Thread.sleep(1000);
		    	out.writeBytes("Level_" + rest_level + '\n');
		    	Thread.sleep(1000);*/

		    	// Send UDP packets to all online clients letting them know that new users have entered
		    	loginInform[0] = true;
		    
		    }
		    Thread.sleep (1000);
		    out.close();
			sock.close();
		    
		}
		catch (Exception e)
		{}
	}


	/*private void notifyAllUsersOfLogin(String username) {
		try
		{
		String send = "statuschange_" + username + "_1";
		for (int i = 0; i<activeClientOutputStreams.size(); i++)
			activeClientOutputStreams.elementAt(i).writeBytes(send);
		}
		catch (Exception e)
		{System.out.println ("Error in notifyAllUsersOfLogin");}
	}*/
	
}
