/*
 /*
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

 

package com.tracker.Mylocation_func;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

public class DbAdapterLoc {

    private static final String DB_NAME = "LocationData";
    private static final String TABLE_ITEMS = "Location_items";
    public static final String COL_ID = "_id";
    public static final String COL_UPD = "update";
    public static final String COL_LONG = "long";
    public static final String COL_LAT = "lat";
    
	private static final int DB_VERSION = 3;

	private final Context mCtx;
    private DatabaseHelperLoc mDbHelper;
   public SQLiteDatabase mDb;
	
	private static final String CREATE_DB_TABLE = 
		
		"CREATE TABLE "+TABLE_ITEMS+" ("+ 
			COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ 
			COL_LONG + " DOUBLE, " + 
			COL_LAT+ " DOUBLE" +
			COL_UPD+ " INTEGER" +
			");";
	

		
	
	
	
	private static class DatabaseHelperLoc extends SQLiteOpenHelper {

		DatabaseHelperLoc(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_DB_TABLE);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			// if the new version is higher than the old version, 
			// delete existing tables:
			if (newVersion > oldVersion) {
	            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
	            onCreate(db);
			} else {
				// otherwise, create the database
	            onCreate(db);
			}

		}

	}

	/**
	 * Constructor - takes the context to allow the database to be
	 * opened/created
	 * 
	 * @param ctx the Context within which to work
	 */
	public DbAdapterLoc(Context ctx) {
		this.mCtx = ctx;
	}
//Open method
    public DbAdapterLoc open() throws SQLException {
        mDbHelper = new DatabaseHelperLoc(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public Cursor fetchListItems() {

    	Cursor cursor = 
    		mDb.query(TABLE_ITEMS, new String[] 
    		          {COL_ID, COL_LAT, COL_LONG }, 
    		          null, null, null, null, null);
    	
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
      //  cursor.requery();
    }
    
    
 
    
    public boolean  updateCords( double longa, double lat ) {
    	// Intializing the content value object to be passed in the update query
        ContentValues args = new ContentValues();
        //Placing values into the object and assigning the columns that need to be updated.
        args.put(COL_LAT, lat);
        args.put(COL_LONG, longa);
 
        // check if a record exists 
        Cursor nCursor = getCords(1);
        // if no record exists then return false.
        if(nCursor==null){
         	 
        	return false;

       }
        //close cursor to free resources.
        nCursor.close();
   		//else if exists then run a update query.
    return    mDb.update(TABLE_ITEMS, args, COL_ID + "=" + 1, null) > 0;
    }
    
    
    public long createCords ( double longa, double lat) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(COL_LAT, lat);
        initialValues.put(COL_LONG, longa);
       
        //initialValues.put(COL_TITLE, title);
        return mDb.insert(TABLE_ITEMS, null, initialValues);
    }
    
    
    public Cursor getCords(long rowId) throws SQLException {

        Cursor mCursor =

            mDb.query(true, TABLE_ITEMS, new String[] {COL_ID,
                    COL_LAT, COL_LONG}, COL_ID + "=" + rowId, null,
                    null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
      
    }
    
    public Location getLocCords() throws SQLException {
 // create location object
    	Location loc = new Location("dbLoc");
    	// execute query and assign records to a cursor.
        Cursor mCursor =

            mDb.query(true, TABLE_ITEMS, new String[] {COL_ID,
                    COL_LAT, COL_LONG}, COL_ID + "=" + 1, null,
                    null, null, null, null);
        // if the cursor is not null then assign the location object the coordinates. 
        if (mCursor != null) {
            mCursor.moveToFirst();
            loc.setLatitude(mCursor.getDouble(mCursor.getColumnIndexOrThrow(DbAdapterLoc.COL_LAT)));
            loc.setLongitude(mCursor.getDouble(mCursor.getColumnIndexOrThrow(DbAdapterLoc.COL_LONG)));
            
        }
        mCursor.close();
        // return the location object.
        return loc;
      
    }
    
    public Location getLatestCords() throws SQLException {

    	Location loc = new Location("dbLoc");
    	
        Cursor mCursor =

            mDb.query(true, TABLE_ITEMS, new String[] {
                    COL_LAT, COL_LONG}, COL_UPD + "=" + 1, null,
                    null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
            loc.setLatitude(mCursor.getDouble(mCursor.getColumnIndexOrThrow(DbAdapterLoc.COL_LAT)));
            loc.setLongitude(mCursor.getDouble(mCursor.getColumnIndexOrThrow(DbAdapterLoc.COL_LONG)));
            
        }
        mCursor.close();
        return loc;
      
    }
    
    
    
    
}
