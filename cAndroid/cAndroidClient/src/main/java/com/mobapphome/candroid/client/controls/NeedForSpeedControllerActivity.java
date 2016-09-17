package com.mobapphome.candroid.client.controls;

import com.mobapphome.candroid.R;
import com.mobapphome.candroid.client.CAndroidApplication;
import com.mobapphome.candroid.commands.Commands;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


enum TurnState{
	Right, Left, Forward
}

enum DirectionState{
	Direct, Return
}

enum GasPedalSate{
	Pressed, Released
}

enum ControlTypeState{
	Auotomatic, Manual
}

public class NeedForSpeedControllerActivity extends AppCompatActivity implements OnClickListener, OnTouchListener,
	SensorEventListener{
	final static String TAG = NeedForSpeedControllerActivity.class.getName();
	final static int PRIMARY_CODE_SPACE = -0x20;
	final static int PRIMARY_CODE_RIGHT = 'd';
	final static int PRIMARY_CODE_LEFT = 'a';
	final static int PRIMARY_CODE_UP = 'w';
	final static int PRIMARY_CODE_DOWN = 's';
	final static int PRIMARY_CODE_SHIFT = ';';
	final static int PRIMARY_CODE_CTRL = '.';


	SensorManager sensorManager = null;

	Button btnMinus;
	Button btnPlus;
	Button btnForceStop;
	Button btnDirection;
	Button btnManualAoutomat;
	Button btnStartPause;

	TurnState turnState = TurnState.Forward;
	DirectionState directionState = DirectionState.Direct;
	GasPedalSate gasPedalSate = GasPedalSate.Released;
	ControlTypeState controlTypeState = ControlTypeState.Auotomatic;
	boolean started = false;

	Resources res;
    CAndroidApplication cAndroidApplication;

	TextView orX ;
	TextView orY ;
	TextView orZ ;
	TextView acX ;
	TextView acY ;
	TextView acZ ;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    cAndroidApplication = (CAndroidApplication) getApplicationContext();
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		setContentView(R.layout.need_for_speed_controller_activity);
		res = getResources();

		try {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}catch (NullPointerException npe){
			Log.i("test", npe.getMessage());
		}

		btnMinus = (Button)findViewById(R.id.btnNFSMinus);
		btnMinus.setOnClickListener(this);

		btnPlus = (Button)findViewById(R.id.btnNFSPlus);
		btnPlus.setOnClickListener(this);

		btnForceStop = (Button) findViewById(R.id.btnNFSForceStop);
		btnForceStop.setOnTouchListener(this);

		btnDirection = (Button)findViewById(R.id.btnNFSDirection);
		btnDirection.setOnClickListener(this);
		directionState = DirectionState.Direct;
		btnDirection.setText(res.getString(R.string.need_for_speed_act_btn_return));

		btnManualAoutomat = (Button)findViewById(R.id.btnNFSManualAoutomat);
		btnManualAoutomat.setOnClickListener(this);
		controlTypeState = ControlTypeState.Auotomatic;
		btnManualAoutomat.setText(res.getString(R.string.need_for_speed_act_btn_manual));
		btnMinus.setVisibility(View.GONE);
		btnPlus.setVisibility(View.GONE);

		btnStartPause = (Button)findViewById(R.id.btnNFSStartPause);
		btnStartPause.setOnClickListener(this);
		started = false;
		setEnabledOfButtons(false);
		btnStartPause.setText(res.getString(R.string.need_for_speed_act_btn_start));


		orX  = (TextView) findViewById(R.id.textViewOrX);
		orY  = (TextView) findViewById(R.id.textViewOrY);
		orZ  = (TextView) findViewById(R.id.textViewOrZ);
		acX  = (TextView) findViewById(R.id.textViewAcX);
		acY  = (TextView) findViewById(R.id.textViewAcY);
		acZ  = (TextView) findViewById(R.id.textViewAcZ);

        Resources res = getResources();
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
		builder.setMessage(res.getString(R.string.dialog_nfs_enabling_info));
		builder.setCancelable(false);
		builder.setPositiveButton(res.getString(R.string.dialg_go_on), new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.need_for_speed, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			startActivity(new Intent(NeedForSpeedControllerActivity.this, SettingsActivity.class));
			return true;
		} else if (id == R.id.action_control) {
			startActivity(new Intent(NeedForSpeedControllerActivity.this, TouchPadActivity.class));
			return true;
		} else if (id == android.R.id.home) {
			onBackPressed();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}


	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnNFSMinus:
			Log.d(TAG, "This boutton dont have  onClick Listener. Minus");
			cAndroidApplication.getClient().sendCommandKeyAsync(
					Commands.COMMAND_TYPE_KEY_PRESSED_RELEASED, PRIMARY_CODE_CTRL);
			break;

		case R.id.btnNFSPlus:
			Log.d(TAG, "This boutton dont have  onClick Listener. Plus");
			cAndroidApplication.getClient().sendCommandKeyAsync(
					Commands.COMMAND_TYPE_KEY_PRESSED_RELEASED, PRIMARY_CODE_SHIFT);
			break;

		case R.id.btnNFSDirection:
			if(directionState == DirectionState.Direct){
				directionState = DirectionState.Return;
				btnDirection.setText(res.getString(R.string.need_for_speed_act_btn_direct));
			}else if(directionState == DirectionState.Return){
				directionState = DirectionState.Direct;
				btnDirection.setText(res.getString(R.string.need_for_speed_act_btn_return));
			}else{
				Log.e(TAG, "This DirectionSate is not defined = " + directionState);
			}
			createGoEvent();
			break;

		case R.id.btnNFSManualAoutomat:
			if(controlTypeState == ControlTypeState.Manual){
				controlTypeState = ControlTypeState.Auotomatic;
				Log.d(TAG, "This boutton dont have  onClick Listener. Manual ");
				btnManualAoutomat.setText(res.getString(R.string.need_for_speed_act_btn_manual));
				btnMinus.setVisibility(View.GONE);
				btnPlus.setVisibility(View.GONE);
			}else if(controlTypeState == ControlTypeState.Auotomatic){
				controlTypeState = ControlTypeState.Manual;
				Log.d(TAG, "This boutton dont have  onClick Listener. Aoutomat ");
				btnManualAoutomat.setText(res.getString(R.string.need_for_speed_act_btn_aoutomatic));
				btnMinus.setVisibility(View.VISIBLE);
				btnPlus.setVisibility(View.VISIBLE);
			}else{
				Log.e(TAG, "This ControlTypeState is not defined = " + controlTypeState);
			}
			break;

		case R.id.btnNFSStartPause:
			if(started){
				Log.d(TAG, "This boutton dont have  onClick Listener. Presses Started ");
				btnStartPause.setText(res.getString(R.string.need_for_speed_act_btn_start));
				btnStartPause.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_play_arrow_white_36dp, 0, 0, 0);
				started = false;
				setEnabledOfButtons(false);
			}else{
				Log.d(TAG, "This boutton dont have  onClick Listener. Prosess Stoped ");
				btnStartPause.setText(res.getString(R.string.need_for_speed_act_btn_pause));
				btnStartPause.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pause_white_36dp, 0, 0, 0);
				started = true;
				setEnabledOfButtons(true);
				createGoEvent();
				createTurnEvent();
			}
			break;

		default:
			Log.e(TAG, "This boutton dont have  onClick Listener. id = " + v.getId());
			break;
		}
	}

	public void setEnabledOfButtons(boolean enabled){
		btnMinus.setEnabled(enabled);
		btnPlus.setEnabled(enabled);
		btnForceStop.setEnabled(enabled);
		btnManualAoutomat.setEnabled(enabled);
		btnDirection.setEnabled(enabled);
	}

	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.btnNFSForceStop:
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				btnForceStop.setPressed(true);
				Log.d(TAG, "This boutton dont have  onClick Listener. Stop Down");
				cAndroidApplication.getClient().sendCommandKeyAsync(Commands.COMMAND_TYPE_KEY_PRESSED, PRIMARY_CODE_SPACE);
			}else if(event.getAction() == MotionEvent.ACTION_UP){
				btnForceStop.setPressed(false);
				Log.d(TAG, "This boutton dont have  onClick Listener. Stop Up");
				cAndroidApplication.getClient().sendCommandKeyAsync(Commands.COMMAND_TYPE_KEY_RElEASED, PRIMARY_CODE_SPACE);
			}
			break;
		default:
			Log.e(TAG, "This boutton dont have  onTouch Listener. id = " + v.getId());
			break;
		}

		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "Resumed");
	    sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_GAME);
	    sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), sensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "Stopped");
		sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
		sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION));
		cAndroidApplication.getClient().sendCommandKeyAsync(Commands.COMMAND_TYPE_KEY_RElEASED, PRIMARY_CODE_SPACE);
		cAndroidApplication.getClient().sendCommandKeyAsync(Commands.COMMAND_TYPE_KEY_RElEASED, PRIMARY_CODE_UP);
		cAndroidApplication.getClient().sendCommandKeyAsync(Commands.COMMAND_TYPE_KEY_RElEASED, PRIMARY_CODE_DOWN);
		cAndroidApplication.getClient().sendCommandKeyAsync(Commands.COMMAND_TYPE_KEY_RElEASED, PRIMARY_CODE_RIGHT);
		cAndroidApplication.getClient().sendCommandKeyAsync(Commands.COMMAND_TYPE_KEY_RElEASED, PRIMARY_CODE_LEFT);

	}
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	public void createTurnEvent(){
		if(!started){
			return;
		}
		if(turnState == TurnState.Right){
			cAndroidApplication.getClient().sendCommandKeyAsync(
					Commands.COMMAND_TYPE_KEY_RElEASED, PRIMARY_CODE_LEFT);
			cAndroidApplication.getClient().sendCommandKeyAsync(
					Commands.COMMAND_TYPE_KEY_PRESSED, PRIMARY_CODE_RIGHT);
		}else if(turnState == TurnState.Left){
			cAndroidApplication.getClient().sendCommandKeyAsync(
					Commands.COMMAND_TYPE_KEY_RElEASED, PRIMARY_CODE_RIGHT);
			cAndroidApplication.getClient().sendCommandKeyAsync(
					Commands.COMMAND_TYPE_KEY_PRESSED, PRIMARY_CODE_LEFT);
		}else if(turnState == TurnState.Forward){
			cAndroidApplication.getClient().sendCommandKeyAsync(
					Commands.COMMAND_TYPE_KEY_RElEASED, PRIMARY_CODE_LEFT);
			cAndroidApplication.getClient().sendCommandKeyAsync(
					Commands.COMMAND_TYPE_KEY_RElEASED, PRIMARY_CODE_RIGHT);
		}else{
			Log.e(TAG, "This TurnSate is not defined = " + turnState);
		}
	}

	public void createGoEvent(){
		if(!started){
			return;
		}
		int pressedKey = 0;
		int releasedKey = 0;
		if(directionState == DirectionState.Direct){
			pressedKey = PRIMARY_CODE_UP;
			releasedKey = PRIMARY_CODE_DOWN;
		}else if(directionState == DirectionState.Return){
			pressedKey = PRIMARY_CODE_DOWN;
			releasedKey = PRIMARY_CODE_UP;
		}else{
			Log.e(TAG, "This DirectionSate is not defined = " + directionState);
		}

		if(gasPedalSate == GasPedalSate.Pressed){
    		cAndroidApplication.getClient().sendCommandKeyAsync(
    					Commands.COMMAND_TYPE_KEY_RElEASED, releasedKey);
    		cAndroidApplication.getClient().sendCommandKeyAsync(
					Commands.COMMAND_TYPE_KEY_PRESSED, pressedKey);
		}else if(gasPedalSate == GasPedalSate.Released){
    		cAndroidApplication.getClient().sendCommandKeyAsync(
					Commands.COMMAND_TYPE_KEY_RElEASED, PRIMARY_CODE_DOWN);
    		cAndroidApplication.getClient().sendCommandKeyAsync(
				Commands.COMMAND_TYPE_KEY_RElEASED, PRIMARY_CODE_UP);
		}else{
			Log.e(TAG, "This GasPedalSate is not defined = " + gasPedalSate);
		}

	}

	public void onSensorChanged(SensorEvent event) {
	    synchronized (this) {
	        switch (event.sensor.getType()){
	            case Sensor.TYPE_ACCELEROMETER:
		        	acX.setText(Float.toString(event.values[0]));
		        	acY.setText(Float.toString(event.values[1]));
		        	acZ.setText(Float.toString(event.values[2]));
	                break;
		        case Sensor.TYPE_ORIENTATION:
		        	float x = event.values[0];
		        	float y = event.values[1];
		        	float z = event.values[2];
		        	orX.setText(Float.toString(x));
		        	orY.setText(Float.toString(y));
		        	orZ.setText(Float.toString(z));

		        	TurnState currTurnState = null;
		        	GasPedalSate currGasPedalSate = null;

		        	if( y > -30 && y < -15){
		        		//right
		        		currTurnState = TurnState.Right;
		        	}else if(y >15 && y < 30){
		        		//left
		        		currTurnState = TurnState.Left;
		        	}else{
		        		//forward
		        		currTurnState = TurnState.Forward;
		        	}

		        	if(z > -10 && z < 40){
		        		//Gas pedal pressed
	        			currGasPedalSate = GasPedalSate.Pressed;
		        	}else{
		        		//Gas pedal pressed
	        			currGasPedalSate = GasPedalSate.Released;
		        	}

		        	if(turnState != currTurnState){
		        		turnState = currTurnState;
		        		//Create turn event
		        		createTurnEvent();
		        	}

		        	if(gasPedalSate != currGasPedalSate){
		        		gasPedalSate = currGasPedalSate;
		        		//Create go event
		        		createGoEvent();
		        	}

		        	break;
		        default:
					Log.e(TAG, "This type of event does not registered. type = " + event.sensor.getType());
		        	break;
	        }
	    }
	}
}
