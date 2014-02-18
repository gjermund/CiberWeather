package no.ciber.network;

import android.os.AsyncTask;
import android.util.Log;
import no.ciber.data.Area;
import no.ciber.data.WeatherData;
import no.ciber.interfaces.Callback;
import no.ciber.utils.XMLParser;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class GetForecastTask extends AsyncTask<Area, Void, WeatherData> {

	private Callback callback;
	
	public GetForecastTask(Callback callback) {
		this.callback = callback;
	}
	
    @Override
    protected WeatherData doInBackground(Area... params) {
        try {
            Area area = params[0];

            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(area.getForecastXMLURL());
            ResponseHandler<String> handler = new BasicResponseHandler();
            String forecastXml = client.execute(get, handler);

            WeatherData result = new WeatherData(area);

            XMLParser.parseForecast(forecastXml, result);

            return result;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onPostExecute(WeatherData weatherData) {
        callback.onTaskDone(weatherData);
    }
}
