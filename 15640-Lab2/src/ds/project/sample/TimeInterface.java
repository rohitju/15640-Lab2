package ds.project.sample;

import java.util.Date;

import ds.rmi.remote.Remote;

public interface TimeInterface extends Remote {
	public Date getCurrentDate();
}
