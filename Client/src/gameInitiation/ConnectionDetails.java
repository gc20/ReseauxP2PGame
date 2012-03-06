package gameInitiation;


import java.io.*;
import java.net.*;

public class ConnectionDetails {

    Socket socket;
    CommandManager cmd;
    DataOutputStream out;

    public ConnectionDetails(Socket s, CommandManager c, DataOutputStream d) {
        socket = s;
        cmd = c;
        out = d;
    }
}
