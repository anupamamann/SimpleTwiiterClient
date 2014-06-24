package com.android.apps.basictwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

public class User extends Model{

	@Column(name = "name")
	private String name;
	@Column(name = "uid")
	private long uid;
	@Column(name = "screenName")
	private String screenName;
	@Column(name = "profileImageUrl")
	private String profileImageUrl;
	
	
	public static User fromJson(JSONObject json) {
		User u = new User();
		try{
		u.name = json.getString("name");
		u.uid = json.getLong("id");
		u.screenName = json.getString("screen_name");
		u.profileImageUrl = json.getString("profile_image_url");
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

}
