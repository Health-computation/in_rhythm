package com.example.ds;

import android.os.Bundle;
import android.app.ListActivity;
import android.view.Menu;
import com.parse.ParseQueryAdapter;
import com.example.ds.BadgeAdapter;

public class BadgesActivity extends ListActivity {
	private BadgeAdapter badgeAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getListView().setClickable(false);
		// Subclass of ParseQueryAdapter
		badgeAdapter = new BadgeAdapter(this);
		
		badgeAdapter.loadObjects();
		setListAdapter(badgeAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.badges, menu);
		return true;
	}

}
