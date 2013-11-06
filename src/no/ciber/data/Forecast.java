package no.ciber.data;

import java.util.Date;


public abstract class Forecast implements Comparable<Forecast> {

	private Date from;
	private Date to;

	public Forecast(Date from, Date to) {
		this.from = from;
		this.to = to;
	}
	
	@Override
	public int compareTo(Forecast another) {
		return this.from.compareTo(another.from);
	}
	
	public Date getFrom() {
		return from;
	}
	public Date getTo() {
		return to;
	}

}