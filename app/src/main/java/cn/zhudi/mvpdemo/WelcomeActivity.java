package cn.zhudi.mvpdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import cn.zhudi.mvpdemo.utils.SystemInfoUtil;

public class WelcomeActivity extends Activity {
    private static final int sleepTime = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View view = View.inflate(this, R.layout.activity_welcome, null);
        setContentView(view);
        initView(view);
    }

    /********************************************************/
    /*               欢迎界面逻辑                             */
    /* 1.判断是否有网络，没有网络加载默认的，有网络请求数据         */
    /* 2.有网络根据服务器返回图片显示                           */
    /*******************************************************/
    private void initView(final View view) {
        if (SystemInfoUtil.checkNetWorkStatus(getApplicationContext())) {
            //网络请求
            getNetImage(view);
        } else {
            startMain(sleepTime);
        }
    }

    /**
     * 获取网络图片
     *
     * @param view
     */
    private AsyncHttpClient mAsyncHttpClient;

    private void getNetImage(final View view) {
        final long start = System.currentTimeMillis();
        mAsyncHttpClient = new AsyncHttpClient();
        // 设置连接超时时间
        mAsyncHttpClient.setConnectTimeout(1500);
        // 设置最大连接数
        mAsyncHttpClient.setMaxConnections(1);
        // 设置重连次数以及间隔时间
        mAsyncHttpClient.setMaxRetriesAndTimeout(1, 1);
        // 设置响应超时时间
        mAsyncHttpClient.setResponseTimeout(1500);
        mAsyncHttpClient.get(this, "http://www.baidu.com", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                ImageView ivBg = (ImageView) view.findViewById(R.id.ivBg);
                Glide.with(WelcomeActivity.this)
                        .load("http://newimg.4001113900.com/img/201701/startUpImg/startUpImg148543016773193.jpg")
                        .into(ivBg);
                startMain(sleepTime);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                long costTime = System.currentTimeMillis() - start;
                if (sleepTime - costTime > 0) {
                    startMain(sleepTime - costTime);
                } else {
                    Intent intent = new Intent(WelcomeActivity.this, JDMainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    /**
     * 启动主Activity
     */
    private void startMain(long time) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, JDMainActivity.class);
                startActivity(intent);
                finish();
            }
        },time);

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAsyncHttpClient != null) {
            mAsyncHttpClient.cancelAllRequests(true);
        }
    }
}



