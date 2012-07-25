package com.example.test002;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import com.example.test002.R.layout;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
	

	private String ipResult = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
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
        return true;
    }
    
    public void setIpResult (String ipResult) {
    	this.ipResult = ipResult;
    }
    
    private class Task extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... arg0) {
			String url = arg0[0];
			AndroidHttpClient client = AndroidHttpClient.newInstance("");
			String r = null; 
			
			try {
				HttpResponse resp = client.execute(new HttpGet(url));
				InputStream content = resp.getEntity().getContent();
				BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
				
				r = buffer.readLine();
				
			} catch (IOException e) {
				r = "Error";
				// TODO Auto-generated catch block
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
		}
    	
    }
}
