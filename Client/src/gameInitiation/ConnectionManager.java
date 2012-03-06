package gameInitiation;


import java.util.Hashtable;
import java.net.*;
import java.io.*;
import java.util.Collection;
import java.util.Iterator;

import data.ClientPrimaryData;

public class ConnectionManager implements Runnable {

    private Hashtable<String, ConnectionDetails> sockList;
    private ServerSocket servSocket;
    private ListModel parent;
    private Thread t;
    private boolean endThread;
    int randomPort = 13425;
    ClientPrimaryData myClientPrimaryData;

    public ConnectionManager(ListModel l, ClientPrimaryData myClientPrimaryData) {
    	System.out.println ("In connmgr constructor");
        sockList = new Hashtable<String, ConnectionDetails>();
        parent = l;
        servSocket = null;
        endThread = false;
        this.myClientPrimaryData = myClientPrimaryData;
        try {
            servSocket = new ServerSocket(randomPort);
        } catch (Exception e) {
            parent.ShowError("Opening server socket failed");
        }
        t = new Thread(this, "Server Thread");
        t.start();
        System.out.println ("Exited connmgr constructor");
        
    }

    public void run() {
        while (true) {
            Socket connSocket = null;
            try {
                System.out.println ("In CM Thread start"); //Debug
            	connSocket = servSocket.accept(); // To accept a socket opening req
            	//To get the input string when socket opened on the random port (defined above)
                BufferedReader instream = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
                //String below gives the type (team number) of the intended receiver and the username of the sender 
            	String peerNameAndType = instream.readLine();
                String peerName = peerNameAndType.substring(0, peerNameAndType.length()-2);
                //Get the type or the team number of the intended receiver
            	char Type = peerNameAndType.charAt(peerNameAndType.length()-1);
                System.err.println(peerName);
                //Starting a new instance of CommandManager
                CommandManager c = new CommandManager(instream, parent, myClientPrimaryData);
                DataOutputStream outstream = new DataOutputStream(connSocket.getOutputStream());
                //Appending the new socket to sockList
                sockList.put(peerName, new ConnectionDetails(connSocket, c, outstream));
                System.out.println ("Type " + Type);
                if(Type == '1') {
                	//If the team number (of the rx) is 1, add the peer (tx) in team1
                    parent.addPlayerToTeam1(peerName); 
                }
                else if(Type == '2') {
                	//If the team number (of the rx) is 2, add the peer (tx) in team2
                    parent.addPlayerToTeam2(peerName);
                }
            } catch (IOException e) {
                if(endThread) {
                    System.err.println("Exiting server thread");
                    return;
                }
            }
        }
    }

    public String getMyIP() { //Function to fetch the IP address of the local host (user!)
        return servSocket.getInetAddress().getHostAddress();
    }
//    public void addUser(String user) {
//        Socket clientSocket = null;
//        try {
//            clientSocket = new Socket(parent.getPlayerIP(user), randomPort);
//            sockList.put(user, clientSocket);
//        }
//        catch (Exception e) {
//            parent.ShowError("Could not connect to " + user);
//        }
//    }

    /**
     * Connects to the specified User, opens input and output streams to it
     * and adds it to its private database
     * @param User - the Username of the User to connect to
     */
    public boolean ConnectTo(String User, int type) { 
        Socket connSocket = null;
        try {
            connSocket = new Socket(parent.getPlayerIP(User), randomPort);
            BufferedReader instream = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
            CommandManager c = new CommandManager(instream, parent, myClientPrimaryData);
            DataOutputStream outstream = new DataOutputStream(connSocket.getOutputStream());
            //Writes to the intended receiver the username of self (tx) and the type (team number) it should add it to
            outstream.writeBytes(parent.UserName + "_" + type + "\n"); 
            sockList.put(User, new ConnectionDetails(connSocket, c, outstream));
            
        } catch (IOException e) {
            parent.ShowError("Could not connect to " + User + " at IP " + parent.getPlayerIP(User));
            return false;
        }
        return true;
    }

    public void DisconnectFrom(String player) { //Invoked if a player goes offline or is removed from a team
        ConnectionDetails conn = sockList.get(player);
        try {
            conn.out.close();
            conn.cmd.end();
            conn.socket.close();
        } catch (Exception e) {
        }
        sockList.remove(player);
    }

    /**
     * Sends Data to User
     * @param User
     * @param Data
     */
    public void SendData(String User, String Data) { //Function used when we want to send data string to a particular user
    	System.out.println ("In send data");
    	System.err.println(sockList.toString());
    	ConnectionDetails conn = sockList.get(User);
    	System.out.println ("Is conn null?");
        if (conn != null) {
            try {
            	System.out.println ("Conn is not null " + Data + " " + User);
                conn.out.writeBytes(Data);
                System.out.println ("After write bytes");
                System.err.println(Data);
            } catch (Exception e) {
                parent.ShowError("Could not send message to " + User);
            }
        }
    }

    /**
     * Sends Data to all connected users
     * @param Data - data to send
     */
    public void SendDataToAll(String Data) { //Function used to send data to all players in the sockList
        Iterator <String> i = sockList.keySet().iterator();
        while (i.hasNext()) {
            SendData(i.next(), Data);
        }
    }

    /**
     * Ends the server thread alongwith all reading client threads and also
     * clears up all the data structures associated with this thread
     */
    public void endThread() { //Function to endThread and close all the sockets opened on this client!!
        endThread = true;
        try {
            servSocket.close();
            t.join();
        } catch (Exception e) {
        }
        System.err.println("Ended server port and thread");
        Collection <ConnectionDetails> conns = sockList.values();
        Iterator <ConnectionDetails> i = conns.iterator();
        while(i.hasNext()) {
            ConnectionDetails c = i.next();
            try {
                c.cmd.end();
                c.socket.close();
            } catch (Exception e) {
                
            }
        }
        sockList.clear();
    }
}
