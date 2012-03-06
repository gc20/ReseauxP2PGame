package Server;

import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Vector;

public class checkTimestamp extends Thread {
	Vector<StoredInformation> info;
	// Vector <DataOutputStream> activeClientOutputStreams;
	static FileWriter writer;
	int backupRate = 100; // As number of seconds * 3
	int clientUDPPort;
	boolean loginInform [];

	public checkTimestamp(Vector<StoredInformation> info, int clientUDPPort, boolean loginInform []) {
		this.info = info;
		this.clientUDPPort = clientUDPPort;
		this.loginInform = loginInform;
	}

	public void run() {
		try {
			int i;
			int backupVersion = 1;
			int backupCount = backupRate - 1;
			boolean openFlag = false;

			while (true) {

				for (i = 0; i < info.size(); i++) {
					if (backupCount == (backupRate - 1)) {
						if (i == 0) {
							openFlag = true;
							backupVersion++;
							openCsvFile(backupVersion);
						}
						addtoCsvFile(info.elementAt(i));
					}

					if (info.elementAt(i).getStatus() == 1
							&& info.elementAt(i).checkTimestamp() == false) {
						info.elementAt(i).logout();
						notifyAllUsersOfStatus (info.elementAt(i));
						Thread.sleep(100);
					}
				}
				
				// If server has set loginInform to true, notify all active users of
				// everyone's status
				//if (loginInform[0] == true)
				//{
				//System.out.println ("Dude, info size is " + info.size());
				
					for (i=0; i<info.size(); i++)
					{
						//System.out.println ("Status " + info.elementAt(i).status);
						if (info.elementAt(i).getStatus() == 1)
							notifyAllUsersOfStatus(info.elementAt(i));
					}
					//loginInform[0] = false;
				//}
				Thread.sleep(5000); // Wait for 3 seconds before repeating check
									// cycle

				backupCount = (backupCount + 1) % backupRate;
				if (backupCount == 0 && openFlag == true) {
					closeCsvFile();
					openFlag = false;
				}
			}
		} catch (Exception e) {
			closeCsvFile();
			System.out.println("Timestamp exception");
		}
	}

	public static void openCsvFile(int backupVersion) {
		try {
			String filename = "backup_" + backupVersion + ".csv";
			System.out.println("filename: " + filename);
			writer = new FileWriter(filename);

			writer.append("Username");
			writer.append(',');
			writer.append("Password");
			writer.append(',');
			writer.append("Secret Question");
			writer.append(',');
			writer.append("Secret Answer");
			writer.append(',');
			writer.append("Level");
			writer.append(',');
			writer.append("Best Score");
			writer.append('\n');
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addtoCsvFile(StoredInformation tempObject) {
		try {
			writer.append(tempObject.getusername());
			writer.append(',');
			writer.append(tempObject.getencryptedpass());
			writer.append(',');
			writer.append(tempObject.getSecretQn());
			writer.append(',');
			writer.append(tempObject.getSecretAns());
			writer.append(',');
			writer.append(Integer.toString(tempObject.getLevel()));
			writer.append(',');
			writer.append(Integer.toString(tempObject.getBestScore()));
			writer.append('\n');
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void closeCsvFile() {
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Function that shoots UDP packets to all online users, letting them know
	// that another user has logged in
	public void notifyAllUsersOfStatus (StoredInformation obj) {
		try {

			int j = 0;
			String sendDataString = "statuschange_" + obj.getusername() + "_"
					+ Integer.toString(obj.status) + "_xxxx";
			DatagramSocket UDPSocket = new DatagramSocket();
			//System.out.println ("Should ideally send " + sendDataString);

			for (j = 0; j < info.size(); j++) {

				// Doesn't send UDP packet if the object found is the object in
				// question
				//if (info.elementAt(j).getusername().equals(obj.getusername()) == true)
					//continue;

				// If user is online, inform him/her of the arrival of a new
				// user
				if (info.elementAt(j).getStatus() == 1) {

					byte[] sendData = new byte[100];
					sendData = sendDataString.getBytes();
					InetAddress IPAddress = InetAddress.getByName(info.elementAt(j).getIP());
					DatagramPacket sendPacket = new DatagramPacket(sendData,
							sendData.length, IPAddress, clientUDPPort);
					UDPSocket.send(sendPacket);
					//System.out.println("SCT: Sent " + sendDataString + " to " + obj.getusername() + " " + IPAddress);
				}
			}

			UDPSocket.close();

		} catch (Exception e) {
			System.out
					.println("Error in firing login status change UDP packets to " + obj.getusername());
		}
	}

	/*
	 * private void notifyAllUsersOfLogout (String username) { try { String send
	 * = "statuschange_" + username + "_0"; System.out.println ("notify: " +
	 * send); System.out.println (activeClientOutputStreams.size()); for (int i
	 * = 0; i<activeClientOutputStreams.size(); i++)
	 * activeClientOutputStreams.elementAt(i).writeBytes(send); } catch
	 * (Exception e) {System.out.println ("Error in notifyAllUsersOfLogout");} }
	 */
}
