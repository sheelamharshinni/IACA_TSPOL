package com.tecdatum.iaca_tspolice.DataBase;

/**
 * Created by HI on 1/9/2018.
 */


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hi on 11/25/2016.
 */


public class DbHandlerCrime extends SQLiteOpenHelper {
    //here
    public static final String DATABASE = "database.Crime";
    public static final int VERSION = 1;
    public static final String TABLE_CONTACTS = "Crime";
    private static final String KEY_ID = "_id";
    public DbHandlerCrime(Context context) {
        super(context, DATABASE, null, VERSION);
        // TODO Auto-generated constructor stub
    }
    @Override
    public void onCreate(SQLiteDatabase database) {
        // TODO Auto-generated method stub
        database.execSQL("create table  Crime (_id integer primary key autoincrement," +
                "crimeNumber text," +
                "crimeTypeId text," +
                "crimeType text," +
                "subCrimeTypeId text," +
                "subCrimeType text," +
                "dateTime text, " +
                "location text," +
                "detectedStatus text," +
                "detectedStatusId text," +
                "latitude text," +
                "longitude text," +
                "description text)");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        // Create tables again
        onCreate(db);
    }

    public void deleteRecord(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

}
