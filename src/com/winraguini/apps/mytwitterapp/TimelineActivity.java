package com.winraguini.apps.mytwitterapp;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.winraguini.apps.mytwitterapp.fragments.HomeTimelineFragment;
import com.winraguini.apps.mytwitterapp.fragments.MentionsFragment;
import com.winraguini.apps.mytwitterapp.models.Tweet;
import com.winraguini.apps.mytwitterapp.models.User;


public class TimelineActivity extends FragmentActivity implements TabListener {
	User u;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);		
		setupNavigationTabs();
	}

	private void setupNavigationTabs() {
		// TODO Auto-generated method stub
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		Tab tabHome = actionBar.newTab().setText("Home")
				.setTag("HomeTimelineFragment").setIcon(R.drawable.ic_home)
				.setTabListener(this);
		Tab tabMentions = actionBar.newTab().setText("Mentions")
				.setTag("MentionsTimelineFragment").setIcon(R.drawable.ic_mentions)
				.setTabListener(this);
		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		actionBar.selectTab(tabHome);		
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
		//adapter.clear();
//		lastTweet = null;
//		getTweets();
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

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = fragmentManager.beginTransaction();		
		if (tab.getTag() == "HomeTimelineFragment") {
			//set fragment to HomeTimeline
			fts.replace(R.id.frame_container, new HomeTimelineFragment());
		} else {
			//set fragment to Mentions
			fts.replace(R.id.frame_container, new MentionsFragment());
		}
		fts.commit();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	public void onProfileView(MenuItem mi) {
		MyTwitterApp.getRestClient().getVerifyCredentials(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject json) {
				u = User.fromJson(json);
				onUserInfo(u);
			}

		});
	}
	
	public void onUserInfo(User u) {
		Intent i = new Intent(this, ProfileActivity.class);	
		i.putExtra("User", u);
		startActivity(i);
	}
	
	
}
