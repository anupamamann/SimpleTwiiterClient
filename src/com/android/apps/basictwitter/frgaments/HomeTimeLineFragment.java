package com.android.apps.basictwitter.frgaments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.apps.basictwitter.EndlessScrollListener;
import com.android.apps.basictwitter.TwitterApplication;
import com.android.apps.basictwitter.TwitterClient;
import com.android.apps.basictwitter.models.Tweet;
import com.android.apps.basictwitter.models.Tweet.tweetType;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class HomeTimeLineFragment extends TweetsListFragments {

	private TwitterClient client;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		client = TwitterApplication.getRestClient();
		Map<String, String>map = new HashMap<String, String>();
		map.put("since_id","1");
		populateTimeLine(map);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View v = super.onCreateView(inflater, container, savedInstanceState);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				Map<String, String>map = new HashMap<String, String>();

				if(page>2){
					//Toast.makeText(TimelineActivity.this, tweets.get(tweets.size() -1).getUid()+"", Toast.LENGTH_LONG).show();
					map.put("max_id",tweets.get(tweets.size() -1).getUid()+"");
					populateTimeLine(map);

				}
			
			}
		});
		
		lvTweets.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				aTweets.clear();
				Map<String, String>map = new HashMap<String, String>();
				map.put("since_id","1");
				populateTimeLine(map);

			}
		});
		
		return v;
	}

	public void populateTimeLine(Map<String, String>map){

		
		client.getHomeTimeline(map, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(JSONArray json) {
				
				addAll(Tweet.fromJsonArray(json, tweetType.HOME));
				Log.d("debug", json.toString());
				
				//lvTweets.onRefreshComplete(); 
			}

			@Override
			public void onFailure(Throwable e) {
				// TODO Auto-generated method stub
				//Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
				Log.d("debug", e.toString());
				addAll((ArrayList<Tweet>)Tweet.getAll(tweetType.HOME));
			}
		});
	}


}
