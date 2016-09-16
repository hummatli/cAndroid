package com.mobapphome.candroid.client.controls;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.mobapphome.candroid.R;

public class AboutActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);
		Resources res = getResources();
		TextView tv = (TextView)findViewById(R.id.tvAboutAct_info);
		tv.setText(Html.fromHtml(res.getString(R.string.about_info_text)));
	}

//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//			case android.R.id.home:
//				// this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
//				// if this doesn't work as desired, another possibility is to call `finish()` here.
//				onBackPressed();
//				return true;
//			default:
//				return super.onOptionsItemSelected(item);
//		}
//	}
}
