package no.ciber.fragments;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import android.text.Html;
import no.ciber.ciberweather.R;
import no.ciber.data.Area;
import no.ciber.data.TabularForecast;
import no.ciber.data.TextForecast;
import no.ciber.data.WeatherData;
import no.ciber.interfaces.Callback;
import no.ciber.network.GetForecastTask;
import android.R.array;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TextualFragment extends Fragment {
		public static final String ARG_SECTION_NUMBER = "section_number";

		public TextualFragment() {
		}

		ArrayList<TextForecast> arrayList = new ArrayList<TextForecast>();

		@Override
		public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_dummy, container, false);
            final ListView lv = (ListView) rootView.findViewById(R.id.listView1);

            Area area = new Area(null, null, null, null, null, "http://www.yr.no/sted/Norge/%C3%98stfold/Fredrikstad/Apenesfjellet/varsel.xml") {

            };
			new GetForecastTask(new Callback() {
				
				@Override
				public void onTaskDone(WeatherData weatherData) {
					Iterator<TextForecast> iterator = weatherData.getTextForecasts().iterator();
					while (iterator.hasNext()) {
						arrayList.add(iterator.next());
					}
                    lv.setAdapter(getTextForecastArrayAdapter(inflater));
				}
			}).execute(area);



            ArrayAdapter<TextForecast> adapter = getTextForecastArrayAdapter(inflater);
            lv.setAdapter(adapter);
		    return rootView;
	    }

    private ArrayAdapter<TextForecast> getTextForecastArrayAdapter(final LayoutInflater inflater) {
        return new ArrayAdapter<TextForecast>(getActivity(), android.R.layout.simple_list_item_1, arrayList) {

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View vi = convertView;
                        if (convertView == null) {
                            vi = inflater.inflate(R.layout.list_item, null);
                        }
                        TextForecast item = getItem(position);
                        TextView header = (TextView)vi.findViewById(R.id.itemHeader);
                        header.setText(item.getTitle());

                        TextView body = (TextView)vi.findViewById(R.id.itemBody);
                        body.setText(Html.fromHtml(item.getBody()));

                        return vi;
                    }

                };
    }
}
