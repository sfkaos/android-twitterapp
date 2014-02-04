package com.winraguini.apps.mytwitterapp;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.codepath.oauth.OAuthLoginActivity;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.winraguini.apps.mytwitterapp.models.TwitterManager;
import com.winraguini.apps.mytwitterapp.models.User;

public class LoginActivity extends OAuthLoginActivity<TwitterClient> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	// OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
    @Override
    public void onLoginSuccess() {
    	getUserInfo();
    	Intent i = new Intent(this, TimelineActivity.class);
    	startActivity(i);
    }
    
    // OAuth authentication flow failed, handle the error
    // i.e Display an error dialog or toast
    @Override
    public void onLoginFailure(Exception e) {
    	Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }
    
    // Click handler method for the button used to start OAuth flow
    // Uses the client to initiate OAuth authorization
    // This should be tied to a button used to login
    public void loginToRest(View view) {
        getClient().connect();
    }
    
	public void getUserInfo() {
    	MyTwitterApp.getRestClient().getVerifyCredentials(new JsonHttpResponseHandler() {
			public void onSuccess(JSONObject twitterUser) {			
		        try {
		        	TwitterManager.getInstance().setProfileImageUrl(twitterUser.getString("profile_image_url"));
		        	TwitterManager.getInstance().setScreenName(twitterUser.getString("screen_name"));
		        } catch (JSONException e) {
		            e.printStackTrace();
		        }
					
				Log.d("DEBUG", TwitterManager.getInstance().getScreenName());
				Log.d("DEBUG", TwitterManager.getInstance().getProfileImageUrl());
			}
			
			public void onFailure(java.lang.Throwable e) {
				Toast.makeText(getBaseContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
				Log.d("DEBUG", "Error: " + e.getMessage());
			}
	});
}


}
