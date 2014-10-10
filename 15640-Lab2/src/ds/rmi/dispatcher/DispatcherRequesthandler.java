package ds.rmi.dispatcher;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.UUID;

import ds.rmi.util.Command;
import ds.rmi.util.Constants;
import ds.rmi.util.RMIMessage;
import ds.rmi.util.RemoteMethod;
import ds.rmi.util.RemoteObject;

public class DispatcherRequesthandler extends Thread {
	private Socket client;
	private ObjectInputStream inStream = null;
	private ObjectOutputStream outStream = null;

	private static boolean checkForRemote(Class<?>[] interfaces) {
		for(int i = 0; i < interfaces.length; i++) {
			if(Arrays.asList(interfaces[i].getInterfaces()).toString().contains("Remote"))
				return true;
		}
		return false;
	}

	private RemoteObject addRemoteObject(Object obj) {
		RemoteObject remoteRef = null;
		String objKey = UUID.randomUUID().toString();
		try {
			remoteRef = new RemoteObject(InetAddress.getLocalHost()
					.getHostName(), Constants.DISPATCHER_PORT, objKey, obj
					.getClass().getInterfaces()[0].getName());
			Dispatcher.objectList.put(objKey, obj);

		} catch (UnknownHostException e) {
			System.out.println("RequestHandler: UnknownHostException: "
					+ e.getMessage());
		}
		return remoteRef;
	}

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
				Class<?> c = Class.forName(className);
				Object o = null;
				if (Dispatcher.objectList.containsKey(rm.getObjectId()))
					o = Dispatcher.objectList.get(rm.getObjectId());
				else {
					Constructor<?> con = c.getConstructor();
					o = con.newInstance();
					Dispatcher.objectList.put(rm.getObjectId(), o);
				}
				Method method = o.getClass().getDeclaredMethod(methodName,
						rm.getParameterTypes());
				Object ret = method.invoke(o, rm.getParams());

				System.out.println(Arrays.toString(ret.getClass()
						.getInterfaces()));

				// Check if returned object extends our remote interface
				if (checkForRemote(ret.getClass().getInterfaces())) {
					System.out.println("In check for remote");
					ret = addRemoteObject(ret);
				}

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
