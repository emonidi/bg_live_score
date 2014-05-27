package com.example.bglivescore;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ViewPagerFragment extends android.support.v4.app.Fragment{
	League league;
	ArrayList<Match> matches;
	String leagueName;
	Bundle args;
	Bitmap imageBitmap;
	ImageView image;
	int position;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.view_pager_item, container,false);
		args = getArguments();
		leagueName = args.getString("league_name");
		position = args.getInt("position");
		matches = args.getParcelableArrayList("matches");
		ListView listView = (ListView) view.findViewById(R.id.match_list_view);
		MatchListAdapter adapter = new MatchListAdapter(getActivity(),R.layout.match_item,matches);
		listView.setAdapter(adapter);
		return view;
	}
	
	
	
}
