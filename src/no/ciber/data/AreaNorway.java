package no.ciber.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AreaNorway extends Area {
	private String name;
	private String muncipality;
	private String county;

	public AreaNorway(ArrayList<String> areaString) {
		super(
				areaString.get(3), areaString.get(4), areaString.get(5),
				areaString.get(8), areaString.get(9), areaString.size() == 14 ? areaString.get(11) : areaString.get(10)
			);
		this.name = areaString.get(1);
		muncipality = areaString.get(6);
		county = areaString.get(7);
	}

	public String getName() {
		return name;
	}

	public String getMuncipality() {
		return muncipality;
	}

	public String getCounty() {
		return county;
	}
}
