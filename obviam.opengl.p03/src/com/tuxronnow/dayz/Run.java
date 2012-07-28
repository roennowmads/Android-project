package com.tuxronnow.dayz;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;

public class Run extends Activity {
	
	/** The OpenGL view */
	private GLSurfaceView glSurfaceView;
	private GlRenderer renderer;
	private TouchControl touchControl;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		
		//Control touch through touchControl
		this.touchControl = new TouchControl(this);
		
        // Initiate the Open GL view and
        // create an instance with this activity
        glSurfaceView = new GLSurfaceView(this);
        
        // set our renderer to be the main renderer with
        // the current activity context
        renderer = new GlRenderer(this,touchControl);
        glSurfaceView.setRenderer(renderer);
        setContentView(glSurfaceView);
    }

	/**
	 * Remember to resume the glSurface
	 */
	@Override
	protected void onResume() {
		super.onResume();
		glSurfaceView.onResume();
	}

	/**
	 * Also pause the glSurface
	 */
	@Override
	protected void onPause() {
		super.onPause();
		glSurfaceView.onPause();
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return this.touchControl.onTouchEvent(ev);
	}
	
	

}