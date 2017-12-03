package com.example.hanbin.networkapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.networklibrary.OkHttpUtils;
import com.example.networklibrary.http.HttpResponse;
import com.example.networklibrary.interfaces.HttpResponseCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OkHttpUtils.get(this, "", 123434, "http://www.baidu.com", new HttpResponseCallback() {
            @Override
            public void onRequestSuccess(HttpResponse httpResponse) {
                Log.e("hanbin", httpResponse.getResult());
            }

            @Override
            public void onRequestFailed(String string) {

            }

            @Override
            public void networkConnectedStatus(boolean b) {

            }
        });
    }
}
