package com.example.hanbin.networkapplication.utils;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.example.hanbin.networkapplication.AppApplication;

/**
 * Created by hanbin on 2017/12/3.
 * 吐司的工具类
 */

public class ToastUtil {
    public static Toast toast = null;

    /**
     * 普通的toast弹窗
     *
     * @param msg 提示信息
     */
    @SuppressLint("ShowToast")
    public static void showTextToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(AppApplication.getContext(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
