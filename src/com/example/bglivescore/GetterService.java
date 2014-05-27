package com.example.bglivescore;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;




import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;


public class GetterService extends IntentService {
	
	private static final String URL = "http://www.livescore.com/soccer/bulgaria/";
	Elements table;
	ArrayList<League> myLeagues;
	Intent intent;
	
	
	
	public GetterService() {
		super("GetterService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		this.intent = intent;
		Getter getter = new Getter();
		getter.execute(URL);		
	}
	
	private void setToObjects(Elements table ){
		ArrayList<Match> matches = new ArrayList<Match>();
		myLeagues = new ArrayList<League>();
		for(int i = 0; i < table.size(); i++){
			if(table.get(i).select(".league span a").text() != ""){
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
							if(td.get(k).select("a").text() == ""){
								match.score = td.get(k).text();
							}else{
								match.score = td.get(k).select("a").text();
							}
							
						}
						
					}
					
					legaue.matches.add(match);
					matches.add(match);
				}
				myLeagues.add(legaue);
				
			}
			
			}
			
		intent = new Intent();
		intent.setAction("leagues");
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.putParcelableArrayListExtra("leagues", myLeagues);
		sendBroadcast(intent);
		stopSelf();
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
