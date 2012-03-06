package welcomeScreen;

import global.HelloThread;
import global.StatusChangeThread;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import data.ClientPrimaryData;

public class InitiationControl {

	// Thread instances
	private static HelloThread myHelloThread;
	private static StatusChangeThread mySCThread;

	// Data instance
	private static ClientPrimaryData myClientPrimaryData;

	// Network access information
	static int serverConnectPort;
	static int helloUDPPort;
	static int statuschangeUDPPort;
	static InetAddress serverIP;
	
	// Sets status of user to "login" to inform all users
	boolean checkLogin [] = new boolean [1];
	
	
	// Constructor
	@SuppressWarnings("static-access")
	public InitiationControl(ClientPrimaryData myClientPrimaryData,
			String serverIP, int serverConnectPort, int statusChangeUDPPort, int helloUDPPort) {
		try {
			this.myClientPrimaryData = myClientPrimaryData;
			System.out.println(serverIP);
			this.serverIP = InetAddress.getByName(serverIP);
			this.serverConnectPort = serverConnectPort;
			this.statuschangeUDPPort = statusChangeUDPPort;
			this.helloUDPPort = helloUDPPort;
		} catch (Exception e) {
			System.out.println("Error in Initiation Control constructor");
		}
	}

	
	// Logs in, registers, resets password based on user GUI Inputs
	public void ClientConnect() throws Exception {

		// Variables to store and display GUI input and messages
		boolean loginResult = false;
		String welcomeInputs[] = new String[5];
		String warningMessages[] = new String[1];
		WelcomeForm myWelcomeForm = new WelcomeForm();
		myWelcomeForm.StartWelcome(welcomeInputs); // Begin myWelcome class
		checkLogin[0] = false;

		System.out.println("\nAwaiting user initiation input");
		while (!loginResult) {

			// Set/reset default values
			welcomeInputs[0] = "noInput"; // Default value to determine GUI
											// response
			warningMessages[0] = "Please Retry"; // Default warning message

			// Obtain inputs from welcome GUI
			myWelcomeForm.setVisible(true);
			while (true) {
				Thread.sleep(100);
				if (welcomeInputs[0].equals("noInput") == false)
					break;
			}

			// Print user inputs
			for (int qq = 0; qq < 5; qq++)
				System.out.println(welcomeInputs[qq]);

			// Perform actions according to GUI inputs
			if (welcomeInputs[0].equals("Login") == true)
				loginResult = Login(welcomeInputs, warningMessages);
			if (welcomeInputs[0].trim().equals("Register") == true)
				Register(welcomeInputs, warningMessages);
			if (welcomeInputs[0].trim().equals("FPass") == true)
				ForgotPassword(welcomeInputs, warningMessages);
			if (welcomeInputs[0].trim().equals("SQn") == true)
				SecretQuestion(welcomeInputs, warningMessages);

			// Hide welcome GUI and display warning message
			myWelcomeForm.setVisible(false);
			myWelcomeForm.DisplayWarning(warningMessages[0]);
		}

		// Start threads to check for login timeouts and status change of friends
		System.out.println("Begin internal client play");
		checkLogin[0] = true;
		this.ClientPlay();
	}

	
	public static boolean Login(String welcomeInputs[],
			String warningMessages[]) {
		
		// Format of input:
		// welcomeInputs[1] is username
		// welcomeInputs [2] is password
		
		// Variables used to manage login inputs
		String loginSentence, loginRec, levelRec;
		Socket LoginSocket;
		boolean loginResult = false;
		DataOutputStream outToServer;
		MessageDigest mdEnc;
		
		
		try {
		
			// Create socket connection with server
			System.out.println("Login socket: " + serverIP + " "
					+ serverConnectPort);
			LoginSocket = new Socket(serverIP, serverConnectPort);
			LoginSocket.setReceiveBufferSize(2000);
			outToServer = new DataOutputStream(LoginSocket.getOutputStream());
			System.out.println("Buffer size: "
					+ LoginSocket.getReceiveBufferSize());

			// MD5 encryption of password
			mdEnc = MessageDigest.getInstance("MD5");
			mdEnc.update(welcomeInputs[2].getBytes(), 0,
					welcomeInputs[2].length());
			System.out.println("Input String: " + welcomeInputs[2]);
			welcomeInputs[2] = new BigInteger(1, mdEnc.digest()).toString(16);
			System.out.println("Encrypted String: " + welcomeInputs[2]);

			// Send login information to server
			loginSentence = "Login_" + welcomeInputs[1] + "_"
					+ welcomeInputs[2];
			outToServer.writeBytes(loginSentence + '\n');
			System.out.println("Login sentence " + loginSentence);
			loginRec = ReceiveData(LoginSocket, "Login_", new BufferedReader(
					new InputStreamReader(LoginSocket.getInputStream())));

			// Interprets response from user
			// XX Remember to add timeout
			if (loginRec.equals("Login_Failed") == true) {
				System.out.println("Failed to login");
				warningMessages[0] = "Failed to login";
			}

			if (loginRec.equals("Login_Success") == true) {
				
				// Displays success message to usre
				System.out.println("Login Successful!");
				warningMessages[0] = "Login Successful!";
				loginResult = true;
				myClientPrimaryData.UserName = welcomeInputs[1];
				
				// Receives level information
				levelRec = null;
				System.out.println("Before receiving level");
				levelRec = ReceiveData(
						LoginSocket,
						"Level_",
						new BufferedReader(new InputStreamReader(LoginSocket
								.getInputStream())));
				System.out.println("Received " + levelRec);
				myClientPrimaryData.level = Integer.parseInt(levelRec.trim()
						.substring(6));
				
				// Receives and displays friend information
				ReceiveFriendData(LoginSocket);
				DisplayFriendData();
			}

			LoginSocket.close();
			return (loginResult);
		}

		catch (IOException e) {
			System.out.println("Error creating login socket");
		}

		catch (NoSuchAlgorithmException e) {
			System.out.println("Login function: MD5 Error");
		}

		return loginResult;
	}

	
	public static void Register(String welcomeInputs[],
			String warningMessages[]) {
		
		// Format of user input
		// welcomeInputs [1] is username
		// welcomeInputs [2] is password
		// welcomeInputs [3] is secret question
		// welcomeInputs [4] is secret answer
		
		
		// Variables used to facilitate registration
		String reply;
		String registerSentence;
		Socket RegisterSocket;
		DataOutputStream outToServer;
		MessageDigest mdEnc;
		warningMessages[0] = "";

		
		System.out.println("Beginning registration check");
		try {
		
			// Check for validity of inputs and break if invalid
			warningMessages[0] += checkInput(welcomeInputs[1], "Username")
					+ checkInput(welcomeInputs[2], "Password")
					+ PWStrengthChecker(welcomeInputs[2])
					+ checkInput(welcomeInputs[3], "Secret Question")
					+ checkInput(welcomeInputs[4], "Secret Answer");
			if (warningMessages[0].equals("") == false) {
				warningMessages[0] = "Registration Unsuccessful. "
						+ warningMessages[0];
				return;
			}

			// MD5 encryption of password
			mdEnc = MessageDigest.getInstance("MD5");
			mdEnc.update(welcomeInputs[2].getBytes(), 0,
					welcomeInputs[2].length());
			System.out.println("Input String: " + welcomeInputs[2]);
			welcomeInputs[2] = new BigInteger(1, mdEnc.digest()).toString(16);
			System.out.println("Encrypted String: " + welcomeInputs[2]);

			
			// Create output sockets
			System.out.println("Registration socket " + serverIP + " "
					+ serverConnectPort);
			RegisterSocket = new Socket(serverIP, serverConnectPort);
			outToServer = new DataOutputStream(RegisterSocket.getOutputStream());


			// Send registration data to server
			System.out.println(serverIP.toString());
			registerSentence = "Register_" + welcomeInputs[1] + "_"
					+ welcomeInputs[2] + "_" + InetAddress.getLocalHost().getHostAddress().toString()
					+ "_" + welcomeInputs[3] + "_" + welcomeInputs[4];
			System.out.println("Register Sentence " + registerSentence);
			outToServer.writeBytes(registerSentence + '\n');
			System.out.println("Sent register sentence");

			
			// Obtain reply from server
			reply = ReceiveData( RegisterSocket, "Register_",
					new BufferedReader(new InputStreamReader(RegisterSocket
							.getInputStream())));
			
			
			// Interprets reply from server
			if (reply.equals("Register_Success") == true) {
				System.out.println("Registration completed successfully.");
				warningMessages[0] = "Registration successful!";
				myClientPrimaryData.UserName = welcomeInputs[1];
			} else
				warningMessages[0] = "Username exists. Please retry registration";

			// Close registration session
			RegisterSocket.close();

		} catch (IOException e) {
			System.out.println("Error creating register socket");
		}

		catch (NoSuchAlgorithmException e) {
			System.out.println("Login function: MD5 Error");
		}

	}
	

