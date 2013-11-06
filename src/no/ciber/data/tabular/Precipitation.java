package no.ciber.data.tabular;

public class Precipitation {
	
	private Double value;
	private Double minValue;
	private Double maxValue;

	public Precipitation(Double value, Double minValue, Double maxValue) {
		this.value = value;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public Double getValue() {
		return value;
	}
	public Double getMinValue() {
		return minValue;
	}
	public Double getMaxValue() {
		return maxValue;
	}
}
