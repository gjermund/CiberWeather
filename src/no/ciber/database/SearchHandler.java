package no.ciber.database;

import java.util.ArrayList;
import java.util.List;

import no.ciber.data.Area;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SearchHandler {
	
	private DatabaseHandler databaseHandler;
	
	public SearchHandler(Context context) {
		databaseHandler = DatabaseHandler.getInstance(context);
	}
	
	public List<Area> search(String query) {
		SQLiteDatabase db = databaseHandler.getReadableDatabase();
		db.beginTransaction();
		
		//db.execSQL("SELECT id, area_name FROM ")
		
		
		List<Area> result = new ArrayList<Area>();
		
		return result;
	}

}
