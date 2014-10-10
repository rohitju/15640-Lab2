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
import ds.rmi.util.RemoteException;
import ds.rmi.util.RemoteMethod;
import ds.rmi.util.RemoteObject;

public class Time_stub implements Time {

	String host;
	int port;
	String objKey;
	
	public Time_stub(String h, Integer p) {
		host = h;
		port = p;
		objKey = UUID.randomUUID().toString();
	}
	
	@Override
	public Date getCurrentDate() throws RemoteException{
		RemoteMethod rm = new RemoteMethod("ds.project.sample.TimeServer", "getCurrentDate", objKey, null, null);
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

	@Override
	public Date getDateMinusDays(Integer days) {
		Object[] params = new Object[1];
		params[0] = days;
		Class<?>[] paramTypes = new Class<?>[1];
		paramTypes[0] = Integer.class;
		RemoteMethod rm = new RemoteMethod("ds.project.sample.TimeServer", "getDateMinusDays", objKey, paramTypes, params);
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
	
	@Override
	public DateInterface_stub getDateObject() {
		RemoteMethod rm = new RemoteMethod("ds.project.sample.TimeServer", "getDateObject", objKey, null, null);
		RMIMessage message = new RMIMessage(Command.EXECUTE, rm);
		RMIMessage reply = null;
		ObjectInputStream inStream = null;
		ObjectOutputStream outStream = null;
		DateObject date = null;
		RemoteObject dobj = null;
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
					dobj = (RemoteObject) reply.getPayload();
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
		
		return (DateInterface_stub) dobj.localize();
	}

}
