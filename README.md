# 使用LeakCanary 分析常见内存泄漏问题

## 1. LeakCanary官方Demo

即在Activity中启动一个AsyncTask,但是如果屏幕翻转，Activity声明周期会重新执行，但是由于AsyncTask任务中尚未执行完成，<font color=red>匿名类持有Activity的强引用</font>，导致前一个Activity的资源不能回收，造成了内存泄漏。

内存泄漏代码：

```
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                // Do some slow work in background
                SystemClock.sleep(20000);
                return null;
            }
        }.execute();
```

//todo 插入official_demo.png图片

解决方案：使用静态内部类，保存一个Activity的弱引用。因为静态的内部类不会持有外部类的引用，所以不会导致外部类实例的内存泄露。当你需要在静态内部类中调用外部的Activity时，我们可以使用弱引用来处理。

```
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
```

## 2. 静态组件(如TextView)

```
    private static TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_text_view);
        mTextView = (TextView) findViewById(R.id.txt_static);
    }
```

由于静态TextView会持有Activitiy的强引用，所以在Activity生命周期结束后，资源并没有释放，引起内存泄漏。

解决方案：不要使用静态组件。



