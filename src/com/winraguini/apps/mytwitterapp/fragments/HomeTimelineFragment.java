package com.winraguini.apps.mytwitterapp.fragments;


import org.json.JSONArray;

import android.util.Log;
import android.widget.Toast;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.winraguini.apps.mytwitterapp.MyTwitterApp;

public class HomeTimelineFragment extends TweetsListFragment {
	
	public void getTweets() {
		MyTwitterApp.getRestClient().getHomeTimeline(lastTweet, new JsonHttpResponseHandler() {
			public void onSuccess(JSONArray jsonTweets) {
				processTweets(jsonTweets);				
			}
			
			public void onFailure(java.lang.Throwable e) {
				Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
				Log.d("DEBUG", "Error: " + e.getMessage());
			}
		});
	}

}
