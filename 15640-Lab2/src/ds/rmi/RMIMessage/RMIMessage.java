package ds.rmi.RMIMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

import ds.rmi.rmiregistry.Constants;

public class RMIMessage extends Thread implements Serializable {
	
	private static final long serialVersionUID = 1L;
	Command cmd; // bind,lookup,list,rebind,unbind
	String host;
	int port;
	Socket client;
	ObjectInputStream inStream = null;
	ObjectOutputStream outStream = null;

	public RMIMessage(Command c, String h, int p) {
		cmd = c;
		host = h;
		port = p;
	}

	public void sendMessage() {
		this.start();
	}

	@Override
	public void run() {
		try {
			client = new Socket(host, Constants.port);
			inStream = new ObjectInputStream(client.getInputStream());
			outStream = new ObjectOutputStream(client.getOutputStream());
			switch(cmd) {
			case BIND:
				break;
			case LOOKUP:
				break;
			case LIST:
				break;
			case REBIND:
				break;
			case UNBIND:
				break;
			}
		} catch (UnknownHostException e) {
			System.out.println("UnknownHostException occurred: "
					+ e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException occurred: " + e.getMessage());
		}
	}
}
