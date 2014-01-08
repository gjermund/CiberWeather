package no.ciber.database;

import java.util.ArrayList;
import java.util.List;

import no.ciber.data.Area;
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
		
		Cursor cursor = db.rawQuery("select * from area a, area_norway an where an.area_name LIKE ? AND a.id = an.id", new String[]{"%"+query+"%"}); 
			
		System.out.println(cursor.getCount());
		
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      System.out.println(cursor.getString(cursor.getColumnIndex("area_name")));
	      cursor.moveToNext();
	    }
	    
	    // make sure to close the cursor
	    cursor.close();
		List<Area> result = new ArrayList<Area>();
		
		return result;
	}

}
