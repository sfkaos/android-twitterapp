package com.winraguini.apps.mytwitterapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.winraguini.apps.mytwitterapp.models.EndlessScrollListener;
import com.winraguini.apps.mytwitterapp.models.Tweet;


public class TimelineActivity extends Activity {
	ListView lvTweets;
	Tweet lastTweet;
	TweetsAdapter adapter;
	ArrayList<Tweet> tweets = new ArrayList<Tweet>();;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		Log.d("DEBUG", "onCreating stuff");
        // Attach the listener to the AdapterView onCreate
		adapter = new TweetsAdapter(getBaseContext(), tweets);
		lvTweets.setAdapter(adapter);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
        @Override
        public void onLoadMore(int page, int totalItemsCount) {
            // Triggered only when new data needs to be appended to the list
            // Add whatever code is needed to append new items to your AdapterView
            //customLoadMoreDataFromApi(page); 
            customLoadMoreDataFromApi(totalItemsCount); 
        }
        });
		getTweets();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	public void onComposeAction(MenuItem m) {
		Intent i = new Intent(this, ComposeActivity.class);
		startActivityForResult(i, 1);
	}
	
	public void onRefreshAction(MenuItem m) {
		refreshTimeline();
	}
	
	public void refreshTimeline() {
		adapter.clear();
		lastTweet = null;
		getTweets();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  // REQUEST_CODE is defined above
		  if (resultCode == RESULT_OK && requestCode == 1) {
			  String tweet = (String) data.getStringExtra(Tweet.TWEET_KEY);
			  postStatusUpdate(tweet);			  					  			  
		  }
	}
	
	public void postStatusUpdate(String tweet)
	{
		MyTwitterApp.getRestClient().postStatusUpdate(tweet, new JsonHttpResponseHandler() {
			public void onSuccess(JSONObject statusUpdate) {				
												
				Log.d("DEBUG", statusUpdate.toString());
				refreshTimeline();
			}
			
			public void onFailure(java.lang.Throwable e) {
				Toast.makeText(getBaseContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
				Log.d("DEBUG", "Error: " + e.getMessage());
				refreshTimeline();
			}
		});
	}
	
	
	public void customLoadMoreDataFromApi(int totalItemsCount)
	{
		Log.d("DEBUG", "loading more data...");
		getTweets();
	}
	
	public void getTweets() {
		MyTwitterApp.getRestClient().getHomeTimeline(lastTweet, new JsonHttpResponseHandler() {
			public void onSuccess(JSONArray jsonTweets) {				
				adapter.addAll(Tweet.fromJson(jsonTweets));
				lastTweet = tweets.get(tweets.size() - 1);				
				Log.d("DEBUG", jsonTweets.toString());
			}
			
			public void onFailure(java.lang.Throwable e) {
				Toast.makeText(getBaseContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
				Log.d("DEBUG", "Error: " + e.getMessage());
			}
		});
	}
	
}
