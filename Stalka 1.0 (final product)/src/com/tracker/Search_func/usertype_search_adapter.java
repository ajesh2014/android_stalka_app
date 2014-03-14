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

public class usertype_search_adapter extends BaseAdapter{

    private JSONArray items;
    private Context cont;
private Location loc ;
 
    public usertype_search_adapter(Context context, JSONArray array)
    {
        super();
        this.items = array;
        this.cont = context;
       
     
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
       
        TextView type;
        TextView no;
        
       
    
        
       
        try
        {       
            if(!items.isNull(position))
            {
                JSONObject item = items.getJSONObject(position);
                
              
                
          
                
                if (v == null) {
                    v = LayoutInflater.from(cont).inflate(R.layout.usertype_list, null);
                }           
              type=(TextView)v.findViewById(R.id.type);
              no=(TextView)v.findViewById(R.id.num);
              
              if(type != null)
              {
            	  type.setText(item.getString("Type"));
            	  no.setText(item.getString("No"));
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
