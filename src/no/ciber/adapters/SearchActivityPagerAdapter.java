package no.ciber.adapters;

import java.util.Locale;

import no.ciber.ciberweather.R;
import no.ciber.fragments.MapFragment;
import no.ciber.fragments.SearchFragment;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SearchActivityPagerAdapter extends FragmentPagerAdapter {
	private Context context;
	
	public SearchActivityPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
			case 0:
				SearchFragment sfragment = new SearchFragment();
				sfragment.initialize(context);
				return sfragment;
			case 1:
				return new MapFragment();
			default:
				return null;
		}
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return context.getString(R.string.searchactivity_title_section1)
					.toUpperCase(l);
		case 1:
			return context.getString(R.string.searchactivity_title_section2)
					.toUpperCase(l);
		}
		return null;
	}
}
