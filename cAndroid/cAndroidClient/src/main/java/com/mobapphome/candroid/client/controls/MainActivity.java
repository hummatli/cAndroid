package com.mobapphome.candroid.client.controls;

import com.mobapphome.candroid.R;
import com.mobapphome.candroid.client.CAndroidApplication;
import com.mobapphome.candroid.client.LocaleUpdater;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity implements OnClickListener{
	
	private static final String TAG = MainActivity.class.getName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LocaleUpdater.updateLocale(this,"az");
		setContentView(R.layout.main_activity);
		findViewById(R.id.btnMATouchPad).setOnClickListener(this);
		findViewById(R.id.btnMANeedForSpeed).setOnClickListener(this);
		findViewById(R.id.btnMASlideShow).setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			startActivity(new Intent(MainActivity.this, SettingsActivity.class));
			return true;
		} else if (id == R.id.action_about) {
			startActivity(new Intent(MainActivity.this, AboutActivity.class));
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnMATouchPad:
			startActivity(new Intent(MainActivity.this, TouchPadActivity.class));							
			break;

		case R.id.btnMANeedForSpeed:
			startActivity(new Intent(MainActivity.this, NeedForSpeedControllerActivity.class));							
			break;

		case R.id.btnMASlideShow:
			startActivity(new Intent(MainActivity.this, SlideShowControllerActivity.class));							
			break;

		default:
			Log.e(TAG, "This boutton dont have  onClick Listener. id = " + v.getId());
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		CAndroidApplication cAndroidApplication = (CAndroidApplication)getApplicationContext();
		if(cAndroidApplication.getClient() != null){
			cAndroidApplication.getClient().close();
		}
		super.onDestroy();
	}
}
