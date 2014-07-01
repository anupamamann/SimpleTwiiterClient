package com.android.apps.basictwitter.frgaments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.apps.basictwitter.TwitterApplication;
import com.android.apps.basictwitter.TwitterClient;
import com.android.apps.basictwitter.models.Tweet;
import com.android.apps.basictwitter.models.Tweet.tweetType;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimeLineFragment extends TweetsListFragments {
	private TwitterClient client;
	String userHandle;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApplication.getRestClient();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		populateTimeLine();
	}
	
	public void setUserHandle(String userHandle){
		this.userHandle = userHandle;
	}

	public void populateTimeLine(){
		
		if(userHandle==null || userHandle.trim().length()==0){

			client.getUserTimeline(new JsonHttpResponseHandler(){

				@Override
				public void onSuccess(JSONArray json) {
					//Toast.makeText(getActivity(), json.toString(), Toast.LENGTH_LONG).show();
					//Log.d("debug", json.toString());
					addAll(Tweet.fromJsonArray(json, tweetType.USER));
										
				}

				@Override
				public void onFailure(Throwable e) {
					//pull stale timeline from storage
					Log.d("debug", e.toString());
					addAll((ArrayList<Tweet>)Tweet.getAll(tweetType.USER));
					
					
				}
			});
		}else{
			
			client.getUserTimeline(userHandle, new JsonHttpResponseHandler(){
				
				@Override
				public void onSuccess(JSONArray json) {
					addAll(Tweet.fromJsonArray(json, tweetType.USER));
					
				}

				@Override
				public void onFailure(Throwable e) {
					// TODO Auto-generated method stub
					Log.d("debug", e.toString());
					addAll((ArrayList<Tweet>)Tweet.getAll(tweetType.USER));
				}
				
			});

		}
	}
}
