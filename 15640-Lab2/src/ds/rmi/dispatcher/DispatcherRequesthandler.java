package ds.rmi.dispatcher;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

import ds.rmi.util.Command;
import ds.rmi.util.RMIMessage;
import ds.rmi.util.RemoteMethod;

public class DispatcherRequesthandler extends Thread {
	private Socket client;
	private ObjectInputStream inStream = null;
	private ObjectOutputStream outStream = null;

	public DispatcherRequesthandler(Socket c) {
		client = c;
	}

	@Override
	public void run() {
		try {
			inStream = new ObjectInputStream(client.getInputStream());
			outStream = new ObjectOutputStream(client.getOutputStream());
			RMIMessage msg = (RMIMessage) inStream.readObject();
			if (msg.getCommand() == Command.EXECUTE) {
				System.out.println("received run");
				RemoteMethod rm = (RemoteMethod) msg.getPayload();
				String className = rm.getClassName();
				String methodName = rm.getMethodName();
				// TODO get parameters
				Class<?> c = Class.forName(className);
				Constructor<?> con = c.getConstructor();
				Object o = con.newInstance();
				Method method = o.getClass().getMethod(methodName);
				Object ret = method.invoke(o);
				RMIMessage result = new RMIMessage(Command.RESULT, ret);
				outStream.writeObject(result);
			}
			client.close();
		} catch (IOException e) {
			System.out
					.println("RequestHandler: IOException: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("RequestHandler: ClassNotFoundException: "
					+ e.getMessage());
		} catch (NoSuchMethodException e) {
			System.out.println("RequestHandler: No such method: "
					+ e.getMessage());
		} catch (SecurityException e) {
			System.out.println("RequestHandler: Security exception: "
					+ e.getMessage());
		} catch (InvocationTargetException e) {
			System.out.println("RequestHandler: Invocation error: "
					+ e.getMessage());
		} catch (IllegalAccessException e) {
			System.out.println("RequestHandler: Illegal access error: "
					+ e.getMessage());
		} catch (InstantiationException e) {
			System.out.println("RequestHandler: Instantiation error: "
					+ e.getMessage());
		}
	}
}
