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


package com.tracker.AddFav_func;

import com.google.android.maps.GeoPoint;
import com.tracker.Mylocation_func.DbAdapterLoc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbAdapterFPlaces {

    private static final String DB_NAME = "PlacesData";
    private static final String TABLE_PLACES= "Fav_places";
    public static final String ID = "_id";
    public static final String PLACENAME = "placename";
    public static final String COL_LONG = "long";
    public static final String COL_LAT = "lat";
  
    
	private static final int DB_VERSION = 2;

	private final Context mCtx;
    private DatabaseHelperFPlaces mDbHelper;
    private SQLiteDatabase mDb;
	
	private static final String CREATE_DB_TABLE = 
		
		"CREATE TABLE "+TABLE_PLACES+" ("+ 
			ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ 
			PLACENAME+ " TEXT," +
			
			COL_LONG + " DOUBLE," +
			COL_LAT  + " DOUBLE" +
			");";
	
		
	
	
	
	private static class DatabaseHelperFPlaces extends SQLiteOpenHelper {

		DatabaseHelperFPlaces(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_DB_TABLE);
			//db.execSQL(CREATE_TEST_DATA1);
			//db.execSQL(CREATE_TEST_DATA2);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			// if the new version is higher than the old version, 
			// delete existing tables:
			if (newVersion > oldVersion) {
	            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
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
	public DbAdapterFPlaces(Context ctx) {
		this.mCtx = ctx;
	}

    public DbAdapterFPlaces open() throws SQLException {
        mDbHelper = new DatabaseHelperFPlaces(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

   
    
    
    
   
    
    
    
    
    public long createFavPlace (String placename,  double longa, double lat) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(PLACENAME, placename);
        initialValues.put(COL_LAT, lat);
        initialValues.put(COL_LONG, longa);
        return mDb.insert(TABLE_PLACES, null, initialValues);
    }
    
    
    
    public Cursor getPlaces() throws SQLException {
    	// Query all records in the database table
   Cursor cus = mDb.query(TABLE_PLACES, null, null, null, null, null, null);
       // return all records as a cursor object.
      return cus;
    }
    
    public boolean updatePlace(long rowId, String title) {
        ContentValues args = new ContentValues();
        args.put(PLACENAME, title);
       

        return mDb.update(TABLE_PLACES, args, ID + "=" + rowId, null) > 0;
    }
    public String getPlace(long rowId) throws SQLException {
String name ="";
        Cursor mCursor =

            mDb.query(true, TABLE_PLACES,null,ID + "=" + rowId, null,
                    null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
            
           name = mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapterFPlaces.PLACENAME));
           mCursor.close();
        }
        return name;

    }
    
    public GeoPoint getPlacePoint(long rowId) throws SQLException {
String name ="";

GeoPoint point = null ;
// Query the database table for record using the record ID
        Cursor mCursor =

            mDb.query(true, TABLE_PLACES,null,ID + "=" + rowId, null,
                    null, null, null, null);
        
        // If records are returned then move to the first record.
        if (mCursor != null) {
            mCursor.moveToFirst();
            // obtain the lattitude and longiitude from the record 
          int lng = (int) (mCursor.getDouble(mCursor.getColumnIndexOrThrow(DbAdapterFPlaces.COL_LONG ))*1E6);
          int lat  = (int)(mCursor.getDouble(mCursor.getColumnIndexOrThrow(DbAdapterFPlaces.COL_LAT ))*1E6);
         //Instialise a GeoPoint.
          point = new GeoPoint (lng,lat);
           mCursor.close();
        }
        // Return the Geopoint.
        return point;

    }
    
    public boolean deleteFav(long rowId) {

        return mDb.delete(TABLE_PLACES, ID + "=" + rowId, null) > 0;
    }
    
    
}

