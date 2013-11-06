package no.ciber.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import android.app.Activity;
import android.util.Log;

import no.ciber.ciberweather.R;
import no.ciber.data.Area;

public class CSVParser {
	private static final String TAG = CSVParser.class.getSimpleName();


	private static List<String> parse(BufferedReader bufferedReader) {
		List<String> rawData= new ArrayList<String>();	
		String line = "";
		
		try {
			bufferedReader.readLine(); //skip first line, contains definitions.
			while ((line = bufferedReader.readLine()) != null) {
				rawData.add(line);
				Log.d(TAG, line);
			}
		} catch (IOException e) {
			Log.d(TAG, "Parse Error: " + e.getMessage(), e);
			return null;
		}

		return rawData;	
	}

	public static List<String> parseAreaFile(Activity activity, int res_id) {
		InputStream is = activity.getResources().openRawResource(res_id);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		return parse(reader);
	}
	
	public static ArrayList<String> parseLineTilPassering(String line) {
		ArrayList<String> passering = new ArrayList<String>();
		String[] passering2 = line.split(";");
		
		for(int i = 0; i<passering2.length; i++){
			passering.add(passering2[i]);
		}
		
		return passering;
	}

}
