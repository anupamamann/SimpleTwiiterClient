package com.android.apps.basictwitter.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.format.DateUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


@Table(name = "Tweet")
public class Tweet extends Model{

	@Column(name = "body")
	private String body;
	@Column(name = "uid",  unique = true)
	private long uid;
	@Column(name = "createdAt")
	private String createdAt;

	private User user; 
	
	

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static ArrayList<Tweet> fromJsonArray(JSONArray jsonArray) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());
		for(int i = 0; i<jsonArray.length(); i++ ){
			try{
				Tweet tweet = Tweet.fromJson(jsonArray.getJSONObject(i));
				if(tweet!=null)
					tweets.add(tweet);
				
			}catch(JSONException e){
				e.printStackTrace();
				continue;
			}
			
		}
		
		return tweets;
	}
	
	public static Tweet fromJson(JSONObject jsonObject){
		Tweet tweet = new Tweet();
		//get tweets from json 
		try{
			tweet.body = jsonObject.getString("text");
			tweet.uid = jsonObject.getLong("id");
			tweet.createdAt = jsonObject.getString("created_at");
			tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
			
			
		}catch(JSONException e){
			e.printStackTrace();
			return null;
			
		}
		return tweet;
		
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getBody()+" -" + getUser().getScreenName();
	}
	
	public String getRelativeTimeAgo(String rawJsonDate) {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);
	 
		String relativeDate = "";
		try {
			long dateMillis = sf.parse(rawJsonDate).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	 
		return relativeDate;
	}
	
}
