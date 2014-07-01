package com.android.apps.basictwitter;

import org.json.JSONObject;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.android.apps.basictwitter.frgaments.UserTimeLineFragment;
import com.android.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {
	private TwitterClient client; 
	protected String userHandle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		ActionBar ab = getActionBar(); 
		ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#2ECCFA"));     
		ab.setBackgroundDrawable(colorDrawable);

		client = TwitterApplication.getRestClient();

		//extract handle Name for which to pull the timeline

		this.userHandle = getIntent().getStringExtra("userHandle");
		UserTimeLineFragment userFragment = (UserTimeLineFragment)getSupportFragmentManager().findFragmentById(R.id.frUserProfile);
		userFragment.setUserHandle(userHandle);
		loadProfileInfo();

	}

	public String getUserHandle(){
		return userHandle;
	}
	public void loadProfileInfo(){

		if(userHandle==null || userHandle.trim().length() ==0){

			client.getMyProfile(new JsonHttpResponseHandler(){

				@Override
				public void onSuccess(JSONObject json) {
					User u = User.fromJson(json);
					getActionBar().setTitle("@"+u.getScreenName());
					populateProfileHeader(u);
				}
				
				@Override
				public void onFailure(Throwable e) {
					Toast.makeText(ProfileActivity.this,  "Error Populating User Info", Toast.LENGTH_LONG).show();
					Log.d(e.toString());
				}
			});
		}else{
			
			
			client.getUserInfo(userHandle, new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(JSONObject json) {
					User u = User.fromJson(json);
					getActionBar().setTitle("@"+u.getScreenName());
					populateProfileHeader(u);
				}
				
				@Override
				public void onFailure(Throwable e) {
					Toast.makeText(ProfileActivity.this,  "Error Populating User Info", Toast.LENGTH_LONG).show();
					Log.d(e.toString());
				}
			});
		}
	}		
	public void populateProfileHeader(User user){

		TextView tvName = (TextView)findViewById(R.id.tvName);
		TextView tvTagline = (TextView)findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView)findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView)findViewById(R.id.tvFollowing);
		ImageView ivProfileImage = (ImageView)findViewById(R.id.ivProfileImage);
		tvName.setText(user.getName());
		tvTagline.setText(user.getTagline());
		tvFollowers.setText(user.getFollowersCount() + " Followers");
		tvFollowing.setText(user.getFriendsCount() + " Following");
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);
	}



}
