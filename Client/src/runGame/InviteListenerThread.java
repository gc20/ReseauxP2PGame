package runGame;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import data.ClientPrimaryData;



public class InviteListenerThread extends Thread
{
	int invitePort;
	ClientPrimaryData myClientPrimaryData;
	DatagramSocket serverSocket;
	boolean runningStatus;
	
	public InviteListenerThread (int invitePort, ClientPrimaryData myClientPrimaryData)
	{
		this.invitePort = invitePort;
		this.myClientPrimaryData = myClientPrimaryData;
		this.runningStatus = true;
	}
	
	public void run ()
	{
		try
		{
			byte[] receiveData = new byte[500];
			DatagramPacket receivePacket = null;
			String str;
			
			serverSocket = new DatagramSocket(invitePort);				  
			
			while (runningStatus)
			{
					// Receive game requests
					receivePacket = new DatagramPacket(receiveData, receiveData.length); 
					serverSocket.receive(receivePacket); 
					str = new String(receivePacket.getData());
					
					// Store request in game requests array
					myClientPrimaryData.gameRequests.add(str);
			}
		}
		catch (SocketException e)
		{System.out.println ("Exited InviteListenerThread with Socket Exception");}
		catch (IOException e)
		{System.out.println ("Exited InviteListenerThread with IO Exception");}
		} // end run ()
	
} // end class
