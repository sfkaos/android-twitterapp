package com.winraguini.apps.mytwitterapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.winraguini.apps.mytwitterapp.MyTwitterApp;
import com.winraguini.apps.mytwitterapp.models.Tweet;

public class MentionsFragment extends TweetsListFragment {
	Tweet lastTweet;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		lastTweet = new Tweet();
		getTweets();
	}
	
	public void getTweets() {
		MyTwitterApp.getRestClient().getMentions(lastTweet, new JsonHttpResponseHandler() {
			public void onSuccess(JSONArray jsonTweets) {
				Log.d("DEBUG", jsonTweets.toString());
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
				getAdapter().addAll(tweets);
			}
			
			public void onFailure(java.lang.Throwable e) {
				Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
				Log.d("DEBUG", "Error: " + e.getMessage());
			}
		});
	}
	
	public void customLoadMoreDataFromApi(int totalItemsCount)
	{
		getTweets();
	}

}
