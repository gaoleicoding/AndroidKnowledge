package com.example.knowledge.path.progress;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.knowledge.R;

public class PathMeasureProgressActivity extends Activity implements View.OnClickListener {

    private ProgressView customStatusView;

    private Button btnSuccess;
    private Button btnFailure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_status);
        customStatusView = (ProgressView) findViewById(R.id.as_status);
        btnSuccess = (Button) findViewById(R.id.as_btn_success);
        btnFailure = (Button) findViewById(R.id.as_btn_failure);

        customStatusView.loadLoading();
        btnSuccess.setOnClickListener(this);
        btnFailure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.as_btn_success:
                customStatusView.loadSuccess();
                break;
            case R.id.as_btn_failure:
                customStatusView.loadFailure();
                break;
        }
    }
}