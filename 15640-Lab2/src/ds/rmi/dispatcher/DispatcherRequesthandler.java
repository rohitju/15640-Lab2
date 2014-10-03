package ds.rmi.dispatcher;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import ds.rmi.util.Command;
import ds.rmi.util.RMIMessage;

public class DispatcherRequesthandler extends Thread {
	private Socket client;
	private ObjectInputStream inStream = null;
	private ObjectOutputStream outStream = null;

	public DispatcherRequesthandler(Socket c) {
		client = c;
	}

	@Override
	public void run() {
		try {
			inStream = new ObjectInputStream(client.getInputStream());
			outStream = new ObjectOutputStream(client.getOutputStream());
			RMIMessage msg = (RMIMessage) inStream.readObject();
			if(msg.getCommand() == Command.RUN) {
				//TODO
			}
		} catch (IOException e) {
			System.out
					.println("RequestHandler: IOException: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("RequestHandler: ClassNotFoundException: "
					+ e.getMessage());
		}

	}
}
