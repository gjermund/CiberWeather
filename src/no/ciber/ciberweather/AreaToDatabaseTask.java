package no.ciber.ciberweather;

import java.util.List;

import no.ciber.data.Area;
import no.ciber.database.DatabaseHandler;

import android.os.AsyncTask;
import no.ciber.interfaces.Callback;

public class AreaToDatabaseTask extends AsyncTask<List<Area>, Integer, Long> {
	private DatabaseHandler database;
    private Callback callback;
	
	public AreaToDatabaseTask(DatabaseHandler database, Callback callback){
		this.database = database;
        this.callback = callback;
	}
	
	@Override
	protected Long doInBackground(List<Area>... params) {
		List<Area> areas = params[0];
		long totalSize = areas.size();
		long numberAdded = 0;
		
		for(Area a : areas){
			database.addArea(a);
			numberAdded++;
			publishProgress((int) ((numberAdded / (float)totalSize) * 100));
		}
		return numberAdded;
	}
	
	protected void onProgressUpdate(Integer... progress){
		//System.out.println(progress[0]);
	}

	protected void onPostExecute(Long result) {
		System.out.println("done: " + result);
        callback.onTaskDone(null);
	}
}
