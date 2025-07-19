package com.example.samitiapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.samitiapplication.data.model.UserInfo;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "samitiDatabase.db";

    private static final String TABLE_NAME = "monthly_installment";
    private static final String COL1 = "member_id";
    private static final String COL2 = "member_name";
    private static final String COL3 = "installment_status";

    private static final String COL4 = "emi_amount";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 2);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " ("+COL1+" INTEGER,"+COL2+" TEXT,"+ COL3 +" TEXT,"+ COL4 +" NUMBER)");

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
        contentValues.put(COL4, userInfo.getEmiAmount());
        return database.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor getAllData() {
        SQLiteDatabase database = this.getWritableDatabase();
        return database.rawQuery("select * from " + TABLE_NAME, null);
    }

    public void resetTable() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from "+TABLE_NAME);
    }

    public void updateRecord(int memberId, String value) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("update "+TABLE_NAME+" set "+COL3+"="+value+" where "+COL1+"="+memberId);
    }
}
