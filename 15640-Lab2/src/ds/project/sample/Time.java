package ds.project.sample;

import java.util.Date;

import ds.rmi.remote.Remote;

public interface Time extends Remote {
	public Date getCurrentDate();
	public Date getDateMinusDays(Integer days);
	//public Foo 
}
