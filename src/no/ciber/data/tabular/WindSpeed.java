package no.ciber.data.tabular;

public class WindSpeed {

	private Double metersPerSecond;
	private String name;

	public WindSpeed(Double metersPerSecond, String name) {
		this.metersPerSecond = metersPerSecond;
		this.name = name;
	}

	public Double getMetersPerSecond() {
		return metersPerSecond;
	}
	public String getName() {
		return name;
	}
}
