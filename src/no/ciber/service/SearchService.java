package no.ciber.service;

import java.util.List;

import no.ciber.data.Area;
import no.ciber.database.SearchHandler;
import android.content.Context;

public class SearchService {
	private SearchHandler handler;
	
	public SearchService(Context context){
		handler = new SearchHandler(context);
	}
	public List<Area> searchAreas(String searchTerm){
		return handler.search(searchTerm);
	}
}
