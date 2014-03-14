package com.tracker.Find_freinds_func;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tracker.R;

public class Friends_menu_adp extends BaseAdapter{

	    private ArrayList <menuitem>items;
	    private Context cont;

	 
	    public Friends_menu_adp(Context context, ArrayList <menuitem> array)
	    {
	        super();
	        this.items = array;
	        this.cont = context;
	      
	     
	    }

	    @Override
	    public int getCount() {
	        return items.size();
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
	       
	        TextView title;
	        TextView descrip;
	        
	       
	        
	       
	        try
	        {       
	            if(items.get(position)!=null)
	            {
	                menuitem item = items.get(position);
	               String desc= item.getDescr();
	              Log.i("testfirendslist", "workd");
	                
	                if (v == null) {
	                    v = LayoutInflater.from(cont).inflate(R.layout.friends_menu_list, null);
	                }           
	              title=(TextView)v.findViewById(R.id.Title);
	              descrip=(TextView)v.findViewById(R.id.Desc);
	              
	              if( title != null)
	              {
	                 title.setText(item.getTitle());
	                 descrip.setText(item.getDescr());
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


