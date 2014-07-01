package com.android.apps.basictwitter.frgaments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.apps.basictwitter.TwitterApplication;
import com.android.apps.basictwitter.TwitterClient;
import com.android.apps.basictwitter.models.Tweet;
import com.android.apps.basictwitter.models.Tweet.tweetType;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsTimeLineFragment extends TweetsListFragments {

	private TwitterClient client;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		client = TwitterApplication.getRestClient();
		Map<String, String>map = new HashMap<String, String>();
		map.put("since_id","1");
		populateTimeLine(map);
	}

	public void populateTimeLine(Map<String, String>map){

		client.getMentionsTimeline(map, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(JSONArray json) {
				//Toast.makeText(getActivity(), json.toString(), Toast.LENGTH_LONG).show();
				addAll(Tweet.fromJsonArray(json, tweetType.MENTION));
				Log.d("debug", json.toString());
				lvTweets.onRefreshComplete(); 
			}

			@Override
			public void onFailure(Throwable e) {
				// TODO Auto-generated method stub
				//Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
				addAll((ArrayList<Tweet>)Tweet.getAll(tweetType.MENTION));
				Log.d("debug", e.toString());
			}
		});
	}
}
