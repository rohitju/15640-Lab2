package ds.project.sample;

import java.util.Date;

import ds.rmi.rmiregistry.RegistryManager;
import ds.rmi.util.RemoteObject;

public class Time implements TimeInterface {
	
	@Override
	public Date getCurrentDate() {
		return new Date();
	}

	public static void main(String[] args){
		RegistryManager rm = new RegistryManager(args[0], Integer.parseInt(args[1]));
		RemoteObject ro = new RemoteObject(args[2], Integer.parseInt(args[3]), 0, "ds.project.sample.Time");
		rm.bind(ro);
	}
}
