package com.mobapphome.candroid.client.controls;

import candroid.client.R;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);
		Resources res = getResources();
		TextView tv = (TextView)findViewById(R.id.tvAboutAct_info);
		tv.setText(Html.fromHtml(res.getString(R.string.about_info_text)));
	}
}
