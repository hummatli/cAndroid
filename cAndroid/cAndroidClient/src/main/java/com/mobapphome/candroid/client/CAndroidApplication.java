package com.mobapphome.candroid.client;

import android.app.Application;

public class CAndroidApplication extends Application {

	private Client client;

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

}
