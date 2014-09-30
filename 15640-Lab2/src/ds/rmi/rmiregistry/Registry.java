package ds.rmi.rmiregistry;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RemoteObject;
import java.util.HashMap;

public class Registry {
	
	HashMap<String, RemoteObject> objectRegistry = new HashMap<String, RemoteObject>();
	
	public static void main(String args[]){
		try {
			ServerSocket server = new ServerSocket(Constants.port);
			while (true) {
				Socket client = server.accept();
				RequestHandler handler = new RequestHandler(client);
				new Thread(handler).start();
				
			}
		} catch (IOException e){
			System.out.println("Error communicating with client " + e.getMessage());
		}
	}
}
