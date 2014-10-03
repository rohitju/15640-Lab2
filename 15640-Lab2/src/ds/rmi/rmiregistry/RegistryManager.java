package ds.rmi.rmiregistry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

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
	
	private RMIMessage contactRegistry(RMIMessage message) {
		ObjectInputStream inStream = null;
		ObjectOutputStream outStream = null;
		RMIMessage reply = null;
		try {
			Socket s = new Socket(host, port);
			outStream = new ObjectOutputStream(s.getOutputStream());
			outStream.writeObject(message);
			inStream = new ObjectInputStream(s.getInputStream());
			reply = (RMIMessage)inStream.readObject();
			s.close();
		} catch (UnknownHostException e) {
			System.out.println("RegistryManager: UnknownHostException: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("RegistryManager: IOException: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("RegistryManager: ClassNotFoundException: " + e.getMessage());
		}
		return reply;
	}
	
	public void bind(RemoteObject ro){
		RMIMessage message = new RMIMessage(Command.BIND, ro);
		RMIMessage reply = contactRegistry(message);
		if(reply != null) {
			if(reply.getCommand() == Command.ACK){
				System.out.println("Remoteobject stored in registry");
			}
		}
	}
	
	public RemoteObject lookup(String serviceName) throws RemoteException{
		RMIMessage message = new RMIMessage(Command.LOOKUP, serviceName);
		RMIMessage reply = contactRegistry(message);
		RemoteObject ro = null;
		if(reply == null){
			throw new RemoteException("NOT GOOD");
		}
		else {
			if(reply.getCommand() == Command.ROR){
				ro = (RemoteObject)reply.getPayload();
			}
		}
		return ro;
	}
}
