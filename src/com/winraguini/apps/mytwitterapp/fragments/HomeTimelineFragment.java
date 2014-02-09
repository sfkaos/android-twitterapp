package com.winraguini.apps.mytwitterapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.winraguini.apps.mytwitterapp.MyTwitterApp;
import com.winraguini.apps.mytwitterapp.R;
import com.winraguini.apps.mytwitterapp.models.EndlessScrollListener;
import com.winraguini.apps.mytwitterapp.models.Tweet;

public class HomeTimelineFragment extends TweetsListFragment {
	Tweet lastTweet;
	ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	TweetsListFragment fragmentTweets;
	ListView lvTweets;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.d("DEBUG", "onCreating stuff");
        // Attach the listener to the AdapterView onCreate
		//adapter = new TweetsAdapter(getBaseContext(), tweets);	
		//lvTweets = (ListView) getActivity().findViewById(R.id.lvTweets);

		//fragmentTweets = (TweetsListFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentTweets);
		
		getTweets();
	}
	
	public void getTweets() {
		MyTwitterApp.getRestClient().getHomeTimeline(lastTweet, new JsonHttpResponseHandler() {
			public void onSuccess(JSONArray jsonTweets) {		
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
