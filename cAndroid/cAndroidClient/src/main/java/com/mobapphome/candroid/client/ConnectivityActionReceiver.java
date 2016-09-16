package com.mobapphome.candroid.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.mobapphome.candroid.R;

public class ConnectivityActionReceiver extends BroadcastReceiver {
	final static String TAG = ConnectivityActionReceiver.class.getName();

	@Override
	public void onReceive(final Context context, final Intent intent) {
		CAndroidApplication cAndroidApplication = (CAndroidApplication)context.getApplicationContext();
		Resources res = context.getResources();

		if(intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
		    NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
		    if(networkInfo.isConnected()) {
		        // Wifi is connected
		        Log.d(TAG, "Wifi is connected: " + String.valueOf(networkInfo));
				if(cAndroidApplication.getClient() != null){
					cAndroidApplication.getClient().connectWithAsyncTask();
		        }
		    }
		} else if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
		    NetworkInfo networkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
		    if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI && !networkInfo.isConnected()) {
		        // Wifi is disconnected
		        Log.d(TAG, "Wifi is disconnected: " + String.valueOf(networkInfo));
				if(cAndroidApplication.getClient() != null){
					cAndroidApplication.getClient().close();
				}
		    }
		}
	}
}
