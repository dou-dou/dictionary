package com.example.dictionary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by doudou on 2016/6/20.
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    public static final String CREATE_UNFAMILIAR_WORD = "create table unfamiliar_word(id integer primary key autoincrement ,word text,ph_en_map3_src text,parts text)";
    public static final String CREATE_HISTORY_WORD = "create table history_word(id integer primary key  autoincrement ,word text,ph_en_map3_src text,parts text)";

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_UNFAMILIAR_WORD);
        db.execSQL(CREATE_HISTORY_WORD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
