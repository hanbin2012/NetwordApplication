package com.example.hanbin.networkapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hanbin.networkapplication.R;
import com.example.hanbin.networkapplication.utils.ToastUtil;
import com.example.networklibrary.OkHttpUtils;
import com.example.networklibrary.http.HttpResponse;
import com.example.networklibrary.interfaces.HttpResponseCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, HttpResponseCallback {

    private static final String TEST_GET_URL = "http://www.baidu.com";
    private static final String TAG = "hanbin";
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnTestGet = findViewById(R.id.btn_test_get);
        tvContent = findViewById(R.id.tv_content);
        btnTestGet.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_test_get:
                OkHttpUtils.get(this, TEST_GET_URL, this);
                break;
        }
    }

    @Override
    public void onRequestSuccess(HttpResponse httpResponse) {
        String url = httpResponse.getUrl();
        String result = httpResponse.getResult();
        switch (url) {
            case TEST_GET_URL:
                Log.e(TAG, result);
                tvContent.setText(result);
                break;
        }
    }

    @Override
    public void onRequestFailed(String string) {

    }

    @Override
    public void networkConnectedStatus(boolean b) {
        if (!b) {
            ToastUtil.showTextToast("请检查网络连接");
        }
    }
}
