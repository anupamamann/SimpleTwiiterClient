package com.android.apps.basictwitter.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "User")
public class User extends Model implements Serializable{

	
	private static final long serialVersionUID = 2213022504686541503L;
	@Column(name = "Name")
	private String name;
	@Column(name = "UserId")
	private long uid;
	@Column(name = "ScreenName")
	private String screenName;
	@Column(name = "ProfileImageUrl")
	private String profileImageUrl;
	@Column(name = "Tagline")
	private String tagline;
	@Column(name = "Followers")
	private int followersCount;
	@Column(name = "Following")
	private int friendsCount;
	
	public User(){
		super();
	}
	
	public static User fromJson(JSONObject json) {
		User u = new User();
		try{
		u.name = json.getString("name");
		u.uid = json.getLong("id");
		u.screenName = json.getString("screen_name");
		u.profileImageUrl = json.getString("profile_image_url");
		u.tagline = json.getString("description");
		u.followersCount = json.getInt("followers_count");
		u.friendsCount = json.getInt("friends_count");
		u.save();
		}catch(JSONException e){
			e.printStackTrace();
			return null;
			
		}
		return u;
	}


	public String getName() {
		return name;
	}


	public long getUid() {
		return uid;
	}


	public String getScreenName() {
		return screenName;
	}


	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	
	public String getTagline(){
		return tagline;
	}


	public int getFollowersCount() {
		return followersCount;
	}


	public int getFriendsCount() {
		return friendsCount;
	}

}
