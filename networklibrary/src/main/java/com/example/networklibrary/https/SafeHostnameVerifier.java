package com.example.networklibrary.https;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Created by hanbin on 2017/12/3.
 * 校验主机名是否合法,这种写法默认是不验证
 */

public class SafeHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String s, SSLSession sslSession) {
        return true;
    }
}
