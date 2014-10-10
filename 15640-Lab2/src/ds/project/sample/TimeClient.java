package ds.project.sample;

import ds.rmi.rmiregistry.RegistryManager;
import ds.rmi.util.RemoteException;
import ds.rmi.util.RemoteObject;

public class TimeClient {

	public static void main(String[] args) {
		RegistryManager rm = new RegistryManager(args[0], Integer.parseInt(args[1]));
		RemoteObject ro= null;
		try {
			ro = rm.lookup("ds.project.sample.Time");
			Time time = (Time) ro.localize();
			System.out.println(time.getCurrentDate().toString());
			System.out.println(time.getDateMinusDays(1));
			
			DateInterface dateObj = time.getDateObject();
			System.out.println(dateObj.getDateMinusDays(1));
		} catch (RemoteException e) {
			System.out.println("TimeClient: Not good: " + e.getMessage());
		}
	}
}
