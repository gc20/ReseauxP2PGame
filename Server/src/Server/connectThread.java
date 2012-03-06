package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

// This class deals with login, registration and forget password scenarios
public class connectThread extends Thread {
	int connectPort;
	
	Vector<StoredInformation> info;
	boolean loginInform [];

	static int permissibleConcurrentConnections = 1;
	
	// Array of threads used to store each new thread that has been spawned
	static loginDthread A[] = new loginDthread[permissibleConcurrentConnections];
	static int A_count;
	static registerDthread B[] = new registerDthread[permissibleConcurrentConnections];
	int B_count = 0;
	static fpassDthread C[] = new fpassDthread[permissibleConcurrentConnections];
	int C_count = 0;
	static sQnDthread D[] = new sQnDthread[permissibleConcurrentConnections];
	int D_count = 0;
	static activeClientThread E[] = new activeClientThread[permissibleConcurrentConnections];
	int E_count = 0;
	
	// Array of maximum permissible concurrent sockets
	static Socket sock[] = new Socket[permissibleConcurrentConnections];
	int sock_count = 0;

	
	// Constructor
	public connectThread(int connectPort, Vector<StoredInformation> info, boolean loginInform []) {
		this.connectPort = connectPort;
		this.info = info;
		this.loginInform = loginInform;
	}

	
	public void run() {
		try {
			
			String command; // Stores string to interpret received command
			BufferedReader in; // Used to accept data from client
			String str; // Stores data accepted from client
			ServerSocket connectSock = new ServerSocket(connectPort); // Server socket
			int i = 0; // Counter

			while (true) {
				
				// Obtain a port entry in array that is available. Loop infinitely until
				// entry is made available
				while (true)
				{
					// Use socket entry, only if sock_count entry is not in use
					if (sock[sock_count] == null || sock[sock_count].isClosed() == true)
						break;
					
					// Move to next entry in array
					sock_count = (sock_count+1)%permissibleConcurrentConnections;
				}
				
				
				// Listen on requisite port for commands that may spawn new threads
				System.out.println("Listening on port " + connectPort + " (Connect Port).");
				sock[sock_count] = connectSock.accept();
				sock[sock_count].setSendBufferSize(2000);
				in = new BufferedReader(new InputStreamReader(sock[sock_count].getInputStream()));
				str = in.readLine().trim();
				System.out.println("Obtained input on port" + connectPort + ": " + str);

				
				// Check for command header
				command = str.substring(0, str.indexOf('_'));

				
				// This thread manages login requests
				if (command.equals("Login") == true) {
					// Finds an empty thread slot to use
					i=0;
					while (i<permissibleConcurrentConnections)
					{
						if (A[A_count] == null || A[A_count].isAlive() == false)
							break;
						i++;
						A_count = (A_count + 1) % permissibleConcurrentConnections;
					}
					// Initiates thread, if permitted
					if (i >= permissibleConcurrentConnections)
						System.out.println ("Too many concurrent threads. Request discarded.");
					else
					{
						System.out.println("Starting login thread");
						A[A_count] = new loginDthread(str, info, sock[sock_count], loginInform);
						A[A_count].start();
						A_count = (A_count + 1) % permissibleConcurrentConnections;
						System.out.println("connectThread: Login Thread Initiated.");
					}
				}

				
				// This thread manages registration requests
				if (command.equals("Register") == true) {
					// Finds an empty thread slot to use
					i=0;
					while (i<permissibleConcurrentConnections)
					{
						if (B[B_count] == null || B[B_count].isAlive() == false)
							break;
						i++;
						B_count = (B_count + 1) % permissibleConcurrentConnections;
					}
					// Initiates thread, if permitted
					if (i >= permissibleConcurrentConnections)
						System.out.println ("Too many concurrent threads. Request discarded.");
					else
					{
						System.out.println("Starting registration thread");
						B[B_count] = new registerDthread(str, info, sock[sock_count]);
						B[B_count].start();
						B_count = (B_count + 1) % permissibleConcurrentConnections;
						System.out.println("connectThread: Registration Thread Initiated.");
					}
				}

				
				// This thread manages password change requests
				if (command.equals("FPass") == true) {
					// Finds an empty thread slot to use
					i=0;
					while (i<permissibleConcurrentConnections)
					{
						if (C[C_count] == null || C[C_count].isAlive() == false)
							break;
						i++;
						C_count = (C_count + 1) % permissibleConcurrentConnections;
					}
					// Initiates thread, if permitted
					if (i >= permissibleConcurrentConnections)
						System.out.println ("Too many concurrent threads. Request discarded.");
					else
					{
						System.out.println("Starting forgot password thread");
						C[C_count] = new fpassDthread(str, info, sock[sock_count]);
						C[C_count].start();
						C_count = (C_count + 1) % permissibleConcurrentConnections;
					}
				}

				
				// This thread returns secret question for given login name
				if (command.equals("SQn") == true) {
					// Finds an empty thread slot to use
					i=0;
					while (i<permissibleConcurrentConnections)
					{
						if (D[D_count] == null || D[D_count].isAlive() == false)
							break;
						i++;
						D_count = (D_count + 1) % permissibleConcurrentConnections;
					}
					// Initiates thread, if permitted
					if (i >= permissibleConcurrentConnections)
						System.out.println ("Too many concurrent threads. Request discarded.");
					else
					{
						System.out.println("Starting retrieve secret question thread");
						D[D_count] = new sQnDthread(str, info, sock[sock_count]);
						D[D_count].start();
						D_count = (D_count + 1) % permissibleConcurrentConnections;
						}
				}

				
				// Starts thread for managing post login requests from client
				if (command.equals("AddFriend") == true || command.equals("RemoveFriend") == true
						|| command.equals("ScoreUpdate")) {
					// Finds an empty thread slot to use
					i=0;
					while (i<permissibleConcurrentConnections)
					{
						if (E[E_count] == null || E[E_count].isAlive() == false)
							break;
						i++;
						E_count = (E_count + 1) % permissibleConcurrentConnections;
					}
					
					// Initiates thread, if permitted
					if (i >= permissibleConcurrentConnections)
						System.out.println ("Too many concurrent threads. Request discarded.");
					else
					{
						System.out.println("Starting active client thread");
						E[E_count] = new activeClientThread(str, info, sock[sock_count]);
						E[E_count].start();
						E_count = (E_count + 1) % permissibleConcurrentConnections;
					}

				// Move to next available socket, for next iteration
				sock_count = (sock_count + 1) % permissibleConcurrentConnections;
				
				}
			}
		}
		
		catch (Exception e) // Add detailed exceptions later
		{
			System.out.println("Connect Thread Exception");
		}
	}
}
