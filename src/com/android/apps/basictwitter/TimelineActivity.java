package com.android.apps.basictwitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.apps.basictwitter.ComposeTweetDialog.ComposeDialogListener;
import com.android.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TimelineActivity extends FragmentActivity implements ComposeDialogListener{
	private TwitterClient client;
	ArrayList<Tweet> tweets;
	ArrayAdapter<Tweet> aTweets;
	private PullToRefreshListView lvTweets;
	Map<String, String>params;
	final int tweet_count=20;
	public static long MAX_ID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApplication.getRestClient();
		lvTweets = (PullToRefreshListView)findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		aTweets = new TwitterArrayAdapter(this,  tweets);
		lvTweets.setAdapter(aTweets);
		Map<String, String>map = new HashMap<String, String>();
		map.put("since_id","1");
		populateTimeLine(map);
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				Map<String, String>map = new HashMap<String, String>();
				
				if(page>2){
					//Toast.makeText(TimelineActivity.this, tweets.get(tweets.size() -1).getUid()+"", Toast.LENGTH_LONG).show();
					map.put("max_id",tweets.get(tweets.size() -1).getUid()+"");
					populateTimeLine(map);
				
			}
				//else{
				//Toast.makeText(TimelineActivity.this, page+"", Toast.LENGTH_LONG).show();
				//map.put("since_id","1");
				//populateTimeLine(map);
			//}
			}
		});
		
		lvTweets.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				Map<String, String>map = new HashMap<String, String>();
				map.put("since_id","1");
				populateTimeLine(map);
				
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.timeline, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	public void onTweet(MenuItem mi){
		FragmentManager fm = getSupportFragmentManager();
		ComposeTweetDialog composeDialog = ComposeTweetDialog.newInstance("Compose new Tweet");
		composeDialog.show(fm, "compose_dialog");
	}
	
	
	
	public void populateTimeLine(Map<String, String>map){
		//aTweets.clear();
		client.getHomeTimeline(map,new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONArray json) {
				aTweets.addAll(Tweet.fromJsonArray(json));
				Log.d("debug", json.toString());
				lvTweets.onRefreshComplete(); 
			}
			
			@Override
			public void onFailure(Throwable e) {
				// TODO Auto-generated method stub
				Toast.makeText(TimelineActivity.this, e.toString(), Toast.LENGTH_LONG).show();
				Log.d("debug", e.toString());
			}
		});
	}

	@Override
	public void onFinishComposeDialog(String text) {
		//get Tweet, call compose
		
		
		client.postTweet(text, new JsonHttpResponseHandler(){
			
			@Override
				public void onSuccess(JSONObject json) {
					Tweet newTweet = Tweet.fromJson(json);
					//Toast.makeText(TimelineActivity.this, newTweet.toString(), Toast.LENGTH_LONG).show();
					if(newTweet !=null){
						aTweets.insert(newTweet, 0);
					}
					
					//populateTimeLine();
								
				}
			
			@Override
			public void onFailure(Throwable e) {
				Toast.makeText(TimelineActivity.this, e.toString(), Toast.LENGTH_LONG).show();
				Log.d("debug", e.toString());
			}
		});
		
	}
}
