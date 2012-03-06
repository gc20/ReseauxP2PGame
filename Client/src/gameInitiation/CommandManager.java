// This file accepts the input string from sockets and executes the intended function(s)
package gameInitiation;


import java.io.*;

import data.ClientPrimaryData;

public class CommandManager implements Runnable {

    private BufferedReader input;
    private ListModel parent;
    private Thread t;
    private boolean runThread;
    ClientPrimaryData myClientPrimaryData;


    public CommandManager(BufferedReader instream, ListModel l,  ClientPrimaryData myClientPrimaryData) {
        input = instream;
        parent = l;
        runThread = true;
        t = new Thread(this, "New Thread");
        t.start();
        this.myClientPrimaryData = myClientPrimaryData;
        
    }

    public void run() {
        while (runThread) {
            // input events will be read in this loop asynchronously,
            // and appropriate actions should be taken by using the provided
            // ConnectionManager and/or ListModel
            try {
                String s = input.readLine().trim();
                System.err.println(s);
                if (s.startsWith("a_")) { //'a' stands for addPlayer
                    char Type = s.charAt(s.length()-1);
                    if(Type == '1') { //If Type==1, add to team 1
                    /*	Pass the user name to the function to add the user to team list 
                    	and open socket to the existing players in the chat*/                    	
                    	parent.addPlayerToTeam1(s.substring(2, s.length()-2)); 
                        System.err.println("Adding player to team1");
                    }
                    else if(Type == '2') {//If Type==2, add to team 2
                    	/*	Pass the user name to the function to add the user to team list 
                    	and open socket to the existing players in the chat*/   
                        parent.addPlayerToTeam2(s.substring(2, s.length()-2));
                        System.err.println("Adding player to team2");
                    }
                } else if (s.startsWith("c_")) {
                    parent.showInChatWindow(s.substring(2));
                } else if (s.startsWith("cg_")) {
                	// If its a group chat (both team1 and team2) send it to the common Chat Area
                    parent.showInChatWindow(s.substring(3));
                } else if (s.startsWith("ct_")) {
                	// If its a intra-team chat (either team1 or team2) send it to the Team Chat Area
                    parent.showInChatWindow2(s.substring(3));
                } else if (s.startsWith("r_")) { 
                	char Type = s.charAt(s.length()-1);
                    if(Type == '1') { //If Type==1, add to team 1
                    /*	Pass the user name to the function to add the user to team list 
                    	and open socket to the existing players in the chat*/                    	
                    	parent.removePlayerFromTeam1(s.substring(2, s.length()-2)); 
                        System.err.println("Removing" + s.substring(2, s.length()-2) + "from team 1");
                    }
                    else if(Type == '2') {//If Type==2, add to team 2
                    	/*	Pass the user name to the function to add the user to team list 
                    	and open socket to the existing players in the chat*/   
                        parent.removePlayerFromTeam2(s.substring(2, s.length()-2));
                        System.err.println("Removing" + s.substring(2, s.length()-2) + "from team 1");
                    }
                } else if (s.startsWith("AddedYou_")) {
                	//To add a person to Friends List
                	parent.addFriendToArray(s.substring(9));
                } else if (s.startsWith("RemovedYou_")) {
                	//To remove a person from Friends list
                	parent.removeFriendFromArray((s.substring(11)));
                } else if (s.startsWith("StartGame_")) {
                	//Interprets the message as a Game Initialisation string! 
                	System.out.println ("Going to start game");
                	myClientPrimaryData.gameRequests.add(s);
                	//myClientPrimaryData.gameRequests.add(s);
                    // parent.AddRequest(s.substring(10)); 
                }else if(myClientPrimaryData.myGameManage != null) 
            	{
                	System.out.println ("Obtained in comm-manager " + s);
            		if (s.startsWith("Attack_")) {
            			//Interpret as Attack action
            			myClientPrimaryData.myGameManage.attackString(s);
            		} else if (s.startsWith("Teamdefence_")) {
            			//Interpret as Defend action
            			myClientPrimaryData.myGameManage.teamdefenceString(s);
            		} else if (s.startsWith("Score_")) {
            			myClientPrimaryData.myGameManage.scoreString(s);
            		} else if (s.startsWith("Oppword_")) {
            			myClientPrimaryData.myGameManage.oppwordString(s);
            		}
            	}

            } catch (Exception e) {
                //System.err.println("Client thread ended");
                return;
            }
        }
    }
    
    //Ending the run thread!
    public void end() {
        runThread = false;
        try {
            input.close();
            t.interrupt();
            t.join();
        } catch (Exception e) {
        }
    }
}
