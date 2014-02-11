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

}
