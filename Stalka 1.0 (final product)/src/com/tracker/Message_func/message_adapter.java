package com.tracker.Message_func;
/*Code adapted from:
* URL :http://thinkandroid.wordpress.com/2010/01/13/custom-baseadapters/
* Date acces: 21/01/2012
* Author: jwei
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

public class message_adapter extends BaseAdapter{

    private JSONArray items;
    private Context cont;
private Location loc ;
 
    public message_adapter(Context context, JSONArray array)
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
       
        TextView username;
        TextView message;
        
       
    
        
       
        try
        {       
            if(!items.isNull(position))
            {
                JSONObject item = items.getJSONObject(position);
                
              
                
          
                
                if (v == null) {
                    v = LayoutInflater.from(cont).inflate(R.layout.messages_list, null);
                }           
                username=(TextView)v.findViewById(R.id.user);
                message=(TextView)v.findViewById(R.id.mess);
              
              if(username != null)
              {
            	  username.setText(item.getString("USERNAME"));
            	  message.setText(item.getString("mess"));
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
