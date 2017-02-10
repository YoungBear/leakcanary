package com.example.leakcanary_my_sample;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by bearyang on 2017/2/10.
 */

public class MyExampleApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
