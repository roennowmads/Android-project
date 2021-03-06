/**
 * 
 */
package com.tuxronnow.dayz;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.util.Log;

/**
 * @author impaler
 *
 */
public class GlRenderer implements Renderer{
    	
	
	private Context 			context;
	private TouchControl 		touchControl;
	private ArrayList<Square>	squares;
	
	float angle;
	
	/** Constructor to set the handed over context */
	public GlRenderer(Context context,TouchControl touchControl) {
		this.context = context;
		
		// initialise the square
		//this.square = new Square(R.drawable.android);
		
		
		angle = 0.0f;
		
		// create a grid
		createSquaresGrid(17,14);

		this.touchControl = touchControl;

		
	}	
	
	private void createSquaresGrid(int sizeX,int sizeY) {
		float globalTransX = 8f;
		float globalTransY = -8f;
		this.squares = new ArrayList<Square>();
		int counter = 0;
		for (int y=0; y<sizeY; y++) {
			final float fy = (float)(y+1)*2*-1; 
			
			for (int x=0; x<sizeX; x++) {
				counter+=1;
				final float fx = (float)(x+1)*2;

				if (!((x+1) == sizeX)) {
					float vertices[] = {
							-1.0f+fx-globalTransX,	-1.0f+fy-globalTransY,  0.0f,		// V1 - bottom left
							-1.0f+fx-globalTransX,   1.0f+fy-globalTransY,  0.0f,		// V2 - top left
							 1.0f+fx-globalTransX,	-1.0f+fy-globalTransY,  0.0f,		// V3 - bottom right
							 1.0f+fx-globalTransX,	 1.0f+fy-globalTransY,  0.0f	    // V4 - top right
					};
					
					
					
					squares.add(new Square(counter,vertices));					
				}
				

			}
		}		
	}
	

	@Override
	public void onDrawFrame(GL10 gl) {
		// clear Screen and Depth Buffer
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// Reset the Modelview Matrix
		gl.glLoadIdentity();

		//angle = (angle+0.5f) % 360.0f;
		//gl.glTranslatef(0f,0f, 48f * (float)Math.sin(Math.PI * ((angle % 360.0f)/180.0f)));
		
		gl.glTranslatef(0f,0f,-25f);
		
		final float mScaleFactor = touchControl.getmScaleFactor(); 	
		final float transX = (touchControl.getmPosX())/100*mScaleFactor;
		final float transY = (touchControl.getmPosY()/100*mScaleFactor*-1);
		
		final float deltaX = touchControl.getmFocusX()/100*mScaleFactor;
		final float deltaY = touchControl.getmFocusY()/100*mScaleFactor;
		
		//final float deltaX = touchControl.getmFocus();				
		
		gl.glTranslatef(transX,transY ,0f);
		
		
		//Log.d("mScaleFactor", Float.toString(mScaleFactor));
		gl.glScalef(mScaleFactor,mScaleFactor,0f);
				
		for (Square s : squares) {
			s.draw(gl);
		}

		
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if(height == 0) { 						//Prevent A Divide By Zero By
			height = 1; 						//Making Height Equal One
		}

		gl.glViewport(0, 0, width, height); 	//Reset The Current Viewport
		gl.glMatrixMode(GL10.GL_PROJECTION); 	//Select The Projection Matrix
		gl.glLoadIdentity(); 					//Reset The Projection Matrix

		//Calculate The Aspect Ratio Of The Window
		GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 0.1f, 100.0f);

		gl.glMatrixMode(GL10.GL_MODELVIEW); 	//Select The Modelview Matrix
		gl.glLoadIdentity(); 					//Reset The Modelview Matrix
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Load the texture for the square
		//square.loadGLTexture(gl, this.context);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping ( NEW )
		gl.glShadeModel(GL10.GL_SMOOTH); 			//Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); 	//Black Background
		gl.glClearDepthf(1.0f); 					//Depth Buffer Setup
		gl.glEnable(GL10.GL_DEPTH_TEST); 			//Enables Depth Testing
		gl.glDepthFunc(GL10.GL_LEQUAL); 			//The Type Of Depth Testing To Do
		
		for (Square s : squares) {
			try {
				s.loadGLTexture(gl, this.context);
			} catch (FileNotFoundException e) {
				Log.d("s: "+s.getFormattedTileNumber(), e.getLocalizedMessage());
				e.printStackTrace();
				
			}
		}
		
		//Really Nice Perspective Calculations
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST); 

	}

}
