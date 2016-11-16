package cn.zhudi.mvpdemo.mvp.view;

import cn.zhudi.mvpdemo.base.IBaseView;
import cn.zhudi.mvpdemo.entity.User;

/**
 * 类名称：android_mvp_demo
 * 类描述：
 * 创建人：zhudi
 * 创建时间：2016/11/16 11:20
 * 修改人：${user}
 * 修改时间：${date} ${time}
 * 修改备注：
 */
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
