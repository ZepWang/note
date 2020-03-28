package com.example.note;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;

public class NoteModel extends SQLiteOpenHelper {

    public static final String DBName = "spending.db";
    public static final String TableName = "SpendingTable";
    public static final String ItemName = "Item";
    public static final String DateName = "Date";
    public static final String PriceName = "Price";

    public NoteModel(Context context){
        super(context, DBName, null, 4);
    }

    public void onCreate(SQLiteDatabase sqlDB){
        sqlDB.execSQL("CREATE TABLE " + TableName + " (Count INTEGER PRIMARY KEY AUTOINCREMENT, Item varchar(100), Date varchar(100), Price DOUBLE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int older, int newer){
        sqlDB.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(sqlDB);
    }


    public boolean createHistory(String item, String data, double price){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ItemName, item);
        contentValues.put(DateName, data);
        contentValues.put(PriceName, price);
        long result = db.insert(TableName, null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }

    public Cursor pullData() {
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        Cursor res = sqlDB.rawQuery("SELECT * FROM " + TableName, null);
        return res;
    }
}