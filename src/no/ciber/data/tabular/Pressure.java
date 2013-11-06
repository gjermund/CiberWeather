package no.ciber.data.tabular;

public class Pressure {

	private String unit;
	private Double value;
	
	public Pressure(String unit, Double value) {
		this.unit = unit;
		this.value = value;
	}
	
	public String getUnit() {
		return unit;
	}
	public Double getValue() {
		return value;
	}
	
}
