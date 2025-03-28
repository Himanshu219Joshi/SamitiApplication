package com.example.samitiapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.samitiapplication.data.model.UserInfo;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SamitiDatabase.db";

    private static final String TABLE_NAME = "Monthly_Installment";
    private static final String COL1 = "Member_ID";
    private static final String COL2 = "Member_Name";
    private static final String COL3 = "Installment_Status";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " ("+COL1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COL2+" TEXT,"+ COL3 +" TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(db);
    }

    public long insetData(UserInfo userInfo) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, userInfo.getMemeberId());
        contentValues.put(COL2, userInfo.getMemberName());
        contentValues.put(COL3, userInfo.getInstallmentStatus());
        return database.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor getAllData() {
        SQLiteDatabase database = this.getWritableDatabase();
        return database.rawQuery("select * from " + TABLE_NAME, null);
    }
}
