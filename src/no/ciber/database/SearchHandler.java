package no.ciber.database;

import java.util.ArrayList;
import java.util.List;

import no.ciber.data.Area;
import no.ciber.data.AreaNorway;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SearchHandler {
	
	private DatabaseHandler databaseHandler;
	
	public SearchHandler(Context context) {
		databaseHandler = DatabaseHandler.getInstance(context);
	}
	
	public List<Area> search(String query) {
		SQLiteDatabase db = databaseHandler.getReadableDatabase();
		db.beginTransaction();
		List<Area> result = new ArrayList<Area>();
		Cursor cursor = db.rawQuery("select a.area_type_newno, a.area_type_no, a.area_type_en, a.latitude, a.longitude, a.forecast_url, an.area_name " +
				" from area a, area_norway an where an.area_name LIKE ? AND a.id = an.id", new String[]{"%"+query+"%"}); 
					
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Area area = CursorToAreaNorway(cursor);
	      result.add(area);
	      cursor.moveToNext();
	    }
	    
	    cursor.close();
		
		return result;
	}

	private Area CursorToAreaNorway(Cursor cursor) {
		return new AreaNorway(
				cursor.getString(0), cursor.getString(1), cursor.getString(2),
				cursor.getString(3), cursor.getString(4),
				cursor.getString(5), cursor.getString(6), null, null);
	}

}
