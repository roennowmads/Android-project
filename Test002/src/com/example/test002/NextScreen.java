package com.example.test002;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class NextScreen extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_screen);
        
        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Task t = new Task();
				t.execute("http://ifconfig.me/ip");
			}
        	
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_next_screen, menu);
        return true;
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return r;
		}
		
		protected void onPostExecute(String result) {
			TextView tv = (TextView) findViewById(R.id.textView3);
			tv.setText(result);
		}
    	
    }
}