	public static void ForgotPassword(String welcomeInputs[],
			String warningMessages[]) {
		
		// Format of input
		// welcomeInputs [1] is username
		// welcomeInputs [2] is secret answer
		// welcomeInputs [3] is new password
		
		try {
			
			// Variables used to facilitate forgot password session
			String fPassSend, fPassRec;
			Socket FPassSocket;
			DataOutputStream outToServer;
			warningMessages[0] = ""; // Set default value
			MessageDigest mdEnc;

			
			// Check for validity of password and break if invalid
			warningMessages[0] += checkInput(welcomeInputs[3], "Password")
					+ PWStrengthChecker(welcomeInputs[3]);
			if (warningMessages[0].equals("") == false) {
				warningMessages[0] = "Password Change Unsuccessful. "
						+ warningMessages[0];
				return;
			}

			
			// MD5 Encryption
			mdEnc = MessageDigest.getInstance("MD5");
			mdEnc.update(welcomeInputs[3].getBytes(), 0,
					welcomeInputs[3].length());
			System.out.println("Input String: " + welcomeInputs[3]);
			welcomeInputs[3] = new BigInteger(1, mdEnc.digest()).toString(16);
			System.out.println("Encrypted String: " + welcomeInputs[3]);

			
			// Establish connection with fPassDthread in server
			FPassSocket = new Socket(serverIP, serverConnectPort);
			outToServer = new DataOutputStream(FPassSocket.getOutputStream());

			
			// Send username, secret answer and password to server
			fPassSend = "FPass_" + welcomeInputs[1] + "_" + welcomeInputs[2]
					+ "_" + welcomeInputs[3];
			outToServer.writeBytes(fPassSend + '\n');

			
			// Set default warning message
			warningMessages[0] = "Unknown error occured.";

			
			// Received from server
			fPassRec = ReceiveData(FPassSocket, "FPass_", new BufferedReader(
					new InputStreamReader(FPassSocket.getInputStream())));

			
			// Interprets reply from server
			if (fPassRec.equals("FPass_UserAbsent")) {
				System.out.println("Username does not exist, please register");
				warningMessages[0] = "This user does not exist. Please retry.";
			}

			if (fPassRec.equals("FPass_WrongAnswer")) {
				System.out.println("Secret answer is wrong");
				warningMessages[0] = "Your secret answer is wrong.";
			}

			if (fPassRec.equals("FPass_PasswordChanged")) {
				System.out.println("Password has been changed");
				warningMessages[0] = "Password change successful!";
			}

			// Close forgot password session
			FPassSocket.close();
			
		} catch (Exception e) {
			System.out.println("Error in ForgotPassword method");
		}
	}

