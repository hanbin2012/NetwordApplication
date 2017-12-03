package com.example.networklibrary.http;
/**
 * Created by hanbin on 2017/12/3.
 * 网络请求的回调实体类
 */
public class HttpResponse {
    private String url;
    private String result;
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
