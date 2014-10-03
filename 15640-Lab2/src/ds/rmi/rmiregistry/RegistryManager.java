package ds.rmi.rmiregistry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import ds.rmi.util.Command;
import ds.rmi.util.RMIMessage;
import ds.rmi.util.RemoteObject;

public class RegistryManager {
	String host;
	int port;

	public RegistryManager(String h, int p) {
		host = h;
		port = p;
	}
	
	public RemoteObject lookup(String serviceName) {
		ObjectInputStream inStream = null;
		ObjectOutputStream outStream = null;
		RemoteObject ro = null;
		try {
			Socket s = new Socket(host, port);
			RMIMessage message = new RMIMessage(Command.LOOKUP, serviceName);
			outStream = new ObjectOutputStream(s.getOutputStream());
			outStream.writeObject(message);
			inStream = new ObjectInputStream(s.getInputStream());
			RMIMessage reply = (RMIMessage)inStream.readObject();
			if(reply.getCommand() == Command.ROR){
				ro = (RemoteObject)reply.getPayload();
			}
			s.close();
		} catch (UnknownHostException e) {
			System.out.println("RegistryFinder: UnknownHostException: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("RegistryFinder: IOException: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("RegistryFinder: ClassNotFoundException: " + e.getMessage());
		}
		return ro;
	}
}
