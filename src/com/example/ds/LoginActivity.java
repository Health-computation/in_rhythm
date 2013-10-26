package com.example.ds;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class LoginActivity extends Activity {

	private Button loginButton;
	private Dialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Check if there is a currently logged in user
		// and they are linked to a Facebook account.
		ParseUser currentUser = ParseUser.getCurrentUser();
		/*
		badge.saveInBackground(new SaveCallback() {
		 
		    public void done(ParseException e) {
		        if (e != null) {
		        	Badge meal = new Badge();

		        
		        } else {
		        }
		    };
		});
		*/
		if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
			// Go to the user info activity
			showMainPage();
		}

		setContentView(R.layout.activity_login);

		loginButton = (Button) findViewById(R.id.loginButton);
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onLoginButtonClicked();
			}
		});



	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}

	private void onLoginButtonClicked() {
		LoginActivity.this.progressDialog = ProgressDialog.show(
				LoginActivity.this, "", "Logging in...", true);
		List<String> permissions = Arrays.asList("basic_info", "user_about_me",
				"user_relationships", "user_birthday", "user_location");
		ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException err) {
				LoginActivity.this.progressDialog.dismiss();
				if (user == null) {
					Log.d("LOL",
							"Uh oh. The user cancelled the Facebook login.");
				} else if (user.isNew()) {
					Log.d("LOL",
							"User signed up and logged in through Facebook!");
					showMainPage();
				} else {
					Log.d("LOL",
							"User logged in through Facebook!");
					showMainPage();
				}
			}
		});
	}

	private void showUserDetailsActivity() {
		Intent loggedInPage = new Intent(this, MainActivity.class);
		startActivity(loggedInPage);
	}
	
	private void showBadgePage(){
		Intent badgesPage = new Intent(this, BadgesActivity.class);
		startActivity(badgesPage);
	}
	
	private void showMainPage(){
		Intent mainPage = new Intent(this, MainActivity.class);
		startActivity(mainPage);
	}
	
	private byte[] createBadge(){
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		InputStream raw = getResources().openRawResource(com.example.ds.R.drawable.ic_launcher);
	    int i;
	    try
	    {
	        i = raw.read();
	        while (i != -1)
	        {
	            byteArrayOutputStream.write(i);
	            i = raw.read();
	        }
	        raw.close();
	    }
	    catch (IOException e)
	    {

	        e.printStackTrace();
	    }
	    return byteArrayOutputStream.toByteArray();
	}
}


