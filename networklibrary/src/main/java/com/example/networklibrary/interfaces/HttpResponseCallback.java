package com.example.networklibrary.interfaces;

import com.example.networklibrary.http.HttpResponse;

/**
 * Created by hanbin on 2017/12/3.
 * 网络请求的回调
 */
public interface HttpResponseCallback {
    /**
     * 请求成功的回调
     *
     * @param httpResponse 回调的实体对象
     */
    void onRequestSuccess(HttpResponse httpResponse);

    /**
     * 请求失败的回调
     *
     * @param string 失败信息
     */
    void onRequestFailed(String string);

    /**
     * 是否连接到网络
     *
     * @param b true连接到网络 false未连接网络
     */
    void networkConnectedStatus(boolean b);
}