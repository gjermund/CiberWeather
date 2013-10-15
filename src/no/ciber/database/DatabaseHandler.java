package no.ciber.database;

import no.ciber.data.Area;
import no.ciber.data.AreaNorway;
import no.ciber.data.AreaWorld;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "ciberWeather";
    private static final String TABLE_AREA = "area";
    private static final String TABLE_AREA_NORWAY = "area_norway";
    private static final String TABLE_AREA_WORLD = "area_world";
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createAreaTable(db);
		createAreaNorwayTable(db);
		createAreaWorldTable(db);		
	}

	private void createAreaWorldTable(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_AREA_WORLD +
				"(id INTEGER, area_name_newno TEXT, area_name_no TEXT," +
				"area_name_en TEXT, country_newno TEXT, country_no TEXT," +
				" country_en TEXT" +
				")";
        db.execSQL(CREATE_TABLE);
	}

	private void createAreaNorwayTable(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_AREA_NORWAY +
				"(id INTEGER, area_name TEXT, muncipality TEXT," +
				"county TEXT" +
				")";
        db.execSQL(CREATE_TABLE);
	}

	private void createAreaTable(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_AREA +
				"(id INTEGER PRIMARY KEY, area_type_newno TEXT, area_type_no TEXT," +
				"area_type_en TEXT, latitude TEXT, longitude TEXT, forecast_url TEXT" +
				")";
        db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_AREA);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_AREA_NORWAY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_AREA_WORLD);
		
        onCreate(db);
	}
	
	public int getAreaCount() {
		String selectAll = "SELECT * FROM " + TABLE_AREA;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectAll, null);
		
		return cursor.getCount();
	}
	
	public void addArea(Area area) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues areaValues = new ContentValues();
		
		areaValues.put("area_type_newno", area.getAreaTypeNewNorwegian());
		areaValues.put("area_type_newno", area.getAreaTypeNorwegian());
		areaValues.put("area_type_newno", area.getAreaTypeEnglish());
		areaValues.put("latitude", area.getLatitude());
		areaValues.put("longitude", area.getLongitude());
		areaValues.put("forecast_url", area.getForecastXMLURL());

		long id = db.insert(TABLE_AREA, null, areaValues);
		
		if(area instanceof AreaNorway){
			ContentValues norwayValues = new ContentValues();
			norwayValues.put("id", id);
			norwayValues.put("area_name", ((AreaNorway) area).getName());
			norwayValues.put("muncipality", ((AreaNorway) area).getMuncipality());
			norwayValues.put("county", ((AreaNorway) area).getCounty());
			db.insert(TABLE_AREA_NORWAY, null, norwayValues);
		}else {
			ContentValues worldValues = new ContentValues();
			AreaWorld areaWorld = (AreaWorld) area;
			worldValues.put("id", id);
			worldValues.put("area_name_newno",areaWorld.getAreaNameNewNorwegian());
			worldValues.put("area_name_no", areaWorld.getAreaNameNorwegian());
			worldValues.put("area_name_en", areaWorld.getAreaNameEnglish());
			worldValues.put("country_newno", areaWorld.getCountryNewNorwegian());
			worldValues.put("country_no", areaWorld.getCountryNorwegian());
			worldValues.put("country_en", areaWorld.getCountryEnglish());
			db.insert(TABLE_AREA_WORLD, null, worldValues);
		}
		db.close();
	}

	public void empty() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_AREA, null, null);
		db.delete(TABLE_AREA_NORWAY, null, null);
		db.delete(TABLE_AREA_WORLD, null, null);
	}
}
