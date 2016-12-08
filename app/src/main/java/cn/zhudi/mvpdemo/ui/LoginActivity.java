package cn.zhudi.mvpdemo.ui;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import cn.zhudi.mvpdemo.MainActivity;
import cn.zhudi.mvpdemo.R;
import cn.zhudi.mvpdemo.base.BaseMVPActivity;
import cn.zhudi.mvpdemo.entity.User;
import cn.zhudi.mvpdemo.mvp.presenter.LoginPresenter;
import cn.zhudi.mvpdemo.mvp.view.LoginView;

/**
 * 登陆界面
 */
public class LoginActivity extends BaseMVPActivity<LoginView, LoginPresenter> implements LoginView {

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
    }

    @OnClick(R.id.btnLogin)
    public void loginClick() {
        presenter.login(getUserName(), getUserPassword());
    }

    @Bind(R.id.tvShow)
    TextView tvShow;
    @Override
    public void loginSuccess(User data) {
        tvShow.setText("success");
        startSelfActivity(this, MainActivity.class);
    }

    @Bind(R.id.etUserName)
    EditText etUserName;

    @Override
    public String getUserName() {
        return etUserName.getText().toString().trim();
    }

    @Bind(R.id.etUserPassword)
    EditText etPassword;

    @Override
    public String getUserPassword() {
        return etPassword.getText().toString().trim();
    }
}
