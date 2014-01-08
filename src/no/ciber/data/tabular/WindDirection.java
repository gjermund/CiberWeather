package no.ciber.data.tabular;

public class WindDirection {

	private Double degrees;
	private String code;
	private String name;

	public WindDirection(Double degrees, String code, String name) {
		this.degrees = degrees;
		this.code = code;
		this.name = name;
	}

	public Double getDegrees() {
		return degrees;
	}
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
}
