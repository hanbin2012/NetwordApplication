package com.example.networklibrary.interfaces;

import com.example.networklibrary.http.HttpResponse;

/**
 * Created by hanbin on 2017/12/3.
 * 网络请求的回调
 */
public interface HttpResponseCallback {
    void onRequestSuccess(HttpResponse httpResponse);
    void onRequestFailed(String string);
    void networkConnectedStatus(boolean b);
}