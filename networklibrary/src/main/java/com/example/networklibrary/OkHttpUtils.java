package com.example.networklibrary;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.networklibrary.http.HttpResponse;
import com.example.networklibrary.interfaces.HttpResponseCallback;
import com.example.networklibrary.params.HeadParams;
import com.example.networklibrary.params.RequestParams;
import com.example.networklibrary.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hanbin on 2017/12/3.
 * okhttp请求封装类
 */

public class OkHttpUtils {
    public static final String HTTP_REQUEST_METHOD_POST = "POST";
    public static final String HTTP_REQUEST_METHOD_GET = "GET";
    public static final String HTTP_REQUEST_METHOD_PUT = "PUT";
    public static final String HTTP_REQUEST_METHOD_DELETE = "DELETE";
    private static Handler mainHandler;
    private volatile static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;

    public OkHttpClient getmOkHttpClient() {
        return mOkHttpClient;
    }

    public static OkHttpUtils getInstance() {
        return initClient();
    }

    private static OkHttpUtils initClient() {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils();
                }
            }
        }
        return mInstance;
    }

    private OkHttpUtils() {
        if (mOkHttpClient == null) {//创建OkHttpClient对象
            mOkHttpClient = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();
        }
        if (mainHandler == null) {//创建Handler对象
            mainHandler = new Handler(Looper.getMainLooper());
        }
    }

    /**
     * get请求方式
     *
     * @param context       上下文,一般传递application
     * @param kVersionValue 请求接口的版本号
     * @param passportId    用户唯一id
     * @param url           请求的URL地址
     * @param callback      请求的回调
     */
    public static void get(Context context, String kVersionValue, int passportId, String url,
                           HttpResponseCallback callback) {
        get(context, kVersionValue, passportId, url, null, callback);
    }

    /**
     * get请求方式
     *
     * @param context       上下文,一般传递application
     * @param kVersionValue 请求接口的版本号
     * @param passportId    用户唯一id
     * @param url           请求的URL地址
     * @param headParams    请求头
     * @param callback      请求的回调
     */
    public static void get(Context context, final String kVersionValue, final int passportId,
                           final String url, HeadParams headParams,
                           final HttpResponseCallback callback) {
        if (Utils.isNetworkConnected(context)) {
            callback.networkConnectedStatus(true);
            OkHttpClient okHttpClient = OkHttpUtils.getInstance().getmOkHttpClient();
            Request request = changeBuilderType(HTTP_REQUEST_METHOD_GET, url, headParams,
                    kVersionValue, passportId, "");
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    mainHandler.post(new Runnable() {//请求失败的回调
                        @Override
                        public void run() {
                            callback.onRequestFailed(e.toString());
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    final HttpResponse responseResult = new HttpResponse();
                    responseResult.setUrl(url);
                    responseResult.setResult(json);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onRequestSuccess(responseResult);

                        }
                    });
                }
            });
        } else {
            if (callback != null) {
                callback.networkConnectedStatus(false);
            }
        }

    }

    /**
     * post请求方式
     *
     * @param context       上下文,一般传递application
     * @param kVersionValue 请求接口的版本号
     * @param passportId    用户唯一id
     * @param url           请求的URL地址
     * @param body          请求体
     * @param callback      请求的回调
     */
    public static void post(Context context, final String kVersionValue, final int passportId,
                            final String url, String body,
                            final HttpResponseCallback callback) {
        if (Utils.isNetworkConnected(context)) {
            callback.networkConnectedStatus(true);
            OkHttpClient okHttpClient = OkHttpUtils.getInstance().getmOkHttpClient();
            Request request = changeBuilderType(HTTP_REQUEST_METHOD_POST, url, null,
                    kVersionValue, passportId, body);
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    mainHandler.post(new Runnable() {//请求失败的回调
                        @Override
                        public void run() {
                            callback.onRequestFailed(e.toString());
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    final HttpResponse responseResult = new HttpResponse();
                    responseResult.setUrl(url);
                    responseResult.setResult(json);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onRequestSuccess(responseResult);

                        }
                    });
                }
            });
        } else {
            if (callback != null) {
                callback.networkConnectedStatus(false);
            }
        }

    }

    /**
     * post请求方式
     *
     * @param context       上下文,一般传递application
     * @param kVersionValue 请求接口的版本号
     * @param passportId    用户唯一id
     * @param url           请求的URL地址
     * @param requestParams 请求体参数
     * @param callback      请求的回调
     */
    public static void post(Context context, final String kVersionValue, final int passportId,
                            final String url, RequestParams requestParams,
                            final HttpResponseCallback callback) {
        if (Utils.isNetworkConnected(context)) {
            callback.networkConnectedStatus(true);
            OkHttpClient okHttpClient = OkHttpUtils.getInstance().getmOkHttpClient();
            String body = "";
            if (requestParams != null) {
                HashMap<String, Object> hashMap = requestParams.build();
                if (hashMap.size() > 0) {
                    JSONObject jsonObject = new JSONObject();
                    for (String key : hashMap.keySet()) {
                        try {
                            jsonObject.put(key, String.valueOf(hashMap.get(key)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    body = jsonObject.toString();
                }
            }
            Request request = changeBuilderType(HTTP_REQUEST_METHOD_POST, url, null,
                    kVersionValue, passportId, body);
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    mainHandler.post(new Runnable() {//请求失败的回调
                        @Override
                        public void run() {
                            callback.onRequestFailed(e.toString());
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    final HttpResponse responseResult = new HttpResponse();
                    responseResult.setUrl(url);
                    responseResult.setResult(json);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onRequestSuccess(responseResult);

                        }
                    });
                }
            });
        } else {
            if (callback != null) {
                callback.networkConnectedStatus(false);
            }
        }

    }

    /**
     * post请求方式
     *
     * @param context       上下文,一般传递application
     * @param kVersionValue 请求接口的版本号
     * @param passportId    用户唯一id
     * @param url           请求的URL地址
     * @param headParams    请求头
     * @param body          请求体
     * @param callback      请求的回调
     */
    public static void post(Context context, final String kVersionValue, final int passportId,
                            final String url, HeadParams headParams, String body,
                            final HttpResponseCallback callback) {
        if (Utils.isNetworkConnected(context)) {
            callback.networkConnectedStatus(true);
            OkHttpClient okHttpClient = OkHttpUtils.getInstance().getmOkHttpClient();
            Request request = changeBuilderType(HTTP_REQUEST_METHOD_POST, url, headParams,
                    kVersionValue, passportId, body);
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    mainHandler.post(new Runnable() {//请求失败的回调
                        @Override
                        public void run() {
                            callback.onRequestFailed(e.toString());
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    final HttpResponse responseResult = new HttpResponse();
                    responseResult.setUrl(url);
                    responseResult.setResult(json);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onRequestSuccess(responseResult);

                        }
                    });
                }
            });
        } else {
            if (callback != null) {
                callback.networkConnectedStatus(false);
            }
        }

    }

    /**
     * 根据请求方式转换Request对象
     *
     * @param requestMethod 请求方式,get,post,put,delete
     * @param url           请求的URL
     * @param headParams    请求头参数
     * @param body          请求体
     * @return Request对象
     */
    private static Request changeBuilderType(String requestMethod, String url, HeadParams headParams,
                                             String kVersionValue, final int passportId, String body) {
        MediaType MEDIA_TYPE_TEXT = MediaType.parse("application/json; charset=utf-8");
        Request request = null;
        Request.Builder builder = new Request.Builder().url(url);
        if (headParams != null) {//拼装请求头
            //headMap不可能为空
            HashMap<String, Object> headMap = headParams.build();
            for (String key : headMap.keySet()) {
                builder.addHeader(key, String.valueOf(headMap.get(key)));
            }
        }
        //拼装请求头信息
        switch (requestMethod) {
            case HTTP_REQUEST_METHOD_GET://get请求
                request = builder.build();
                break;
            case HTTP_REQUEST_METHOD_POST://post请求
                request = builder.post(RequestBody.create(MEDIA_TYPE_TEXT, body)).build();
                break;
            case HTTP_REQUEST_METHOD_PUT://put请求
                request = builder.put(RequestBody.create(MEDIA_TYPE_TEXT, body)).build();
                break;
            case HTTP_REQUEST_METHOD_DELETE://delete请求
                request = builder.delete(RequestBody.create(MEDIA_TYPE_TEXT, body)).build();
                break;
        }
        return request;
    }
}
