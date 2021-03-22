package com.example.knowledge.contentprovider;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;

public class DBManager {
    private String TAG = "DBManager";
    private static DBManager instance;
    private Context context;

    private DBManager() {
    }

    public static DBManager getInstance() {
        if (instance == null) {
            synchronized (DBManager.class) {
                if (instance == null)
                    instance = new DBManager();
            }
        }
        return instance;
    }

    public void initDB(Context context) {
        this.context = context;

    }

    /**
     * 添加书籍的事件监听
     */
    public void addBooks() {
        try {
            Uri bookUri = BookProvider.BOOK_CONTENT_URI;
            ContentValues values = new ContentValues();
            values.put("_id", 6);
            values.put("name", "信仰上帝");
            context.getContentResolver().insert(bookUri, values);
        } catch (Exception e) {
            Log.e(TAG, "addBooks-e:" + e);
        }
    }

    public void batchAddBooks() {
        try {
            Uri bookUri = BookProvider.BOOK_CONTENT_URI;

            ArrayList<ContentProviderOperation> ops = new ArrayList<>();
            for (int i = 4; i < 7; i++) {
                ContentValues values = new ContentValues();
                values.put("_id", i);
                values.put("name", "batchAdd书籍 " + i);
                ops.add(ContentProviderOperation.newInsert(bookUri)
                        .withValues(values).build());
            }
            ContentProviderResult rs[] = context.getContentResolver().applyBatch(BookProvider.AUTHORITY, ops);
            for (ContentProviderResult s : rs) {
                Log.d(TAG, "batchAddBooks-s:" + s);
            }
        } catch (Exception e) {
            Log.e(TAG, "batchAddBooks-e:" + e);
        }
    }

    public void batchUpdateBooks() {
        try {
            Uri bookUri = BookProvider.BOOK_CONTENT_URI;

            ArrayList<ContentProviderOperation> ops = new ArrayList<>();
            for (int i = 4; i < 7; i++) {
                ContentValues values = new ContentValues();
                values.put("name", "batchUpdate书籍 " + i);
                ops.add(ContentProviderOperation.newUpdate(bookUri)
                        .withSelection("_id=?",
                                new String[]{i + ""})
                        .withValues(values).build());
            }
            ContentProviderResult rs[] = context.getContentResolver().applyBatch(BookProvider.AUTHORITY, ops);
            for (ContentProviderResult s : rs) {
                Log.d(TAG, "batchUpdateBooks-s:" + s);
            }
        } catch (Exception e) {
            Log.e(TAG, "batchUpdateBooks-e:" + e);
        }
    }

    /**
     * 更新书籍的事件监听
     */
    public void updateBooks() {
        try {
            Uri bookUri = BookProvider.BOOK_CONTENT_URI;
            ContentValues values = new ContentValues();
            String where = String.format(Locale.US, "%s = %d", "_id", 6);
            values.put("_id", 6);
            values.put("name", "高磊");
            int count = context.getContentResolver().update(bookUri, values, where, null);
        } catch (Exception e) {
            Log.e(TAG, "deleteBooks-e:" + e);
        }
    }

    /**
     * 删除书籍的事件监听
     */
    public void deleteBooks() {
        try {
            Uri bookUri = BookProvider.BOOK_CONTENT_URI;
            String where = String.format(Locale.US, "%s = %d", "_id", 6);
            int count = context.getContentResolver().delete(bookUri, where, null);
        } catch (Exception e) {
            Log.e(TAG, "deleteBooks-e:" + e);
        }
    }

    /**
     * 显示书籍
     */
    public String queryBooks() {
        StringBuilder content = new StringBuilder();
        Uri bookUri = BookProvider.BOOK_CONTENT_URI;
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(bookUri, new String[]{"_id", "name"}, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Book book = new Book();
                    book.bookId = cursor.getInt(0);
                    book.bookName = cursor.getString(1);
                    content.append(book.toString()).append("\n");
                    Log.d(TAG, "query book: " + book.toString());
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "queryBooks-e:" + e);
        } finally {
            closeCursor(cursor);
        }
        return content.toString();
    }

    /**
     * 显示作者
     */
    public String queryUsers() {
        Cursor cursor = null;
        StringBuilder content = new StringBuilder();
        try {

            Uri userUri = BookProvider.USER_CONTENT_URI;
            cursor = context.getContentResolver().query(userUri, new String[]{"_id", "name", "sex"}, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    User user = new User();
                    user.userId = cursor.getInt(0);
                    user.userName = cursor.getString(1);
                    user.gender = cursor.getInt(2);
                    content.append(user.toString()).append("\n");
                    Log.d(TAG, "query user:" + user.toString());
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "queryUsers-e:" + e);
        } finally {
            closeCursor(cursor);
        }
        return content.toString();
    }


    private void closeCursor(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Exception e) {
                Log.e(TAG, "closeCursor-e:" + e);
            }
        }
    }
}
