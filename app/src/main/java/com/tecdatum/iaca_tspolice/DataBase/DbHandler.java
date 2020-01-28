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


public class DbHandler extends SQLiteOpenHelper {
    //here
    public static final String DATABASE = "database.location";
    public static final int VERSION = 1;
    public static final String TABLE_CONTACTS = "Location";
    private static final String KEY_ID = "_id";
    public DbHandler(Context context) {
        super(context, DATABASE, null, VERSION);
        // TODO Auto-generated constructor stub
    }
    @Override
    public void onCreate(SQLiteDatabase database) {
        // TODO Auto-generated method stub
        database.execSQL("create table  Location (_id integer primary key autoincrement," +
                "crimeNumber text," +
                "AccidentType text," +
                "dateTime text, " +
                "location text," +
                "RoadTypeId text," +
                "RoadNumber text," +
                "detectedStatus text," +
                "latitude text," +
                "longitude text," +
                "description text," +
                "Noofinjuries text," +
                "Noofdeaths text," +
                "VictimcategorieId text, " +
                "VictimStatus text," +
                "VictimAlcoholicPercentage text," +
                "AccusedCategorieId text," +
                "AccusedStatus text," +
                "AccusedAlcoholicPercentage text," +
                "AccidentType1 text," +
                "RoadType text," +

                "Victimcategorie text," +
                "AccusedCategorie text," +
                "VictimStatusName text," +
                "AccusedStatusName text," +

                "Locality text)");

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
