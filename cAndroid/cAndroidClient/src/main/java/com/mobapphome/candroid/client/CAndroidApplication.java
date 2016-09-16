package com.mobapphome.candroid.client;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class CAndroidApplication extends Application {

	private Client client;
	static final String IP_KEY = "ip";
	static final String PORT_KEY = "port";
	static final String DEFAULT_IP = "127.0.0.1";
	static final int DEFAULT_PORT = 6000;

	@Override
	public void onCreate() {
		super.onCreate();
		client = new Client(this);
	}
	
	public Client getClient() {
		if(client == null){
			client = new Client(this);
		}
		return client;
	}

	@Override
	protected void finalize() throws Throwable {
		if(client != null){
			client.close();
		}
		super.finalize();
	}

	public String getIP() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		return prefs.getString(IP_KEY, DEFAULT_IP);
	}

	public int getPort() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		return prefs.getInt(PORT_KEY, DEFAULT_PORT);
	}

	public void setIp(String IP) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(IP_KEY, IP); // value to store
		editor.commit();
	}

	public void setPort(int port) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(PORT_KEY, port); // value to store
		editor.commit();
	}

}
