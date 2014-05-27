package com.phiworks.sumosenseinew;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class BackgroundSoundService extends Service {
	MediaPlayer mp;

	
	//REFERENTE AO BINDER QUE A ACTIVITY VAI USAR PRA SE COMUNICAR COM O SERVICE
	// Binder given to clients
    private final IBinder mBinder = new BackgroundSoundServiceBinder();
	@Override
	public IBinder onBind(Intent arg0) {
		mp.start();
		mp.setLooping(true);
		return mBinder;

	}
	
	@Override
	public boolean onUnbind(Intent arg0) {

		mp.release();
		super.onDestroy();
		return true;
	}
	
	
	public class BackgroundSoundServiceBinder extends Binder {
	        BackgroundSoundService getService() {
	            // Return this instance of LocalService so clients can call public methods
	            return BackgroundSoundService.this;
	        }
	}

	@Override

	  public void onCreate() {

	  super.onCreate();

	  mp = MediaPlayer.create(getApplicationContext(), R.raw.lazy_susan);
	
	}
	
	
	

	@Override

	public int onStartCommand(Intent intent, int flags, int startId) {

	mp.start();
	mp.setLooping(true);

	return 0;

	}

	@Override

	public void onDestroy() {

	mp.release();

	super.onDestroy();

	}
	
	public void mudarMusica(int indiceMusica)
	{
		mp.release();
		mp = MediaPlayer.create(getApplicationContext(), indiceMusica);
		mp.start();
		mp.setLooping(true);
	}
	
	
	

	
}