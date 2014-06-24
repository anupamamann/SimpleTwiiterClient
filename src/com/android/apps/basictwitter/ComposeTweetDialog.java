package com.android.apps.basictwitter;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ComposeTweetDialog extends DialogFragment{
	EditText etCompose;
	Button btnTweet;
	private View view;
	
	public interface ComposeDialogListener {
        void onFinishComposeDialog(String text);
    }
	
	public ComposeTweetDialog() {
		
	}
	
	public static ComposeTweetDialog newInstance(String title){
		ComposeTweetDialog comoseFrag = new ComposeTweetDialog();
		Bundle args = new Bundle();
		args.putString("title", title);
		comoseFrag.setArguments(args);
		return comoseFrag;
		  
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 view = inflater.inflate(R.layout.fragment_compose_tweet, container);
		String title = getArguments().getString("title", "Compose new Tweet");
		getDialog().setTitle(title);
		etCompose = (EditText)view.findViewById(R.id.etCompose);
		btnTweet = (Button)view.findViewById(R.id.btTweet);
		btnTweet.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//get tweet 
					String text = etCompose.getText().toString();
				//call compose
					ComposeDialogListener listener = (ComposeDialogListener)getActivity();
				listener.onFinishComposeDialog(text);
				dismiss();
			}
		});
		return view;
		
	}
	
	

}
