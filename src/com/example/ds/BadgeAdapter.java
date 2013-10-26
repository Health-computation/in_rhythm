package com.example.ds;

import java.util.Arrays;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.example.ds.Badge;

public class BadgeAdapter extends ParseQueryAdapter<Badge>{
	public BadgeAdapter(Context context) {
		super(context, new ParseQueryAdapter.QueryFactory<Badge>() {
			public ParseQuery<Badge> create() {
				// Here we can configure a ParseQuery to display
				// only top-rated meals.
				ParseQuery query = new ParseQuery("Badge");
				return query;
			}
		});
	}
	@Override
	public View getItemView(Badge badge, View v, ViewGroup parent) {
	 
	    if (v == null) {
	        v = View.inflate(getContext(), R.layout.badge_list_item, null);
	    }
	 
	    super.getItemView(badge, v, parent);
	 
	    ParseImageView badgeImage = (ParseImageView) v.findViewById(R.id.icon);
	    ParseFile photoFile = badge.getParseFile("photo");
	    if (photoFile != null) {
	        badgeImage.setParseFile(photoFile);
	        badgeImage.loadInBackground(new GetDataCallback() {
	            @Override
	            public void done(byte[] data, ParseException e) {
	                // nothing to do
	            }
	        });
	    }
	 
	    TextView titleTextView = (TextView) v.findViewById(R.id.description);
	        titleTextView.setText(badge.getName());
	    return v;
	}

}
