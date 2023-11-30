package com.fifedu.lib_common_utils.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.fifedu.lib_common_utils.R;


/**
 * Created by gaolei on 2023/6/6.
 */

public class BaseDialogUtils {
    private static final String TAG = "BaseDialogUtils";
    public static int DIALOG_SHOWTYPE_NORMAL = 0;//对话框正常，带取消和确认按钮
    public static int DIALOG_SHOWTYPE_ONE = 1;//对话框样式1，无取消按钮
    public static int DIALOG_SHOWTYPE_TWO = 2;//对话框样式2，无底部确认按钮

    public static boolean isDialogCancelSafely(Activity activity, Dialog dialog) {
        if (dialog == null) return false;
        if (!dialog.isShowing()) return false;
        return (activity != null && !activity.isDestroyed() && !activity.isFinishing());
    }

    /**
     * 创建loading对话框
     */
    public static Dialog createLoadingDialog(Activity context) {
        return createLoadingDialog(context, "");
    }

    public static Dialog createLoadingDialog(Activity context, String title) {
        if (context.isFinishing()) return null;
        View view = View.inflate(context, R.layout.lib_utils_loading_dialog, null);// 得到加载view
        // main.xml中的ImageView
        ImageView iv_loading = view.findViewById(R.id.iv_loading);
        TextView tv_loading = view.findViewById(R.id.tv_loading);
        if (!TextUtils.isEmpty(title)) {
            tv_loading.setVisibility(View.VISIBLE);
            tv_loading.setText(title);
        }
        // 加载动画(旋转动画)
        Animation aa = AnimationUtils.loadAnimation(context, R.anim.lib_utils_anim_progress_rotate);
        // 使用ImageView显示动画
        iv_loading.startAnimation(aa);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.lib_utils_loading_dialog);
        builder.setView(view);
        final Dialog loadingDialog = builder.show();
        loadingDialog.setCancelable(true);// 可以用“返回键”取消

        return loadingDialog;
    }


    public static void createCommonDialog(final Activity activity, String title, String msg, String confirm, String cancel, int showType, boolean isCancelable, final BaseDialogCallBack dialogCallBack) {
        createCustomDialog(activity, R.layout.lib_utils_dialog_common, title, msg, confirm, cancel, showType, isCancelable, dialogCallBack);

    }


    public static void createCustomDialog(final Activity activity, int layoutId, String title, String msg, String confirm, String cancel, int showType, boolean isCancelable, final BaseDialogCallBack dialogCallBack) {

        boolean isActivitySafe = (activity != null && !activity.isDestroyed() && !activity.isFinishing());
        if (!isActivitySafe) return;
        View view = View.inflate(activity, layoutId, null);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_content = view.findViewById(R.id.tv_content);
        Button bt_cancel = view.findViewById(R.id.bt_cancel);
        Button bt_confirm = view.findViewById(R.id.bt_confirm);
        LinearLayout ll_btn = view.findViewById(R.id.ll_btn);
        View line_button = view.findViewById(R.id.line_button);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.lib_utils__dialog_trans_style);
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
            tv_title.setVisibility(View.GONE);
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

    //修改头像弹窗
    public static void showSetPhotoDialog(Activity activity, SelectPhotoCallBack callBack) {
        showCustomPhotoDialog(activity, R.layout.lib_utils_dialog_select_photo, callBack);

    }

    public static void showCustomPhotoDialog(Activity activity, int layoutId, SelectPhotoCallBack callBack) {
        boolean isActivitySafe = (activity != null && !activity.isDestroyed() && !activity.isFinishing());
        if (!isActivitySafe) return;
        View view = View.inflate(activity, layoutId, null);
        TextView tv_album = view.findViewById(R.id.tv_album);
        TextView tv_takephoto = view.findViewById(R.id.tv_takephoto);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.lib_utils__dialog_trans_style);
        builder.setView(view);
        Dialog headPortraitDialog = builder.show();

        tv_takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headPortraitDialog.dismiss();
                callBack.onTake();
            }
        });
        tv_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headPortraitDialog.dismiss();
                callBack.onSelect();
            }
        });

    }

}
