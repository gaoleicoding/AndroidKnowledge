package com.example.knowledge.provider;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;

public class ProviderActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "DEBUG-WCL: " + ProviderActivity.class.getSimpleName();

    private TextView mTvShowBooks; // 显示书籍
    private TextView mTvShowUsers; // 显示用户

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contentprovider);

        mTvShowBooks = findViewById(R.id.main_tv_show_books);
        mTvShowUsers = findViewById(R.id.main_tv_show_users);
        findViewById(R.id.bt_add_book).setOnClickListener(this);
        findViewById(R.id.bt_update_book).setOnClickListener(this);
        findViewById(R.id.bt_del_book).setOnClickListener(this);
        findViewById(R.id.bt_show_book).setOnClickListener(this);
        findViewById(R.id.bt_show_author).setOnClickListener(this);
        DBManager.getInstance().initDB(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add_book:
                DBManager.getInstance().batchAddBooks();
                break;
            case R.id.bt_update_book:
                DBManager.getInstance().batchUpdateBooks();
                break;
            case R.id.bt_del_book:
                DBManager.getInstance().deleteBooks();
                break;
            case R.id.bt_show_book:
                String books = DBManager.getInstance().queryBooks();
                mTvShowBooks.setText(books);
                break;
            case R.id.bt_show_author:
                String authors = DBManager.getInstance().queryUsers();
                mTvShowUsers.setText(authors);
                break;
        }

    }
}
