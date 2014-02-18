package no.ciber.adapter;

import java.util.ArrayList;
import java.util.List;

import no.ciber.ciberweather.R;
import no.ciber.data.Area;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import no.ciber.data.AreaNorway;

public class AreaAdapter extends BaseAdapter {
	private List<Area> areasInList;
	private static LayoutInflater inflater = null;
	private Context context;
	
	public AreaAdapter(Context context){
		this.context = context;
		areasInList = new ArrayList<Area>();
		inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
	}
	
	@Override
	public int getCount() {
		return areasInList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return areasInList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = arg1;
		if(view == null){
			view = inflater.inflate(R.layout.responselist_row, null);
		}
		TextView title = (TextView)view.findViewById(R.id.title);
        
        Area area = areasInList.get(arg0);
        title.setText(((AreaNorway) area).getName() + " (" + ((AreaNorway) area).getMuncipality()+")" + " (" + ((AreaNorway) area).getCounty()+")");
        return view;
	}

	public void clear() {
		areasInList.clear();
	}

	public void addAll(List<Area> result) {
		for(Area item : result){
			areasInList.add(item);
		}
		
		notifyDataSetChanged();
	}
}
