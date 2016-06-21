package com.example.dictionary.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dictionary.db.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by doudou on 2016/6/20.
 */
public class DictionaryDB {
    private  static  DictionaryDB dictionaryDB;
    public static final String DB_NAME="DictionaryDB";
    public static final int VERSION=1;
    private static SQLiteDatabase db;

    private DictionaryDB(Context context){
        DBOpenHelper dbOpenHelper=new DBOpenHelper(context,DB_NAME,null, VERSION);
        db=dbOpenHelper.getReadableDatabase();

    }
    public synchronized static DictionaryDB getInstance(Context context){
        if(dictionaryDB==null){
             dictionaryDB=new DictionaryDB(context);
        }
        return dictionaryDB;
    }
    public static void saveWord(Word word){
        ContentValues contentValues=new ContentValues();
        contentValues.put("word",word.getWord());
        contentValues.put("parts",word.getParts());
        contentValues.put("ph_en_map3_src",word.getPh_en_map3_src());
        Log.i("xianshi",word.getWord());
        Log.i("xianshi",word.getParts());
        Log.i("xianshi",word.getPh_en_map3_src());
        db.insert("unfamiliar_word",null,contentValues);
    }
    public static int  getSum(){
        Cursor cursor=db .query("unfamiliar_word",null,null,null,null,null,null);
        return cursor.getCount();
    }
    public static boolean querySameWord(String word){
        Cursor cursor=db .query("unfamiliar_word",null,"word=?",new String[]{word},null,null,null);
        return cursor.moveToFirst();
    }
    public static List<Word> load_unfamiliar_word(){
        List<Word> list=new ArrayList<>();
        Cursor cursor=db .query("unfamiliar_word",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                Word word=new Word();
                word.setWord(cursor.getString(cursor.getColumnIndex("word")));
                word.setParts(cursor.getString(cursor.getColumnIndex("parts")));
                word.setPh_en_map3_src(cursor.getString(cursor.getColumnIndex("ph_en_map3_src")));
                list.add(word);
            }while (cursor.moveToNext());
        }
        return list;

    }

}
