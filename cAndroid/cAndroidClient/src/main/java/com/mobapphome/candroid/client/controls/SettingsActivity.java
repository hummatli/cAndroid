package com.mobapphome.candroid.client.controls;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mobapphome.candroid.R;
import com.mobapphome.candroid.client.CAndroidApplication;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;



public class SettingsActivity extends AppCompatActivity implements  OnClickListener {

	final String TAG = SettingsActivity.class.getName();
	private static final String IPADDRESS_PATTERN = 
			"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	private static final String PORT_PATTERN = "([0-9]{1,5})";




	Pattern patternIP = Pattern.compile(IPADDRESS_PATTERN);
	Pattern patternPort = Pattern.compile(PORT_PATTERN);
		
	EditText etSAIP;
	EditText etSAPort;
	CAndroidApplication cAndroidApplication;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cAndroidApplication = (CAndroidApplication)getApplicationContext();
		setContentView(R.layout.settings_activity);
		
		findViewById(R.id.btnSASave).setOnClickListener(this);
		findViewById(R.id.btnSAClose).setOnClickListener(this);
		etSAIP = (EditText) findViewById(R.id.etSAIP);
		etSAPort = (EditText) findViewById(R.id.etSAPort);
		etSAIP.setText(cAndroidApplication.getIP());
		etSAPort.setText(String.valueOf(cAndroidApplication.getPort()));
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSASave:
			StringBuffer strBuf = new StringBuffer("");
			boolean dontMatch = false;
		    Matcher matcherIP = patternIP.matcher(etSAIP.getText());
		    Matcher matcherPort = patternPort.matcher(etSAPort.getText());
		    Resources res = getResources();
		    if(!matcherIP.matches()){
		    	strBuf.append(res.getString(R.string.settings_act_match_ip_text));
		    	dontMatch = true;
		    }
		    
		    if(!matcherPort.matches()){
		    	if(dontMatch) strBuf.append("\n");
		    	strBuf.append(res.getString(R.string.settings_act_match_port_text));
		    	dontMatch = true;
		    }
		    
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	builder.setTitle(res.getString(R.string.settings_act_match_dialog_title));
	    	builder.setInverseBackgroundForced(true);
	    	builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		    	public void onClick(DialogInterface dialog, int which) {
		    		dialog.cancel();
		    	}
	    	});
		    if(dontMatch){
		    	builder.setMessage(strBuf.toString());
		    	AlertDialog alert = builder.create();
		    	alert.show();
		    }else{
		    	Log.d(TAG, "");
				cAndroidApplication.setIp(etSAIP.getText().toString());
				cAndroidApplication.setPort(Integer.valueOf(etSAPort.getText().toString()));
		    	boolean result = true;// Bura normal yazilmamaq shertin qoyarsan
		    	if(result){
		    		builder.setMessage(res.getString(R.string.settings_act_saved_normally_text));
			    	cAndroidApplication.getClient().close();
					cAndroidApplication.getClient().connectWithAsyncTask();
		    	}else{
		    		builder.setMessage(res.getString(R.string.settings_act_saved_not_normally_text));		    		
		    	}
		    	AlertDialog alert = builder.create();
		    	alert.show();
		    }
		
			break;
			
		case R.id.btnSAClose:
			finish();
			break;

		default:
			Log.d(TAG, "This boutton dont have  onClick Listener. id = " + v.getId());
			break;
		}
	}



}
