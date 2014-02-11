package com.winraguini.apps.mytwitterapp;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.winraguini.apps.mytwitterapp.fragments.UserTimelineFragment;
import com.winraguini.apps.mytwitterapp.models.User;

public class ProfileActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		loadProfileInfo();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
	
	public void loadProfileInfo() {
		User u = (User) getIntent().getSerializableExtra("User");
		getActionBar().setTitle("@"+u.getScreenName());
		populateProfileHeader(u);
		UserTimelineFragment userTimelineFragment = (UserTimelineFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragmentUserTimeline);

        if (userTimelineFragment != null) {
        	userTimelineFragment.updateUser(u);
        }
	}
	
	private void populateProfileHeader(User user) {
		TextView tvName = (TextView)findViewById(R.id.tvName);
		TextView tvTagline = (TextView)findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView)findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView)findViewById(R.id.tvFollowing);
		ImageView ivProfileImage = (ImageView)findViewById(R.id.ivProfileImage);
		tvName.setText(user.getName());
		tvTagline.setText(user.getTagline());
		tvFollowers.setText(user.getFollowersCount() + " Followers");
		tvFollowing.setText(user.getFriendsCount() + " Following");
		//load profile Image
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);
	}

}
