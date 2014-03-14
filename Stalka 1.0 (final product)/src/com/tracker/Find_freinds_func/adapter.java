package com.tracker.Find_freinds_func;


/*
 * Code adapted from:
 * URL :http://thinkandroid.wordpress.com/2010/01/13/custom-baseadapters/
 * Date acces: 21/01/2012
 * Author: jwei
 * 
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
        View v = convertView;
       
        TextView fname;
        TextView lname;
        TextView coords;
       
       Location pointb = new Location("pointb");
        
       
        try
        {       
            if(!items.isNull(position))
            {
                JSONObject item = items.getJSONObject(position);
                
                pointb.setLatitude(item.getDouble("LAT"));
                pointb.setLongitude(item.getDouble("LNG"));
                
             float distance = loc.distanceTo(pointb);
                String dis = Float.toString(distance)+" Meters";
                
                if (v == null) {
                    v = LayoutInflater.from(cont).inflate(R.layout.list_item, null);
                }           
              fname=(TextView)v.findViewById(R.id.firstName);
              lname=(TextView)v.findViewById(R.id.lastName);
              coords=(TextView)v.findViewById(R.id.longcod);
              
              if(fname != null)
              {
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
        return v;
    

    }
	
}
