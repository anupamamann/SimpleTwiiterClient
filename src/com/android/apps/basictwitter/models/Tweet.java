package com.android.apps.basictwitter.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.format.DateUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ForeignKeyAction;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;


@Table(name = "Tweet")
public class Tweet extends Model implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7824043443598174160L;
	@Column(name = "body")
	private String body;
	@Column(name = "t_id",  unique = true)
	private long tId;
	@Column(name = "created_at")
	private String createdAt;
	@Column(name = "User", onUpdate = ForeignKeyAction.CASCADE, onDelete = ForeignKeyAction.CASCADE)
	private User user; 
	@Column(name = "tweet_type")
	private tweetType tweetType;
	
	public Tweet(){
		super();
	}
	
	public enum tweetType{ HOME, MENTION, USER};
	
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public long getUid() {
		return tId;
	}

	public void setUid(long tId) {
		this.tId = tId;
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

	public static ArrayList<Tweet> fromJsonArray(JSONArray jsonArray, tweetType type) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());
		for(int i = 0; i<jsonArray.length(); i++ ){
			try{
				Tweet tweet = Tweet.fromJson(jsonArray.getJSONObject(i), type);
				if(tweet!=null)
					tweets.add(tweet);
				
			}catch(JSONException e){
				e.printStackTrace();
				continue;
			}
			
		}
		
		return tweets;
	}
	
	public static Tweet fromJson(JSONObject jsonObject, tweetType type){
		Tweet tweet = new Tweet();
		//get tweets from json 
		try{
			tweet.body = jsonObject.getString("text");
			tweet.tId = jsonObject.getLong("id");
			tweet.createdAt = jsonObject.getString("created_at");
			tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
			tweet.tweetType = type;
			tweet.save();
			
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
	
	
	 public static List<Tweet> getAll(tweetType Type) {
	        // This is how you execute a query
	        return new Select()
	          .from(Tweet.class)
	          .where("tweet_type = ?", Type)
	          .orderBy("created_at DESC")
	          .execute();
	    }
	
}
