package com.android.apps.basictwitter;

import javax.crypto.spec.IvParameterSpec;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends Activity {
	ImageView imgProfile; 
	TextView tvBody; 
	TextView tvUsername; 
	TextView tvName; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		tvUsername = (TextView)findViewById(R.id.tvUsername);
		tvBody = (TextView)findViewById(R.id.tvBody);
		tvName = (TextView)findViewById(R.id.tvName);
		imgProfile = (ImageView)findViewById(R.id.ivProfileImage);
		
		tvUsername.setText(getIntent().getStringExtra("username"));
		tvBody.setText(getIntent().getStringExtra("body"));
		tvName.setText(getIntent().getStringExtra("name"));
		imgProfile.setImageResource(android.R.color.transparent);
	       ImageLoader imageLoader = ImageLoader.getInstance();
	       imageLoader.displayImage(getIntent().getStringExtra("image"), imgProfile);
		finish();
		
	}
}
