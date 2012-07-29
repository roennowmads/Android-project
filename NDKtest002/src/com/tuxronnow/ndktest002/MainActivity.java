package com.tuxronnow.ndktest002;

import java.io.DataOutputStream;
import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	
	static {
		System.loadLibrary("ffmpeg-prebuilt");
		System.loadLibrary("ndk1");
	}

	private native void memoryMap(); 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        String[] chCom = {"su", "-c", "chmod 777 /dev/graphics/fb0"};
        try {
			executeRootProcess(chCom);
			memoryMap();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void executeRootProcess(String[] command) throws IOException {
        Process p = Runtime.getRuntime().exec(command);
        
        DataOutputStream os = new DataOutputStream(p.getOutputStream());  
        os.close();
        
        try {
			p.waitFor();
			if (p.exitValue() != 255) 
	            Log.e("cat", "pass");
			else 
	            Log.e("cat", "fail");
			
        } catch (InterruptedException e) {
			e.printStackTrace();
		} 
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
