package cn.zhudi.mvpdemo.mvp.model.impl;

import android.content.Context;
import android.text.TextUtils;

import cn.zhudi.mvpdemo.entity.User;
import cn.zhudi.mvpdemo.impl.OnRequestListener;
import cn.zhudi.mvpdemo.mvp.model.ILoginRequestBiz;


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
        //模拟请求成功返回
        listener.onSuccess(new User());
    }
}
