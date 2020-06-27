package com.example.knowledge.recyclerview.itemdecoration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;

public class RVItemDecorationActivity extends AppCompatActivity implements View.OnClickListener {

    Button mBtnDivider;
    Button mBtnDivider1;
    Button mBtnDivider2;
    Button mBtnDivider3;
    Button mBtnDivider4;
    Button mBtnDivider5;
    TextView mTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_item_decoration);

        mBtnDivider = (Button) findViewById(R.id.btn_divider);
        mBtnDivider1 = (Button) findViewById(R.id.btn_divider1);
        mBtnDivider2 = (Button) findViewById(R.id.btn_divider2);
        mBtnDivider3 = (Button) findViewById(R.id.btn_divider3);
        mBtnDivider4 = (Button) findViewById(R.id.btn_divider4);
        mBtnDivider5 = (Button) findViewById(R.id.btn_divider5);
        mTv = (TextView) findViewById(R.id.tv_content);
        mBtnDivider.setOnClickListener(this);
        mBtnDivider1.setOnClickListener(this);
        mBtnDivider2.setOnClickListener(this);
        mBtnDivider3.setOnClickListener(this);
        mBtnDivider4.setOnClickListener(this);
        mBtnDivider5.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btn_divider:
                Intent intent = new Intent(RVItemDecorationActivity.this,DividerActivity.class);
                startActivity(intent);

                break;
            case R.id.btn_divider1:
                Intent intent1 = new Intent(RVItemDecorationActivity.this,ColorDividerActivity.class);
                startActivity(intent1);

                break;
            case R.id.btn_divider2:
                Intent intent2 = new Intent(RVItemDecorationActivity.this,TimelineActivity.class);
                startActivity(intent2);

                break;
            case R.id.btn_divider3:
                Intent intent3 = new Intent(RVItemDecorationActivity.this,BookRankActivity.class);
                startActivity(intent3);

                break;
            case R.id.btn_divider4:
                Intent intent4 = new Intent(RVItemDecorationActivity.this,HeaderActivity.class);
                startActivity(intent4);
                break;
            case R.id.btn_divider5:
                Intent intent5 = new Intent(RVItemDecorationActivity.this,StickyHeaderActivity.class);
                startActivity(intent5);

                break;

            default:
                break;
        }
    }
}
