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


package com.tracker.Login_func;

import com.tracker.Mylocation_func.DbAdapterLoc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbAdapterUser {

    private static final String DB_NAME = "UserData";
    private static final String TABLE_LOGIN = "User_Data";
    public static final String ID = "_id";
    public static final String PHONE_ID = "phone_id";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String FIRSTNAME = "firstname";
    public static final String LASTNAME = "lastname";
    public static final String PASSWORD = "password";
    public static final String TYPE = "type";
    
	private static final int DB_VERSION = 6;

	private final Context mCtx;
    private DatabaseHelperLogin mDbHelper;
    private SQLiteDatabase mDb;
	
	private static final String CREATE_DB_TABLE = 
		
		"CREATE TABLE "+TABLE_LOGIN+" ("+ 
			ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ 
			PHONE_ID+ " TEXT," +
			EMAIL + " TEXT NOT NULL, " + 
			USERNAME + " TEXT NOT NULL, " + 
			FIRSTNAME + " TEXT NOT NULL, " + 
			LASTNAME + " TEXT NOT NULL," + 
			PASSWORD + " TEXT NOT NULL," +
			TYPE + " TEXT NOT NULL" +
			");";
	
		
	
	
	
	private static class DatabaseHelperLogin extends SQLiteOpenHelper {

		DatabaseHelperLogin(Context context) {
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
	            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
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
	public DbAdapterUser(Context ctx) {
		this.mCtx = ctx;
	}

    public DbAdapterUser open() throws SQLException {
        mDbHelper = new DatabaseHelperLogin(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

   
    
    
    
   
    
    
    
    
    public long createUser (String PhoneId, String UserName, String email, String firstname, String lastname, String Password, String type) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(PHONE_ID, PhoneId);
        initialValues.put(USERNAME, UserName);
        initialValues.put(FIRSTNAME, firstname );
        initialValues.put(LASTNAME, lastname);
        initialValues.put(EMAIL,email);
        initialValues.put(TYPE,type);
        initialValues.put(PASSWORD,Password);
        return mDb.insert(TABLE_LOGIN, null, initialValues);
    }
    
    
    
    public int getUser() throws SQLException {
int i = 0;
    	
    	
        Cursor mCursor = mDb.query(TABLE_LOGIN, null, 
		          null, null, null, null, null);
        		
        	
        if (mCursor != null) {
         if( mCursor.moveToFirst()==true){
        	 i=1;
         }else{
        	 i=0;
         }
            
        }
        mCursor.close();
        return i;

    }
    
    public String getUserPhoneID() throws SQLException {
    String ID="";
    	    	
    	    	
    	        Cursor mCursor = mDb.query(TABLE_LOGIN, null, 
    			          null, null, null, null, null);
    	        		
    	        	
    	        if (mCursor != null) {
    	         if( mCursor.moveToFirst()==true){
    	        	 ID =(mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapterUser.PHONE_ID)));
    	         }
    	            
    	        
    	        mCursor.close();
    	      

    	    }
				return ID;
    
   
}
}
