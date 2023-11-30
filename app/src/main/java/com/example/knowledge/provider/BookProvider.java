package com.example.knowledge.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;


public class BookProvider extends ContentProvider {

    private static final String TAG = "DEBUG-WCL: " + BookProvider.class.getSimpleName();

    public static final String AUTHORITY = "org.wangchenlong.book.provider"; // 与AndroidManifest保持一致
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + DBHelper.BOOK_TABLE_NAME);
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + DBHelper.USER_TABLE_NAME);

    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // 关联Uri和Uri_Code
    static {
        sUriMatcher.addURI(AUTHORITY, DBHelper.BOOK_TABLE_NAME, BOOK_URI_CODE);
        sUriMatcher.addURI(AUTHORITY, DBHelper.USER_TABLE_NAME, USER_URI_CODE);
    }

    private Context mContext;
    private SQLiteDatabase mDb;

    @Override
    public boolean onCreate() {
        showLogs("onCreate 当前线程: " + Thread.currentThread().getName());
        mContext = getContext();

        initProviderData(); // 初始化Provider数据

        return false;
    }

    private void initProviderData() {
        mDb = new DBHelper(mContext).getWritableDatabase();
        mDb.execSQL("delete from " + DBHelper.BOOK_TABLE_NAME);
        mDb.execSQL("delete from " + DBHelper.USER_TABLE_NAME);
        mDb.execSQL("insert into " + DBHelper.BOOK_TABLE_NAME + " values(1,'Android');");
        mDb.execSQL("insert into " + DBHelper.BOOK_TABLE_NAME + " values(2, 'iOS');");
        mDb.execSQL("insert into " + DBHelper.BOOK_TABLE_NAME + " values(3, 'HTML5');");
        mDb.execSQL("insert into " + DBHelper.USER_TABLE_NAME + " values(1, 'ZhaoYue', 1);");
        mDb.execSQL("insert into " + DBHelper.USER_TABLE_NAME + " values(2, 'GaoLei', 0);");
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        showLogs("query 当前线程: " + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return mDb.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    @Override
    public String getType(Uri uri) {
        showLogs("getType");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        showLogs("insert");
        String table = getTableName(uri);
        if (TextUtils.isEmpty(table)) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        long id = -1;
        id = mDb.insert(table, null, values);
        Log.d(TAG, "insert - uri :" + uri.getPath() + "  id: " + id);
        if (id >= 0) {
            // 插入数据后通知改变
            mContext.getContentResolver().notifyChange(uri, null);
            return uri;
        } else {
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        showLogs("delete");

        String table = getTableName(uri);
        if (TextUtils.isEmpty(table)) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int count = mDb.delete(table, selection, selectionArgs);
        if (count > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }

        return count; // 返回删除的函数
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        showLogs("update");

        String table = getTableName(uri);
        if (TextUtils.isEmpty(table)) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int row = mDb.update(table, values, selection, selectionArgs);
        if (row > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        Log.d(TAG, "update - uri :" + uri.getPath() + "  row: " + row);
        Log.d(TAG, "update - uri :" + uri.getPath() + "  selection: " + selection);
        return row; // 返回更新的行数
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (sUriMatcher.match(uri)) {
            case BOOK_URI_CODE:
                tableName = DBHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DBHelper.USER_TABLE_NAME;
                break;
            default:
                break;
        }
        return tableName;
    }

    private void showLogs(String msg) {
        Log.d(TAG, msg);
    }
}