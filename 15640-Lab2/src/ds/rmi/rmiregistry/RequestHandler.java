package ds.rmi.rmiregistry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestHandler extends Thread{
private Socket client;
	
	public RequestHandler(Socket client){
		this.client = client;
	}
	
	@Override
	public void run(){
		try {
			ObjectInputStream inStream = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream outStream = new ObjectOutputStream(client.getOutputStream());
			Object obj = inStream.readObject();
		} catch (IOException e) {
			System.out.println("Error communicating with client");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Error reading object");
			e.printStackTrace();
		}
		
		
	}
}
