package com.example.knowledge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.knowledge.R;
import com.example.knowledge.bean.CheckVersionInfo;

/**
 * 对话框工具类
 */

public class DialogUtils {
    public static int DIALOG_SHOWTYPE_NORMAL = 0;//对话框正常，带取消和确认按钮
    public static int DIALOG_SHOWTYPE_ONE = 1;//对话框样式1，无取消按钮
    public static int DIALOG_SHOWTYPE_TWO = 2;//对话框样式2，无底部确认按钮


    public static void createCommonDialog(final Context context, String title, String msg, String confirm, String cancel, int showType, boolean isCancelable, final DialogCallBack dialogCallBack) {
        View view = View.inflate(context, R.layout.lib_base_layout_dialog_common, null);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_content = view.findViewById(R.id.tv_content);
        Button bt_cancel = view.findViewById(R.id.bt_cancel);
        Button bt_confirm = view.findViewById(R.id.bt_confirm);
        LinearLayout ll_btn = view.findViewById(R.id.ll_btn);
        View line_button = view.findViewById(R.id.line_button);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.lib_base_dialog_trans);
        builder.setView(view);
        final Dialog commonDialog = builder.show();
        commonDialog.setCancelable(isCancelable);

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonDialog.cancel();
                if (dialogCallBack != null) {
                    dialogCallBack.onCancel();
                }
            }
        });
        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonDialog.cancel();
                if (dialogCallBack != null) {
                    dialogCallBack.onConfirm();
                }
            }
        });

        if (TextUtils.isEmpty(title)) {
            tv_title.setVisibility(View.INVISIBLE);
        } else {
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(title);
        }
        if (TextUtils.isEmpty(msg)) {
            tv_content.setVisibility(View.GONE);
        } else {
            tv_content.setVisibility(View.VISIBLE);
            tv_content.setText(Html.fromHtml(msg));
        }

        if (!TextUtils.isEmpty(cancel)) {
            bt_cancel.setText(cancel);
        }
        if (!TextUtils.isEmpty(confirm)) {
            bt_confirm.setText(confirm);
        }

        if (showType == DIALOG_SHOWTYPE_ONE) {
            line_button.setVisibility(View.GONE);
            bt_cancel.setVisibility(View.GONE);
        }
        if (showType == DIALOG_SHOWTYPE_TWO) {
            ll_btn.setVisibility(View.GONE);
        }
    }

    /**
     * 创建loading进度条
     *
     * @param context
     * @return
     */
    public static Dialog createLoadingDialog(Context context) {

        View v = View.inflate(context, R.layout.lib_base_layout_dialog_loading, null);// 得到加载view
        RelativeLayout rl_dialog = (RelativeLayout) v.findViewById(R.id.rl_dialog);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.iv_progress);
        // 加载动画(旋转动画)
        Animation aa = AnimationUtils.loadAnimation(context,
                R.anim.loading_progress);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(aa);
        Dialog loadingDialog = new Dialog(context, R.style.lib_base_dialog_trans);// 创建自定义样式dialog
        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(rl_dialog, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        return loadingDialog;
    }

    public static void createVersionDialog(final Context context, final CheckVersionInfo checkVersionInfo) {
        View view = View.inflate(context, R.layout.lib_base_dialog_checkversion, null);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_content.setText(checkVersionInfo.getMsg());
        final Button bt_update = view.findViewById(R.id.bt_update);
        LinearLayout ll_cancel = view.findViewById(R.id.ll_cancel);
        final LinearLayout ll_button = view.findViewById(R.id.ll_button);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.lib_base_checkVersion_dialog);
        builder.setView(view);
        builder.setCancelable(false);
        final Dialog versionDialog = builder.show();
        versionDialog.setCancelable(false);
        bt_update.setText("立即更新");
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //允许了去下载
            }
        });

        if (TextUtils.isEmpty(checkVersionInfo.getIsUpdate())
                || checkVersionInfo.getIsUpdate().equals("0")) { //不需要强制更新
            ll_cancel.setVisibility(View.VISIBLE);
            ll_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    versionDialog.dismiss();
                    if (!checkVersionInfo.isDownloaded()) {
                        if (!TextUtils.isEmpty(checkVersionInfo.getIsHaveNewVer())
                                && checkVersionInfo.getIsHaveNewVer().equalsIgnoreCase("1")) {

                        }
                    }

                }
            });
        } else {
            //需要强制更新
            ll_cancel.setVisibility(View.INVISIBLE);
        }
    }


    public abstract static class DialogCallBack {
        public abstract void onConfirm();

        public abstract void onCancel();
    }
}
