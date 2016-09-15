package com.mobapphome.candroid.client.controls;

import com.mobapphome.candroid.client.CAndroidApplication;
import candroid.client.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener{
	
	private static final String TAG = MainActivity.class.getName();
	CAndroidApplication cAndroidApplication;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cAndroidApplication = (CAndroidApplication)getApplicationContext();
		setContentView(R.layout.main_activity);
		findViewById(R.id.btnMATouchPad).setOnClickListener(this);
		findViewById(R.id.btnMANeedForSpeed).setOnClickListener(this);
		findViewById(R.id.btnMASlideShow).setOnClickListener(this);
		findViewById(R.id.btnMAWiFiEnable).setOnClickListener(this);
		findViewById(R.id.btnMASettings).setOnClickListener(this);
		findViewById(R.id.btnMAAbout).setOnClickListener(this);
		findViewById(R.id.btnMAExit).setOnClickListener(this);
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

		case R.id.btnMAWiFiEnable:
			startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));		
			break;
			
		case R.id.btnMASettings:
			startActivity(new Intent(MainActivity.this, SettingsActivity.class));							
			break;

		case R.id.btnMAAbout:
			startActivity(new Intent(MainActivity.this, AboutActivity.class));							
			break;

		case R.id.btnMAExit:			
			finish();
			break;

		default:
			Log.e(TAG, "This boutton dont have  onClick Listener. id = " + v.getId());
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		if(cAndroidApplication.getClient() != null){
			cAndroidApplication.getClient().close();
		}
		super.onDestroy();
	}
}
