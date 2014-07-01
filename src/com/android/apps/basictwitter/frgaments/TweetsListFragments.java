package com.android.apps.basictwitter.frgaments;


import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.apps.basictwitter.DetailActivity;
import com.android.apps.basictwitter.R;
import com.android.apps.basictwitter.TwitterArrayAdapter;
import com.android.apps.basictwitter.models.Tweet;

import eu.erikw.PullToRefreshListView;

public class TweetsListFragments extends Fragment {

	ArrayList<Tweet> tweets;
	TwitterArrayAdapter aTweets;
	protected PullToRefreshListView lvTweets;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tweets = new ArrayList<Tweet>();
		aTweets = new TwitterArrayAdapter(getActivity(),  tweets);
		
		//aTweets.clear();

	
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		lvTweets = (PullToRefreshListView)v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);
		setListeners();
		return v;

	}
	
	
	public void setListeners(){
		lvTweets.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//Toast.makeText(TimelineActivity.this, "item clicked", Toast.LENGTH_LONG).show();
				Tweet tweet = aTweets.getItem(position);
				Intent intent = new Intent(getActivity(), DetailActivity.class);
				intent.putExtra("tweet", tweet);
				
				startActivity(intent);
			}
			
		});
		
		
	}

	
	public void addAll(ArrayList<Tweet> tweets){
		aTweets.addAll(tweets);
	}
	
	public TwitterArrayAdapter getAdapter(){
		return aTweets;
	}
	




}
