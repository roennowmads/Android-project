package com.tuxronnow.screen.recorder;

import java.io.DataOutputStream;
import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        try {
			String[] chCom = {"su", "-c", "chmod 777 /dev/graphics/fb0"};
            
			Process p = Runtime.getRuntime().exec(chCom);
            
            DataOutputStream os = new DataOutputStream(p.getOutputStream());  
            os.close();
            
            try {
				p.waitFor();
				
				if (p.exitValue() != 255) { 
					// TODO Code to run on success  
					
		            Toast toastMessage = Toast.makeText(this, "chmod", Toast.LENGTH_SHORT);  
		            toastMessage.show();
		            Log.e("chmod", "pass");
				}
				else {
					Toast toastMessage = Toast.makeText(this, "chmod failed", Toast.LENGTH_SHORT);  
		            toastMessage.show();
		            Log.e("chmod", "fail");
				}
				Log.e("chmod", "anyway");
					
				
            } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
            String[] cpCom = {"su", "-c", "cat /dev/graphics/fb0 > /sdcard/frame.raw"};
            
            p = Runtime.getRuntime().exec(cpCom);
            
            os = new DataOutputStream(p.getOutputStream());  
            os.close();
            
            try {
				p.waitFor();
				
				if (p.exitValue() != 255) { 
					// TODO Code to run on success  
					
		            Toast toastMessage = Toast.makeText(this, "cat", Toast.LENGTH_SHORT);  
		            toastMessage.show();
		            Log.e("cat", "pass");
				}
				else {
					Toast toastMessage = Toast.makeText(this, "cat failed", Toast.LENGTH_SHORT);  
		            toastMessage.show();
		            Log.e("cat", "fail");
				}
					
				
            } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("root", "fail");
        }
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
