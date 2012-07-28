package com.tuxronnow.ip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.ads.*;
import com.tuxronnow.ip.R;

public class MainActivity extends Activity {
	
	private String ipResult = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        
        AdView adView = new AdView(this, AdSize.BANNER, "a150102bc1ee29b");

        // Lookup your LinearLayout assuming it’s been given
        // the attribute android:id="@+id/mainLayout"
        LinearLayout layout = (LinearLayout) findViewById(R.id.linear);
        
        //Button button = new Button(this);
        
        //layout.addView(button);
        
        // Add the adView to it
        layout.addView(adView);
        
        AdRequest adRequest = new AdRequest();
        //adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
        
        adView.loadAd(adRequest); 
        
        Task t = new Task();
		t.execute("http://ifconfig.me/ip");

    }    
    
    public void onConfigurationChanged(Configuration newConfig) {
    	super.onConfigurationChanged(newConfig);
    	//TextView tv = (TextView) findViewById(R.id.textView1);
		//tv.setVisibility(View.VISIBLE);
		//tv.setText(newConfig.orientation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        
        final Activity thisActivity = this;
        
        menu.findItem(R.id.refresh).setOnMenuItemClickListener(new OnMenuItemClickListener() {

			public boolean onMenuItemClick(MenuItem arg0) {
				ProgressBar progress = (ProgressBar) findViewById(R.id.progressBar1);
				progress.setVisibility(View.VISIBLE);
				TextView tv = (TextView) findViewById(R.id.textView1);
				tv.setVisibility(View.GONE);
				
				Task t = new Task();
				t.execute("http://ifconfig.me/ip");
				return true;
			}
    		
    	});
        
        menu.findItem(R.id.copy).setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@SuppressWarnings("deprecation")
			@SuppressLint("NewApi")
			public boolean onMenuItemClick(MenuItem item) {
				String copiedIP = ipResult;
				
				int sdk = android.os.Build.VERSION.SDK_INT;
				if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
					android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
				    clipboard.setText(copiedIP);
				} else {
				    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE); 
				    android.content.ClipData clip = android.content.ClipData.newPlainText("IP address", copiedIP);
				    clipboard.setPrimaryClip(clip);
				}
				
				Toast toast = Toast.makeText(thisActivity, "IP address copied to clipboard", Toast.LENGTH_SHORT);
				toast.show();
				
				return false;
			}
        	
        });
        return true;
    }
    
    public void setIpResult (String ipResult) {
    	this.ipResult = ipResult;
    }
    
    private class Task extends AsyncTask<String, Void, String> {
    	
    	AndroidHttpClient client;

		@Override
		protected String doInBackground(String... arg0) {
			String url = arg0[0];
			client = AndroidHttpClient.newInstance("");
			String r = null; 
			
			try {
				HttpResponse resp = client.execute(new HttpGet(url));
				InputStream content = resp.getEntity().getContent();
				BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
				
				r = buffer.readLine();
				
			} catch (IOException e) {
				r = "Error";
				e.printStackTrace();
			}

			return r;
		}
		
		protected void onPostExecute(String result) {
			ProgressBar progress = (ProgressBar) findViewById(R.id.progressBar1);
			progress.setVisibility(View.GONE);
			TextView tv = (TextView) findViewById(R.id.textView1);
			tv.setVisibility(View.VISIBLE);
			tv.setText(result);
			setIpResult(result);
			client.close();
		}
    	
    }
}
