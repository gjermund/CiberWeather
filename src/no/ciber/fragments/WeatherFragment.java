package no.ciber.fragments;

import android.widget.ImageView;
import android.widget.TextView;
import no.ciber.ciberweather.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import no.ciber.data.Area;
import no.ciber.data.AreaNorway;

public class WeatherFragment extends Fragment {


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.weather, container, false);
        final TextView lv = (TextView) rootView.findViewById(R.id.name);
        final ImageView iv = (ImageView) rootView.findViewById(R.id.symbol);

        AreaNorway area = (AreaNorway) this.getArguments().getSerializable("area");

        lv.setText(area.getName());

        return rootView;
    }
}
