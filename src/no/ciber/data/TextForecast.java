package no.ciber.data;

import java.util.Date;

public class TextForecast extends Forecast {

	private String title;
	private String body;
	
	public TextForecast(Date from, Date to, String title, String body) {
		super(from, to);
		this.title = title;
		this.body = body;
	}
	
	public String getTitle() {
		return title;
	}
	public String getBody() {
		return body;
	}
	
}
