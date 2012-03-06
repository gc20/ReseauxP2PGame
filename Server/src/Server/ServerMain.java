// Remember to close sockets and threads
// Add detailed exceptions
// Account for exits

// This class represents the main server class, which initiates 3 threads that control flow of server
package Server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;

public class ServerMain {

	static int connectPort = 1141; // Port on which to check for TCP messages
									// regarding login functions
	static int helloPort = 1142; // Hello/terminate (UDP messages)
	int portC = 1143; // Update server database (TCP messages)
	int portD = 1144;
	static int clientUDPPort = 1152; // Port on which client receives UDP
										// messages
	static int postLoginPort = 1231;
	static boolean loginInform []= new boolean [1];
	
	static Vector <StoredInformation> info;
	//static Vector <DataOutputStream> activeClientOutputStreams;

	static connectThread A;
	static helloThread B;
	static checkTimestamp C;
	//static postLoginThread D;

	public static void main(String[] args) {

		info = new Vector<StoredInformation>();
		populateFromCSV(info);
		loginInform[0] = true;

		A = new connectThread(connectPort, info, loginInform);
		B = new helloThread(helloPort, info);
		C = new checkTimestamp(info, clientUDPPort, loginInform);
		//D = new postLoginThread (info, postLoginPort);

		A.start();
		B.start();
		C.start();
		//D.start();
	}
	

	private static void populateFromCSV(Vector<StoredInformation> info) {
		try {

			// Open the filestream
			FileInputStream fstream = new FileInputStream("backup.csv");

			// Temp variables
			String splitArr[];
			StoredInformation tempInformation;

			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			// Variables
			String strLine;

			boolean flagFirst = false;

			while ((strLine = br.readLine()) != null) {

				System.out.println ("Strline: " + strLine);
				if (flagFirst == false) // Discard first line
				{
					flagFirst = true;
					continue;
				}

				splitArr = strLine.split(",");
				tempInformation = new StoredInformation(splitArr[0],
						splitArr[1], "127.0.0.1", splitArr[2], splitArr[3]);
				tempInformation.level = Integer.parseInt(splitArr[4]);
				tempInformation.bestscore = Integer.parseInt(splitArr[5]);
				
				info.add(tempInformation);
				
			}

			// Close the input stream
			in.close();

		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

	}

}
