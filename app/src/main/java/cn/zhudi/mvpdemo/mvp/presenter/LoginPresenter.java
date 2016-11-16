package cn.zhudi.mvpdemo.mvp.presenter;

import android.content.Context;

import cn.zhudi.mvpdemo.base.BasePresenter;
import cn.zhudi.mvpdemo.entity.User;
import cn.zhudi.mvpdemo.impl.OnRequestListener;
import cn.zhudi.mvpdemo.mvp.model.ILoginRequestBiz;
import cn.zhudi.mvpdemo.mvp.model.impl.LoginRequestBizImpl;
import cn.zhudi.mvpdemo.mvp.view.LoginView;

/**
 * 类名称：android_mvp_demo
 * 类描述：
 * 创建人：zhudi
 * 创建时间：2016/11/16 11:20
 * 修改人：${user}
 * 修改时间：${date} ${time}
 * 修改备注：
 */
public class LoginPresenter extends BasePresenter<LoginView> {
    private Context context;
    private ILoginRequestBiz requestBiz;

    public LoginPresenter(Context context) {
        this.context = context;
        requestBiz = new LoginRequestBizImpl(context);
    }


    public void login(String userName, String userPassword) {
        if (requestBiz.isEmpty(userName)) {
            mView.showMessage("请输入用户名");
            return;
        }
        if (requestBiz.isEmpty(userPassword)) {
            mView.showMessage("请输入密码");
            return;
        }
        requestBiz.requestForData(userName, userPassword, new OnRequestListener<User>() {
            @Override
            public void onSuccess(User data) {
                mView.loginSuccess(data);
            }

            @Override
            public void onFailed(String msg) {
                mView.showMessage(msg);
            }
        });
    }
}
