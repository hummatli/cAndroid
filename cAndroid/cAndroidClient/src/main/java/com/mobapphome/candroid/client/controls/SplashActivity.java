package com.mobapphome.candroid.client.controls;

import candroid.client.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class SplashActivity extends Activity {
	
	protected boolean _active = true;
	protected int _splashTime = 5000; // time to display the splash screen in ms

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.splash_activity);
	 
	    // thread for displaying the SplashScreen
	    Thread splashTread = new Thread() {
	        @Override
	        public void run() {
	            try {
	                int waited = 0;
	                while(_active && (waited < _splashTime)) {
	                    sleep(100);
	                    if(_active) {
	                        waited += 100;
	                    }
	                }
	            } catch(InterruptedException e) {
	                // do nothing
	            } finally {
	                finish();
	                startActivity(new Intent(SplashActivity.this, MainActivity.class));
	                _active = false;
	            }
	        }
	    };
	    splashTread.start();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
	        _active = false;
	    }
	    return true;
	}
}
