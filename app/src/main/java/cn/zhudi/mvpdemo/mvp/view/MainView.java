package cn.zhudi.mvpdemo.mvp.view;

import cn.zhudi.mvpdemo.base.IBaseView;

/**
 * 类名称：android_mvp_demo
 * 类描述：
 * 创建人：zhudi
 * 创建时间：2016/11/16 13:47
 * 修改人：${user}
 * 修改时间：${date} ${time}
 * 修改备注：
 */
public interface MainView extends IBaseView{
    /**
     * 显示软件版本号
     */
    void showVersion(String version);
}
