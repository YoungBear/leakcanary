package com.example.leakcanary_my_sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.leakcanary_my_sample.activity.OfficialDemoActivity;
import com.example.leakcanary_my_sample.activity.StaticTextViewActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @Bind(R.id.btn_official_demo)
    Button mBtnOfficialDemo;
    @Bind(R.id.btn_static_text_view)
    Button mBtnStaticTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        mBtnOfficialDemo.setOnClickListener(btnClickListener);
        mBtnStaticTextView.setOnClickListener(btnClickListener);
    }

    private void startActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_official_demo:
                    startActivity(OfficialDemoActivity.class);
                    break;
                case R.id.btn_static_text_view:
                    startActivity(StaticTextViewActivity.class);
                    break;
            }
        }
    };
}
