package ds.project.sample;

import java.util.Calendar;
import java.util.Date;

public class DateObject implements DateInterface {
	
	private static final long serialVersionUID = 1L;
	Date date;
	
	public DateObject(){
		date = new Date();
	}

	@Override
	public Date getDateMinusDays(Integer days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -days);
		return cal.getTime();
	}
}
