package com.example.leakcanary_my_sample.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.leakcanary_my_sample.R;

public class StaticTextViewActivity extends Activity {

    private static TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_text_view);
        mTextView = (TextView) findViewById(R.id.txt_static);
    }
}
