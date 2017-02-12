package com.example.leakcanary_my_sample.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

import com.example.leakcanary_my_sample.R;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OfficialDemoActivity extends Activity {

    @Bind(R.id.async_task)
    Button mAsyncTask;
    @Bind(R.id.btn_async_task_safely)
    Button mBtnAsyncTaskSafely;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_demo);
        ButterKnife.bind(this);
        mAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAsyncTask();
            }
        });
        mBtnAsyncTaskSafely.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAsyncTaskSafely();
            }
        });
    }

    void startAsyncTask() {
        // This async task is an anonymous class and therefore has a hidden reference to the outer
        // class MainActivity. If the activity gets destroyed before the task finishes (e.g. rotation),
        // the activity instance will leak.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                // Do some slow work in background
                SystemClock.sleep(20000);
                return null;
            }
        }.execute();
    }

    void startAsyncTaskSafely() {
        new TestAsyncTask(this).execute();
    }

    private static class TestAsyncTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<OfficialDemoActivity> mWeakReference;

        public TestAsyncTask(OfficialDemoActivity activity) {
            super();
            mWeakReference = new WeakReference<OfficialDemoActivity>(activity);
        }

        @Override
        protected Void doInBackground(Void... params) {
            OfficialDemoActivity activity = mWeakReference.get();
            if (activity != null) {
                SystemClock.sleep(20000);
            }
            return null;
        }
    }
}