	public static void SecretQuestion(String welcomeInputs[],
			String warningMessages[]) {
		
		// Format of input from user
		// welcomeInputs [1] is username
		
		try {
			
			// Variables used to remind user of his/her secret question
			String SQnUsername, SQnRec;
			Socket SQnSocket;
			DataOutputStream outToServer;

			
			// Creates connection with server
			SQnSocket = new Socket(serverIP, serverConnectPort);
			outToServer = new DataOutputStream(SQnSocket.getOutputStream());

			
			// Sends user input to server
			SQnUsername = "SQn_" + welcomeInputs[1];
			outToServer.writeBytes(SQnUsername + '\n');

			
			// Obtains reply from user
			SQnRec = ReceiveData(SQnSocket, "SecretQn_", new BufferedReader(
					new InputStreamReader(SQnSocket.getInputStream())));

			
			// Interprets server reply
			if (SQnRec.equals("SecretQn_Userabsent")) {
				System.out.println("Username does not exist, please register");
				warningMessages[0] = "Username does not exist, please register";
			}

			else {
				System.out.println("Secret Question: " + SQnRec);
				warningMessages[0] = "Secret Question: " + SQnRec.substring(9);
			}

			
			// End secret question session
			SQnSocket.close();
			
		} catch (Exception e) {
			System.out.println("Error in Secret Question method");
		}
	}
	
	
	// This function provides a template for receiving data on a given socket, and discarding
	// any packets that don't meet the required syntax
	public static String ReceiveData(Socket ReceiveSocket, String syntaxCheck,
			BufferedReader inFromServer) {
		
		String RecData = null;
		int tries = 0;

		try {
			
			ReceiveSocket.setSoTimeout(30000); // Set a 30 second timeout
												// threshold for obtaining reply
			
			for (tries = 0; tries < 5; tries++) { // 5 tries for obtaining correct syntax packet
				RecData = inFromServer.readLine();
				System.out.println("Received: x" + RecData + "x");
				if (RecData == null || RecData.isEmpty()) {
					continue;
				}

				// If received data does not follow the syntax required, search
				// for another input
				if (RecData.length() < syntaxCheck.length())
					continue;
				if (syntaxCheck.length() != 0
						&& RecData.substring(0, syntaxCheck.length()).equals(
								syntaxCheck) == false)
					continue;

				ReceiveSocket.setSoTimeout(0); // Remove timeout
				return RecData;
			}

			ReceiveSocket.setSoTimeout(0); // Remove timeout
			return (null);
		} catch (IOException e) {
			System.out.println("Error creating receiving socket - ReceiveData");
		}
		return RecData;
	}

	
	// Receive friend data from user, following login
	public static void ReceiveFriendData(Socket ReceiveSocket) {
		
		// Variables used to store and manage input data
		String FNum, FUsernames, FIPs, FLoginStatus, FLevel, RNum, RUsernames, RIPs, RLoginStatus, RLevel;
		String tempArr[] = new String[100];
		int i;
		BufferedReader in;
		String receivedString;

		try {

			in = new BufferedReader(new InputStreamReader(
					ReceiveSocket.getInputStream()));
			receivedString = ReceiveData(ReceiveSocket, "Friends_", in);

			// Receive friend data

			System.out.println("FNUM1");
			FNum = receivedString.substring(8); //  Number of friends
			System.out.println("FNUM2" + FNum);
			FUsernames = in.readLine().substring(6); // Friend names
			System.out.println("FNUM3" + FUsernames);
			FIPs = in.readLine().substring(3); // Friend IPs
			System.out.println("FNUM4" + FIPs);
			FLoginStatus = in.readLine().substring(7); // Friend login status
			System.out.println("FNUM5" + FLoginStatus);
			FLevel = in.readLine().substring(6); // Friend level
			System.out.println("FNUM6" + FLevel);

			// Receive rest data
			RNum = in.readLine().substring(5); // Number of public members
			System.out.println("FNUM7" + RNum);
			RUsernames = in.readLine().substring(6); // Remaining (public) members' names
			System.out.println("FNUM8" + RUsernames);
			RIPs = in.readLine().substring(3); // IPSs of other users
			System.out.println("FNUM9" + RIPs);
			RLoginStatus = in.readLine().substring(7); // Remaining users' statuses
			System.out.println("FNUM10" + RLoginStatus);
			RLevel = in.readLine().substring(6); // Remaining users' levels
			System.out.println("FNUM11" + RLevel);

			// Store friend and rest count data
			myClientPrimaryData.FNumber = Integer.parseInt(FNum);
			System.out.println("Temp arr X. FNumber is "
					+ myClientPrimaryData.FNumber);
			myClientPrimaryData.RNumber = Integer.parseInt(RNum);
			System.out.println("Temp arr Y. RNumber is "
					+ myClientPrimaryData.RNumber);

			// Store friend data in myClientPrimaryData
			populateArr(myClientPrimaryData.FUsernamesArr,
					FUsernames.split("_"));
			System.out.println("Post populate");
			populateArr(myClientPrimaryData.FIPsArr, FIPs.split("_"));
			tempArr = FLoginStatus.split("_");
			System.out.println("Post populate 2 " + tempArr.length);
			for (i = 0; i < tempArr.length; i++)
				myClientPrimaryData.FLoginStatusArr[i] = Integer
						.parseInt(tempArr[i]);
			tempArr = FLevel.split("_");
			for (i = 0; i < tempArr.length; i++)
				myClientPrimaryData.FLevelArr[i] = Integer.parseInt(tempArr[i]);

			// Store rest data in myClientPrimaryData
			populateArr(myClientPrimaryData.RUsernamesArr,
					RUsernames.split("_"));
			System.out.println("Post populate 3");

			populateArr(myClientPrimaryData.RIPsArr, RIPs.split("_"));
			System.out.println("Post populate 4");

			tempArr = RLoginStatus.split("_");
			for (i = 0; i < tempArr.length; i++) {
				System.out.println(tempArr[i]);
				myClientPrimaryData.RLoginStatusArr[i] = Integer
						.parseInt(tempArr[i]);
			}
			System.out.println("Post populate 5");

			tempArr = RLevel.split("_");
			for (i = 0; i < tempArr.length; i++) {
				System.out.println(tempArr[i]);
				myClientPrimaryData.RLevelArr[i] = Integer.parseInt(tempArr[i]);
			}
			System.out.println("Ended receive friend data");

		} catch (IOException e) {
			System.out
					.println("Error generating buffered reader in ReceiveFriendData");
		}
	}

