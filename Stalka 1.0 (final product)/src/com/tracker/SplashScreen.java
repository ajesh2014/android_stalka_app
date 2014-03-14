package com.tracker;

/* **********************************************************
*
  Code researched and adapted
*  from :http://p-xr.com/android-tutorial-how-to-make-a-basic-splash-screen/
*  Title : Android tutorial: How to make a basic splash screen
*  Site name : Programmer XR 
*  Authors :http://p-xr.com/category/android-tutorials/
*  Date Accssed :25/11/2011
*
*************************************************************/


import com.tracker.Login_func.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class SplashScreen extends Activity {
	
	
	private Thread splashTread;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.splash);
	    
	    
	    final SplashScreen Screen = this; 
	    
	    // thread for displaying the SplashScreen
	    splashTread = new Thread() {
	        @Override
	        public void run() {
	            try {	            	
	            	synchronized(this){
	            		wait(5000);
	            	}
	            	
	            } catch(InterruptedException e) {} 
	            finally {
	                finish();
	                
	                Intent i = new Intent();
	                i.setClass(Screen, Login.class);
	        		startActivity(i);
	                
	                stop();
	            }
	        }
	    };
	    
	    splashTread.start();
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
	    	synchronized(splashTread){
	    		splashTread.notifyAll();
	    	}
	    }
	    return true;
	}
	
}
