package com.example.knowledge.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 书籍的数据库
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "book_provider.db";
    static final String BOOK_TABLE_NAME = "book";
    static final String USER_TABLE_NAME = "author";

    private static final int DB_VERSION = 1;

    DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOK_TABLE = "CREATE TABLE IF NOT EXISTS "
                + BOOK_TABLE_NAME + "(_id INTEGER PRIMARY KEY, name TEXT)";
        String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS "
                + USER_TABLE_NAME + "(_id INTEGER PRIMARY KEY, name TEXT, sex INT)";
        db.execSQL(CREATE_BOOK_TABLE);
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
