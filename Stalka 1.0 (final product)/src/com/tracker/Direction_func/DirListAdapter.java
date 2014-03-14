package com.tracker.Direction_func;
/*
* Class adapted from a class example
* 
* SOURCE 1:
* from:http://code.google.com/p/custom-list-data-example-android/downloads/detail?name=CustomDataList_CodeProject.zip&can=2&q=
* Date :Feb 11, 2011
* author Joseph Fernandez 
* 
* SOURCE 2:
* from : http://developer.android.com/resources/tutorials/notepad/notepad-ex2.html
*  Date 19/11/2011
*/
import java.lang.reflect.Array;
import java.util.ArrayList;

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

public class DirListAdapter extends BaseAdapter{

    private ArrayList <String> instructions;
    private Context cont;
private Location loc ;
 // Constructor for the adapter.
    public DirListAdapter(Context context, ArrayList <String> array)
    {
        super();
        this.instructions = array;
        this.cont = context;
       
     
    }

    @Override
    public int getCount() {
        return instructions.size();
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
    	// view object. 
        View v = convertView;
        // text view variable which corrisponds to text view in dir_list.
        TextView place;
        try
        {       
        	// check if the index at the current position of the list is not null
            if(instructions.get(position)!=null)
            {
            	// get the string at the current postion if not null
            	String test = instructions.get(position);
                  
                if (v == null) {
                	// create an instance object of the dir_list layout xml file.
                    v = LayoutInflater.from(cont).inflate(R.layout.dir_list, null);
                }    
                // get the text view from the layout object.
              place=(TextView)v.findViewById(R.id.item);
             
              // check if the text view is not null.
              if(place != null)
              {
            	  // set the text view a string at the current position in the list array of strings. 
                 place.setText(test);
              }
        }
        }
        catch(Exception e)
        {
            Log.e("adapter error", "list is empty " + e.toString());
        }
        // Return the view to the parent view.
        return v;
    }
	
}
