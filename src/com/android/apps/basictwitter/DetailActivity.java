package com.android.apps.basictwitter;



import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DetailActivity extends FragmentActivity {
	ImageView imgProfile; 
	TextView tvBody; 
	TextView tvUsername; 
	TextView tvName; 
	Button btReply;
	Tweet tweet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		//ActionBar ab = getActionBar(); 
		//ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#ffffff"));     
		//ab.setBackgroundDrawable(colorDrawable);
		//	Toast.makeText(DetailActivity.this, "item clicked", Toast.LENGTH_LONG).show();

		tvUsername = (TextView)findViewById(R.id.tvUsername);
		tvBody = (TextView)findViewById(R.id.tvBody);
		tvName = (TextView)findViewById(R.id.tvName);
		imgProfile = (ImageView)findViewById(R.id.ivProfileImage);
		btReply = (Button)findViewById(R.id.btnReply);

		tweet = (Tweet)getIntent().getSerializableExtra("tweet");
		tvUsername.setText(tweet.getUser().getScreenName());
		tvBody.setText(tweet.getBody());
		tvName.setText(tweet.getUser().getName());
		imgProfile.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), imgProfile);
		btReply.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//inflate the fragment passing the uid of the tweet
				long in_reply_to_status_id = tweet.getUid();
				FragmentManager fmManager = getSupportFragmentManager();
				ComposeTweetDialog replyDialog = ComposeTweetDialog.newInstance("ReTweet");
				replyDialog.show(fmManager, "TweetReply");
				
			}
		});
	}
}
