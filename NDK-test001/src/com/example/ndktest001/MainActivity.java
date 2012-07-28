package com.example.ndktest001;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	static {  
	    System.loadLibrary("ndk1");  
	}  
	
	private native void helloLog(String logThis); 
	private native String getString(int value1, int value2); 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        
        try {
			Process p = Runtime.getRuntime().exec("su");
	        Button b = new Button(this);
	        b.setWidth(50);
	        b.setHeight(30);
	        b.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View arg0) {
					helloLog("This will log to LogCat via the native call.");  
					String result = getString(5,2);  
					Log.v("Debug Output", "Result: "+result);  
					result = getString(105, 1232);  
					Log.v("Debug Output", "Result2: "+result);  
					
				}
	        	
	        });
	        
	        setContentView(b);
        
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
