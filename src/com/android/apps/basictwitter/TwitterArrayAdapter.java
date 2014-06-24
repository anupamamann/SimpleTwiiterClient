package com.android.apps.basictwitter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TwitterArrayAdapter extends ArrayAdapter<Tweet>{

	public TwitterArrayAdapter(Context context, List<Tweet> tweets){
		super(context, 0, tweets);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Tweet tweet = getItem(position);
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
	       
	       // Populate the data into the template view using the data object
	       tvBody.setText(tweet.getBody());
	       tvTimeStamp.setText(tweet.getRelativeTimeAgo(tweet.getCreatedAt()));
	       tvName.setText(tweet.getUser().getName());
	       tvUsername.setText(" @" + tweet.getUser().getScreenName());
	        
	       return v;
	}
}
