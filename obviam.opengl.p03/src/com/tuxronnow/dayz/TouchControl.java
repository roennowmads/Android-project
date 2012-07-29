package com.tuxronnow.dayz;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;


public class TouchControl extends View {
	
	private static final int INVALID_POINTER_ID = -1;

	// The ‘active pointer’ is the one currently moving our object.
	private int mActivePointerId = INVALID_POINTER_ID;
	
	private ScaleGestureDetector mScaleDetector;
	private float mScaleFactor = 1.f;
	


	private float mPosX;
    private float mPosY;
    
    private float mLastTouchX;
    private float mLastTouchY;

    public TouchControl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
		
	}    
        

	public float getmScaleFactor() {
		return mScaleFactor;
	}


	public float getmPosX() {
		return mPosX;
	}

	public float getmPosY() {
		return mPosY;
	}

	public float getmLastTouchX() {
		return mLastTouchX;
	}

	public float getmLastTouchY() {
		return mLastTouchY;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		mScaleDetector.onTouchEvent(ev);
		
	    final int action = ev.getAction();
	    switch (action & MotionEvent.ACTION_MASK) {
	    case MotionEvent.ACTION_DOWN: {
	        final float x = ev.getX();
	        final float y = ev.getY();
	        
	        mLastTouchX = x;
	        mLastTouchY = y;

	        // Save the ID of this pointer
	        mActivePointerId = ev.getPointerId(0);
	        break;
	    }
	        
	    case MotionEvent.ACTION_MOVE: {
	        // Find the index of the active pointer and fetch its position
	        final int pointerIndex = ev.findPointerIndex(mActivePointerId);
	        final float x = ev.getX(pointerIndex);
	        final float y = ev.getY(pointerIndex);
	        
	        final float dx = x - mLastTouchX;
	        final float dy = y - mLastTouchY;
	        
	        mPosX += dx;
	        mPosY += dy;
	        
	        mLastTouchX = x;
	        mLastTouchY = y;
	        
	        invalidate();
	        break;
	    }
	        
	    case MotionEvent.ACTION_UP: {
	        mActivePointerId = INVALID_POINTER_ID;
	        break;
	    }
	        
	    case MotionEvent.ACTION_CANCEL: {
	        mActivePointerId = INVALID_POINTER_ID;
	        break;
	    }
	    
	    case MotionEvent.ACTION_POINTER_UP: {
	        // Extract the index of the pointer that left the touch sensor
	        final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) 
	                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
	        final int pointerId = ev.getPointerId(pointerIndex);
	        if (pointerId == mActivePointerId) {
	            // This was our active pointer going up. Choose a new
	            // active pointer and adjust accordingly.
	            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
	            mLastTouchX = ev.getX(newPointerIndex);
	            mLastTouchY = ev.getY(newPointerIndex);
	            mActivePointerId = ev.getPointerId(newPointerIndex);
	        }
	        break;
	    }
	    }
	    
	    return true;
	}

	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
		
		
	    public ScaleListener() {
			super();
		}

		@Override
	    public boolean onScale(ScaleGestureDetector detector) {	    
	        mScaleFactor *= Math.max(0.1f, Math.min(detector.getScaleFactor(), 5.0f));
	        //Log.d("mScaleFactor", Float.toString(mScaleFactor));
	        invalidate();
	        return true;
	    }


	    
	    
	}
}


