package no.ciber.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AreaWorld extends Area {
	private Map<Language, String> areaName;
	private Map<Language, String> country;
	
	public AreaWorld(ArrayList<String> areaString) {
		super(
				areaString.get(5), areaString.get(6), areaString.get(7),
				areaString.get(12), areaString.get(13), areaString.size() == 18 ? areaString.get(16) : areaString.get(15)
				);		
		areaName = parseAreaNames(areaString);
		country = parseCountry(areaString);
	}
	
	private Map<Language, String> parseCountry(ArrayList<String> areaString) {
		Map<Language, String> countries = new HashMap<Language, String>();
		countries.put(Language.NEW_NORWEGIAN, areaString.get(8));
		countries.put(Language.NORWEGIAN, areaString.get(9));
		countries.put(Language.ENGLISH, areaString.get(10));
		
		return countries;
	}

	private Map<Language, String> parseAreaNames(ArrayList<String> areaString) {
		Map<Language, String> areaNames = new HashMap<Language, String>();
		areaNames.put(Language.NEW_NORWEGIAN, areaString.get(1));
		areaNames.put(Language.NORWEGIAN, areaString.get(2));
		areaNames.put(Language.ENGLISH, areaString.get(3));
		
		return areaNames;
	}

	public String getAreaNameNewNorwegian() {
		return areaName.get(Language.NEW_NORWEGIAN);
	}

	public String getAreaNameNorwegian() {
		return areaName.get(Language.NORWEGIAN);
	}

	public String getAreaNameEnglish() {
		return areaName.get(Language.ENGLISH);
	}

	public String getCountryNewNorwegian() {
		return country.get(Language.NEW_NORWEGIAN);
	}

	public String getCountryEnglish() {
		return country.get(Language.NORWEGIAN);
	}

	public String getCountryNorwegian() {
		return country.get(Language.ENGLISH);
	}
}