	public static void populateArr(String dataarr[], String temparr[]) {
		int datalength = temparr.length;
		if (temparr.length > dataarr.length) {
			System.out
					.println("Error in received data length. Excess length will be discarded");
			datalength = dataarr.length;
		}
		for (int i = 0; i < datalength; i++)
			dataarr[i] = temparr[i];
	}

	public static void DisplayFriendData() {
		int i;

		if (myClientPrimaryData.FNumber == 0)
			System.out.println("None of your friends are online");
		else {
			System.out.println("List of Online Friends:");
			for (i = 0; i < myClientPrimaryData.FNumber; i++) {
				if (myClientPrimaryData.FLoginStatusArr[i] == 1)
					System.out.println(myClientPrimaryData.FUsernamesArr[i]);
			}

			System.out.println("\nList of Offline Friends");
			for (i = 0; i < myClientPrimaryData.FNumber; i++) {
				if (myClientPrimaryData.FLoginStatusArr[i] == 0)
					System.out.println(myClientPrimaryData.FUsernamesArr[i]);
			}
		}

		if (myClientPrimaryData.RNumber == 0)
			System.out.println("\nNo public members are online currently");
		else {
			System.out.println("\nList of Online Public Members");
			for (i = 0; i < myClientPrimaryData.RNumber; i++) {
				if (myClientPrimaryData.RLoginStatusArr[i] == 0)
					System.out.println(myClientPrimaryData.RUsernamesArr[i]);
			}

			System.out.println("\nList of Offline Public Members");
			for (i = 0; i < myClientPrimaryData.RNumber; i++) {
				if (myClientPrimaryData.RLoginStatusArr[i] == 0)
					System.out.println(myClientPrimaryData.RUsernamesArr[i]);
			}
		}
	}

