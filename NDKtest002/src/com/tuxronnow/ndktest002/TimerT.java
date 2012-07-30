package com.tuxronnow.ndktest002;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class TimerT extends TimerTask {
	
	/*static {
		//System.loadLibrary("ffmpeg-prebuilt");
		System.loadLibrary("ndk1");
	}*/
	
	private int i = 0;
	private Context context;
	//private native byte[] memoryMap(String filename); 
	private boolean hasPrepared = false;
	private ScreenShotService service;
	
	public TimerT(Context context, ScreenShotService s) {
		this.context = context;	
		this.service = s;
	}
	
	private final Handler toastHandler = new Handler()
	{
	    @Override
	    public void handleMessage(Message msg)
	    {
	        Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();
	    }
	}; 

	public void run() {
		/*if (!hasPrepared) {
			Looper.prepare();
			hasPrepared = true;
		}*/
		service.callMemoryMap(i);
		//memoryMap("");
		//Looper.loop();
		
		//Log.d("timer: ", "" + i);
		//Looper.prepare();
		
		//toastHandler.sendEmptyMessage(0);
		//memoryMap("/sdcard/screenshots/screen" + i + ".bmp");
		//Toast.makeText(thisServ, "Screenshot taken", Toast.LENGTH_SHORT).show();
		
		i++;
		
		//Looper.loop();
	}
	
}
