package com.mogul.music;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mogul.network.core.app.HttpHelper;
import com.mogul.network.core.main.CommonCallback;

import java.io.IOException;

import okhttp3.Call;


public class MainActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.tv);
        getData();
    }

    private void getData() {

        HttpHelper.with(this).url("http://mogulcoder.com/user/2").get().execute(new CommonCallback<User>() {
            @Override
            public void onError(Call call, IOException e) {

            }

            @Override
            public void onSuccess(Call call, User user) {
                mTextView.setText(user.getUsername());
            }
        });
    }
}