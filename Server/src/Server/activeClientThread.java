package Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

public class activeClientThread extends Thread {

	String str;
	Vector<StoredInformation> info;
	Socket sock;

	public activeClientThread(String str, Vector<StoredInformation> info, Socket sock) {
		System.out
				.println("Creating Active Client Thread - Inside Constructor");
		this.str = str;
		this.info = info;
		this.sock = sock;
		setDaemon(true); // This thread is a daemon thread of the main connect
							// thread
	}

	public void run() {
		// Format example: AddFriend_MyName_FriendName
		String splitArr[];
		StoredInformation me = null;
		StoredInformation you = null;
		int i;
		splitArr = str.split("_");

		
		if (splitArr[0].equals("AddFriend") == true || splitArr[0].equals("RemoveFriend"))
		{
		// Get friend and opponent objects
		for (i = 0; i < info.size(); i++) {
			if (splitArr[1].equals(info.elementAt(i).getusername()) == true)
			{
				me = info.elementAt(i);
				System.out.println ("Me " + me.getusername());
			}
			if (splitArr[2].equals(info.elementAt(i).getusername()) == true)
			{

				you = info.elementAt(i);
				System.out.println ("You " + you.getusername());
			}
		}
		
		// Befriending entities should not be null, should be distinct, and should
		// not already be friends
		if (!(me == null || you == null || me == you)
				&& me.friends.contains(you.getusername()) == false
				&& you.friends.contains(me.getusername()) == false) {
			
			// Add or remove friends from both parties' friend vectors
			if (splitArr[0].equals("AddFriend") == true) {
				me.nofriends++;
				you.nofriends++;
				me.friends.add(you.getusername());
				you.friends.add(me.getusername());
				//System.out.println ("Server added friends " + me.getusername() + " " + you.getusername());
			}

			if (splitArr[0].equals("RemoveFriend") == true) {
				me.friends.remove(you.getusername());
				you.friends.remove(me.getusername());
				me.nofriends--;
				you.nofriends--;
				//System.out.println ("Server removed friends " + me.getusername() + " " + you.getusername());
			}
		}
		}
		
		
		if (splitArr[0].equals("ScoreUpdate") == true)
		{
			for (i=0; i<info.size(); i++)
			{
				if (splitArr[1].equals(info.elementAt(i).getusername()) == true)
				{
					info.elementAt(i).updateScore(Integer.parseInt (splitArr[2]));
					System.out.println ("new score for " + info.elementAt(i).getusername() + " is " + info.elementAt(i).level);
					try
					{
						DataOutputStream out = new DataOutputStream(sock.getOutputStream());
						out.writeBytes("Level_" + Integer.toString(info.elementAt(i).getLevel()) + "_" + '\n');
						out.close();
					}
					catch (Exception e)
					{System.out.println ("Could not send level update back to client");}
				}
			}
		}
		
		try {sock.close();} 
		catch (IOException e) 
		{ System.out.println ("Could not close socket on active client thread");}
	}
}