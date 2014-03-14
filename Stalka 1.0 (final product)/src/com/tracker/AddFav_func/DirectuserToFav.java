package com.tracker.AddFav_func;
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
import com.tracker.Direction_func.DirListAdapter;
import com.tracker.Direction_func.Placemark;
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

public class DirectuserToFav  extends MapActivity{
    /** Called when the activity is first created. */
  
    private List<Overlay> mapOverlays;
private ArrayList<Placemark> places;
    private Projection projection;
    private  ArrayList<String> inst;
    private  DbAdapterFPlaces dbHelper;
    private  Context mCtx;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directions);
        
        Bundle extras = getIntent().getExtras();
		String ide = extras.getString("ID");
        long   id = Long.parseLong(ide);
        dbHelper = new  DbAdapterFPlaces(this);
        dbHelper.open();
        Log.i("sax parser: ",ide);
       final httpconnector con = new httpconnector();
       
       
       
       
     final DbAdapterLoc localcon = new  DbAdapterLoc(this);
     
   
     
     
     
     mCtx = this;
     
     MapView mapView = (MapView) findViewById(R.id.mapview);
     mapView.setBuiltInZoomControls(true);
     
     
     // getting the users current location from the local database table location.
        localcon.open();
     Location loc = localcon.getLocCords();
        // Convert the Location objecet into a GeoPoint object.
        int lats = (int) (loc.getLongitude()*1E6);
        int lngs = (int) (loc.getLatitude()* 1E6);
        
      GeoPoint src = new GeoPoint(lngs,lats);
        Log.i("sax parser: ",src.toString());
        // close the location database.
      localcon.close();
       /**calling method to get the faviourate place as a GeoPoint, 
      using the DbAdapterFPlaces adapter which was opened earlier **/
        GeoPoint dest =  dbHelper.getPlacePoint(id);
        Log.i("sax parser: ",dest.toString());
        // close the faviourte places database table.
        dbHelper.close();
        // check for an Internet connection
       if( con.IsConnected()){
    	   // Constructing the Url using the two GeoPoint Objects.
        String url = con.getUrl(src, dest);
        //Obtainign the Placemark Array
         places = con.getNavigationInstructions(url);
       }
        if(places!=null){
	    	 
	    	 
	    	 
        int i =0;
        
        inst = new  ArrayList <String>();
        
        
        
      List <GeoPoint> points = new ArrayList<GeoPoint> ();
        for (Iterator<Placemark> iter=places.iterator();iter.hasNext();) {
	        Placemark p = (Placemark)iter.next();
	        if(p.getLatitude()!=null){
	       double rawlat =Double.parseDouble(p.getLatitude());
	       double rawlng =Double.parseDouble(p.getLongitude());
	        int lat =((int)(rawlat *1E6));
	        int lng =((int)(rawlng*1E6));
	        Log.i("map cords: ", p.getLatitude()+"="+lat+","+p.getLongitude()+"="+lng);
	       points.add(new GeoPoint(lat,lng));
	        Log.i("sax parser: ", p.getTitle());
	        inst.add(p.getTitle());
	        }else{
	        	inst.add(p.getTitle());
	        }
        }
        
        
        
        
       
        
       
        
        LinearLayout slide = (LinearLayout) findViewById(R.id.content);
        
        
        
        mapOverlays = mapView.getOverlays();        
        projection = mapView.getProjection();
 
     
 
for (int j=1; j <points.size(); j++){

	mapOverlays.add( new MyOverlay(points.get(j-1),points.get(j)));
	   Log.i("array index: ", "length:"+points.get(j-1).toString()+" to"+points.get(j).toString());
}
 
        
        
        Log.i("array index: ", "length:");
        
        
        ListView sliderListView = (ListView) findViewById(R.id.listview_);
        
       DirListAdapter adp = new  DirListAdapter(  mCtx ,inst) ;
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
	class MyOverlay extends Overlay{ 
		GeoPoint goeP1 ;
    GeoPoint geoP2 ;

	    public MyOverlay(GeoPoint P1,GeoPoint P2){
	    	
	    	 goeP1=P1;
	    	 geoP2=P2;
	    	 
	    }   

	    public void draw(Canvas canvas, MapView mapv, boolean shadow){
	        super.draw(canvas, mapv, shadow);

	            Paint mPaint = new Paint();
	        mPaint.setDither(true);
	        mPaint.setColor(Color.RED);
	        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
	        mPaint.setStrokeJoin(Paint.Join.ROUND);
	        mPaint.setStrokeCap(Paint.Cap.ROUND);
	        mPaint.setStrokeWidth(20);

	        GeoPoint gP1 =  goeP1;
	        GeoPoint gP2 =  geoP2;

	        
	     
	        Point p1 = new Point();
	        Point p2 = new Point();

	            Path path = new Path();

	            Projection projection = mapv.getProjection();
	            projection.toPixels(gP1, p1);
	        projection.toPixels(gP2, p2);

	        path.moveTo(p2.x, p2.y);
	        path.lineTo(p1.x,p1.y);

	        canvas.drawPath(path, mPaint);
	    }
	   
}
}