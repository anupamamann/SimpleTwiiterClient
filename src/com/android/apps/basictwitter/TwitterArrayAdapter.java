package com.android.apps.basictwitter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TwitterArrayAdapter extends ArrayAdapter<Tweet>{
	Tweet tweet;
	public TwitterArrayAdapter(Context context, List<Tweet> tweets){
		super(context, 0, tweets);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		 tweet = getItem(position);
		View v;
		if (convertView == null) {
	          v = LayoutInflater.from(getContext()).inflate(R.layout.tweet_item, parent, false);
	       }else{
	    	   v = convertView;
	       }
	       // Lookup view for data population
	       ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
	       TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
	       TextView tvName = (TextView) v.findViewById(R.id.tvName);
	       TextView tvUsername = (TextView) v.findViewById(R.id.tvUsername);
	       TextView tvTimeStamp = (TextView) v.findViewById(R.id.tvTimeStamp);
	       ivProfileImage.setImageResource(android.R.color.transparent);
	       ImageLoader imageLoader = ImageLoader.getInstance();
	       imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
	       ivProfileImage.setContentDescription(tweet.getUser().getScreenName());
	       // Populate the data into the template view using the data object
	       tvBody.setText(tweet.getBody());
	       tvTimeStamp.setText(tweet.getRelativeTimeAgo(tweet.getCreatedAt()));
	       tvName.setText(tweet.getUser().getName());
	       tvUsername.setText(" @" + tweet.getUser().getScreenName());
	    
	       // Button btnReply = (Button)v.findViewById(R.id.btnReply);
	       ivProfileImage.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//extract user from the item 
					String userHandle = (String)v.getContentDescription();
					//call getUser timeline and populate it in 
					Intent intent = new Intent(getContext(), ProfileActivity.class);
					intent.putExtra("userHandle", userHandle);
					getContext().startActivity(intent);
						
				}
			});
	       
	       return v;
	}
}
