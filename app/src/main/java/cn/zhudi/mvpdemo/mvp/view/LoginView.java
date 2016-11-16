package cn.zhudi.mvpdemo.mvp.view;

import cn.zhudi.mvpdemo.base.IBaseView;
import cn.zhudi.mvpdemo.entity.User;


public interface LoginView extends IBaseView {
    /**
     * 登录成功
     *
     * @param data 成功返回的数据
     */
    void loginSuccess(User data);


    /**
     * 获取用户名
     *
     * @return 用户名
     */
    String getUserName();

    /**
     * 获取密码
     *
     * @return 密码
     */
    String getUserPassword();

}
