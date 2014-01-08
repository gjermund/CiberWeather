package no.ciber.data;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public class WeatherData {

	private Area area;
	private SortedSet<TextForecast> textForecasts = new TreeSet<TextForecast>();
	private SortedSet<TabularForecast> tabularForecasts = new TreeSet<TabularForecast>();
	
	public WeatherData(Area area) {
		this.area = area;
	}
	
	public Area getArea() {
		return area;
	}
	
	public void addTextForecast(TextForecast textForecast) {
		this.textForecasts.add(textForecast);
	}
	
	public void addTabularForecast(TabularForecast tabularForecast) {
		this.tabularForecasts.add(tabularForecast);
	}

	public SortedSet<TextForecast> getTextForecasts() {
		return Collections.unmodifiableSortedSet(textForecasts);
	}
	
	public SortedSet<TabularForecast> getTabularForecasts() {
		return Collections.unmodifiableSortedSet(tabularForecasts);
	}
	
}
