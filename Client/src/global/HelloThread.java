package global;



import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class HelloThread extends Thread
{
	
	InetAddress IPAddress;
	byte[] sendData = new byte[50];
	String hellodata;
	String terminatedata;
	int UDPPort;
	boolean checkLogin [] = new boolean [1];
	
	String temp;
	
	public HelloThread(int UDPPort, String username, InetAddress serverIP, boolean checkLogin []) throws Exception
	{
		this.UDPPort = UDPPort;
		this.hellodata = "Hello_" + username;
		this.terminatedata = "Terminate_" + username;
		this.IPAddress = serverIP;
		this.checkLogin = checkLogin;
		this.temp = username;
	}
	
	public void run()
	{	
		try 
		{		
			DatagramSocket HelloSocket = new DatagramSocket();
			DatagramPacket helloPacket;
			DatagramPacket terminatePacket;
				
			sendData = hellodata.getBytes();         
			helloPacket = new DatagramPacket(sendData, sendData.length, IPAddress, UDPPort);
				
			System.out.println ("HelloThread - before while starts");
			while(checkLogin[0] == true) 
			{
				//System.out.println ("Sending Hello packet from user: " + temp);
				HelloSocket.send(helloPacket);
				Thread.sleep(8000); // 8 seconds
			}
				
			sendData = terminatedata.getBytes();         
			terminatePacket = new DatagramPacket(sendData, sendData.length, IPAddress, UDPPort);				
			HelloSocket.send(terminatePacket);
				
			HelloSocket.close();
				
		} 
		catch (Exception e) 
		{ System.out.println("Error creating hello socket"); }
	}
}