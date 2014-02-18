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
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "ciberWeather";
    private static final String TABLE_AREA = "area";
    private static final String TABLE_AREA_NORWAY = "area_norway";
    private static final String TABLE_AREA_WORLD = "area_world";
    private static final String TABLE_COUNTRY = "country";
    private static final String TABLE_MUNCIPALITY = "muncipality";
    private static final String TABLE_COUNTY = "county";
	
    private static DatabaseHandler instance = null;
    
	private DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public static DatabaseHandler getInstance(Context context) {
		if(instance == null) instance = new DatabaseHandler(context);
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createCountryTable(db);
		createMuncipalityTable(db);
		createCountyTable(db);
		createAreaTable(db);
		createAreaNorwayTable(db);
		createAreaWorldTable(db);		
	}

	private void createCountyTable(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_COUNTY +
				"(id INTEGER PRIMARY KEY, county TEXT" +
				")";
        db.execSQL(CREATE_TABLE);		
	}

	private void createMuncipalityTable(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_MUNCIPALITY +
				"(id INTEGER PRIMARY KEY, muncipality TEXT" +
				")";
        db.execSQL(CREATE_TABLE);
	}

	private void createCountryTable(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_COUNTRY +
				"(id INTEGER PRIMARY KEY, country_newno TEXT" +
				", country_no TEXT, country_en TEXT" +
				")";
        db.execSQL(CREATE_TABLE);
	}

	private void createAreaWorldTable(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_AREA_WORLD +
				"(id INTEGER, area_name_newno TEXT, area_name_no TEXT," +
				"area_name_en TEXT, country_id INTEGER" +
				")";
        db.execSQL(CREATE_TABLE);
	}

	private void createAreaNorwayTable(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_AREA_NORWAY +
				"(id INTEGER, area_name TEXT, muncipality_id INTEGER," +
				"county_id INTEGER TEXT" +
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
		areaValues.put("area_type_no", area.getAreaTypeNorwegian());
		areaValues.put("area_type_en", area.getAreaTypeEnglish());
		areaValues.put("latitude", area.getLatitude());
		areaValues.put("longitude", area.getLongitude());
		areaValues.put("forecast_url", area.getForecastXMLURL());

		long id = db.insert(TABLE_AREA, null, areaValues);
		
		if(area instanceof AreaNorway){
			ContentValues norwayValues = new ContentValues();
			norwayValues.put("id", id);
			norwayValues.put("area_name", ((AreaNorway) area).getName());
			
			long muncipality_id = findMuncipalityID(((AreaNorway) area).getMuncipality());
			
			System.out.println("id_muncipality: " + muncipality_id);
			if(muncipality_id == -1){
				muncipality_id = addMuncipality(((AreaNorway) area).getMuncipality());
			}
			norwayValues.put("muncipality_id", muncipality_id);
			
			long county_id = findCountyID(((AreaNorway) area).getCounty());
			if(county_id == -1){
				county_id = addCounty(((AreaNorway) area).getCounty());
			}
			norwayValues.put("county_id",  county_id);
			db.insert(TABLE_AREA_NORWAY, null, norwayValues);
		}else {
			ContentValues worldValues = new ContentValues();
			AreaWorld areaWorld = (AreaWorld) area;
			worldValues.put("id", id);
			
			long country_id = findCountryId(areaWorld.getCountryEnglish());
			
			if(country_id == -1){
				country_id = addCountry(areaWorld.getCountryNewNorwegian(), areaWorld.getCountryNorwegian(), areaWorld.getCountryEnglish());
			}
			worldValues.put("area_name_newno",areaWorld.getAreaNameNewNorwegian());
			worldValues.put("area_name_no", areaWorld.getAreaNameNorwegian());
			worldValues.put("area_name_en", areaWorld.getAreaNameEnglish());
			worldValues.put("country_id", country_id);
			db.insert(TABLE_AREA_WORLD, null, worldValues);
		}
		db.close();
	}

	private long findCountryId(String countryEnglish) {
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.query(TABLE_COUNTRY, new String[] { "id" }, "country_en=?",
	            new String[] {countryEnglish }, null, null, null, null);
	    if (cursor.getCount() > 0){
	    	cursor.moveToFirst();
	    	return cursor.getLong(0);
	    }
		return -1;
	}

	private long addCountry(String countryNewNorwegian,
			String countryNorwegian, String countryEnglish) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("country_newno", countryNewNorwegian);
		values.put("country_no", countryNorwegian);
		values.put("country_en", countryEnglish);
		long id = db.insert(TABLE_COUNTRY, null, values);
		return id;
	}

	private long addCounty(String county) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("county", county);
		long id = db.insert(TABLE_COUNTY, null, values);
		return id;
	}

	private long findCountyID(String county) {
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.query(TABLE_COUNTY, new String[] { "id" }, "county=?",
	            new String[] {county }, null, null, null, null);
	    if (cursor.getCount() > 0){
	    	cursor.moveToFirst();
	    	return cursor.getLong(0);
	    }
		return -1;
	}

	private long addMuncipality(String muncipality) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("muncipality", muncipality);
		long id = db.insert(TABLE_MUNCIPALITY, null, values);
		return id;
	}

	private long findMuncipalityID(String muncipality) {
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.query(TABLE_MUNCIPALITY, new String[] { "id" }, "muncipality=?",
	            new String[] {muncipality }, null, null, null, null);
	    if (cursor.getCount() > 0){
	    	cursor.moveToFirst();
	    	return cursor.getLong(0);
	    }
		return -1;
	}

}
