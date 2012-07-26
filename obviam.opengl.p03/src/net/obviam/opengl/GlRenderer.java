/**
 * 
 */
package net.obviam.opengl;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;

/**
 * @author impaler
 *
 */
public class GlRenderer implements Renderer {

	private Square 		square;		// the square
	private Context 	context;
	
	private ArrayList<Square> squares;
	
	/** Constructor to set the handed over context */
	public GlRenderer(Context context) {
		this.context = context;
		
		// initialise the square
		this.square = new Square(R.drawable.android);
		
		// create a grid
		createSquaresGrid(8,8);

		
	}
	
	public void createSquaresGrid(int sizeX,int sizeY) {
		this.squares = new ArrayList<Square>();
		for (int y=0; y<sizeY; y++) {
			final float fy = (float)y*2; 
			
			for (int x=0; x<sizeX; x++) {
				final float fx = (float)x*2;

				float vertices[] = {
						-1.0f+fx, -1.0f+fy,  0.0f,		// V1 - bottom left
						-1.0f+fx,  1.0f+fy,  0.0f,		// V2 - top left
						 1.0f+fx, -1.0f+fy,  0.0f,		// V3 - bottom right
						 1.0f+fx,  1.0f+fy,  0.0f			// V4 - top right
				};
				
				squares.add(new Square(R.drawable.android,vertices));
			}
		}		
	}
	

	@Override
	public void onDrawFrame(GL10 gl) {
		// clear Screen and Depth Buffer
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		// Reset the Modelview Matrix
		gl.glLoadIdentity();

		// Drawing
		gl.glTranslatef(0.0f, 0.0f, -100.0f);		// move 5 units INTO the screen
												// is the same as moving the camera 5 units away
//		gl.glScalef(0.5f, 0.5f, 0.5f);			// scale the square to 50% 
												// otherwise it will be too large
		square.draw(gl);						// Draw the triangle
		
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
		square.loadGLTexture(gl, this.context);
		
		for (Square s : squares) {
			s.loadGLTexture(gl, this.context);
		}
		
		gl.glEnable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping ( NEW )
		gl.glShadeModel(GL10.GL_SMOOTH); 			//Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); 	//Black Background
		gl.glClearDepthf(1.0f); 					//Depth Buffer Setup
		gl.glEnable(GL10.GL_DEPTH_TEST); 			//Enables Depth Testing
		gl.glDepthFunc(GL10.GL_LEQUAL); 			//The Type Of Depth Testing To Do
		
		//Really Nice Perspective Calculations
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST); 

	}

}
