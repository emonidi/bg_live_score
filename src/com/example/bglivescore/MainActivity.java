package com.example.bglivescore;


import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.app.ActionBar.Tab;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.os.Build;



public class MainActivity extends ActionBarActivity {
	
	GetterService getterService;
	ArrayList<League> leagues;
	ViewPager viewPager;
	PagerAdapter pagerAdapter;
	ActionBar actionBar;
	public static BackgroundHelper backgroundHelper;
	static HorizontalScrollView background;
	GetterReciever reciever;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		//getter = new LsGetter();
		
		
		
		IntentFilter filter = new IntentFilter("leagues");
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		reciever = new GetterReciever();
		registerReceiver(reciever, filter);
		
		Intent intent =  new Intent(this,GetterService.class);
		startService(intent);
	}
	
	
	public class GetterReciever extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Bundle bundle = intent.getExtras();
			if(bundle != null){
				leagues = bundle.getParcelableArrayList("leagues");
				backgroundHelper = new BackgroundHelper(getWindowManager(),background,leagues.size());
				//backgroundHelper.blurImageBitmap(getBaseContext(), 20);
				setViewPager();
				setActionBarTabs();	
				
				unregisterReceiver(reciever);
			}
		}}
	
	
	public void setViewPager(){
		viewPager = (ViewPager) findViewById(R.id.pager);
		pagerAdapter = new PagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(pagerAdapter);
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				actionBar.setSelectedNavigationItem(arg0);
				backgroundHelper.scrollToPosition(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				int offset = arg2;
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void setActionBarTabs(){
		actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(android.support.v7.app.ActionBar.Tab arg0,
					FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTabSelected(android.support.v7.app.ActionBar.Tab arg0,
					FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(arg0.getPosition());
			}
			
			@Override
			public void onTabReselected(android.support.v7.app.ActionBar.Tab arg0,
					FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				
			}
		};
		for(int i = 0; i < leagues.size(); i++){
			android.support.v7.app.ActionBar.Tab tab = actionBar.newTab();
			tab.setTabListener(tabListener);
			tab.setText(leagues.get(i).title);
			actionBar.addTab(tab);
		}
	}
	
	private class PagerAdapter extends FragmentStatePagerAdapter{

		public PagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			Fragment fragment = new ViewPagerFragment();
			Bundle args = new Bundle();
			args.putString("league_name", leagues.get(arg0).title);
			args.putInt("position", arg0);
			args.putParcelableArrayList("matches", leagues.get(arg0).matches);
			fragment.setArguments(args);
			
			return fragment;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return leagues.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			super.destroyItem(container, position, object);
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			background = (HorizontalScrollView) rootView.findViewById(R.id.background_scroll_view);
			return rootView;
		}
	}
	
	
	

}
