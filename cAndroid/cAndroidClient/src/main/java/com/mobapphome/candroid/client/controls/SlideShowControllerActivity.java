package com.mobapphome.candroid.client.controls;

import java.util.Date;

import com.mobapphome.candroid.R;
import com.mobapphome.candroid.client.CAndroidApplication;
import com.mobapphome.candroid.client.command.Commands;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

enum SlideDirection{
	Right, Left
}

public class SlideShowControllerActivity extends AppCompatActivity implements OnClickListener, OnTouchListener {
	static final String TAG = SlideShowControllerActivity.class.getName();
	int PRIMARY_CODE_F5 = -0X74;
	int PRIMARY_CODE_ESC = -0X1B;
	int PRIMARY_CODE_RIGHT = -0X27;
	int PRIMARY_CODE_LEFT = -0X25;
	
	float initX;
	long downTime;
	
	boolean started = false;
	Button btnSSStart;
	
	Toast arrowImageToast;
	long toastStartTime;
	
	Resources res;
	
    CAndroidApplication cAndroidApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slide_show_controller_activity);
		res = getResources();

		cAndroidApplication = (CAndroidApplication)getApplicationContext();

		btnSSStart = (Button)findViewById(R.id.btnSSStart);
		btnSSStart.setOnClickListener(this);
		btnSSStart.setText(res.getString(R.string.slide_show_act_btn_start));
		started = false;
		
		TextView tv = (TextView)findViewById(R.id.tvSSActivity);
		tv.setText(Html.fromHtml(res.getString(R.string.slide_show_act_information)));
		
		findViewById(R.id.btnSSTouchPad).setOnClickListener(this);
		findViewById(R.id.btnSSSettings).setOnClickListener(this);
		findViewById(R.id.linLayoutSSSlide).setOnTouchListener(this);
		
		
		
	    final WifiManager wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
	    int wifi = wifiManager.getWifiState();
		if (wifi != WifiManager.WIFI_STATE_ENABLED && wifi != WifiManager.WIFI_STATE_ENABLING) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(res.getString(R.string.dialog_wi_fi_enabling_question));
			builder.setCancelable(false);
			builder.setPositiveButton(res.getString(R.string.dialg_yes), new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
			        	   wifiManager.setWifiEnabled(true);
			           }
			       });
			 builder.setNegativeButton(res.getString(R.string.dialg_no), new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
		}else{
			cAndroidApplication.getClient().connectWithAsyncTask();
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(res.getString(R.string.dialog_slide_show_enabling_info));
		builder.setCancelable(false);
		builder.setPositiveButton(res.getString(R.string.dialg_go_on), new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();	

	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSSStart:
			if(!started){
				started = true;
				btnSSStart.setText(res.getString(R.string.slide_show_act_btn_end));
				Log.d(TAG, "This boutton dont have  onClick Listener. Minus");
				cAndroidApplication.getClient().sendCommandKeyWithHandler(
						Commands.COMMAND_TYPE_KEY_PRESSED_RELEASED, PRIMARY_CODE_F5);
			}else{
				started = false;
				btnSSStart.setText(res.getString(R.string.slide_show_act_btn_start));
				Log.d(TAG, "This boutton dont have  onClick Listener. Plus");
				cAndroidApplication.getClient().sendCommandKeyWithHandler(
						Commands.COMMAND_TYPE_KEY_PRESSED_RELEASED, PRIMARY_CODE_ESC);
			}
			break;

		case R.id.btnSSSettings:
			startActivity(new Intent(SlideShowControllerActivity.this, SettingsActivity.class));	
			break;

		case R.id.btnSSTouchPad:
			startActivity(new Intent(SlideShowControllerActivity.this, TouchPadActivity.class));	
			break;

		default:
			Log.e(TAG, "This boutton dont have  onClick Listener. id = " + v.getId());
			break;
		}
	}
	
	public void showToastImage(int drowble){
		long diff = new Date().getTime() - toastStartTime;
		Log.d(TAG, "Diff Toast = " + diff);
		
		if(toastStartTime > 0 && diff < 500){
			toastStartTime = new Date().getTime();
			return;
		}
		
		arrowImageToast = new Toast(getBaseContext());
	    LinearLayout toastLayout = new LinearLayout(getBaseContext());
	    toastLayout.setOrientation(LinearLayout.HORIZONTAL);
	    ImageView image = new ImageView(getBaseContext());
	    image.setImageResource(drowble);
	    toastLayout.addView(image);
	    arrowImageToast.setView(toastLayout);
	    arrowImageToast.setDuration(Toast.LENGTH_SHORT);
	    arrowImageToast.show();
	    toastStartTime = new Date().getTime();
	}

	public void createSlideEvent(SlideDirection slideDirection){
		if(!started){
			return;
		}
        Log.d(TAG, "Diff X = " + slideDirection);	
        if(slideDirection == SlideDirection.Right){
        	showToastImage(R.drawable.forward_arrow);
    		cAndroidApplication.getClient().sendCommandKeyWithHandler(
    				Commands.COMMAND_TYPE_KEY_PRESSED_RELEASED, PRIMARY_CODE_RIGHT);        	
        }else if(slideDirection == SlideDirection.Left){
        	showToastImage(R.drawable.back_arrow);
    		cAndroidApplication.getClient().sendCommandKeyWithHandler(
    				Commands.COMMAND_TYPE_KEY_PRESSED_RELEASED, PRIMARY_CODE_LEFT);        	        	
        }else{
			Log.e(TAG, "This SlideDirection is not defined = " + slideDirection);        	
        }

	}
	
	public boolean onTouch(View v, MotionEvent me) {
        switch (v.getId()) {
        case R.id.linLayoutSSSlide:
            if (me.getAction() == MotionEvent.ACTION_DOWN) {
                initX = me.getX();
                downTime = me.getEventTime();
            } else if (me.getAction() == MotionEvent.ACTION_UP) {
                float diffX = me.getX() - initX;
            	long diffTime = me.getEventTime() - downTime;
            	float speed = diffX/diffTime;
            	if(Math.abs(speed) >1.0){
            		if(speed > 0){
            			createSlideEvent(SlideDirection.Right);
            		}else{
            			createSlideEvent(SlideDirection.Left);            			
            		}
            	}
            }
            break;
         default:
            Log.e(TAG, "There is not onTouch action for Id = = " + v.getId());
            break;
    }
    return true;

	}
	
	@Override
	protected void onStop() {
		cAndroidApplication.getClient().sendCommandKeyWithHandler(
				Commands.COMMAND_TYPE_KEY_PRESSED_RELEASED, PRIMARY_CODE_ESC);
		super.onStop();
	}
}
