package ds.rmi.rmiregistry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import ds.rmi.util.Constants;
import ds.rmi.util.RemoteObject;

public class Registry extends Thread {

	public static ConcurrentHashMap<String, RemoteObject> objectRegistry = new ConcurrentHashMap<String, RemoteObject>();
	static ServerSocket server = null;
	ObjectInputStream inStream = null;
	ObjectOutputStream outStream = null;

	@Override
	public void run() {
		try {
			server = new ServerSocket(Constants.REGISTRY_PORT);
			while (true) {
				Socket client = server.accept();
				System.out.println("Client connected " + client.getInetAddress());
				RegistryRequestHandler handler = new RegistryRequestHandler(client);
				new Thread(handler).start();
			}
			//server.close();
		} catch (IOException e) {
			System.out.println("Error communicating with client "
					+ e.getMessage());
		}
	}
	
	public static void printHashMap() {
		for(String key : objectRegistry.keySet()) {
			System.out.println("Key: " + key + " and value: " + objectRegistry.get(key).getServiceName());
		}
	}

	public static void main(String args[]) {
		Registry registry = new Registry();
		System.out.println("Registry started");
		registry.start();
	}
}
