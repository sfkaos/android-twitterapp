package com.winraguini.apps.mytwitterapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.winraguini.apps.mytwitterapp.R;
import com.winraguini.apps.mytwitterapp.TweetsAdapter;
import com.winraguini.apps.mytwitterapp.models.EndlessScrollListener;
import com.winraguini.apps.mytwitterapp.models.Tweet;

public abstract class TweetsListFragment extends Fragment {
			TweetsAdapter adapter;
			ListView lvTweets;
			HomeTimelineFragment timelineFragment;
			Tweet lastTweet;
			ArrayList<Tweet> tweets;
			
			@Override
			public void onCreate(Bundle savedInstanceState) {
				// TODO Auto-generated method stub
				super.onCreate(savedInstanceState);
				tweets = new ArrayList<Tweet>();
				lastTweet = null;
				Log.d("DEBUG", "Refreshing lastTweet");
			}
			
			public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				// Defines the xml file for the fragment
				View view = inflater.inflate(R.layout.frament_tweets_list, container, false);
				// Setup handles to view objects here				
				return view;
		    }
			
			public void onActivityCreated(Bundle savedInstanceState)
			{
				super.onActivityCreated(savedInstanceState);
				tweets = new ArrayList<Tweet>();
				//adapter.addAll(Tweet.fromJson(jsonTweets));
				adapter = new TweetsAdapter(getActivity(), tweets);		
				
				getTweets();
				if (getAdapter().getCount() > 0) {
	        		lastTweet = adapter.getItem(adapter.getCount() - 1);//tweets.get(tweets.size() - 1);
	        		Log.d("DEBUG", "last tweet id is" + lastTweet.getId());
	        	}
				
				lvTweets = (ListView) getActivity().findViewById(R.id.lvTweets);
				lvTweets.setAdapter(adapter);
				lvTweets.setOnScrollListener(new EndlessScrollListener() {
			        @Override
			        public void onLoadMore(int page, int totalItemsCount) {
			            // Triggered only when new data needs to be appended to the list
			            // Add whatever code is needed to append new items to your AdapterView
			            //customLoadMoreDataFromApi(page); 
			            //customLoadMoreDataFromApi(totalItemsCount);
			        	getTweets();
//			        	if (tweets.size() > 0) {
//			        		Log.d("DEBUG", "tweets count is " + adapter.getCount());
//			        		lastTweet = adapter.getItem(adapter.getCount() - 1);//tweets.get(tweets.size() - 1);
//			        		Log.d("DEBUG", "last tweet id is" + lastTweet.getId());
//			        	}			        	
			        }
			     });
				//lastTweet = tweets.get(tweets.size() - 1);				
				//Log.d("DEBUG", jsonTweets.toString());
			}
			
			public TweetsAdapter getAdapter() {
				return adapter;
			}
			
			public void processTweets(JSONArray jsonTweets) {
				Log.d("DEBUG", jsonTweets.toString());
				ArrayList<Tweet> newTweets = Tweet.fromJson(jsonTweets);
				lastTweet = newTweets.get(newTweets.size() - 1);
				getAdapter().addAll(newTweets);
			}
			
			abstract void getTweets();			
			
}
