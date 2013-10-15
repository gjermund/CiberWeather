package no.ciber.data;

import java.util.HashMap;
import java.util.Map;

public abstract class Area {
	private String latitude;
	private String longitude;
	private String forecastXMLURL;
	private Map<Language, String> areaType;
	
	public Area(String areaNameNewNorwegian, String areaNameBokmaal, String areaNameEnglish,
			String latitude, String longitude, String forecastXMLURL){
		this.latitude = latitude;
		this.longitude = longitude;
		this.forecastXMLURL = forecastXMLURL;
		this.areaType = parseAreaTypes(areaNameNewNorwegian, areaNameBokmaal, areaNameEnglish);
	}
	
	private Map<Language, String> parseAreaTypes(
			String areaNameNewNorwegian, String areaNameBokmaal, String areaNameEnglish) {
		Map<Language, String> types = new HashMap<Language, String>();
		types.put(Language.NEW_NORWEGIAN, areaNameNewNorwegian);
		types.put(Language.NORWEGIAN, areaNameBokmaal);
		types.put(Language.ENGLISH, areaNameEnglish);
		
		return types;
	}

	public String getAreaTypeNewNorwegian() {
		return areaType.get(Language.NEW_NORWEGIAN);
	}

	public String getAreaTypeNorwegian() {
		return areaType.get(Language.NORWEGIAN);
	}

	public String getAreaTypeEnglish() {
		return areaType.get(Language.ENGLISH);
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}


	public String getForecastXMLURL() {
		return forecastXMLURL;
	}
}
