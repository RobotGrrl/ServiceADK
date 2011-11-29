// ServiceADKApplication.java
// ---------------------------
// RobotGrrl.com
// November 29, 2011

package com.robotgrrl.serviceadk;


import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.android.future.usb.UsbAccessory;

import android.app.Application;
import android.os.ParcelFileDescriptor;
import android.util.Log;

public class ServiceADKApplication extends Application {
  private static final String TAG = "ServiceADKApplication";

  private FileInputStream mInputStream;
  private FileOutputStream mOutputStream;
  private UsbAccessory mAccessory;
  private ParcelFileDescriptor mFileDescriptor;
  
  @Override
  public void onCreate() {
    super.onCreate();
    Log.v(TAG, "Hello");
    
  }

  public FileInputStream getInputStream() {
	  return mInputStream;
  }
  
  public FileOutputStream getOutputStream() {
	  return mOutputStream;
  }
  
  public boolean adkConnected() {
	
	 /*
  	if(mInputStream != null && mOutputStream != null) {
  		Log.v(TAG, "ADK is connected");
  		return true;
  	}
  	*/
  	
  	if(mFileDescriptor != null) {
  		Log.v(TAG, "ADK is connected");
  		return true;
  	}
  	
  	Log.v(TAG, "ADK not connected");
  	return false;
  }
  
  
  public void setInputStream(FileInputStream s) {
	  mInputStream = s;
  }
  
  public void setOutputStream(FileOutputStream s) {
	  mOutputStream = s;
  }
  
  public void setFileDescriptor(ParcelFileDescriptor f) {
	  mFileDescriptor = f;
  }
  
  public void setUsbAccessory(UsbAccessory a) {
	  mAccessory = a;
  }
  
  
}
