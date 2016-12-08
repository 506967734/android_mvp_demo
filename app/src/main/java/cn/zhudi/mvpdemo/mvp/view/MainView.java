package cn.zhudi.mvpdemo.mvp.view;

import cn.zhudi.mvpdemo.base.IBaseView;

public interface MainView extends IBaseView{
    /**
     * 显示软件版本号
     */
    void showVersion(String version);
}