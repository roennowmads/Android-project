package com.tuxronnow.screen.recorder;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
            executeRootProcess(chCom);
			
            String[] cpCom = {"su", "-c", "cat /dev/graphics/fb0 > /sdcard/frame.raw"};
            executeRootProcess(cpCom);
            
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("root", "fail");
        }
        
        ArrayList<Byte> input = new ArrayList<Byte>();
        
        try {
			FileInputStream fis = new FileInputStream(Environment.getExternalStorageDirectory().getPath() + "/frame.raw");
			
			int c = 0;
			int element = fis.read();
			while (element >= 0) {
				//Log.d("data", "" + element);
				input.add((byte)element);
				element = fis.read();
				c++;
			}
			fis.close();
			Log.d("c: ", "" + c);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        byte[] data = new byte[input.size()];
        
        
        for (int i = 0; i < data.length; i++) {
        	data[i] = input.get(i).byteValue();
        }
        
        
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        
        try {
			FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/frame.bmp");
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
