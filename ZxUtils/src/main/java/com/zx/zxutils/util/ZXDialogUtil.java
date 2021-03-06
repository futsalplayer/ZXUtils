package com.zx.zxutils.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zx.zxutils.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiangb on 2017/3/24.
 * 功能：专用于dialog的工具类
 */
public class ZXDialogUtil {

    private static Handler handler = new Handler();
    private static boolean hasProgress = false;//是否为带进度条的loadingDialog
    private static ProgressDialog loadingDialog;//简单进度dialog，适用于单纯显示正在加载的情况
    //    private static Dialog dialog;//用于显示各种信息的dialog
    private static List<Dialog> dialogList = new ArrayList<>();

    /**
     * 显示简单加载dialog
     *
     * @param context 上下文
     * @param message 提示消息
     */
    public static ProgressDialog showLoadingDialog(Context context, String message) {
        try {
            if (hasProgress == true && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
            if (hasProgress == true || loadingDialog == null) {
                showSimple(context, message);
            } else {
                loadingDialog.setMessage(message);
                loadingDialog.show();
            }
        } catch (Exception e) {
            showSimple(context, message);
        }
        return loadingDialog;
    }

    private static void showSimple(Context context, String message) {
        loadingDialog = null;
        loadingDialog = ProgressDialog.show(context, "", message);
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingDialog.setCancelable(true);
        loadingDialog.setCanceledOnTouchOutside(false);
        hasProgress = false;
    }

    /**
     * 显示带进度条的加载dialog
     *
     * @param context  上下文
     * @param message  提示消息
     * @param progress 进度
     */
    public static ProgressDialog showLoadingDialog(Context context, String message, int progress) {
        try {
            if (hasProgress == false && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
            if (hasProgress == false || loadingDialog == null) {
                showProgress(context, message, progress);
            } else {
                loadingDialog.setMessage(message);
                loadingDialog.setProgress(progress);
                loadingDialog.show();
            }
        } catch (Exception e) {
            showProgress(context, message, progress);
        }
        return loadingDialog;
    }

    private static void showProgress(Context context, String message, int progress) {
        loadingDialog = null;
        loadingDialog = new ProgressDialog(context);
        loadingDialog.setMessage(message);
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        loadingDialog.setCancelable(true);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setProgress(progress);
        loadingDialog.show();
        hasProgress = true;
    }

    /**
     * 判断加载dialog是否为打开状态
     *
     * @return
     */
    public static boolean isLoadingDialogShow() {
        if (loadingDialog != null) {
            return loadingDialog.isShowing();
        } else {
            return false;
        }
    }

    /**
     * 关闭加载dialog
     */
    public static void dismissLoadingDialog() {
        if (handler != null) {
            handler.post(new Runnable() {
                public void run() {
                    if (null != loadingDialog && loadingDialog.isShowing()) {
                        try {
                            loadingDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    public static Dialog showInfoDialog(Context context, String title, String message) {
        return showInfoDialog(context, title, message, null, false);
    }

    public static Dialog showInfoDialog(Context context, String title, String message, boolean canceledOnTouchOutSide) {
        return showInfoDialog(context, title, message, null, canceledOnTouchOutSide);
    }

    public static Dialog showInfoDialog(Context context, String title, String message, @Nullable DialogInterface.OnClickListener listener) {
        return showInfoDialog(context, title, message, listener, false);
    }

    /**
     * 用于展示单纯信息的dialog
     *
     * @param context  上下文
     * @param title    标题
     * @param message  内容
     * @param listener 确定监听
     */
    public static Dialog showInfoDialog(Context context, String title, String message, @Nullable DialogInterface.OnClickListener listener, boolean canceledOnTouchOutSide) {
        AlertDialog.Builder buider = new AlertDialog.Builder(context);
        buider.setTitle(title);
        buider.setMessage(message);
        buider.setPositiveButton("确定", listener);
        Dialog dialog = buider.show();
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutSide);
        dialogList.add(dialog);
        return dialog;
    }

    /**
     * 有两个按钮，用于判断的dialog，可定义按钮文字及监听事件
     *
     * @param context     上下文
     * @param title       标题
     * @param message     内容
     * @param yesBtnText  确定按钮内容
     * @param noBtnText   取消按钮内容
     * @param yesListener 确定按钮监听器
     * @param noListener  取消按钮监听器
     */
    public static Dialog showYesNoDialog(Context context, String title, String message, String yesBtnText, String noBtnText, @Nullable DialogInterface.OnClickListener yesListener, @Nullable DialogInterface.OnClickListener noListener) {
        return showYesNoDialog(context, title, message, yesBtnText, noBtnText, yesListener, noListener, false);
    }

    public static Dialog showYesNoDialog(Context context, String title, String message, String yesBtnText, String noBtnText, @Nullable DialogInterface.OnClickListener yesListener, @Nullable DialogInterface.OnClickListener noListener, boolean canceledOnTouchOutSide) {
        AlertDialog.Builder buider = new AlertDialog.Builder(context);
        buider.setTitle(title);
        buider.setMessage(message);
        buider.setPositiveButton(yesBtnText, yesListener);
        buider.setNegativeButton(noBtnText, noListener);
        Dialog dialog = buider.show();
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutSide);
        dialogList.add(dialog);
        return dialog;
    }

    /**
     * 有两个按钮，用于判断的dialog，只能定义确定按钮监听事件
     *
     * @param context     上下文
     * @param title       标题
     * @param message     内容
     * @param yesListener 确定按钮点击事件
     */
    public static Dialog showYesNoDialog(Context context, String title, String message, @Nullable DialogInterface.OnClickListener yesListener) {
        return showYesNoDialog(context, title, message, "确定", "取消", yesListener, null, false);
    }

    public static Dialog showYesNoDialog(Context context, String title, String message, @Nullable DialogInterface.OnClickListener yesListener, boolean canceledOnTouchOutSide) {
        return showYesNoDialog(context, title, message, "确定", "取消", yesListener, null, canceledOnTouchOutSide);
    }

    /**
     * 带一个中性按钮，不提供确认和取消按钮的文字修改及取消按钮的点击事件（因为太多参数呢），如需要自行创建或采用showCustomDialog
     *
     * @param context          上下文
     * @param title            标题
     * @param message          内容
     * @param otherBtnText     中性按钮文字
     * @param yesListener      确定按钮点击事件
     * @param otherBtnListener 中性按钮点击事件
     */
    public static Dialog showWithOtherBtnDialog(Context context, String title, String message, String otherBtnText, @Nullable DialogInterface.OnClickListener yesListener, @Nullable DialogInterface.OnClickListener otherBtnListener) {
        return showWithOtherBtnDialog(context, title, message, otherBtnText, yesListener, otherBtnListener, false);
    }

    public static Dialog showWithOtherBtnDialog(Context context, String title, String message, String otherBtnText, @Nullable DialogInterface.OnClickListener yesListener, @Nullable DialogInterface.OnClickListener otherBtnListener, boolean canceledOnTouchOutSide) {
        AlertDialog.Builder buider = new AlertDialog.Builder(context);
        buider.setTitle(title);
        buider.setMessage(message);
        buider.setPositiveButton("确定", yesListener);
        buider.setNegativeButton("取消", null);
        buider.setNeutralButton(otherBtnText, otherBtnListener);
        Dialog dialog = buider.show();
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutSide);
        dialogList.add(dialog);
        return dialog;
    }

    /**
     * 带item勾选列表的dialog，不提供按钮文字及取消按钮监听事件的修改
     *
     * @param context             上下文
     * @param title               标题
     * @param itemName            checkitem的名字
     * @param itemCheckStatus     checkitem的选中状态
     * @param choiceClickListener 选择事件
     * @param yesListener         确认的点击事件
     */
    public static Dialog showCheckListDialog(Context context, String title, String[] itemName, boolean[] itemCheckStatus, @Nullable DialogInterface.OnMultiChoiceClickListener choiceClickListener, @Nullable DialogInterface.OnClickListener yesListener) {
        return showCheckListDialog(context, title, itemName, itemCheckStatus, choiceClickListener, yesListener, false);
    }

    public static Dialog showCheckListDialog(Context context, String title, String[] itemName, boolean[] itemCheckStatus, @Nullable DialogInterface.OnMultiChoiceClickListener choiceClickListener, @Nullable DialogInterface.OnClickListener yesListener, boolean canceledOnTouchOutSide) {
        AlertDialog.Builder buider = new AlertDialog.Builder(context);
        buider.setTitle(title);
        buider.setMultiChoiceItems(itemName, itemCheckStatus, choiceClickListener);
        buider.setPositiveButton("确定", yesListener);
        buider.setNegativeButton("取消", null);
        Dialog dialog = buider.show();
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutSide);
        dialogList.add(dialog);
        return dialog;
    }

    /**
     * 带list的勾选事件，不提供确认取消按钮的文字
     *
     * @param context           上下文
     * @param title             标题
     * @param itemName          item项
     * @param itemClickListener item点击事件
     * @param yesListener       确定按钮点击事件
     */
    public static Dialog showListDialog(Context context, String title, String yesBtnText, String[] itemName, @Nullable DialogInterface.OnClickListener itemClickListener, @Nullable DialogInterface.OnClickListener yesListener) {
        return showListDialog(context, title, yesBtnText, itemName, itemClickListener, yesListener, false);
    }

    public static Dialog showListDialog(final Context context, String title, String yesBtnText, final String[] itemName, @Nullable final DialogInterface.OnClickListener itemClickListener, @Nullable DialogInterface.OnClickListener yesListener, boolean canceledOnTouchOutSide) {
        AlertDialog.Builder buider = new AlertDialog.Builder(context);
        buider.setTitle(title);
        buider.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return itemName.length;
            }

            @Override
            public Object getItem(int i) {
                return itemName[i];
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                View v = LayoutInflater.from(context).inflate(R.layout.item_listdialog, null);
                ((TextView) v.findViewById(R.id.tv_text)).setText(itemName[i]);
                return v;
            }
        }, itemClickListener);
//        buider.setItems(itemName, itemClickListener);
        if (yesListener != null) {
            buider.setPositiveButton(yesBtnText, yesListener);
//            buider.setNegativeButton("取消", null);
        } else {
            buider.setPositiveButton(yesBtnText, null);
        }
        Dialog dialog = buider.show();
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutSide);
        dialogList.add(dialog);
        return dialog;
    }

    /**
     * 带list的勾选事件，不提供确认取消按钮的文字
     *
     * @param context           上下文
     * @param title             标题
     * @param itemName          item项
     * @param itemClickListener item点击事件
     * @param yesListener       确定按钮点击事件
     */
    public static Dialog showListDialog(Context context, String title, String yesBtnText, List<String> itemName, @Nullable DialogInterface.OnClickListener itemClickListener, @Nullable DialogInterface.OnClickListener yesListener) {
        return showListDialog(context, title, yesBtnText, itemName.toArray(new String[0]), itemClickListener, yesListener);
    }

    public static Dialog showListDialog(Context context, String title, String yesBtnText, List<String> itemName, @Nullable DialogInterface.OnClickListener itemClickListener, @Nullable DialogInterface.OnClickListener yesListener, boolean canceledOnTouchOutSide) {
        return showListDialog(context, title, yesBtnText, itemName.toArray(new String[0]), itemClickListener, yesListener, canceledOnTouchOutSide);
    }

    /**
     * 带list的勾选事件，不提供按钮
     *
     * @param context           上下文
     * @param title             标题
     * @param itemName          item项
     * @param itemClickListener item点击事件
     */
    public static Dialog showListDialog(Context context, String title, String yesBtnText, String[] itemName, @Nullable DialogInterface.OnClickListener itemClickListener) {
        return showListDialog(context, title, yesBtnText, itemName, itemClickListener, null);
    }

    public static Dialog showListDialog(Context context, String title, String yesBtnText, String[] itemName, @Nullable DialogInterface.OnClickListener itemClickListener, boolean canceledOnTouchOutSide) {
        return showListDialog(context, title, yesBtnText, itemName, itemClickListener, null, canceledOnTouchOutSide);
    }

    /**
     * 带list的勾选事件，不提供按钮
     *
     * @param context           上下文
     * @param title             标题
     * @param itemName          item项
     * @param itemClickListener item点击事件
     */
    public static Dialog showListDialog(Context context, String title, String yesBtnText, List<String> itemName, @Nullable DialogInterface.OnClickListener itemClickListener) {
        return showListDialog(context, title, yesBtnText, itemName.toArray(new String[0]), itemClickListener);
    }

    public static Dialog showListDialog(Context context, String title, String yesBtnText, List<String> itemName, @Nullable DialogInterface.OnClickListener itemClickListener, boolean canceledOnTouchOutSide) {
        return showListDialog(context, title, yesBtnText, itemName.toArray(new String[0]), itemClickListener, canceledOnTouchOutSide);
    }

    /**
     * 带一个view的dialog，view内部事件处理需要在添加前进行设置
     *
     * @param context     上下文
     * @param title       标题
     * @param view        填入的view
     * @param yesListener 确定按钮的点击事件
     */
    public static Dialog showCustomViewDialog(Context context, String title, View view, @Nullable DialogInterface.OnClickListener yesListener) {
        return showCustomViewDialog(context, title, view, yesListener, null, false);
    }

    public static Dialog showCustomViewDialog(Context context, String title, View view, @Nullable DialogInterface.OnClickListener yesListener, boolean canceledOnTouchOutSide) {
        return showCustomViewDialog(context, title, view, yesListener, null, canceledOnTouchOutSide);
    }

    /**
     * 带一个view的dialog，view内部事件处理需要在添加前进行设置
     *
     * @param context     上下文
     * @param title       标题
     * @param view        填入的view
     * @param yesListener 确定按钮的点击事件
     */
    public static Dialog showCustomViewDialog(Context context, String title, View view, @Nullable DialogInterface.OnClickListener yesListener, DialogInterface.OnClickListener noListener) {
        return showCustomViewDialog(context, title, view, yesListener, noListener, false);
    }

    public static Dialog showCustomViewDialog(Context context, String title, View view, @Nullable DialogInterface.OnClickListener yesListener, DialogInterface.OnClickListener noListener, boolean canceledOnTouchOutSide) {
        AlertDialog.Builder buider = new AlertDialog.Builder(context);
        buider.setTitle(title);
        buider.setView(view, 20, 10, 20, 10);
        if (yesListener != null) {
            buider.setPositiveButton("确定", yesListener);
        }
        if (noListener != null) {
            buider.setNegativeButton("取消", noListener);
        }
        Dialog dialog = buider.show();
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutSide);
        dialogList.add(dialog);
        return dialog;
    }

    /**
     * 关闭dialog
     */
    public static void dismissDialog() {
        try {
            if (dialogList.size() > 0) {
                dialogList.get(dialogList.size() - 1).dismiss();
                dialogList.remove(dialogList.size() - 1);
            }
        } catch (Exception e) {
            dialogList.clear();
            e.printStackTrace();
        }
//        if (dialog != null) {
//            dialog.dismiss();
//        }
    }

}
