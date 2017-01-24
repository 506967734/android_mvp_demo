package cn.zhudi.mvpdemo.mvp.model.impl;

import android.content.Context;
import android.text.TextUtils;

import com.loopj.android.http.RequestParams;

import cn.zhudi.mvpdemo.entity.ModelParser;
import cn.zhudi.mvpdemo.entity.User;
import cn.zhudi.mvpdemo.impl.OnRequestListener;
import cn.zhudi.mvpdemo.mvp.model.ILoginRequestBiz;
import cn.zhudi.mvpdemo.network.NetworkService;
import cn.zhudi.mvpdemo.network.URLs;


public class LoginRequestBizImpl implements ILoginRequestBiz {
    private Context context;

    public LoginRequestBizImpl(Context context) {
        this.context = context;
    }

    @Override
    public boolean isEmpty(String name) {
        if (TextUtils.isEmpty(name)) {
            return true;
        }
        return false;
    }

    @Override
    public void requestForData(String userName, String userPassword, final OnRequestListener listener) {
        String url = URLs.LOGIN_URL;
        RequestParams params = new RequestParams();
        params.add("companyId", userName);
        params.add("userCode", userName);
        params.add("password", userPassword);
        NetworkService<User> service = new NetworkService<>("login", new NetworkService.NetworkListener<User>() {
            @Override
            public void onReceiveResult(User resultData) {
                if (resultData != null) {
                    listener.onSuccess(resultData);
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                if (context != null) {
                    listener.onFailed(code, msg);
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void reLoginResult() {
            }
        });
        service.configContext(context);
        service.configAnalyze(new ModelParser(User.class));
        service.postSubmit(url, params);
    }
}
