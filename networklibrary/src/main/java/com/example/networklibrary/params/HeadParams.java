package com.example.networklibrary.params;

import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by hanbin on 2017/12/3.
 * 请求头的封装
 */

public class HeadParams {
    private static final HashMap<String, Object> paramas = new HashMap<>();

    public HeadParams addParamas(String name, Object value) {
        if (!TextUtils.isEmpty(name)) {
            paramas.put(name, value);
        }
        return this;
    }

    public HashMap<String, Object> build() {
        return paramas;
    }
}
