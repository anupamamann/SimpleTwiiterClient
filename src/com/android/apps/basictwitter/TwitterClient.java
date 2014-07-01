package com.android.apps.basictwitter;

import java.util.Map;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "Ajr8cgK5IPfuUlDSrHSEZm3gI";       // Change this
    public static final String REST_CONSUMER_SECRET = "t66a0iATwAgbXB1XreisCRDcOjbRzRUxDeuf5lFbFiX8E0Sdot"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://cpbasictweets"; // Change this (here and in manifest)
    
    //TODO:declare api end point here
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    public void getHomeTimeline(Map<String, String>map, AsyncHttpResponseHandler handler){
    	
    	String apiUrl = getApiUrl("statuses/home_timeline.json");
    	RequestParams params = new RequestParams();
    	//params.put("since_id", "1");
    	for(Map.Entry<String, String> entry : map.entrySet()){
    		params.put(entry.getKey(), entry.getValue());
    	}
    	client.get(apiUrl,params,handler);
    }
    
    
    //publish tweets 
    public void postTweet(String tweet, AsyncHttpResponseHandler handler){
    	String apiUrl = getApiUrl("/statuses/update.json");
    	//post call 
    	RequestParams params = new RequestParams();
    	params.put("status", tweet);
    	client.post(apiUrl, params, handler);
    }

	public void getMentionsTimeline(Map<String, String> map, AsyncHttpResponseHandler handler) {
		//statuses/mentions_timeline.json
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
    	//RequestParams params = new RequestParams();
    	//for(Map.Entry<String, String> entry : map.entrySet()){
    		//params.put(entry.getKey(), entry.getValue());
    		
    	//}
    	
    	client.get(apiUrl,null,handler);
	}
	
	
	public void getMyProfile(AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("account/verify_credentials.json");
		client.get(apiUrl,null,handler);
	}
    
	public void getUserInfo(String userHandle,AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("users/show.json"); 
		RequestParams params = new RequestParams();
		params.put("screen_name", userHandle);
		client.get(apiUrl,params,handler);
	}
	
	public void getUserTimeline(AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		client.get(apiUrl,null,handler);
	}

	public void getUserTimeline(String userHandle,AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		params.put("screen_name", userHandle);
		client.get(apiUrl,params,handler);
		
	}
   
}