	public static String getUserName() {
		return myClientPrimaryData.UserName;
	}

	public static String checkInput(String input, String type) {

		if ((input.length() > 20 || input.length() < 6)
				&& (type.equals("Username") == true || type.equals("Password") == true))
			return (type + " should be between 6 and 20 characters. ");

		if (input.contains(" ") == true
				&& (type.equals("Secret Question") == false))
			return (type + " should not contain spaces. ");

		if (input.contains("_") == true || input.contains(".") == true)
			return (type + "should not contain '.' and '_'. ");

		return "";
	}

	public static String PWStrengthChecker(String PW) {
		int lowerCase = 0, upperCase = 0, number = 0, specialChar = 0, i;
		int points = 0;

		if (PW.length() >= 8)
			points += 5;
		for (i = 0; i < PW.length(); i++) {
			if (PW.charAt(i) >= 'a' && PW.charAt(i) <= 'z')
				lowerCase++;
			else if (PW.charAt(i) >= 'A' && PW.charAt(i) <= 'Z')
				upperCase++;
			else if (PW.charAt(i) >= '0' && PW.charAt(i) <= '9')
				number++;
			else if (PW.charAt(i) == '@' || PW.charAt(i) == '#'
					|| PW.charAt(i) == '$' || PW.charAt(i) == '%'
					|| PW.charAt(i) == '^' || PW.charAt(i) == '&')
				specialChar++;
		}

		if (lowerCase > 0 && upperCase > 0)
			points += 4;

		if (number < PW.length() / 2 && number > 0)
			points += number;
		else if (number > 0)
			points += 3;

		if (specialChar <= 3 && specialChar > 0)
			points += (specialChar * 2);
		else
			points += 3;

		if (points < 8)
			return ("Password is too weak. ");
		else
			return ("");
	}

	public void ClientPlay() {

		// Start hello thread, which sends regular UDP updates
		try {
			System.out.println("Starting hello thread with IP");
			myHelloThread = new HelloThread(helloUDPPort,
					myClientPrimaryData.UserName, serverIP, checkLogin);
			myHelloThread.start();
		} catch (Exception e) {
			System.out.println("Error in Hello/Terminate Initiation Method");
		}

		/*
		 * // Create input and output streams to communicate with server try {
		 * Socket sock = new Socket(serverIP, serverConnectPort);
		 * sock.setReceiveBufferSize(2000); outToServer = new
		 * DataOutputStream(sock.getOutputStream());
		 * //outToServer.writeBytes("ActiveClient_Initiate"); inFromServer = new
		 * BufferedReader(new InputStreamReader( sock.getInputStream())); }
		 * catch (Exception e) { System.out .println(
		 * "Could not print create active client streams in ClientPlay method");
		 * }
		 */

		// Starts status change thread
		try {
			System.out.println("Starting status change thread");
			mySCThread = new StatusChangeThread(myClientPrimaryData, statuschangeUDPPort,
					serverIP);
			mySCThread.start();
		} catch (Exception e) {
			System.out.println("Error in Status Change Thread");
		}
	}
}
