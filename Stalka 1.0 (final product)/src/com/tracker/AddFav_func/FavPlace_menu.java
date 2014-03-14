package com.tracker.AddFav_func;

import java.util.ArrayList;

import org.json.JSONObject;

import com.tracker.R;
import com.tracker.Message_func.Messages_menu;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FavPlace_menu  extends ListActivity {
    private static Context mCtx;
    private static ArrayList <menuitem> menuarray;
    
    
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_menu);
        mCtx= this;
        TextView title = (TextView)  this.findViewById(R.id.textView1); 
		title.setText("Favourite places:"); 
        
        
        menuarray = new ArrayList<menuitem>();
        
        menuitem add = new menuitem("Add Places","add new places",0);
        menuitem view = new menuitem("Fav Places","view your favourite places",1);
  
       // menuitem Messages = new menuitem("View messages","view your messages",2);
        menuarray.add(add);
        menuarray.add(view);
       
      //  menuarray.add(Messages);
        
       // Log.i("test",friends.getDescr());
        
        Fav_menu_adp adp = new  Fav_menu_adp ( mCtx ,menuarray) ;
           setListAdapter(adp);
        
    }
    
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        
        if(menuarray.get(position).getPos()==0){
        	Intent i = new Intent(this, FavPlace.class);
			
    			startActivity(i);
        }

        
        
        
        if(menuarray.get(position).getPos()==1){
        	Intent i = new Intent(this, Veiw_fav_place.class);
			
    			 startActivity(i);
        }
        if(menuarray.get(position).getPos()==2){
        //	Intent i = new Intent(this, Messages_menu.class);
			
    		//	 startActivity(i);
        }

}
			
   }
    

