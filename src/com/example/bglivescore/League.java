package com.example.bglivescore;

import java.io.Serializable;
import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;


public class League implements Parcelable{
	String title;
	ArrayList<Match> matches;
	
	public League(){
		matches = new ArrayList<Match>();
	}

	public League(Parcel source) {
		// TODO Auto-generated constructor stub
		this.title = source.readString();
		this.matches = source.createTypedArrayList(Match.CREATOR);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(title);
		dest.writeTypedList(matches);
	}
	
	public static final Parcelable.Creator<League> CREATOR = new Parcelable.Creator<League>() {

		@Override
		public League createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new League(source);
		}

		@Override
		public League[] newArray(int size) {
			// TODO Auto-generated method stub
			return new League[size];
		}
	};
}