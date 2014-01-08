package no.ciber.interfaces;

import no.ciber.data.WeatherData;

public interface Callback {

	public void onTaskDone(WeatherData weatherData);
}
