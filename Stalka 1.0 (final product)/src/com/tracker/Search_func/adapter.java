package com.tracker.Search_func;
/*
 * Code adapted from:
 * URL :http://thinkandroid.wordpress.com/2010/01/13/custom-baseadapters/
 * Date acces: 21/01/2012
 * Author: jwei
 * 
 */
import org.json.JSONArray;
import org.json.JSONObject;

import com.tracker.R;
import com.tracker.R.id;
import com.tracker.R.layout;

import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class adapter extends BaseAdapter{

    private JSONArray items;
    private Context cont;
private Location loc ;
 
    public adapter(Context context, JSONArray array, Location location)
    {
        super();
        this.items = array;
        this.cont = context;
        this.loc= location;
     
    }

    @Override
    public int getCount() {
        return items.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;

    }@Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {
    	// check if the array postion is not null
        View v = convertView;
        TextView fname;
        TextView lname;
        TextView coords;
       
       Location pointb = new Location("pointb");
        
       
        try
        {       
            if(!items.isNull(position))
            {
            	// get the JSON Object from the JSON array.
                JSONObject item = items.getJSONObject(position);
                // Assing the location object, the users longitude and latitude.
                pointb.setLatitude(item.getDouble("LAT"));
                pointb.setLongitude(item.getDouble("LNG"));
                // calculating distance.
             float distance = loc.distanceTo(pointb);
             // Assigning the distance to a string for TextView.
                String dis = Float.toString(distance)+" Meters";
                // If the view is empty then inflate the view and obtain the list views. 
                if (v == null) {
                    v = LayoutInflater.from(cont).inflate(R.layout.list_item, null);
                }         
                // Assigning  view from xml file to a variable declared.
              fname=(TextView)v.findViewById(R.id.firstName);
              lname=(TextView)v.findViewById(R.id.lastName);
              coords=(TextView)v.findViewById(R.id.longcod);
              
              // if the view is not already filled with data then assign the itmes from the JSON object.
              if(fname != null)
              {
            	  //Assigning data from JSON Array to the relavent Views
                  fname.setText(item.getString("F_NAME"));
                  lname.setText(item.getString("L_NAME"));
                  coords.setText(dis);
              }
        }
            
        }
        catch(Exception e)
        {
            Log.e("num", "Saved Art Error! " + e.toString());
        }
        // returning the View (List) back.
        return v;
    

    }
	
}
