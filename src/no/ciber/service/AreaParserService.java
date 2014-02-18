package no.ciber.service;

import java.util.ArrayList;
import java.util.List;

import no.ciber.ciberweather.AreaToDatabaseTask;
import no.ciber.ciberweather.R;
import no.ciber.data.Area;
import no.ciber.data.AreaNorway;
import no.ciber.data.AreaWorld;
import no.ciber.database.DatabaseHandler;
import no.ciber.utils.CSVParser;
import android.content.Context;

public class AreaParserService {
	private DatabaseHandler database;
	private List<Area> areas; 
	private Context context;

	public AreaParserService(Context context){
		database = DatabaseHandler.getInstance(context);
		this.context = context;
		parseAreas();
	}
	
	private void parseAreas(){
		
		if (database.getAreaCount() == 0) {
			areas = new ArrayList<Area>();
			List<String> parsedNorwegianAreas = CSVParser.parseAreaFile(context,
					R.raw.norwegian_places);
			List<String> parsedRestOfWorldPlaces = CSVParser.parseAreaFile(
					context, R.raw.rest_of_world_places);

			createAreas(parsedNorwegianAreas);
			createAreas(parsedRestOfWorldPlaces);

			System.out.println("Size: " + areas.size());

			//AreaToDatabaseTask task = new AreaToDatabaseTask(database );
			//task.execute(areas);
		} else {
			System.out.println("database not empty!: "
					+ database.getAreaCount());
		}
	}
	

	private void createAreas(List<String> parsedAreas) {
		for (String s : parsedAreas) {
			ArrayList<String> areaString = CSVParser.parseLineTilPassering(s);
			Area area = areaString.size() > 15 ? new AreaWorld(areaString)
					: new AreaNorway(areaString);
			areas.add(area);
		}
	}
}
