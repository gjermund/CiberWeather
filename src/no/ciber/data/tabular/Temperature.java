package no.ciber.data.tabular;

public class Temperature {

	private String unit;
	private Double value;

	public Temperature(String unit, Double value) {
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
