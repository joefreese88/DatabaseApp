package net.androidbootcamp.databaseapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Joseph on 7/2/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // create table rows, names and headings
    public static final String DATABASE_NAME = "CARDEALER.db";
    public static final String TABLE_NAME = "CUSTOMER_TABLE";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "CUSTOMER_FIRST_NAME";
    public static final String COL_3 = "CUSTOMER_LAST_NAME";
    public static final String COL_4 = "CAR_MAKE";
    public static final String COL_5 = "COST";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,CUSTOMER_FIRST_NAME TEXT,CUSTOMER_LAST_NAME TEXT,CAR_MAKE TEXT,COST INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    // pass data to add to the database
    public boolean insertData(String firstName, String lastName, String carMake, int cost)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, firstName);
        contentValues.put(COL_3, lastName);
        contentValues.put(COL_4, carMake);
        contentValues.put(COL_5, cost);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

    // method to retrieve all information from database
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    // pass data that will be updated in the database
    public boolean updateData( String id, String firstName, String lastName, String carMake, int cost)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, firstName);
        contentValues.put(COL_3, lastName);
        contentValues.put(COL_4, carMake);
        contentValues.put(COL_5, cost);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] {id});
        return true;
    }

    // pass the customer ID to delete all of that customers data
    public Integer deleteData (String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }
}
