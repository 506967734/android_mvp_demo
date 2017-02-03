package cn.zhudi.mvpdemo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.zhudi.mvpdemo.base.BaseFragment;
import cn.zhudi.mvpdemo.ui.FragmentTab;

public class JDMainActivity extends FragmentActivity {
    final String TAG = JDMainActivity.class.getName();
    /**
     * 表示显示第几个标签
     */
    private int currentTab = 1;
    public List<BaseFragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jdmain);
        initView();
        initBottomView();
        Log.v(TAG, Environment.getExternalStorageDirectory().getAbsolutePath());

//        Log.v(TAG, StorageUtils.getCacheDirectory(this).getAbsolutePath());
//        Log.v(TAG, getCacheDir().getPath());
//        String dir = getCacheDir().getPath() + "/zhudi";
//        File destDir = new File(dir);
//        if (!destDir.exists()) {
//            destDir.mkdirs();
//        }
        Log.v(TAG, getFilesDir().getPath());
//        Log.v(TAG,getDir("files", Context.MODE_PRIVATE).getPath());
//        getDir("files", Context.MODE_PRIVATE);
    }

    private void initView() {
        fragments = new ArrayList<>();
        fragments.add(new FragmentTab().newInstance("首页"));
        fragments.add(new FragmentTab().newInstance("分类"));
        fragments.add(new FragmentTab().newInstance("买买买"));
        fragments.add(new FragmentTab().newInstance("购物车"));
        fragments.add(new FragmentTab().newInstance("我的"));

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.om, fragments.get(currentTab));
        ft.commit();
    }

    private void initBottomView() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.on, new BottomFragment().newInstance());
        ft.commit();
    }

    /**
     * 改变fragment
     * @param i
     */
    public void change(int i) {
        currentTab = i;
        Fragment fragment = fragments.get(currentTab);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        fragment.onPause(); // 暂停当前tab
        fragment.onStop(); // 暂停当前tab
        if (fragment.isAdded()) {
            fragment.onStart(); // 启动目标tab的onStart()
            fragment.onResume(); // 启动目标tab的onResume()
        } else {
            ft.add(R.id.om, fragment);
        }
        ft.commitAllowingStateLoss();
        showTabFragment();
    }

    private void showTabFragment() {
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (currentTab == i) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.commitAllowingStateLoss();
        }
    }
}
