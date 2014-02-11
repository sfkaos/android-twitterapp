package com.winraguini.apps.mytwitterapp;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.winraguini.apps.mytwitterapp.models.Tweet;
import com.winraguini.apps.mytwitterapp.models.User;

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
    public static final String REST_CONSUMER_KEY = "UN9NWDT3GUOJcKqZgLSpAA";       // Change this
    public static final String REST_CONSUMER_SECRET = "hfQv5FiYkPEqQnbCJfnKsF3FjGLhNXvApY6y6XdzEHs"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://mytwitterapp"; // Change this (here and in manifest)
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    public void getVerifyCredentials(AsyncHttpResponseHandler handler)
    {
    	String url = getApiUrl("account/verify_credentials.json");
    	client.get(url, null, handler);
    }
    
    public void postStatusUpdate(String tweet, AsyncHttpResponseHandler handler)
    {
    	String url = getApiUrl("statuses/update.json");
    	RequestParams params = new RequestParams("status", tweet);
    	client.post(url, params, handler);
    }
    
    public void getHomeTimeline(Tweet lastTweet, AsyncHttpResponseHandler handler)
    {
    	String url = getApiUrl("statuses/home_timeline.json");
    	if (lastTweet != null) {
    		url = url + "?max_id=" + lastTweet.getId();
    	}
    	Log.d("DEBUG", "url: " + url);
    	client.get(url, null, handler);
    }
    
    // DEFINE METHODS for different API endpoints here
    public void getInterestingnessList(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("format", "json");
        client.get(apiUrl, params, handler);
    }
    
    //statuses/mentions_timeline.json
    public void getMentions(Tweet lastTweet, AsyncHttpResponseHandler handler)
    {
    	//User sinceID;
    	String url = getApiUrl("statuses/mentions_timeline.json");
    	if (lastTweet != null) {
    		url = url + "?max_id=" + lastTweet.getId();
    	}
    	Log.d("DEBUG", "url: " + url);
    	client.get(url, null, handler);
    }
    
    //statuses/mentions_timeline.json
    public void getUserTimeline(User user, Tweet lastTweet, AsyncHttpResponseHandler handler)
    {
    	//User sinceID;
    	String url = getApiUrl("statuses/user_timeline.json");
    	url = url + "?screen_name=" + user.getScreenName();
    	if (lastTweet != null) {
    		url = url + "&max_id=" + lastTweet.getId();
    	}
    	Log.d("DEBUG", "url: " + url);
    	client.get(url, null, handler);
    }
    
//    users/lookup.json
    public void getUserLookup(User user, AsyncHttpResponseHandler handler)
    {
    	String url = getApiUrl("users/lookup.json");
    	if (user != null) {
    		url = url + "?screen_name=" + user.getScreenName();
    	}
    	client.get(url, null, handler);
    }
    
    
    /* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
     * 	  i.e getApiUrl("statuses/home_timeline.json");
     * 2. Define the parameters to pass to the request (query or body)
     *    i.e RequestParams params = new RequestParams("foo", "bar");
     * 3. Define the request method and make a call to the client
     *    i.e client.get(apiUrl, params, handler);
     *    i.e client.post(apiUrl, params, handler);
     */
}