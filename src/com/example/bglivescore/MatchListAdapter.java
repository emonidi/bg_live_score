package com.example.bglivescore;

import java.util.List;

import android.app.Service;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MatchListAdapter extends ArrayAdapter<Match>{
	Context context;
	List<Match> matches;
	public MatchListAdapter(Context context,int textViewResourceId, List<Match> objects) {
		super(context, textViewResourceId, objects);

		// TODO Auto-generated constructor stub
		this.context = context;
		matches = objects;
		cleanMatches();
	}
	
	private void cleanMatches(){
		for(int i = 0; i < matches.size(); i++){
			if(matches.get(i).host == null){
				matches.remove(i);
			}
		}
	}

	@Override
	public Match getItem(int position) {
		// TODO Auto-generated method stub
		return super.getItem(position);
	}

	@Override
	public int getPosition(Match item) {
		// TODO Auto-generated method stub
		return super.getPosition(item);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		String hostNameString = matches.get(position).host;
		String hostImageName = hostNameString.replace(" ", "_").toLowerCase().replace("*", "");
		
		String guestNameString = matches.get(position).guest;
		String guestImageName = guestNameString.replace(" ", "_").toLowerCase().replace("*", "");
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.match_item,parent, false);
		}
		
		if(matches.get(position).guest != null){
			TextView hostNameText = (TextView) convertView.findViewById(R.id.host_name_text);
			hostNameText.setText(matches.get(position).host);
				
			TextView guestNameText = (TextView) convertView.findViewById(R.id.guest_name_text);
			guestNameText.setText(matches.get(position).guest);
			
			TextView resultText = (TextView) convertView.findViewById(R.id.result_text);
			resultText.setText(matches.get(position).score);
			
			ImageView hostLogo = (ImageView) convertView.findViewById(R.id.host_logo);
			int hostImageDrawableId = context.getResources().getIdentifier(hostImageName, "drawable", context.getPackageName());
			if(hostImageDrawableId != 0){
				hostLogo.setImageResource(hostImageDrawableId);
			}else{
				hostLogo.setImageResource(R.drawable.no_logo);
			}
			
			ImageView guestLogo = (ImageView) convertView.findViewById(R.id.guest_logo);
			int guestImageDrawableId = context.getResources().getIdentifier(guestImageName, "drawable", context.getPackageName());
			if(guestImageDrawableId != 0){
				guestLogo.setImageResource(guestImageDrawableId);
			}else{
				guestLogo.setImageResource(R.drawable.no_logo);
			}
		
		}else{
			
		}
		return convertView;

	}
	
	
	
}
