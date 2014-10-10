package ds.project.sample;

import java.util.Date;

import ds.rmi.remote.Remote;
import ds.rmi.util.RemoteException;

public interface Time extends Remote{
	public Date getCurrentDate() throws RemoteException;
	public Date getDateMinusDays(Integer days) throws RemoteException;
	public DateInterface getDateObject() throws RemoteException; 
}
