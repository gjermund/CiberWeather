package no.ciber.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

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

	public static List<String> parseAreaFile(Context context, int res_id) {
		InputStream is = context.getResources().openRawResource(res_id);

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));
            return parse(reader);
        } catch (UnsupportedEncodingException e) {
            return null;
        }

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
