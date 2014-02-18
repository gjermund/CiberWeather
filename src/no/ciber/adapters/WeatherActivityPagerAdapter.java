package no.ciber.adapters;

import java.util.Locale;

import android.os.Bundle;
import no.ciber.ciberweather.R;
import no.ciber.data.Area;
import no.ciber.fragments.TextualFragment;
import no.ciber.fragments.WeatherFragment;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class WeatherActivityPagerAdapter extends FragmentPagerAdapter {

	private Context context;
    private Area area;
	
	public WeatherActivityPagerAdapter(FragmentManager fm, Context context, Area area) {
		super(fm);
		this.context = context;
        this.area = area;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return context.getString(R.string.title_section1).toUpperCase(l);
		case 1:
			return context.getString(R.string.title_section2).toUpperCase(l);
		}
		return null;
	}
	@Override
	public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("area", area);
		switch(position) {
		case 0:
			WeatherFragment weatherFragment =  new WeatherFragment();
            weatherFragment.setArguments(bundle);
            return weatherFragment;
		case 1:
            TextualFragment textualFragment =  new TextualFragment();
            textualFragment.setArguments(bundle);
            return textualFragment;
		default:
			Log.d("TAG", "wtf -- default hvaforno?");
			return null;
		}
	}

	@Override
	public int getCount() {
		return 2;
	}

}
