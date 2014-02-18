package no.ciber.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.view.*;
import android.widget.*;
import no.ciber.adapter.AreaAdapter;
import no.ciber.ciberweather.R;
import no.ciber.data.Area;
import no.ciber.service.SearchService;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView.OnEditorActionListener;

public class SearchFragment extends Fragment {
	private static final String TAG = SearchFragment.class.getSimpleName();
	private SearchService searchService;
	private AreaAdapter adapter;

	private Context context;

	public void initialize(Context context) {
		this.context = context;
		searchService = new SearchService(context);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_search, container,
				false);

		EditText searchBox = (EditText) rootView.findViewById(R.id.searchBox);
		searchBox.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView view, int actionId,
					KeyEvent event) {
				switch (actionId) {
				case EditorInfo.IME_ACTION_SEARCH:
					Log.d(TAG, "Searching for: " + view.getText());
					search(view.getText().toString());
					break;
				default:
					Log.e(TAG, "Wtf.");
					break;
				}
				return true;
			}

		});
		
        ListView searchResults = (ListView)rootView.findViewById(R.id.searchResults);
        adapter = new AreaAdapter(context);
        searchResults.setAdapter(adapter);
        searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e(TAG, "Click!");
            }
        });
		return rootView;
	}

	protected void search(String search) {
		System.out.println("search for: " + search);
		if (search.trim().length() == 0) {
			return;
		}

		List<Area> result = searchService.searchAreas(search);
        System.out.println("search complete: " + result.size());
		if(result.size() == 0){
			showToast();
		}
		else {	        
			adapter.clear();
			adapter.addAll(result);
		}
	}

	private void showToast() {
        CharSequence text = "Ingen resultater funnet";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
        toast.show();
	}
}
