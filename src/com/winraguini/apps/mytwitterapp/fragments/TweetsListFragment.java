package com.winraguini.apps.mytwitterapp.fragments;

import java.util.ArrayList;

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

public class TweetsListFragment extends Fragment {
			TweetsAdapter adapter;
			ListView lvTweets;
			HomeTimelineFragment timelineFragment;
			Tweet lastTweet;
			
			public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				// Defines the xml file for the fragment
				View view = inflater.inflate(R.layout.frament_tweets_list, container, false);
				// Setup handles to view objects here		      
				return view;
		    }
			
			public void onActivityCreated(Bundle savedInstanceState)
			{
				super.onActivityCreated(savedInstanceState);
				ArrayList<Tweet> tweets = new ArrayList<Tweet>();
				//adapter.addAll(Tweet.fromJson(jsonTweets));
				adapter = new TweetsAdapter(getActivity(), tweets);				
				lvTweets = (ListView) getActivity().findViewById(R.id.lvTweets);
				lvTweets.setAdapter(adapter);
				lvTweets.setOnScrollListener(new EndlessScrollListener() {
			        @Override
			        public void onLoadMore(int page, int totalItemsCount) {
			            // Triggered only when new data needs to be appended to the list
			            // Add whatever code is needed to append new items to your AdapterView
			            customLoadMoreDataFromApi(page); 
			            //customLoadMoreDataFromApi(totalItemsCount); 
			        }
			     });
				//lastTweet = tweets.get(tweets.size() - 1);				
				//Log.d("DEBUG", jsonTweets.toString());
			}
			
			public TweetsAdapter getAdapter() {
				return adapter;
			}
			
			public void customLoadMoreDataFromApi(int totalItemsCount)
			{
				//timelineFragment.getTweets();
				Log.d("DEBUG", "loading more data...");
//				(TimelineActivity) getActivity().getTweets();
			}
			
			
			
}
