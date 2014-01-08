package no.ciber.adapters;

import java.util.Locale;

import no.ciber.ciberweather.R;
import no.ciber.fragments.TextualFragment;
import no.ciber.fragments.WeatherFragment;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class WeatherActivityPagerAdapter extends FragmentPagerAdapter {

	private Context context;
	
	public WeatherActivityPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		this.context = context;
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
		switch(position) {
		case 0:
			return new WeatherFragment();
		case 1:
			return new TextualFragment();
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
