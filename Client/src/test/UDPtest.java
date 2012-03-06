package test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

public class UDPtest 
{
	
	public static void main (String args[])
	{
	try 
	{	
		DatagramSocket HelloSocket = new DatagramSocket();
		DatagramPacket helloPacket;
		InetAddress IPAddress = InetAddress.getByName("172.17.180.1");
		byte[] sendData = new byte[50];
		String hellodata;
			
		hellodata = "StartGame_Time_600_Asia_" + (new Date().getTime() + 20000) + "_TeamStinson_1_unmuktgoel_172.17.165.155_3_TeamBarney_2_govind_172.17.180.1_5_gaurav_172.17.170.250_4";
		sendData = hellodata.getBytes();         
		helloPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 1147);
			
		System.out.println ("Sending packet from user: " + hellodata);
		HelloSocket.send(helloPacket);

		/*// Send to sandra
		IPAddress = InetAddress.getByName("172.24.205.97");
		helloPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 1147);
		HelloSocket.send(helloPacket);
		// send to sandra*/
		

		// Send to Shruthi
		IPAddress = InetAddress.getByName("172.17.165.155");
		helloPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 1147);
		HelloSocket.send(helloPacket);
		// Send to Shruthi
		
		HelloSocket.close();
			
	} 
	catch (Exception e) 
	{ System.out.println("Error creating hello socket"); }
}
	
}
