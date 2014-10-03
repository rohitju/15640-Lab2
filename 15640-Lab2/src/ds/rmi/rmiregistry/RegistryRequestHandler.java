package ds.rmi.rmiregistry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import ds.rmi.util.Command;
import ds.rmi.util.RMIMessage;
import ds.rmi.util.RemoteObject;

public class RegistryRequestHandler extends Thread {
	private Socket client;
	private ObjectInputStream inStream = null;
	private ObjectOutputStream outStream = null;

	public RegistryRequestHandler(Socket c) {
		client = c;
	}

	@Override
	public void run() {
		try {
			inStream = new ObjectInputStream(client.getInputStream());
			outStream = new ObjectOutputStream(client.getOutputStream());
			RMIMessage msg = (RMIMessage) inStream.readObject();
			if (msg.getCommand() == Command.LOOKUP) {
				String serviceName = (String) msg.getPayload();
				RemoteObject ro = Registry.objectRegistry.get(serviceName);
				RMIMessage reply = new RMIMessage(Command.ROR, ro);
				outStream.writeObject(reply);
			} else if (msg.getCommand() == Command.BIND
					|| msg.getCommand() == Command.REBIND) {
				RemoteObject ro = (RemoteObject) msg.getPayload();
				Registry.objectRegistry.put(ro.getServiceName(), ro);
				RMIMessage reply = new RMIMessage(Command.ACK, null);
				outStream.writeObject(reply);
			} else if (msg.getCommand() == Command.UNBIND) {
				String serviceName = (String) msg.getPayload();
				Registry.objectRegistry.remove(serviceName);
				RMIMessage reply = new RMIMessage(Command.ACK, null);
				outStream.writeObject(reply);
			}
		} catch (IOException e) {
			System.out
					.println("RequestHandler: IOException: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("RequestHandler: ClassNotFoundException: "
					+ e.getMessage());
		}

	}
}
