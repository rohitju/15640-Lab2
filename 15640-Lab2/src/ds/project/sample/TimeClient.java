package ds.project.sample;

import java.rmi.RemoteException;

import ds.rmi.rmiregistry.RegistryManager;
import ds.rmi.util.RemoteObject;

public class TimeClient {

	public static void main(String[] args){
		RegistryManager rm = new RegistryManager(args[0], Integer.parseInt(args[1]));
		RemoteObject ro= null;
		try {
			ro = rm.lookup("ds.project.sample.Time");
			Time_stub time = (Time_stub) ro.localize();
			System.out.println(time.getCurrentDate().toString());
		} catch (RemoteException e) {
			System.out.println("TimeClient: Not good: " + e.getMessage());
		}
	}
}
