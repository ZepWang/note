package com.example.note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteData extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "spendingManager.db";
    public static final String TABLE_NAME = "spendingManager";
    public static final String item_2 = "ITEM";
    public static final String date_3 = "DATE";
    public static final String price_4 = "PRICE";
    public SQLiteData(Context context){
        super(context, DATABASE_NAME, null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, ITEM varchar(250), DATE varchar(250), PRICE DOUBLE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int NewVersion){
        db.execSQL("DROP TABLE IF EXISTS "
                + TABLE_NAME);
        onCreate(db);
    }

    public boolean createTransaction(TM model){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(item_2, model.mItem);
        contentValues.put(date_3, model.mDate);
        contentValues.put(price_4, model.mPrice);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) { return false; }
        return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM "
                + TABLE_NAME, null);
        return result;
    }
}