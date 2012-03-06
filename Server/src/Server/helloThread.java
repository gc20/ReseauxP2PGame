package Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Vector;

public class helloThread extends Thread
{
	int helloPort;
	Vector <StoredInformation> info;
	
	public helloThread (int helloPort, Vector <StoredInformation> info)
	{
		this.helloPort = helloPort;
		this.info = info;
	}
	
	public void run ()
	{
		try
		{
			byte[] receiveData = new byte[50];
			DatagramSocket serverSocket = null;
			DatagramPacket receivePacket = null;
			String str = null;
			String userEntry [] = new String [40]; // Stores parsed data received from user
			int i;
			
			serverSocket = new DatagramSocket(helloPort);	  
			
			while (true)
			{
				//System.out.println ("helloThread - Beginning session!. Info size is:" + info.size());
				receivePacket = new DatagramPacket(receiveData, receiveData.length); 
				serverSocket.receive(receivePacket); 
			
				str = new String(receivePacket.getData());   
				userEntry = str.split("_");
				userEntry[1] = userEntry[1].trim();
				//System.out.println (str);
				for (i=0;i<info.size();i++)
				{
					if (userEntry[1].equals(info.elementAt(i).getusername().trim()) == true)
					{
						//System.out.println ("Server received UDP for " + userEntry[1]);
						if (userEntry[0].equals("Hello") == true)
							info.elementAt(i).login();
						if (userEntry[0].equals("Terminate") == true)
							info.elementAt(i).logout();
						
					}
					
				}
			}
			// Infinite loop above. Therefore, socket not closed
		}
		catch (Exception e)
		{ 
			System.out.println ("Error/exception in Hello Thread");
		}
	}
}
