package cn.zhudi.mvpdemo.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.ButterKnife;
import cn.zhudi.mvpdemo.utils.Constants;

/**
 * Created by mengyangyang on 2016-08-11.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Context context;

    protected abstract int getLayoutView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(getLayoutView());
        ButterKnife.bind(this);
    }

    /**
     * 设置标题名称
     *
     * @return void
     */
    protected void titleView(int id, int stringId) {
        TextView tv = (TextView) findViewById(id);
        tv.setVisibility(View.VISIBLE);
        tv.setText(getString(stringId));
    }


    /**
     * 监听返回按钮
     *
     * @return void
     */
    protected void activityFinish(int id) {
        TextView tv = (TextView) findViewById(id);
        tv.setVisibility(View.VISIBLE);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private long lastClickTime = 0;

    /**
     * 屏蔽连续点击
     *
     * @return true可以点击 false
     */
    protected boolean noDoubleClickListener() {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > Constants.MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 无参数界面调整
     *
     * @param context
     * @param c       Activity
     */
    public void startSelfActivity(Context context, Class c) {
        Intent intent = new Intent(context, c);
        startActivity(intent);
    }

    /**
     * 有参数界面调整
     *
     * @param context
     * @param c       Activity
     * @param bundle  传递的内容
     */
    public void startSelfActivity(Context context, Class c, Bundle bundle) {
        Intent intent = new Intent(context, c);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    /**
     * 无参数界面调整
     *
     * @param context
     * @param c           Activity
     * @param requestCode
     */
    public void startSelfActivityForResult(Context context, Class c, int requestCode) {
        Intent intent = new Intent(context, c);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 有参数界面调整
     *
     * @param context
     * @param c           Activity
     * @param requestCode
     * @param bundle
     */
    public void startSelfActivityForResult(Context context, Class c, int requestCode, Bundle bundle) {
        Intent intent = new Intent(context, c);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 启动fragment
     *
     * @param id       显示的view
     * @param fragment
     */
    public void startFragment(int id, Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(id, fragment);
        ft.commit();
    }


    /**
     * 强制弹出软键盘
     *
     * @param view
     */
    public void showInputKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 隐藏软键盘
     */
    public void hideInputKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = getCurrentFocus();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private boolean blockTouchOrKeyEvent;

    @Override
    protected void onResume() {
        super.onResume();
        blockTouchOrKeyEvent = false;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return blockTouchOrKeyEvent || super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return blockTouchOrKeyEvent || super.dispatchTouchEvent(ev);
    }

    @Override
    public void startActivity(Intent intent) {
        blockTouchOrKeyEvent = true;
        super.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        blockTouchOrKeyEvent = true;
    }

    @Override
    public void finish() {
        super.finish();
    }
}
