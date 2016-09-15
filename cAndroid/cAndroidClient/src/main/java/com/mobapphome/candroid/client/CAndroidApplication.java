package com.mobapphome.candroid.client;

import android.app.Application;

public class CAndroidApplication extends Application {
	
	private static final String TAG = CAndroidApplication.class.getName();
	private Client client;

	@Override
	public void onCreate() {
		super.onCreate();
		client = new Client(this);
		
	}
	
	public Client getClient() {
		return client;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	@Override
	protected void finalize() throws Throwable {
		//Toast.makeText(this, "Finalize", 5).show();
		if(client != null){
			client.close();
		}
		super.finalize();
	}

}
