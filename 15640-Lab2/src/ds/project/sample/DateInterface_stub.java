package ds.project.sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.UUID;

import ds.rmi.util.Command;
import ds.rmi.util.RMIMessage;
import ds.rmi.util.RemoteMethod;

public class DateInterface_stub implements DateInterface{
	String host;
	int port;
	String objKey;
	
	public DateInterface_stub(String h, Integer p) {
		host = h;
		port = p;
		objKey = UUID.randomUUID().toString();
	}

	@Override
	public Date getDateMinusDays(Integer days) {
		Object[] params = new Object[1];
		params[0] = days;
		Class<?>[] paramTypes = new Class<?>[1];
		paramTypes[0] = Integer.class;
		System.out.println(objKey);
		RemoteMethod rm = new RemoteMethod("ds.project.sample.DateObject", "getDateMinusDays", objKey, paramTypes, params);
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
