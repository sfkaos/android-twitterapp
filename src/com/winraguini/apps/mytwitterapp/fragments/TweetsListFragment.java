package com.winraguini.apps.mytwitterapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.winraguini.apps.mytwitterapp.ProfileActivity;
import com.winraguini.apps.mytwitterapp.R;
import com.winraguini.apps.mytwitterapp.TweetsAdapter;
import com.winraguini.apps.mytwitterapp.models.EndlessScrollListener;
import com.winraguini.apps.mytwitterapp.models.Tweet;
import com.winraguini.apps.mytwitterapp.models.User;

public abstract class TweetsListFragment extends Fragment {
			TweetsAdapter adapter;
			ListView lvTweets;
			HomeTimelineFragment timelineFragment;
			Tweet lastTweet;
			ArrayList<Tweet> tweets;
			User currentUser;
			
			@Override
			public void onCreate(Bundle savedInstanceState) {
				// TODO Auto-generated method stub
				super.onCreate(savedInstanceState);
				tweets = new ArrayList<Tweet>();
				lastTweet = null;
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
	        	}
				
				lvTweets = (ListView) getActivity().findViewById(R.id.lvTweets);
				lvTweets.setAdapter(adapter);
				setupListViewListener();
				lvTweets.setOnScrollListener(new EndlessScrollListener() {
			        @Override
			        public void onLoadMore(int page, int totalItemsCount) {
			        	getTweets();
			        }
			     });
			}
			
			public void setupListViewListener() {
				lvTweets.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
						// TODO Auto-generated method stub
						Intent profileIntent = new Intent(getActivity(), ProfileActivity.class);
						Tweet chosenTweet = tweets.get(pos);
						profileIntent.putExtra("User", chosenTweet.getUser());
						startActivity(profileIntent);
					}
				});				
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
