package cn.zhudi.mvpdemo.mvp.model;

import cn.zhudi.mvpdemo.base.IBaseRequestBiz;
import cn.zhudi.mvpdemo.entity.User;
import cn.zhudi.mvpdemo.impl.OnRequestListener;

public interface ILoginRequestBiz extends IBaseRequestBiz {

    /**
     * 调用登录接口
     *  @param userName     用户名
     * @param userPassword 密码已加密
     * @param listener     返回的监听
     */
    void requestForData(String userName, String userPassword, OnRequestListener<User> listener);
}
