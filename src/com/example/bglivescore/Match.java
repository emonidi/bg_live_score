package com.example.bglivescore;

import android.os.Parcel;
import android.os.Parcelable;

public class Match implements Parcelable{
	String host;
	String guest;
	String score;
	
	
	public Match(){
		
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(host);
		dest.writeString(guest);
		dest.writeString(score);
	}
	
	
	public static final Parcelable.Creator<Match> CREATOR = new Parcelable.Creator<Match>() {

		@Override
		public Match createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Match(source);
		}

		@Override
		public Match[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Match[size];
		}
	};
	
	private Match(Parcel source) {
		// TODO Auto-generated constructor stub
		this.host = source.readString();
		this.guest = source.readString();
		this.score = source.readString();
	}
}	
