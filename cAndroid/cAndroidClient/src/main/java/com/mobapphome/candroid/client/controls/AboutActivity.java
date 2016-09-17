package com.mobapphome.candroid.client.controls;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.mobapphome.candroid.R;

public class AboutActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);
		try {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}catch (NullPointerException npe){
			Log.i("test", npe.getMessage());
		}
		((TextView)findViewById(R.id.tvAboutContent1)).setMovementMethod(LinkMovementMethod.getInstance());
		((TextView)findViewById(R.id.tvAboutContent2)).setMovementMethod(LinkMovementMethod.getInstance());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				// app icon in action bar clicked; goto parent activity.
				onBackPressed();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
