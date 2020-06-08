package com.example.knowledge.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class DownloadImgTask extends AsyncTask<String, Integer, Boolean> {

    private final String TAG = "DownloadImgTask";
    ProgressDialog progressDialog;

    Context context;

    public DownloadImgTask(Context context) {
        this.context = context;

    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMax(100);
        // progressDialog.setCancelable(false);
        //注意这里我将上一行代码注释掉，使得dialog能够被取消，至于为什么这么做后面解释
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        Log.d(TAG, "onPreExecute----------" + getClass().getSimpleName());
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        Log.d(TAG, "doInBackground----------" + getClass().getSimpleName());
        int pro = 0;

        while (true) {
            pro++;
            publishProgress(pro);
            if (pro >= 100) {
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressDialog.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        progressDialog.dismiss();
        if (aBoolean) {
            Log.d(TAG, "onPostExecute---下载成功-------" + getClass().getSimpleName());
            Toast.makeText(context, "下载成功", Toast.LENGTH_SHORT).show();
        } else {
            Log.d(TAG, "onPostExecute---下载失败-------" + getClass().getSimpleName());
            Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
        }
    }
}