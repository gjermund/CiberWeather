package no.ciber.data;

import java.util.Date;

import no.ciber.data.tabular.Precipitation;
import no.ciber.data.tabular.Pressure;
import no.ciber.data.tabular.Symbol;
import no.ciber.data.tabular.Temperature;
import no.ciber.data.tabular.WindDirection;
import no.ciber.data.tabular.WindSpeed;

public class TabularForecast extends Forecast {

	private Symbol symbol;
	private Precipitation precipitation;
	private WindDirection windDirection;
	private WindSpeed windSpeed;
	private Temperature temperature;
	private Pressure pressure;
	
	public TabularForecast(Date from, Date to,
			Symbol symbol,
			Precipitation precipitation,
			WindDirection windDirection,
			WindSpeed windSpeed,
			Temperature temperature,
			Pressure pressure) {
		super(from, to);
		this.symbol = symbol;
		this.precipitation = precipitation;
		this.windDirection = windDirection;
		this.windSpeed = windSpeed;
		this.temperature = temperature;
		this.pressure = pressure;
	}

	public final Symbol getSymbol() {
		return symbol;
	}
	public final Precipitation getPrecipitation() {
		return precipitation;
	}
	public final WindDirection getWindDirection() {
		return windDirection;
	}
	public final WindSpeed getWindSpeed() {
		return windSpeed;
	}
	public final Temperature getTemperature() {
		return temperature;
	}
	public final Pressure getPressure() {
		return pressure;
	}
}
