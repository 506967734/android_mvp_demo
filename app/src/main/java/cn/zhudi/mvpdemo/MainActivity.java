package cn.zhudi.mvpdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zd.layout.LoadingLayout;

import butterknife.Bind;
import butterknife.OnClick;
import cn.zhudi.mvpdemo.base.BaseMVPActivity;
import cn.zhudi.mvpdemo.mvp.presenter.MainPresenter;
import cn.zhudi.mvpdemo.mvp.view.MainView;
import cn.zhudi.mvpdemo.ui.ADDialogFragment;
import cn.zhudi.mvpdemo.utils.ToastSingle;

public class MainActivity extends BaseMVPActivity<MainView, MainPresenter> implements MainView {
    @Bind(R.id.loading)
    LoadingLayout loadingLayout;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void initView() {
        presenter.setVersion();
        loadingLayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "retry" , Toast.LENGTH_LONG).show();
            }
        },true);
        loadingLayout.setLoadingProgressBar(R.drawable.common_loading);
//        loadingLayout.showContent();
        //loadingLayout.showLoading();
        loadingLayout.showError();
    }

    @Bind(R.id.tv)
    TextView tv;

    @Override
    public void showVersion(String version) {
        tv.setText(version);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.btn)
    public void btnClick(){
        ADDialogFragment dialogFragment = new ADDialogFragment();
        dialogFragment.show(getFragmentManager(),"android");
        dialogFragment.setOnDialogListener(new ADDialogFragment.OnDialogListener() {
            @Override
            public void onDialogClick(String person) {
                ToastSingle.showToast(getApplicationContext(),person);
            }
        });
    }


}
