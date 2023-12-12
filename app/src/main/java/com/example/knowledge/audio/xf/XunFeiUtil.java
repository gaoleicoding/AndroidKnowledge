package com.example.knowledge.audio.xf;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;

import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class XunFeiUtil {
    private String TAG = "XunFeiUtil";
    private SpeechRecognizer mIat;
    private RecognizerDialog mIatDialog;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<>();
    // 引擎类型 这里我只考虑了一种引擎类型 :云端的
    private String mCLOUDType = SpeechConstant.TYPE_CLOUD;
    private EditText editText;

    public void init(Activity activity, EditText editText) {
        this.editText = editText;
        mIat = SpeechRecognizer.createRecognizer(activity, mInitListener);
        Log.i(TAG, "onCreate: mIat == null ?" + mIat);
        // SpeechRecognizer对象 null 的原因：一、 so 文件放错了位置 二、so文件与自己的SDK不匹配 3、Application中没有配置好appid
        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置显示RecognizerDialog需要的布局文件和图片资源
        mIatDialog = new RecognizerDialog(activity, mInitListener);
        // 设置参数
        setParam();
        // 显示听写对话框
        mIatDialog.setListener(mRecognizerDialogListener);
        mIatDialog.show();

    }

    public void setParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);

        // 设置听写引擎  注意：这里我只设置云端的方式！后面再考虑本地和混合的类型
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mCLOUDType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

        // 设置语言
        mIat.setParameter(SpeechConstant.LANGUAGE, "cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, "4000");

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, "1000");

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "0");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
    }

    private InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
        }
    };
    /**
     * 听写UI监听器
     */
    private final RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        /**
         * 识别成功时回调数据
         */
        public void onResult(RecognizerResult results, boolean isLast) {
            //避免被重复调用，导致结果重复
            if (!isLast) {
                printResult(results);
            }
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {

        }
    };

    /**
     * 成功时显示说话的文字
     *
     * @param results
     */
    public void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuilder resultBuffer = new StringBuilder();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }
        editText.append(resultBuffer.toString());

    }
}


