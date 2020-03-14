package com.example.knowledge.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;

import java.util.Locale;

public class ProviderActivity extends AppCompatActivity {

    private static final String TAG = "DEBUG-WCL: " + ProviderActivity.class.getSimpleName();

    private TextView mTvShowBooks; // 显示书籍
    private TextView mTvShowUsers; // 显示用户

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contentprovider);

        mTvShowBooks = findViewById(R.id.main_tv_show_books);
        mTvShowUsers = findViewById(R.id.main_tv_show_users);
    }

    /**
     * 添加书籍的事件监听
     *
     * @param view 视图
     */
    public void addBooks(View view) {
        Uri bookUri = BookProvider.BOOK_CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put("_id", 6);
        values.put("name", "信仰上帝");
        getContentResolver().insert(bookUri, values);
    }

    /**
     * 更新书籍的事件监听
     *
     * @param view 视图
     */
    public void updateBooks(View view) {
        Uri bookUri = BookProvider.BOOK_CONTENT_URI;
        ContentValues values = new ContentValues();
        String where = String.format(Locale.US, "%s = %d", "_id", 6);
        values.put("_id", 6);
        values.put("name", "高磊");
        int count=getContentResolver().update(bookUri, values, where, null);
    }

    /**
     * 删除书籍的事件监听
     *
     * @param view 视图
     */
    public void deleteBooks(View view) {
        Uri bookUri = BookProvider.BOOK_CONTENT_URI;
        ContentValues values = new ContentValues();
        String where = String.format(Locale.US, "%s = %d", "_id", 6);
        int count=getContentResolver().delete(bookUri, where, null);
    }

    /**
     * 显示书籍
     *
     * @param view 视图
     */
    public void showBooks(View view) {
        String content = "";
        Uri bookUri = BookProvider.BOOK_CONTENT_URI;
        Cursor bookCursor = getContentResolver().query(bookUri, new String[]{"_id", "name"}, null, null, null);
        if (bookCursor != null) {
            while (bookCursor.moveToNext()) {
                Book book = new Book();
                book.bookId = bookCursor.getInt(0);
                book.bookName = bookCursor.getString(1);
                content += book.toString() + "\n";
                Log.d(TAG, "query book: " + book.toString());
                mTvShowBooks.setText(content);
            }
            bookCursor.close();
        }
    }

    /**
     * 显示用户
     *
     * @param view 视图
     */
    public void showUsers(View view) {
        String content = "";
        Uri userUri = BookProvider.USER_CONTENT_URI;
        Cursor userCursor = getContentResolver().query(userUri, new String[]{"_id", "name", "sex"}, null, null, null);
        if (userCursor != null) {
            while (userCursor.moveToNext()) {
                User user = new User();
                user.userId = userCursor.getInt(0);
                user.userName = userCursor.getString(1);
                user.isMale = userCursor.getInt(2) == 1;
                content += user.toString() + "\n";
                Log.d(TAG, "query user:" + user.toString());
                mTvShowUsers.setText(content);
            }
            userCursor.close();
        }
    }
}
