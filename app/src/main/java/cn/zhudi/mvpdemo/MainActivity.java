package cn.zhudi.mvpdemo;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import cn.zhudi.mvpdemo.base.BaseMVPActivity;
import cn.zhudi.mvpdemo.mvp.presenter.MainPresenter;
import cn.zhudi.mvpdemo.mvp.view.MainView;

public class MainActivity extends BaseMVPActivity<MainView, MainPresenter> implements MainView {

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        presenter.setVersion();
    }

    @Bind(R.id.tv)
    TextView tv;

    @Override
    public void showVersion(String version) {
        tv.setText(version);
    }
}
