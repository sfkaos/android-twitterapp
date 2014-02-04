package com.winraguini.apps.mytwitterapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.winraguini.apps.mytwitterapp.models.Tweet;
import com.winraguini.apps.mytwitterapp.models.TwitterManager;

public class ComposeActivity extends Activity {
	EditText etTweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		etTweet = (EditText)findViewById(R.id.etTweet);
		displayUserInfo();
	}
	
	public void displayUserInfo() {
		ImageView imageView = (ImageView) findViewById(R.id.ivProfile);
		ImageLoader.getInstance().displayImage(TwitterManager.getInstance().getProfileImageUrl(), imageView);
		
		TextView tvTwitterHandle = (TextView)findViewById(R.id.tvTwitterHandle);
		tvTwitterHandle.setText("@"+TwitterManager.getInstance().getScreenName());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}
	
	public void onCancelAction(View v) {
		Intent data = new Intent();
		setResult(RESULT_OK, data);
		finish();
	}
	
	public void onTweetAction(View v) {
		Intent data = new Intent();				
		data.putExtra(Tweet.TWEET_KEY, etTweet.getText().toString());
		Log.d("DEBUG", "in tweet "+ etTweet.getText().toString());
		setResult(RESULT_OK, data);
		finish();
	}

}
