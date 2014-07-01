package com.android.apps.basictwitter;

import java.util.Map;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.apps.basictwitter.ComposeTweetDialog.ComposeDialogListener;
import com.android.apps.basictwitter.frgaments.HomeTimeLineFragment;
import com.android.apps.basictwitter.frgaments.MentionsTimeLineFragment;
import com.android.apps.basictwitter.frgaments.TweetsListFragments;
import com.android.apps.basictwitter.listeners.FragmentTabListener;
import com.android.apps.basictwitter.models.Tweet;
import com.android.apps.basictwitter.models.Tweet.tweetType;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends FragmentActivity implements ComposeDialogListener{
	private TweetsListFragments fragmentTweetsList;

	Map<String, String>params;
	final int tweet_count=20;
	public static long MAX_ID;
	TwitterClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		ActionBar ab = getActionBar(); 
		ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#2ECCFA"));     
		ab.setBackgroundDrawable(colorDrawable);

		setupTabs();

	}

	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tabHome = actionBar
				.newTab()
				.setText("Home")
				.setIcon(R.drawable.ic_home)
				.setTag("HomeTimelineFragment")
				.setTabListener(
						new FragmentTabListener<HomeTimeLineFragment>(R.id.flContainer, this, "home",
								HomeTimeLineFragment.class));

		actionBar.addTab(tabHome);
		actionBar.selectTab(tabHome);

		Tab tabMentions = actionBar
				.newTab()
				.setText("Mentions")
				.setIcon(R.drawable.ic_mentions)
				.setTag("MentionsTimelineFragment")
				.setTabListener(
						new FragmentTabListener<MentionsTimeLineFragment>(R.id.flContainer, this, "mentions",
								MentionsTimeLineFragment.class));

		actionBar.addTab(tabMentions);
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

	public void onProfile(MenuItem mi){
		Intent i = new Intent(this, ProfileActivity.class);
		startActivity(i);

	}


	@Override
	public void onFinishComposeDialog(String userTweet) {
		//add the tweet into the adapter
		client = TwitterApplication.getRestClient();
		client.postTweet(userTweet, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(JSONObject json) {
				Tweet tweet = Tweet.fromJson(json, tweetType.USER);
				if(tweet !=null)
				{		
					fragmentTweetsList =  (TweetsListFragments)getSupportFragmentManager().findFragmentById(R.id.flContainer);
					fragmentTweetsList.getAdapter().insert(tweet, 0);
				}
			}

			@Override
			public void onFailure(Throwable e) {
				Toast.makeText(TimelineActivity.this, "Something went horribly wrong", Toast.LENGTH_LONG).show();
				Log.d("debug", e.toString());
			}
		});



	}
}
