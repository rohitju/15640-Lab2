package ds.project.sample;

import java.util.Date;

import ds.rmi.remote.Remote;

public interface DateInterface extends Remote{
	public Date getDateMinusDays(Integer days);
}
