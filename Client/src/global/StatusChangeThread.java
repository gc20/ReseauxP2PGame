package global;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import data.ClientPrimaryData;

public class StatusChangeThread extends Thread {

	InetAddress IPAddress;
	int UDPPort;
	ClientPrimaryData myClientPrimaryData;

	public StatusChangeThread(ClientPrimaryData myClientPrimaryData,
			int UDPPort, InetAddress serverIP) {
		this.myClientPrimaryData = myClientPrimaryData;
		this.UDPPort = UDPPort;
		this.IPAddress = serverIP;
	}

	
	public void run() {
		try
		{
			byte[] receiveData = new byte[100];
			DatagramSocket statuschangeSocket = null;
			DatagramPacket receivePacket = null;
			String str = null;
			String userEntry [] = new String [100]; // Stores parsed data received from user
			int i;
		
			//System.out.println ("SCT: Creating UDP Socket");
			statuschangeSocket = new DatagramSocket(UDPPort);
			//System.out.println ("SCT: Created UDP Socket");	  
			
			while (true)
			{
				//System.out.println ("SCT - Beginning session!" + " on " + UDPPort);
				receivePacket = new DatagramPacket(receiveData, receiveData.length); 
				statuschangeSocket.receive(receivePacket); 
				//System.out.println ("SCT: Received something");
			
				str = new String(receivePacket.getData());   
				userEntry = str.split("_");
				userEntry[1] = userEntry[1].trim();
				//System.out.println ("Status change of user " + str + " to " + userEntry[2]);
			
				if (userEntry[0].equals("statuschange") == false)
					continue;

				// Go through friends list
				////System.out.println ("Outside first for");
				for (i = 0; i < myClientPrimaryData.FNumber; i++) {

					//System.out.println ("Inside first for");
					if (myClientPrimaryData.FUsernamesArr[i].equals(userEntry[1]) == true) {

						//System.out.println ("Found user");
						// while(lock2 !=0); //uncomment when code for new
						// request gets added
						int initialStatus = myClientPrimaryData.FLoginStatusArr[i];
						if (userEntry[2].length() > 1)
							userEntry[2] = userEntry[2].substring(0, 1);
						if (userEntry[2].trim().equals("0") == true)
							myClientPrimaryData.FLoginStatusArr[i] = 0;
						else
							myClientPrimaryData.FLoginStatusArr[i] = 1;
						if (initialStatus != myClientPrimaryData.FLoginStatusArr[i])
							myClientPrimaryData.update = 1;
						// lock2 = 1;
						//System.out.println("Status Change Thread: Status changed successfully");
						break;
					}
				}

				//System.out.println ("Outside second for");
				for (i = 0; i < myClientPrimaryData.RNumber; i++) {
					//System.out.println ("Inside second for");
					if (myClientPrimaryData.RUsernamesArr[i].equals(userEntry[1]) == true) {
						// while(lock3 !=0); //uncomment when code for new
						// request gets added
						//System.out.println ("Found user " + userEntry[2]);
						if (userEntry[2].length() > 1)
							userEntry[2] = userEntry[2].substring(0, 1);
						if (userEntry[2].trim().equals("0") == true)
							myClientPrimaryData.RLoginStatusArr[i] = 0;
						else
							myClientPrimaryData.RLoginStatusArr[i] = 1;
						//System.out.println ("Updated to " + myClientPrimaryData.RLoginStatusArr[i]);
						myClientPrimaryData.update = 1;
						// lock3 = 1;
						//System.out.println("Status Change Thread: Status changed successfully");
						break;
					}
				}
				}
			//StatusChangeSocket.close();

		}
			catch (SocketException e) {
				System.out.println("Error in status change thread");
			} catch (IOException e) {
				System.out.println ("Error in receiving data in status change thread");
			}
		} // Run 

}
