package dsrmi.rmiregistry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestHandler implements Runnable{
private Socket client;
	
	public RequestHandler(Socket client){
		this.client = client;
	}	
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
