package com.tracker.Direction_func;
/**
SOURCE 1:
Adapted from 
* From:http://stackoverflow.com/questions/3109158/how-to-draw-a-path-on-a-map-using-kml-file
* Date 19:/03/2011

SOURCE 2:
* From:http://developer.android.com/guide/topics/location/obtaining-user-location.html
* Date 18:/11/2011

*/
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

import com.tracker.R;
import com.tracker.httpconnector;
import com.tracker.Find_freinds_func.adapter;
import com.tracker.Mylocation_func.DbAdapterLoc;
import android.widget.ListView;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class Directuser  extends MapActivity{
    /** Called when the activity is first created. */
  
    private List<Overlay> mapOverlays;
private ArrayList<Placemark> places;
    private Projection projection;
    private  ArrayList<String> inst;
    
    private  Context mCtx;
    private DbAdapterLoc localcon;
    private httpconnector con;
    private String phone_id;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directions);
        
        //A bundle object to get the extras that have been passed.
        Bundle extras = getIntent().getExtras();
        // Obtain the phone id sent from the intent which are stored as extra's
       phone_id = extras.getString("PHONE_ID");
       mCtx = this;
       Log.i("sax parser: ",phone_id);
       
       
       
       
       // Database connections
     con = new httpconnector();
     localcon = new  DbAdapterLoc(this);
     
     //opening local location database.
       localcon.open();
       //Retriving current coordinates of the user.
     Location loc = localcon.getLocCords();
        // converting the coordinates into integers for the geopoint's.
        int lats = (int) (loc.getLongitude()*1E6);
        int lngs = (int) (loc.getLatitude()* 1E6);
// Constructing the GeoPoint  identifying current location.
      GeoPoint src = new GeoPoint(lngs,lats);
        Log.i("sax parser: ",src.toString());
        //Closing local database.
      localcon.close();
       
      // Get  the entities location as a GeoPoint object
        GeoPoint dest = con.getUserLoc(phone_id);
       // building the Url to get a Stream oF GeoPoints
        String url = con.getUrl(src, dest);
        
 //Returning Geographical data as place mark objects.
         places = con.getNavigationInstructions(url);
 // Check if the array contains placemarks is not empty.
        if(places!=null){

        int i =0;
// array list to store the navigation instructions.
        inst = new  ArrayList <String>();
        
 // list array to store each GeoPoint for the drawing of route       
      List <GeoPoint> points = new ArrayList<GeoPoint> ();
 // Iteration of the placemark array.
            for (Iterator<Placemark> iter=places.iterator();iter.hasNext();) {
	        Placemark p = (Placemark)iter.next();
	        if(p.getLatitude()!=null){
	        	//checking if there is coordinates in each placemark.
	       double rawlat =Double.parseDouble(p.getLatitude());
	       double rawlng =Double.parseDouble(p.getLongitude());
	       // converting the raw coordinates to string for GeoPoint obejcts
	        int lat =((int)(rawlat *1E6));
	        int lng =((int)(rawlng*1E6));
	        Log.i("map cords: ", p.getLatitude()+"="+lat+","+p.getLongitude()+"="+lng);
	        // Creating a new GeoPoint out of each placemark coordinates. 
	       points.add(new GeoPoint(lat,lng));
	        Log.i("sax parser: ", p.getTitle());
	        // inserting the title of a placemark into the String list array.
	        inst.add(p.getTitle());
	        }else{
	        	// if there are no coordinates then return the title or error.
	        	inst.add(p.getTitle());
	        }
        }
        
        
        
        
       
        
       
        
        LinearLayout slide = (LinearLayout) findViewById(R.id.content);
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        
        mapOverlays = mapView.getOverlays();        
        projection = mapView.getProjection();
 
     
 
for (int j=1; j <points.size(); j++){
// add the map overlays.
	mapOverlays.add( new MyOverlay(points.get(j-1),points.get(j)));
	   Log.i("array index: ", "length:"+points.get(j-1).toString()+" to"+points.get(j).toString());
}
 
        
        
        Log.i("array index: ", "length:");
        
        // getting the list view form the slider drawer.
        ListView sliderListView = (ListView) findViewById(R.id.listview_);
        // Creating the list view adapter to be used.
        /** the parameters is the main context, so the activities parents view
         and the list of strings givign dirrections. **/
       DirListAdapter adp = new  DirListAdapter(  mCtx ,inst) ;
       // Setting the slider list view the apater which will return the list.
       sliderListView.setAdapter(adp);
     
    }
        
    }
	    
 
        
        
        
        
        
        
        
        
        
      
        
    
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 0, 0,"Main Menu");
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case 0:
            //    createNote();
            	finish();
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	// Overlay class.
	class MyOverlay extends Overlay{ 
		GeoPoint goeP1 ;
    GeoPoint geoP2 ;
// constructor for the over lay class
	    public MyOverlay(GeoPoint P1,GeoPoint P2){
	    	
	    	 goeP1=P1;
	    	 geoP2=P2;
	    	 
	    }   
// overriden draw method called automatically 
	    public void draw(Canvas canvas, MapView mapv, boolean shadow){
	        super.draw(canvas, mapv, shadow);

	        // paint object to define the prefrences of the line to be drawn
	            Paint mPaint = new Paint();
	        mPaint.setDither(true);
	        mPaint.setColor(Color.RED);
	        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
	        mPaint.setStrokeJoin(Paint.Join.ROUND);
	        mPaint.setStrokeCap(Paint.Cap.ROUND);
	        mPaint.setStrokeWidth(20);

	        GeoPoint gP1 =  goeP1;
	        GeoPoint gP2 =  geoP2;

	        
	     // the points the will represent the start and finish of the line.
	        Point p1 = new Point();
	        Point p2 = new Point();

	        // path object the draw a reference path.
	            Path path = new Path();
     // A projection object which converts between coordinates and pixels on the screen.
	            Projection projection = mapv.getProjection();
	            // converting of coordinates to pixel
	            projection.toPixels(gP1, p1);
	        projection.toPixels(gP2, p2);
// draw a reference path
	        path.moveTo(p2.x, p2.y);
	        path.lineTo(p1.x,p1.y);
// draw the actual path.
	        canvas.drawPath(path, mPaint);	
	    }
	   
}
}