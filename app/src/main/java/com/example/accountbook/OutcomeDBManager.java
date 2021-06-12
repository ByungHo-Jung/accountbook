package com.example.accountbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OutcomeDBManager extends SQLiteOpenHelper {

    public OutcomeDBManager(Context context){
        super(context, "OutcomeDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Outcome (year int, month int, day int, money int, name text, type text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
