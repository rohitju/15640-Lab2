package ds.project.sample;

import java.util.Calendar;
import java.util.Date;

import ds.rmi.rmiregistry.RegistryManager;
import ds.rmi.util.RemoteObject;

public class TimeServer implements Time {
	
	@Override
	public Date getCurrentDate() {
		return new Date();
	}

	public static void main(String[] args){
		RegistryManager rm = new RegistryManager(args[0], Integer.parseInt(args[1]));
		RemoteObject ro = new RemoteObject(args[2], Integer.parseInt(args[3]), 0, "ds.project.sample.Time");
		rm.bind(ro);
	}

	@Override
	public Date getDateMinusDays(Integer days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -days);
		return cal.getTime();
	}
}
