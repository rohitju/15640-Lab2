package ds.project.sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;

import ds.rmi.util.Command;
import ds.rmi.util.RMIMessage;
import ds.rmi.util.RemoteMethod;

public class Time_stub implements TimeInterface {

	String host;
	int port;
	
	public Time_stub(String h, Integer p) {
		host = h;
		port = p;
	}
	
	@Override
	public Date getCurrentDate() {
		RemoteMethod rm = new RemoteMethod("ds.project.sample.Time", "getCurrentDate", new ArrayList<Object>());
		RMIMessage message = new RMIMessage(Command.EXECUTE, rm);
		RMIMessage reply = null;
		ObjectInputStream inStream = null;
		ObjectOutputStream outStream = null;
		Date date = null;
		try {
			System.out.println(host + " " + port);
			Socket s = new Socket(host, port);			
			outStream = new ObjectOutputStream(s.getOutputStream());
			inStream = new ObjectInputStream(s.getInputStream());
			outStream.writeObject(message);
			System.out.println("Written message to dispatcher");
			reply = (RMIMessage)inStream.readObject();
			System.out.println("reading message from dispatcher");
			if(reply != null) {
				if(reply.getCommand() == Command.RESULT){
					date = (Date) reply.getPayload();
				}
			}
			s.close();
		} catch (UnknownHostException e) {
			System.out.println("Time_stub: UnknownHostException: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Time_stub: IOException: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Time_stub: ClassNotFoundException: " + e.getMessage());
		}
		return date;
	}

}
