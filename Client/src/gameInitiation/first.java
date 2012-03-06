package gameInitiation;

import data.ClientPrimaryData;

public class first {

	ClientPrimaryData myClientPrimaryData;
	public ListModel l;
	String serverIP;
	int serverConnectPort;
	
	public first(ClientPrimaryData myClientPrimaryData, String serverIP, int serverConnectPort) {
		this.myClientPrimaryData = myClientPrimaryData;
		this.serverIP = serverIP;
		this.serverConnectPort = serverConnectPort;
	}

	/**
	 * @param args
	 */
	public void gfirst() {
		myClientPrimaryData.startGame(serverIP, serverConnectPort);
		l = myClientPrimaryData.l;
	}

}
