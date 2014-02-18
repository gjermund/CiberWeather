package no.ciber.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import no.ciber.ciberweather.AreaToDatabaseTask;
import no.ciber.ciberweather.R;
import no.ciber.data.Area;
import no.ciber.data.AreaNorway;
import no.ciber.data.AreaWorld;
import no.ciber.data.WeatherData;
import no.ciber.database.DatabaseHandler;
import no.ciber.interfaces.Callback;
import no.ciber.utils.CSVParser;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends Activity {

    List<Area> areas;
    DatabaseHandler database;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        database = DatabaseHandler.getInstance(this);

        //database.empty();

        //27365 er size
        if(database.getAreaCount() == 0){
            areas = new ArrayList<Area>();
            List<String> parsedNorwegianAreas = CSVParser.parseAreaFile(this, R.raw.norwegian_places);
//			List<String> parsedRestOfWorldPlaces = CSVParser.parseAreaFile(this, R.raw.rest_of_world_places);

            createAreas(parsedNorwegianAreas);
//			createAreas(parsedRestOfWorldPlaces);


            System.out.println("Size: " + areas.size());

            AreaToDatabaseTask task = new AreaToDatabaseTask(database, new Callback() {
                @Override
                public void onTaskDone(WeatherData weatherData) {
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, SearchActivity.class));
                }
            });
            task.execute(areas);
        }else {
            System.out.println("database not empty!: " + database.getAreaCount());
            startActivity(new Intent(this, SearchActivity.class));
        }

    }


    private void createAreas(List<String> parsedAreas) {
        for(String s : parsedAreas){
            ArrayList<String> areaString = CSVParser.parseLineTilPassering(s);
            Area area = areaString.size() > 15 ? new AreaWorld(areaString) : new AreaNorway(areaString);
            areas.add(area);
        }
    }

}