package com.tracker.Mylocation_func;
/*SOURCE 1:
* From:http://developer.android.com/guide/topics/location/obtaining-user-location.html
* Date 18:/11/2011
*/
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class TrackerItemizedOverlay extends ItemizedOverlay {
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
private Context mContext;
	public TrackerItemizedOverlay(Drawable defaultMarker,Context cont) {
	
		
		super(boundCenterBottom(defaultMarker));
		// TODO Auto-generated constructor stub
	 mContext = cont;
	}

	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	
	
	protected OverlayItem createItem(int i) {
		  return mOverlays.get(i);
		}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mOverlays.size();
	}
	public void removeOverlay(int index) {
	    mOverlays.remove(index);
	    
	}

	protected boolean onTap(int index) {
	
		  OverlayItem item = mOverlays.get(index);
		  AlertDialog.Builder dialog = new AlertDialog.Builder( mContext );
		  dialog.setTitle(item.getTitle());
		  dialog.setMessage(item.getSnippet());
		  dialog.show();
		  return true;
		}


}
