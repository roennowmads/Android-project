package com.tuxronnow.ndktest002;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ScreenShotService extends Service {
	
	/*static {
		System.loadLibrary("ndk1");
	}*/
	
	private native byte[] memoryMap(String filename); 
	private Timer t;
	private final Service thisServ = this;
	
	public void callMemoryMap(int i) {
		String zeros = "";
		int temp_i = i;
		while (temp_i / 10 > 0) {
			zeros = zeros + "0";
			temp_i = temp_i / 10; 
		}
		if (i < 10) 
			zeros = "00";
		
		byte[] data = memoryMap("");
		
		Log.d("data size: ", "" + data.length);
		
		String header = "";
		for (int j = 0; j<5000; j++) {
			header += data[i] + " ";
		}
		Log.d("header ", "" + header);
		
		
		BitmapFactory.Options opt = new BitmapFactory.Options();
		//opt.inDither = true;
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		
		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, 0, opt);
		
		
		Log.d("byte count: ", "" + bitmap.getWidth());
		
		/*try {
			FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/screenshots/img"+ zeros + i + ".jpg");
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	@Override
	public void onCreate() {
		Toast.makeText(this, "ScreenShotService Created", Toast.LENGTH_SHORT).show();
		String[] chCom = {"su", "-c", "chmod 777 /dev/graphics/fb0"};
		try {
			executeRootProcess(chCom);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//memoryMap("/sdcard/screen" + 0 + ".bmp");
		t = new Timer();
		t.scheduleAtFixedRate(new TimerT(getApplicationContext(), this), 0, 100);
	}
	
	@Override
	public void onDestroy() {
		Toast.makeText(this, "ScreenShotService Stopped", Toast.LENGTH_SHORT).show();
		t.cancel();
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
		Toast.makeText(this, "ScreenShotService Started", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
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
	
	

}
