package com.tuxronnow.ndktest002;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	static {
		//System.loadLibrary("ffmpeg-prebuilt");
		System.loadLibrary("ndk1");
	}

	//private native void memoryMap(String filename); 
	//private native void test(); 
	//private native void getString(int value1, int value2); 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setupButtonListeners();
        
        //test();
		//getString(1,1);
		
		//memoryMap("/sdcard/screenshots/screen" + 0 + ".bmp");
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private void setupButtonListeners() {
    	Button buttonStart = (Button) findViewById(R.id.buttonstart);
        Button buttonStop = (Button) findViewById(R.id.buttonstop);
        
        final Activity thisAct = this;
        
        buttonStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//memoryMap("/sdcard/screen" + 0 + ".bmp");
				startService(new Intent(thisAct, ScreenShotService.class));
			}
        });
        
        buttonStop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				stopService(new Intent(thisAct, ScreenShotService.class));
			}
        });
    }
}
