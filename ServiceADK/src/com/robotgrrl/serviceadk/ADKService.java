// ADKService.java
// ---------------------------
// RobotGrrl.com
// November 29, 2011

package com.robotgrrl.serviceadk;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.robotgrrl.serviceadk.ServiceADKApplication;
import com.robotgrrl.serviceadk.ADKService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ADKService extends Service {
	
	private static final String TAG = "ServiceADKADKService";
	private Updater updater;
	
	public static ADKService self;
	
	FileInputStream mInputStream;
	FileOutputStream mOutputStream;
	ServiceADKActivity mHostActivity;

	int CURRENT_TAB = 0;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	
	// ---------
	// Lifecycle
	// ---------

	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate");
		self = this;
		updater = new Updater();
		super.onCreate();
	}
	  
	@Override
	public synchronized void onStart(Intent intent, int startId) {
		Log.d(TAG, "onStart");
		super.onStart(intent, startId);
	}
	
	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
			
		// Stop the updater
		if (updater.isRunning()) {
			updater.interrupt();
		}

		updater = null;
			
		super.onDestroy();
	}
		
	
	// -------
	// Updater
	// -------
	
	public void startUpdater() {
		Log.d(TAG, "Starting updater");
	    if (!updater.isRunning()) {
	    	Log.d(TAG, "updater not running");
	    	updater = new Updater();
	    	updater.start();
	    } else {
	    	Log.d(TAG, "updater running"); 
	    }
	}
	
	public void stopUpdater() {
		Log.d(TAG, "Stopping updater");
	    if (updater.isRunning()) {
	      updater.interrupt();
	    }
	}
	
	class Updater extends Thread {
		private static final long DELAY = 1000;
		private boolean isRunning = false;

		public Updater() {
			super("Updater");
		}
		
		@Override
		public void run() {
			isRunning = true;
			while (isRunning) {
				try {
					// Do something
					Log.d(TAG, "Updater running");

					if(((ServiceADKApplication) getApplication()).adkConnected()) {
						sendPress('B');
					}
					
					//checkInput();
					
					// Sleep
					Thread.sleep(DELAY);
				} catch (InterruptedException e) {
					// Interrupted
					isRunning = false;
				}
			}
		}

		public boolean isRunning() {
			return this.isRunning;
		}

	}
	
	
	// -------
	// ADK I&O
	// -------
	
	public void sendPress(char c) {
		
		byte[] buffer = new byte[2];
		buffer[0] = (byte)'B';
		buffer[1] = (byte)c;
			
		if (((ServiceADKApplication) getApplication()).getOutputStream() != null) {
			try {
				((ServiceADKApplication) getApplication()).getOutputStream().write(buffer);
			} catch (IOException e) {
				Log.e(TAG, "write failed", e);
			}
		}
		
	}

	public void checkInput() {
		
		if(((ServiceADKApplication) getApplication()).getInputStream() != null) {
		
		int ret = 0;
		byte[] buffer = new byte[16384];
		int i;

		while (ret >= 0) {
			try {
				ret = ((ServiceADKApplication) getApplication()).getInputStream().read(buffer);
			} catch (IOException e) {
				break;
			}

			i = 0;
			while (i < ret) {
				int len = ret - i;

				Log.v(TAG, "Read: " + buffer[i]);
				
				switch (buffer[i]) {
				default:
					//Log.d(TAG, "unknown msg: " + buffer[i]);
					i = len;
					break;
				}
			}

		}
		
		} else {
			Log.v(TAG, "input stream was null :/");
		}
		
	}
	
	public void enableControls(boolean b) {
		
	}
	
}

