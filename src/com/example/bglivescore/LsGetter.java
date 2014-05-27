package com.example.bglivescore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;


public class LsGetter {


	private static final String URL = "http://www.livescore.com/soccer/bulgaria/";
	private Document html;
	Elements table;
	ArrayList<League> myLeagues;


	public LsGetter(){
		Getter getter = new Getter();
		getter.execute(URL);
	}
	
	
	private void setToObjects(Elements table ){
		myLeagues = new ArrayList<League>();
		for(int i = 0; i < table.size(); i++){
			League legaue = new League();
			legaue.title = table.get(i).select(".league span a").text();
			Elements tableRows = table.get(i).select("tr");
			for(int j = 0; j < tableRows.size(); j++){
				Elements td = tableRows.get(j).select("td");
				Match match = new Match();
				for(int k = 0; k < td.size(); k++ ){
					if( td.get(k).hasClass("fh")){
						match.host = td.get(k).text();
					}
					if(td.get(k).hasClass("fa")){
						match.guest = td.get(k).text();
					}
					if(td.get(k).hasClass("fs")){
						match.score = td.get(k).select("a").text();
					}
					
				}
				
				legaue.matches.add(match);
			}
			
			myLeagues.add(legaue);
		}

	}
	
	private class Getter extends AsyncTask<String, Void, Elements>{

		@Override
		protected Elements doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				Document doc = Jsoup.connect(params[0]).get();
				Elements table= doc.select("table.league-table");
				return table;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Elements result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			setToObjects(result);
		}
		
	}
	
	
	
	
	
	
	
}
