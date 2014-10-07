package ds.rmi.dispatcher;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import ds.rmi.util.Constants;

public class Dispatcher extends Thread {

	public static ConcurrentHashMap<String, Object> objectList = new ConcurrentHashMap<String, Object>();
	static ServerSocket server = null;
	ObjectInputStream inStream = null;
	ObjectOutputStream outStream = null;

	@Override
	public void run() {
		try {
			server = new ServerSocket(Constants.DISPATCHER_PORT);
			while (true) {
				Socket client = server.accept();
				DispatcherRequesthandler handler = new DispatcherRequesthandler(client);
				new Thread(handler).start();
				//server.close();
			}
		} catch (IOException e) {
			System.out.println("Error communicating with client "
					+ e.getMessage());
		}
	}

	public static void main(String args[]) {
		Dispatcher dispatcher = new Dispatcher();
		System.out.println("Dispatcher started");
		dispatcher.start();
	}
}
