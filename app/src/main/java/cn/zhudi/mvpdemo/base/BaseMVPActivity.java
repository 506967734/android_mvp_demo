package cn.zhudi.mvpdemo.base;

import android.os.Bundle;

import cn.zhudi.mvpdemo.utils.ToastSingle;

/**
 * 类名称：MVPDemo
 * 类描述：
 * 创建人：zhudi
 * 创建时间：16/9/14 11:24
 * 修改人：${user}
 * 修改时间：${date} ${time}
 * 修改备注：
 */
public abstract class BaseMVPActivity<V, T extends BasePresenter<V>> extends BaseActivity {
    /**
     * 控制器
     */
    public T presenter;

    /**
     * 初始化控制器
     *
     * @return
     */
    protected abstract T initPresenter();

    /**
     * 初始化View
     */
    protected void initView(){}

    /**
     * 初始化Data
     */
    protected void initData(){}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
        presenter.attach((V) this);
        initView();
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dettach();
    }


    /**
     * 显示提示信息
     *
     * @param message 提示信息
     */
    public void showMessage(String message) {
        ToastSingle.showToast(this, message);
    }

}
