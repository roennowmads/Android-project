package com.example.test002;

import com.example.test002.R.layout;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //String a = getString(R.string.hello_world);
        
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new OnClickListener () {	

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(MainActivity.this, NextScreen.class);
				startActivity(i);
				//TextView a = (TextView) findViewById(R.id.textView1);
				//a.setText("Knap trykket");
			}
        	
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
